import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.SQLException;
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
        // modern, centered login layout (mobile-like)
        JPanel root = new JPanel();
        root.setBackground(UIStyle.PALETTE_LIGHT);
        root.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 12, 8, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.weightx = 1.0;

        JLabel title = new JLabel("Login");
        title.setFont(UIStyle.TITLE_FONT);
        title.setHorizontalAlignment(SwingConstants.LEFT);
        gbc.gridy = 0;
        root.add(title, gbc);

        JLabel subtitle = new JLabel("Masuk untuk melanjutkan ujian");
        subtitle.setFont(UIStyle.BODY_FONT);
        subtitle.setForeground(Color.DARK_GRAY);
        gbc.gridy = 1;
        root.add(subtitle, gbc);

        // spacer
        gbc.gridy = 2;
        root.add(Box.createVerticalStrut(6), gbc);

        // Nomor Ujian field (with placeholder)
        tfExam = new JTextField();
        tfExam.setFont(UIStyle.BODY_FONT);
        tfExam.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0xDD,0xDD,0xDD), 1),
                BorderFactory.createEmptyBorder(8,10,8,10)));
        tfExam.setPreferredSize(new Dimension(560, 44));
        final String phExam = "Nomor Ujian";
        tfExam.setText(phExam);
        tfExam.setForeground(Color.GRAY);
        tfExam.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (tfExam.getText().equals(phExam)) {
                    tfExam.setText("");
                    tfExam.setForeground(Color.BLACK);
                }
            }
            public void focusLost(FocusEvent e) {
                if (tfExam.getText().trim().isEmpty()) {
                    tfExam.setText(phExam);
                    tfExam.setForeground(Color.GRAY);
                }
            }
        });
        gbc.gridy = 3;
        root.add(tfExam, gbc);

        // Password field (with placeholder behavior)
        pfPass = new JPasswordField();
        pfPass.setFont(UIStyle.BODY_FONT);
        pfPass.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0xDD,0xDD,0xDD), 1),
                BorderFactory.createEmptyBorder(8,10,8,10)));
        pfPass.setPreferredSize(new Dimension(560, 44));
        final String phPass = "Password";
        // show placeholder as plain text by temporarily disabling echo char
        pfPass.setText(phPass);
        pfPass.setEchoChar((char)0);
        pfPass.setForeground(Color.GRAY);
        pfPass.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                String cur = new String(pfPass.getPassword());
                if (cur.equals(phPass)) {
                    pfPass.setText("");
                    pfPass.setEchoChar('\u2022');
                    pfPass.setForeground(Color.BLACK);
                }
            }
            public void focusLost(FocusEvent e) {
                String cur = new String(pfPass.getPassword());
                if (cur.trim().isEmpty()) {
                    pfPass.setText(phPass);
                    pfPass.setEchoChar((char)0);
                    pfPass.setForeground(Color.GRAY);
                }
            }
        });
        gbc.gridy = 4;
        root.add(pfPass, gbc);

        // Large Login button
        btnLogin = new JButton("Login");
        btnLogin.setFont(UIStyle.HEADER_FONT);
        btnLogin.setBackground(UIStyle.PRIMARY_BLUE);
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setOpaque(true);
        btnLogin.setBorder(BorderFactory.createEmptyBorder(10, 14, 10, 14));
        btnLogin.setPreferredSize(new Dimension(560, 48));
        gbc.gridy = 5;
        root.add(btnLogin, gbc);

        // Forgot button moved below login
        btnForgot = new JButton("Lupa Password");
        btnForgot.setFont(new Font("SansSerif", Font.BOLD, 12));
        btnForgot.setBorder(BorderFactory.createEmptyBorder(6,10,6,10));
        btnForgot.setContentAreaFilled(false);
        btnForgot.setFocusPainted(false);
        btnForgot.setForeground(UIStyle.ACCENT);
        gbc.gridy = 6;
        root.add(btnForgot, gbc);

        // small note / signup (optional)
        JLabel note = new JLabel("Belum punya akun? minta operator untuk membuatnya.");
        note.setFont(new Font("SansSerif", Font.ITALIC, 12));
        note.setForeground(Color.DARK_GRAY);
        gbc.gridy = 7;
        root.add(note, gbc);

        add(root);

        // actions
        btnForgot.addActionListener(e -> onForgot());
        btnLogin.addActionListener(e -> onLogin());

        // make size consistent with other main panels
        setPreferredSize(new Dimension(700, 420));
        pack();
        setResizable(false);
        setLocationRelativeTo(getOwner());
    }

    private void onForgot() {
        // Build a styled input panel instead of plain showInputDialog
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(UIStyle.PALETTE_LIGHT);
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6,6,6,6);
        c.gridx = 0; c.gridy = 0; c.anchor = GridBagConstraints.WEST;
        JLabel lbl = new JLabel("Masukkan nomor peserta (exam no):");
        lbl.setFont(UIStyle.BODY_FONT);
        inputPanel.add(lbl, c);
        c.gridy = 1; c.fill = GridBagConstraints.HORIZONTAL; c.weightx = 1.0;
        JTextField inputField = new JTextField();
        inputField.setFont(UIStyle.BODY_FONT);
        inputField.setPreferredSize(new Dimension(560, 40));
        inputField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0xDD,0xDD,0xDD), 1),
                BorderFactory.createEmptyBorder(8,10,8,10)));
        inputPanel.add(inputField, c);

        int res = JOptionPane.showConfirmDialog(this, inputPanel, "Lupa Password", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (res != JOptionPane.OK_OPTION) return;
        String exam = inputField.getText();
        if (exam == null) return;
        exam = exam.trim();
        if (exam.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nomor peserta tidak boleh kosong.", "Validasi", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            String pw = userDao.fetchPasswordByExam(exam);
            if (pw == null) {
                JOptionPane.showMessageDialog(this, "Nomor peserta tidak ditemukan.", "Tidak Ditemukan", JOptionPane.INFORMATION_MESSAGE);
            } else {
                // Create styled panel to display the password with matching look
                JPanel showPanel = new JPanel(new GridBagLayout());
                showPanel.setBackground(UIStyle.PALETTE_LIGHT);
                GridBagConstraints s = new GridBagConstraints();
                s.insets = new Insets(6,6,6,6);
                s.gridx = 0; s.gridy = 0; s.anchor = GridBagConstraints.WEST;
                JLabel info = new JLabel("Nomor Peserta: " + exam);
                info.setFont(UIStyle.BODY_FONT);
                showPanel.add(info, s);
                s.gridy = 1; s.fill = GridBagConstraints.HORIZONTAL; s.weightx = 1.0;
                JTextField tf = new JTextField(pw);
                tf.setEditable(false);
                tf.setFont(UIStyle.BODY_FONT);
                // make the display box slimmer so it doesn't span full dialog width
                tf.setPreferredSize(new Dimension(420, 80));
                tf.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(0xCC,0xCC,0xCC), 1),
                        BorderFactory.createEmptyBorder(8,10,8,10)));
                showPanel.add(tf, s);

                // Show in a message dialog but with our styled panel
                JOptionPane.showMessageDialog(this, showPanel, "Password Peserta", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Gagal memuat password: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void onLogin() {
        String exam = tfExam.getText().trim();
        String pass = new String(pfPass.getPassword());
        if (exam.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Masukkan nomor ujian dan password.", "Validasi", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            boolean ok = userDao.authenticate(exam, pass);
            if (ok) {
                loggedExam = exam;
                loggedPassword = pass;
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Nomor ujian atau password salah.", "Login Gagal", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error DB: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public String getLoggedPassword() {
        return loggedPassword;
    }

    public String showDialog() {
        setVisible(true);
        return loggedExam;
    }
}
