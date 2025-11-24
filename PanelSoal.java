import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

public class PanelSoal extends JPanel {
    private JLabel lblSoalInfo;
    private JLabel lblPerQuestionTimer;
    private JTextArea taSoalText;
    private JRadioButton rbA, rbB, rbC, rbD;
    private ButtonGroup bg;
    private JButton btnNext;

    public PanelSoal() {
        setLayout(new BorderLayout());
        // TODO: Setup layout dan komponen UI
        // TODO: Buat header dengan info soal dan timer
        // TODO: Buat text area untuk teks soal
        // TODO: Buat radio button untuk pilihan A, B, C, D
        // TODO: Buat button "JAWAB & LANJUT"
    }

    public void showSoal(Soal s, int nomor, int total) {
        // TODO: Clear selection radio button
        // TODO: Enable semua pilihan
        // TODO: Update label info soal
        // TODO: Set text soal dan pilihan
        // TODO: Enable button next
    }

    public void updateTimerLabel(int seconds) {
        // TODO: Update label timer dengan format detik
    }

    public void lockOptions() {
        // TODO: Disable semua radio button
        // TODO: Disable button next
    }

    public Character getSelected() {
        // TODO: Cek radio button mana yang terpilih
        // TODO: Return 'A', 'B', 'C', 'D', atau null
        return null;
    }

    public void setNextAction(ActionListener l) {
        // TODO: Set action listener untuk button next
    }
}


