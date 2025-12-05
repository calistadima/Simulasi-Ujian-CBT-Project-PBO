import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class MainFrame extends JFrame {
    private UjianController controller;
    private PanelSoal panelSoal;
    private JPanel jenisPanel;
    private List<JButton> jenisButtons = new ArrayList<>();
    private JPanel rightPanel; // contains navPanel + jenisScroll
    private JPanel navPanel;
    private List<JButton> navButtons = new ArrayList<>();
    private int currentNavIndex = -1;
    private JPanel greetingPanel;
    private boolean examStarted = false;
    private String expectedExamPassword; // legacy: currently unused (kept for backward compatibility)
    private boolean greetingVerified = false;
    private JScrollPane jenisScroll;
    private static final String PROCTOR_PASSWORD = "PBOAMAN";
    private JPanel header;

    public MainFrame(UjianController controller) {
        this.controller = controller;
        controller.setView(this);
        initUI();
    }

    private void initUI() {
        setTitle("CBT Simple");
        // Use DO_NOTHING_ON_CLOSE to perform graceful shutdown
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // disable UI quickly
                setEnabled(false);
                // perform shutdown in background so we don't block EDT
                new Thread(() -> {
                    controller.shutdown();
                    // dispose on EDT
                    SwingUtilities.invokeLater(() -> {
                        dispose();
                        System.exit(0);
                    });
                }, "shutdown-thread").start();
            }
        });
        setSize(900, 500);
        setLayout(new BorderLayout());

        // Header: title and global timer
        header = new JPanel(new BorderLayout());
        header.setBorder(BorderFactory.createEmptyBorder(8,12,8,12));
        header.setBackground(UIStyle.PALETTE_LIGHT);
        JLabel title = new JLabel("UJIAN COMPUTER BASED TEST");
        title.setFont(UIStyle.TITLE_FONT);
        title.setForeground(UIStyle.PRIMARY_BLUE);
        // place title in its own centered panel so it stays visually centered
        JPanel centerTitle = new JPanel(new GridBagLayout());
        centerTitle.setOpaque(false);
        centerTitle.add(title);
        header.add(centerTitle, BorderLayout.CENTER);
        // No global timer: per-jenis timer shown in the soal panel.
        add(header, BorderLayout.NORTH);

        panelSoal = new PanelSoal();
        panelSoal.setNextAction(e -> onNext());
        add(panelSoal, BorderLayout.CENTER);

        jenisPanel = new JPanel();
        jenisPanel.setLayout(new BoxLayout(jenisPanel, BoxLayout.Y_AXIS));
        jenisPanel.setBackground(UIStyle.PALETTE_LIGHT);
        // keep plain panel here; the titled border will be provided by the enclosing box
        jenisPanel.setBorder(BorderFactory.createEmptyBorder(6,6,6,6));
        jenisPanel.setPreferredSize(new Dimension(320, 260));

        // next action is handled by PanelSoal

        // build jenis buttons
        for (int i = 0; i < controller.getJenisList().size(); i++) {
            JenisSoal j = controller.getJenisList().get(i);
            JButton b = new JButton(j.namaJenis);
            final int idx = i;
            b.addActionListener(ev -> handleJenisSelection(idx));
            b.setBackground(UIStyle.LIGHT_BLUE);
            b.setForeground(Color.WHITE);
            b.setFocusPainted(false);
            b.setFont(UIStyle.HEADER_FONT.deriveFont(14f));
            b.setPreferredSize(new Dimension(260, 44));
            b.setMaximumSize(new Dimension(260, 44));
            b.setAlignmentX(Component.CENTER_ALIGNMENT);
            int soalCount = (j.daftarSoal == null) ? 0 : j.daftarSoal.size();
            if (soalCount <= 0) {
                j.completed = true;
                b.setBackground(new Color(0xBDBDBD));
                b.setEnabled(false);
                b.setText(j.namaJenis + " (no soal)");
            } else if (i == 0) {
                b.setEnabled(true); // hanya jenis pertama yang enabled
            } else {
                b.setEnabled(false);
            }
            jenisPanel.add(Box.createVerticalStrut(6));
            jenisPanel.add(b);
            jenisButtons.add(b);
        }

        // visual padding (no glue so panel keeps compact height)

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void handleJenisSelection(int idx) {
        // require Start first
        if (!examStarted) {
            JOptionPane.showMessageDialog(this, "Tekan tombol Start terlebih dahulu untuk memilih jenis soal.", "Informasi", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // if a jenis already in progress, disallow switching
        if (controller.getCurrentJenis() >= 0) {
            JOptionPane.showMessageDialog(this, "Selesaikan jenis soal yang sedang dikerjakan terlebih dahulu.", "Informasi", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // if this jenis already completed, ignore silently (no alert)
        if (controller.getJenisList().get(idx).completed) {
            return;
        }

        // Move navigation to east and show soal area
        moveNavToEast();
        // ensure panelSoal is visible in center
        getContentPane().removeAll();
        add(header, BorderLayout.NORTH);
        add(panelSoal, BorderLayout.CENTER);
        add(rightPanel, BorderLayout.EAST);
        revalidate();
        repaint();

        // disable all jenis buttons to prevent switching while this jenis is active
        for (int i = 0; i < jenisButtons.size(); i++) {
            jenisButtons.get(i).setEnabled(false);
        }

        // build question navigation for this jenis (show numbers)
        int total = controller.getJenisList().get(idx).daftarSoal.size();
        createQuestionNav(total);

        // start the selected jenis first so controller.getCurrentJenis() reflects active
        controller.startJenis(idx);

        // visual: mark jenis buttons (active vs greyed) AFTER starting the jenis
        setJenisButtonsVisualState(idx);
    }

    private void startExamInternally() {
        if (examStarted) return;
        // remove greeting panel if present
        if (greetingPanel != null) {
            getContentPane().remove(greetingPanel);
            greetingPanel = null;
        }
        // Show only the jenis list in the main center area. The actual question area
        // and timers will appear only after the user chooses a jenis.
        JPanel centerHolder = new JPanel(new java.awt.GridBagLayout());
        centerHolder.setBackground(UIStyle.PALETTE_LIGHT);
        java.awt.GridBagConstraints gbc = new java.awt.GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = java.awt.GridBagConstraints.CENTER;
        centerHolder.add(jenisPanel, gbc);
        getContentPane().add(centerHolder, BorderLayout.CENTER);

        // no global timer to start here; per-jenis timers start when a jenis is selected

        // ensure header doesn't show global timer yet; it will be added when jenis starts
        examStarted = true;
        revalidate();
        repaint();
    }

    private void moveNavToEast() {
        // if jenisPanel is already attached somewhere, remove it
        if (jenisPanel.getParent() != null) {
            Container p = jenisPanel.getParent();
            p.remove(jenisPanel);
        }
        // keep the area compact: base height on jenisPanel preferred size
        int panelH = jenisPanel.getPreferredSize().height;
        int height = Math.min(getHeight() - 120, panelH + 40); // margin + avoid full-height

        // build rightPanel as a vertical column: top navPanel, spacer, jenis box beneath
        rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBackground(UIStyle.PALETTE_LIGHT);
        rightPanel.setBorder(BorderFactory.createEmptyBorder(8,8,8,8));

        if (navPanel == null) {
            navPanel = new JPanel();
            navPanel.setBackground(UIStyle.PALETTE_LIGHT);
        }
        navPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(UIStyle.PRIMARY_BLUE), "Nomor Soal"));

        // attempt to read preferred height computed earlier by createQuestionNav
        Dimension navPref = navPanel.getPreferredSize();
        if (navPref == null || navPref.height <= 0) navPref = new Dimension(240, 120);
        // make the nav area wider so the grid doesn't compress
        navPanel.setPreferredSize(new Dimension(300, navPref.height));
        navPanel.setMaximumSize(new Dimension(320, navPref.height + 24));

        // Wrap navPanel into a holder to keep centered content
        JPanel navHolder = new JPanel(new BorderLayout());
        navHolder.setBackground(UIStyle.PALETTE_LIGHT);
        navHolder.add(navPanel, BorderLayout.CENTER);
        navHolder.setMaximumSize(new Dimension(320, navPref.height + 32));

        // jenis container with titled border so it looks like a separate box beneath nav
        JPanel jenisBox = new JPanel(new BorderLayout());
        jenisBox.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(UIStyle.PRIMARY_BLUE), "Jenis Soal"));
        jenisBox.setBackground(UIStyle.PALETTE_LIGHT);
        // add the jenisPanel directly (no scrollbars)
        JPanel jenisHolder = new JPanel(new BorderLayout());
        jenisHolder.setBackground(UIStyle.PALETTE_LIGHT);
        jenisHolder.add(jenisPanel, BorderLayout.CENTER);
        jenisHolder.setPreferredSize(new Dimension(300, Math.max(180, height)));
        jenisHolder.setMaximumSize(new Dimension(320, Math.max(180, height + 20)));
        jenisBox.add(jenisHolder, BorderLayout.CENTER);

        // compose rightPanel
        rightPanel.add(navHolder);
        rightPanel.add(Box.createVerticalStrut(8));
        rightPanel.add(jenisBox);
        rightPanel.add(Box.createVerticalGlue());

        // give preferred width for the whole column (wider so center area aligns with mockup)
        rightPanel.setPreferredSize(new Dimension(360, getHeight() - 80));

        add(rightPanel, BorderLayout.EAST);
    }

    public void showGreeting(String exam, String expectedPassword) {
        this.expectedExamPassword = expectedPassword;
        this.greetingVerified = false;
        // build greeting panel
        greetingPanel = new JPanel(new BorderLayout(10,10));
        JPanel top = new JPanel(new BorderLayout());
        JLabel lbl = new JLabel("Selamat datang, peserta " + exam);
        lbl.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        top.add(lbl, BorderLayout.NORTH);
        greetingPanel.add(top, BorderLayout.NORTH);


        // center area: welcome, instructions, and exam info (centered)
        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        center.setBackground(UIStyle.PALETTE_LIGHT);

        JLabel instr = new JLabel("Masukkan password ujian yang diberikan pengawas.");
        instr.setFont(UIStyle.BODY_FONT);
        instr.setAlignmentX(Component.CENTER_ALIGNMENT);
        instr.setBorder(BorderFactory.createEmptyBorder(18,10,4,10));
        center.add(instr);

        JLabel instr2 = new JLabel("Untuk mulai ujian, tunggu pengawas ujian memberikan password ujian.");
        instr2.setFont(UIStyle.BODY_FONT);
        instr2.setAlignmentX(Component.CENTER_ALIGNMENT);
        instr2.setBorder(BorderFactory.createEmptyBorder(0,10,8,10));
        center.add(instr2);

        // Informasi ujian
        int jenisCount = controller.getJenisList().size();
        int soalPerJenis = jenisCount > 0 ? controller.getJenisList().get(0).daftarSoal.size() : 0;
        JLabel info1 = new JLabel(String.format("Jumlah jenis soal: %d", jenisCount));
        info1.setFont(UIStyle.BODY_FONT);
        info1.setAlignmentX(Component.CENTER_ALIGNMENT);
        center.add(info1);
        JLabel info2 = new JLabel(String.format("Jumlah soal per jenis: %d", soalPerJenis));
        info2.setFont(UIStyle.BODY_FONT);
        info2.setAlignmentX(Component.CENTER_ALIGNMENT);
        center.add(info2);
        // remove per-question and total time info from greeting
        JLabel info5 = new JLabel("Pengerjaan dilakukan secara SEKUENSIAL, tidak bisa memilih urutan jenis soal.");
        info5.setFont(UIStyle.BODY_FONT);
        info5.setAlignmentX(Component.CENTER_ALIGNMENT);
        info5.setBorder(BorderFactory.createEmptyBorder(8,10,8,10));
        center.add(info5);


        // prominent Start button centered
        JButton btnStart = new JButton("Start");
        btnStart.setFont(UIStyle.HEADER_FONT);
        btnStart.setBackground(UIStyle.ACCENT);
        btnStart.setForeground(Color.WHITE);
        btnStart.setOpaque(true);
        btnStart.setFocusPainted(false);
        btnStart.setPreferredSize(new Dimension(160, 56));
        btnStart.setMaximumSize(new Dimension(180, 60));
        btnStart.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnStart.addActionListener(e -> {
            // require verification before starting
            if (!greetingVerified) {
                // keep prompting until correct password entered or user cancels
                while (!greetingVerified) {
                    JPanel passPanel = new JPanel(new java.awt.GridBagLayout());
                    passPanel.setBackground(UIStyle.PALETTE_LIGHT);
                    java.awt.GridBagConstraints c = new java.awt.GridBagConstraints();
                    c.insets = new java.awt.Insets(6,6,6,6);
                    c.gridx = 0; c.gridy = 0; c.anchor = java.awt.GridBagConstraints.WEST;
                    JLabel lblPass = new JLabel("Masukkan password ujian:");
                    lblPass.setFont(UIStyle.BODY_FONT);
                    passPanel.add(lblPass, c);

                    c.gridy = 1; c.fill = java.awt.GridBagConstraints.HORIZONTAL; c.weightx = 1.0;
                    JPasswordField pf = new JPasswordField();
                    pf.setFont(UIStyle.BODY_FONT);
                    pf.setPreferredSize(new Dimension(360, 36));
                    pf.setBorder(BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(new Color(0xDD,0xDD,0xDD), 1),
                            BorderFactory.createEmptyBorder(6,8,6,8)));
                    pf.setEchoChar('\u2022');
                    passPanel.add(pf, c);

                    int ok = JOptionPane.showConfirmDialog(this, passPanel, "Verifikasi Pengawas", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                    if (ok != JOptionPane.OK_OPTION) {
                        // user canceled — do not start exam
                        return;
                    }
                    String entered = new String(pf.getPassword());
                    if (PROCTOR_PASSWORD.equals(entered)) {
                        greetingVerified = true;
                        break;
                    } else {
                        // Hanya tampilkan pesan password salah, tidak alert lain
                        JOptionPane.showMessageDialog(this, "Password ujian salah.", "Verifikasi Gagal", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
            // pastikan layout jenis/ujian diinisialisasi dulu, lalu langsung mulai jenis pertama
            if (!examStarted) startExamInternally();
            handleJenisSelection(0);
        });

        center.add(btnStart);

        greetingPanel.add(center, BorderLayout.CENTER);

        // show greeting in center (replace panelSoal)
        getContentPane().remove(panelSoal);
        add(greetingPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    public void showSoal(Soal s, int nomor, int total) {
        panelSoal.showSoal(s, nomor, total);
        // update nav highlight (nomor is 1-based)
        updateNavCurrent(nomor - 1);
    }

    public void updateTimerLabel(int seconds) {
        panelSoal.updateTimerLabel(seconds);
    }

    public void showJenisFinished(int secondsRemaining) {
        panelSoal.showFinishedJenis(secondsRemaining);
    }

    public void lockOptions() {
        panelSoal.lockOptions();
    }

    private void onNext() {
        // If current jenis already finished (all soal answered), this button acts as SUBMIT
        if (controller.isCurrentJenisFinished()) {
            int ok = JOptionPane.showConfirmDialog(this, "Submit dan lanjut ke jenis soal selanjutnya?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (ok == JOptionPane.YES_OPTION) {
                controller.finishJenisBySubmit();
            }
            return;
        }

        // find selected
        Character pilih = panelSoal.getSelected();
        if (pilih == null) {
            JOptionPane.showMessageDialog(this, "Silakan pilih jawaban terlebih dahulu!", "Peringatan", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // find current soal id from controller state
        int cj = controller.getCurrentJenis();
        if (cj < 0) return;
        JenisSoal js = controller.getJenisList().get(cj);
        int idx = controller.getCurrentIndexSoal();
        if (idx >= js.daftarSoal.size()) return;
        int idSoal = js.daftarSoal.get(idx).idSoal;

        // mark current nav button as answered (green)
        int idxSoal = controller.getCurrentIndexSoal();
        markNavAnswered(idxSoal);
        controller.submitAnswer(idSoal, pilih);
    }

    public void markJenisCompleted(int jenisIdx) {
        if (jenisIdx >= 0 && jenisIdx < jenisButtons.size()) {
            JButton b = jenisButtons.get(jenisIdx);
            b.setEnabled(false);
            b.setText(controller.getJenisList().get(jenisIdx).namaJenis + " (done)");
            b.setBackground(new Color(0xBDBDBD));
            // Cari jenis berikutnya yang belum completed
            int next = -1;
            for (int i = jenisIdx + 1; i < jenisButtons.size(); i++) {
                if (!controller.getJenisList().get(i).completed) {
                    next = i;
                    break;
                }
            }
            if (next >= 0) {
                // Otomatis lanjut ke jenis berikutnya
                jenisButtons.get(next).setEnabled(true);
                handleJenisSelection(next);
            } else {
                // Semua selesai, tampilkan hasil (controller akan handle)
                // Tidak perlu kembali ke menu jenis
            }
        }
    }

    // --- Navigation helpers ---
    private void createQuestionNav(int total) {
        navButtons.clear();
        navPanel.removeAll();
        int cols = 5;
        int rows = (total + cols - 1) / cols;
        JPanel grid = new JPanel(new GridLayout(rows, cols, 8, 8));
        grid.setBackground(UIStyle.PALETTE_LIGHT);
        for (int i = 0; i < rows * cols; i++) {
            if (i < total) {
                JButton nb = new JButton(String.valueOf(i + 1));
                nb.setEnabled(false); // read-only navigation
                nb.setOpaque(true);
                nb.setBorderPainted(false);
                nb.setBackground(new Color(0xBDBDBD)); // default grey (locked)
                nb.setForeground(Color.WHITE);
                nb.setFocusPainted(false);
                nb.setPreferredSize(new Dimension(40, 36));
                grid.add(nb);
                navButtons.add(nb);
            } else {
                JPanel empty = new JPanel();
                empty.setBackground(UIStyle.PALETTE_LIGHT);
                grid.add(empty);
            }
        }
        navPanel.setLayout(new BorderLayout());
        JPanel inner = new JPanel(new FlowLayout(FlowLayout.CENTER));
        inner.setBackground(UIStyle.PALETTE_LIGHT);
        inner.add(grid);
        navPanel.add(inner, BorderLayout.CENTER);
        int prefH = rows * 44 + 40;
        navPanel.setPreferredSize(new Dimension(240, prefH));
        navPanel.revalidate();
        navPanel.repaint();
        currentNavIndex = -1;
    }

    private void updateNavCurrent(int idx) {
        if (idx < 0 || idx >= navButtons.size()) return;
        // reset previous highlight if any
        if (currentNavIndex >= 0 && currentNavIndex < navButtons.size()) {
            JButton prev = navButtons.get(currentNavIndex);
            // if it was answered (green) keep it green, else grey
            // we keep green state via markNavAnswered
            // only reset if not marked answered
            if (!prev.getBackground().equals(new Color(0x388E3C))) {
                prev.setBackground(new Color(0xBDBDBD));
            }
        }
        JButton cur = navButtons.get(idx);
        cur.setBackground(UIStyle.ACCENT); // use accent for current
        currentNavIndex = idx;
    }

    private void markNavAnswered(int idx) {
        if (idx < 0 || idx >= navButtons.size()) return;
        JButton b = navButtons.get(idx);
        b.setBackground(new Color(0x388E3C)); // green
    }

    private void setJenisButtonsVisualState(int activeIdx) {
        // If a jenis is currently in progress, lock others and highlight the active one
        int current = controller.getCurrentJenis();
        for (int i = 0; i < jenisButtons.size(); i++) {
            JButton jb = jenisButtons.get(i);
            if (controller.getJenisList().get(i).completed) {
                jb.setBackground(new Color(0xBDBDBD));
                jb.setEnabled(false);
                jb.setText(controller.getJenisList().get(i).namaJenis + " (done)");
            } else if (i == current) {
                jb.setBackground(UIStyle.ACCENT);
                jb.setEnabled(true);
                jb.setText(controller.getJenisList().get(i).namaJenis);
            } else {
                jb.setBackground(new Color(0xBDBDBD));
                jb.setEnabled(false);
                jb.setText(controller.getJenisList().get(i).namaJenis);
            }
        }
    }

    public void showResult(java.util.List<ResultPerJenis> perJenisResults, double nilaiSkala100, int totalBenar, int totalSalah, int totalKosong) {
        // Stop timers so they do not continue while viewing results
        if (controller != null) {
            controller.stopTimers();
        }
        // Replace the entire center area (whatever is shown) with PanelHasil
        Container cp = getContentPane();
        cp.removeAll();
        cp.add(header, BorderLayout.NORTH);
        rightPanel = null;
        PanelHasil hasil = new PanelHasil();
        hasil.showResult(perJenisResults, nilaiSkala100, totalBenar, totalSalah, totalKosong);
        hasil.setCloseAction(e -> {
            dispose();
            System.exit(0);
        });
        cp.add(hasil, BorderLayout.CENTER);
        revalidate();
        repaint();
    }
}
