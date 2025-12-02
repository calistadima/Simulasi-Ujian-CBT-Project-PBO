import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.Timer;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UjianController {
    private List<JenisSoal> jenisList;
    private MainFrame view;
    private int currentJenis = -1;
    private int currentIndexSoal = -1;
    // use timer per-jenis soal (totalWaktu on JenisSoal)
    private final ExecutorService saveExecutor = Executors.newSingleThreadExecutor();
    private final JawabanDAO jawabanDao = new JawabanDAO();
    private Timer jenisTimer;
    private int jenisRemainingSeconds;

    public UjianController(List<JenisSoal> jenisList) {
        this.jenisList = jenisList;
    }

    public void setView(MainFrame view) {
        this.view = view;
        // No global timer: per-jenis timers are used instead.
    }

    public List<JenisSoal> getJenisList() {
        return jenisList;
    }

    // Accessors untuk status saat ini (digunakan oleh view)
    public int getCurrentJenis() {
        return currentJenis;
    }

    public int getCurrentIndexSoal() {
        return currentIndexSoal;
    }

    public void startJenis(int jenisIdx) {
        if (jenisIdx < 0 || jenisIdx >= jenisList.size()) return;
        if (jenisList.get(jenisIdx).completed) return;
        currentJenis = jenisIdx;
        currentIndexSoal = 0;
        // start per-jenis timer. Interpret JenisSoal.totalWaktu as
        // the duration allocated per soal (in seconds). Total jenis =
        // per-soal * number-of-questions. This allows different jenis to
        // have different per-question durations (e.g., 10s/soal, 15s/soal).
        JenisSoal j = jenisList.get(jenisIdx);
        // Treat `totalWaktu` as the total number of seconds allocated for this jenis.
        // The database currently contains total durations (e.g. 50, 75, 60), so use
        // that value directly as the timer length.
        int totalSeconds = Math.max(0, j.totalWaktu);
        startJenisTimer(totalSeconds);
        loadCurrentSoal();
    }

    private void loadCurrentSoal() {
        if (currentJenis < 0) return;
        JenisSoal j = jenisList.get(currentJenis);
        if (currentIndexSoal >= j.daftarSoal.size()) {
            // all questions in this jenis have been answered. Do NOT auto-complete
            // the jenis if timer still running; instead show the finished state and
            // allow user to press SUBMIT to continue, or wait for timer to expire.
            SwingUtilities.invokeLater(() -> view.showJenisFinished(jenisRemainingSeconds));
            return;
        }
        Soal s = j.daftarSoal.get(currentIndexSoal);
        SwingUtilities.invokeLater(() -> view.showSoal(s, currentIndexSoal + 1, j.daftarSoal.size()));
    }

    public boolean isCurrentJenisFinished() {
        if (currentJenis < 0) return false;
        JenisSoal j = jenisList.get(currentJenis);
        return currentIndexSoal >= j.daftarSoal.size();
    }

    public void finishJenisBySubmit() {
        if (currentJenis < 0) return;
        JenisSoal j = jenisList.get(currentJenis);
        // stop timer and mark completed
        if (jenisTimer != null && jenisTimer.isRunning()) jenisTimer.stop();
        j.completed = true;
        int finishedJenis = currentJenis;
        currentJenis = -1;
        currentIndexSoal = -1;
        SwingUtilities.invokeLater(() -> view.markJenisCompleted(finishedJenis));
        checkAllDone();
    }

    public void submitAnswer(Integer idSoal, Character pilihan) {
        // save async
        saveAnswerAsync(idSoal, pilihan);
        // stop timer and go next
        currentIndexSoal++;
        loadCurrentSoal();
    }

    private void saveAnswerAsync(int idSoal, Character pilihan) {
        // submit to single-threaded executor so we can wait for saves on shutdown
        saveExecutor.submit(() -> {
            try {
                jawabanDao.saveAnswer(idSoal, pilihan);
            } catch (SQLException ex) {
                System.err.println("Gagal menyimpan jawaban: " + ex.getMessage());
            }
        });
    }

    /**
     * Shutdown controller: stop running timers and wait for pending saves to finish.
     * Should be called from a background thread (not the EDT) to avoid blocking the UI.
     */
    public void shutdown() {
        if (jenisTimer != null && jenisTimer.isRunning()) jenisTimer.stop();
        saveExecutor.shutdown();
        try {
            if (!saveExecutor.awaitTermination(5, TimeUnit.SECONDS)) {
                // try forceful shutdown
                saveExecutor.shutdownNow();
            }
        } catch (InterruptedException e) {
            saveExecutor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Stop any running timers (used when showing final results so timers stop ticking).
     * This does not shut down the save executor — it only stops UI timers.
     */
    public void stopTimers() {
        if (jenisTimer != null && jenisTimer.isRunning()) {
            jenisTimer.stop();
        }
    }

    private void checkAllDone() {
        boolean all = jenisList.stream().allMatch(j -> j.completed);
        if (all) {
            // Prompt the user immediately that all soal are finished and only compute
            // detailed results if the user chooses to view them. This avoids showing
            // the detailed result screen automatically behind the dialog.
            SwingUtilities.invokeLater(() -> {
                Object[] options = new Object[]{"Lihat", "Tutup"};
                int sel = JOptionPane.showOptionDialog(view,
                        "Selamat — semua soal telah selesai!\nBerikut hasil ujian Anda.",
                        "Informasi",
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.INFORMATION_MESSAGE,
                        null,
                        options,
                        options[0]);
                if (sel == 0) { // user chose to view results
                    new SwingWorker<Void, Void>() {
                            private int totalBenar = 0;
                            private int totalSalah = 0;
                            private int totalKosong = 0;
                            private double nilaiSkala100 = 0.0;
                            private java.util.List<ResultPerJenis> perJenisResults = new java.util.ArrayList<>();

                            @Override
                            protected Void doInBackground() throws Exception {
                                try {
                                    java.util.List<AnswerRecord> rows = jawabanDao.fetchAllForScoring();
                                    // map idJenis -> [benar, salah, kosong]
                                    java.util.Map<Integer, int[]> stats = new java.util.HashMap<>();
                                    for (AnswerRecord r : rows) {
                                        int idJen = r.idJenis;
                                        int[] a = stats.computeIfAbsent(idJen, k -> new int[3]);
                                        if (r.jawabanUser == null) {
                                            a[2]++; // kosong
                                            totalKosong++;
                                        } else if (Character.toUpperCase(r.jawabanUser) == Character.toUpperCase(r.kunci)) {
                                            a[0]++; // benar
                                            totalBenar++;
                                        } else {
                                            a[1]++; // salah
                                            totalSalah++;
                                        }
                                    }
                                    // compute total questions and per-jenis values based on loaded jenisList
                                    int totalQuestions = 0;
                                    for (JenisSoal j : jenisList) {
                                        int num = (j.daftarSoal == null) ? 0 : j.daftarSoal.size();
                                        totalQuestions += num;
                                        int[] a = stats.getOrDefault(j.idJenis, new int[3]);
                                        int benar = a[0];
                                        int salah = a[1];
                                        int kosong = a[2];
                                        double nilaiJenis = 0.0;
                                        if (num > 0) {
                                            nilaiJenis = (benar * 100.0) / num; // per-jenis percentage on 100
                                        }
                                        perJenisResults.add(new ResultPerJenis(j.idJenis, j.namaJenis, benar, salah, kosong, nilaiJenis));
                                    }
                                    if (totalQuestions <= 0) totalQuestions = Math.max(1, jenisList.size() * 5);
                                    // compute overall nilai on scale 100, proportional to correct answers
                                    nilaiSkala100 = (totalBenar * 100.0) / totalQuestions;
                                } catch (SQLException ex) {
                                    System.err.println("Gagal menghitung skor: " + ex.getMessage());
                                }
                                return null;
                            }

                            @Override
                            protected void done() {
                                view.showResult(perJenisResults, nilaiSkala100, totalBenar, totalSalah, totalKosong);
                            }
                        }.execute();
                }
                // if user chose "Tutup" (or closed the dialog) we do nothing here;
                // the application remains on the jenis selection / navigation state.
            });
        }
    }

    private void startJenisTimer(int seconds) {
        // ensure any previous jenis timer is stopped
        if (jenisTimer != null && jenisTimer.isRunning()) jenisTimer.stop();
        jenisRemainingSeconds = seconds;
        // update immediately
        SwingUtilities.invokeLater(() -> view.updateTimerLabel(jenisRemainingSeconds));
        jenisTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jenisRemainingSeconds--;
                SwingUtilities.invokeLater(() -> view.updateTimerLabel(jenisRemainingSeconds));
                if (jenisRemainingSeconds <= 0) {
                    jenisTimer.stop();
                    // time up for this jenis: lock options and mark remaining as unanswered
                    SwingUtilities.invokeLater(() -> view.lockOptions());
                    if (currentJenis >= 0) {
                        JenisSoal j = jenisList.get(currentJenis);
                        // save null answers for all remaining questions in this jenis
                        for (int k = currentIndexSoal; k < j.daftarSoal.size(); k++) {
                            int idSoal = j.daftarSoal.get(k).idSoal;
                            saveAnswerAsync(idSoal, null);
                        }
                        j.completed = true;
                        int finishedJenis = currentJenis;
                        currentJenis = -1;
                        currentIndexSoal = -1;
                        SwingUtilities.invokeLater(() -> view.markJenisCompleted(finishedJenis));
                        checkAllDone();
                    }
                }
            }
        });
        jenisTimer.setInitialDelay(1000);
        jenisTimer.start();
    }
}
