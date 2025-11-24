import java.sql.SQLException;
import java.util.List;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // TODO: Buat UserDAO
                // TODO: Tampilkan LoginDialog
                // TODO: Jika login dibatalkan, exit
                // TODO: Load soal dari SoalDAO
                // TODO: Buat UjianController dengan data soal
                // TODO: Buat MainFrame dan tampilkan greeting
                // TODO: Set frame visible
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Gagal memuat soal: " + ex.getMessage());
            }
        });
    }
}