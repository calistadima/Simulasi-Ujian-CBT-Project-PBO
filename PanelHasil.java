import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

public class PanelHasil extends JPanel {
    private JLabel lblScore;
    private JTextArea taDetail;
    private JButton btnTutup;

    public PanelHasil() {
        setLayout(new BorderLayout());
        // TODO: Setup layout dengan title "HASIL UJIAN"
        // TODO: Buat label untuk skor
        // TODO: Buat text area untuk detail jawaban
        // TODO: Buat button tutup
    }

    public void showResult(int score, String detail) {
        // TODO: Update label skor
        // TODO: Update text area dengan detail
    }

    public void setCloseAction(ActionListener l) {
        // TODO: Set action listener untuk button tutup
    }
}