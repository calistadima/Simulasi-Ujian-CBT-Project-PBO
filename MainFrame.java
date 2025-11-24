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
    private JPanel greetingPanel;
    private boolean examStarted = false;
    private String expectedExamPassword;
    private boolean greetingVerified = false;
    private JScrollPane jenisScroll;
    private static final String PROCTOR_PASSWORD = "PBOAMAN";
    private JLabel globalTimerLabel;
    private JPanel header;

    public MainFrame(UjianController controller) {
        this.controller = controller;
        controller.setView(this);
        initUI();
    }

    private void initUI() {
        // TODO: Set title, size, default close operation
        // TODO: Setup window listener untuk graceful shutdown
        // TODO: Buat header dengan title dan timer label
        // TODO: Buat PanelSoal di center
        // TODO: Buat jenisPanel dengan buttons untuk setiap jenis
        // TODO: Setup action listeners untuk buttons
    }

    private void handleJenisSelection(int idx) {
        // TODO: Validasi examStarted
        // TODO: Cek apakah ada jenis yang sedang dikerjakan
        // TODO: Cek apakah jenis ini sudah completed
        // TODO: Pindahkan navigasi ke east
        // TODO: Disable semua button jenis
        // TODO: Start global timer (jika pertama kali)
        // TODO: Start jenis yang dipilih
    }

    private void startExamInternally() {
        // TODO: Remove greeting panel
        // TODO: Tampilkan jenis panel di center
        // TODO: Set examStarted = true
        // TODO: Revalidate dan repaint
    }

    private void moveNavToEast() {
        // TODO: Pindahkan jenisPanel ke JScrollPane
        // TODO: Add scroll pane ke EAST
        // TODO: Revalidate layout
    }

    public void showGreeting(String exam, String expectedPassword) {
        // TODO: Buat greeting panel dengan welcome message
        // TODO: Buat field untuk input password ujian
        // TODO: Buat tombol Start
        // TODO: Validasi password sebelum start
        // TODO: Replace center dengan greeting panel
    }

    public void showSoal(Soal s, int nomor, int total) {
        // TODO: Delegate ke panelSoal untuk menampilkan soal
    }

    public void updateTimerLabel(int seconds) {
        // TODO: Update label timer per soal
    }

    public void updateGlobalTimerLabel(int seconds) {
        // TODO: Format detik ke MM:SS
        // TODO: Update globalTimerLabel
    }

    public void lockOptions() {
        // TODO: Disable semua pilihan jawaban
    }

    private void onNext() {
        // TODO: Ambil jawaban yang dipilih dari panelSoal
        // TODO: Validasi sudah memilih jawaban
        // TODO: Submit jawaban ke controller
    }

    public void markJenisCompleted(int jenisIdx) {
        // TODO: Disable button jenis yang selesai
        // TODO: Ubah text button jadi "(done)"
        // TODO: Enable button jenis lain yang belum completed
        // TODO: Return view ke jenis selection
        // TODO: Tampilkan message informasi
    }

    public void showResult(int score, String detail) {
        // TODO: Remove panelSoal
        // TODO: Buat dan tampilkan PanelHasil
        // TODO: Set close action untuk keluar aplikasi
    }
}