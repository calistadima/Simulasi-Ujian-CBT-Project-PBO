import java.awt.*;
import javax.swing.*;

public class LoginDialog extends JDialog {
    private JTextField tfExam;
    private JPasswordField pfPass;
    private JButton btnLogin, btnForgot;
    private String loggedExam = null;
    private String loggedPassword = null;
    private final UserDAO userDao;

    public LoginDialog(Frame owner, UserDAO userDao) {
        super(owner, "Login - CBT", true);
        this.userDao = userDao;
        initUI();
    }

    private void initUI() {
        // TODO: Setup layout GridBagLayout
        // TODO: Buat komponen title, subtitle
        // TODO: Buat text field untuk nomor ujian dengan placeholder
        // TODO: Buat password field dengan placeholder
        // TODO: Buat button Login dan Lupa Password
        // TODO: Setup action listeners
        // TODO: Set size dan location
    }

    private void onForgot() {
        // TODO: Tampilkan dialog input nomor peserta
        // TODO: Query password dari database via UserDAO
        // TODO: Tampilkan password dalam dialog
        // TODO: Handle jika nomor tidak ditemukan
    }

    private void onLogin() {
        // TODO: Ambil input nomor ujian dan password
        // TODO: Validasi tidak boleh kosong
        // TODO: Authenticate via UserDAO
        // TODO: Jika berhasil, set loggedExam dan close dialog
        // TODO: Jika gagal, tampilkan pesan error
    }

    public String getLoggedPassword() {
        return loggedPassword;
    }

    public String showDialog() {
        setVisible(true);
        return loggedExam;
    }
}