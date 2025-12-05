import java.sql.SQLException;
import java.util.List;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // show login dialog first
                UserDAO userDao = new UserDAO();
                LoginDialog ld = new LoginDialog(null, userDao);
                String exam = ld.showDialog();
                if (exam == null) {
                    // user closed or cancelled login
                    System.out.println("Login cancelled; exiting.");
                    System.exit(0);
                    return;
                }

                SoalDAO dao = new SoalDAO();
                List<JenisSoal> jenis = dao.loadAll();
                // DEBUG: print loaded counts to console to verify DB content at runtime
                System.out.println("Loaded jenis count: " + jenis.size());
                for (JenisSoal j : jenis) {
                    System.out.println(String.format("Jenis[id=%d,name=%s] -> %d soal", j.idJenis, j.namaJenis, (j.daftarSoal==null?0:j.daftarSoal.size())));
                    if (j.daftarSoal != null) {
                        for (Soal s : j.daftarSoal) {
                            System.out.println("  soal id=" + s.idSoal + " text=" + (s.teksSoal==null?"<null>":s.teksSoal.substring(0, Math.min(20, s.teksSoal.length()))));
                        }
                    }
                }
                UjianController controller = new UjianController(jenis);
                MainFrame frame = new MainFrame(controller);
                // show an in-frame greeting/password/start flow
                // pass the login password so the greeting can verify the exam password
                frame.showGreeting(exam, ld.getLoggedPassword());
                frame.setVisible(true);

            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Gagal memuat soal: " + ex.getMessage());
            }
        });
    }
}
