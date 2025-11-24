import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.Timer;

public class UjianController {
    private List<JenisSoal> jenisList;
    private MainFrame view;
    private int currentJenis = -1;
    private int currentIndexSoal = -1;
    private final int DURATION_PER_SOAL = 20;
    private final ExecutorService saveExecutor = Executors.newSingleThreadExecutor();
    private final JawabanDAO jawabanDao = new JawabanDAO();
    private Timer swingTimer;
    private int remainingSeconds;
    private Timer globalTimer;
    private int globalRemainingSeconds;

    public UjianController(List<JenisSoal> jenisList) {
        this.jenisList = jenisList;
    }

    public void setView(MainFrame view) {
        this.view = view;
        // TODO: Hitung total waktu dari semua jenis soal
        // TODO: Set globalRemainingSeconds
    }

    public List<JenisSoal> getJenisList() {
        return jenisList;
    }

    public int getCurrentJenis() {
        return currentJenis;
    }

    public int getCurrentIndexSoal() {
        return currentIndexSoal;
    }

    public void startJenis(int jenisIdx) {
        // TODO: Validasi index jenis
        // TODO: Set currentJenis dan currentIndexSoal
        // TODO: Load soal pertama
    }

    private void loadCurrentSoal() {
        // TODO: Cek apakah masih ada soal di jenis ini
        // TODO: Jika sudah habis, mark jenis completed
        // TODO: Jika masih ada, tampilkan soal ke view
        // TODO: Start timer per soal
    }

    public void submitAnswer(Integer idSoal, Character pilihan) {
        // TODO: Simpan jawaban ke database (async)
        // TODO: Stop timer
        // TODO: Increment index dan load soal berikutnya
    }

    private void saveAnswerAsync(int idSoal, Character pilihan) {
        // TODO: Submit save task ke executor
        // TODO: Handle SQLException
    }

    public void shutdown() {
        // TODO: Stop semua timer yang berjalan
        // TODO: Tunggu executor selesai menyimpan
        // TODO: Shutdown executor dengan timeout
    }

    private void checkAllDone() {
        // TODO: Cek apakah semua jenis sudah completed
        // TODO: Jika ya, hitung skor dan tampilkan hasil
        // TODO: Gunakan SwingWorker untuk background task
    }

    private void startSwingTimer(int seconds) {
        // TODO: Set remainingSeconds
        // TODO: Buat Timer yang update setiap detik
        // TODO: Jika waktu habis, auto submit dan lanjut
    }

    public void startGlobalTimer() {
        // TODO: Update view dengan waktu global
        // TODO: Buat Timer global yang countdown
        // TODO: Jika habis, paksa selesai dan tampilkan hasil
    }
}
