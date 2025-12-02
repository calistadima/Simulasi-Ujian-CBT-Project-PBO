import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

public class PanelSoal extends BasePanel {
    private JLabel lblSoalHeader;
    private JRadioButton rbA, rbB, rbC, rbD;
    private ButtonGroup bg;
    private JButton btnNext;
    private JLabel lblSoalInfo;
    private JLabel lblJenisTimer;
    private JTextArea taSoalText;

    public PanelSoal() {
        setLayout(new BorderLayout());
        // provide symmetric horizontal padding so question box is not too close to left edge
        setBorder(BorderFactory.createEmptyBorder(10, 32, 10, 32));
        // Top header with soal info and per-question timer
        JPanel top = new JPanel(new BorderLayout());
        top.setBorder(BorderFactory.createEmptyBorder(10,12,10,12));
        top.setBackground(UIStyle.PALETTE_LIGHT);
        lblSoalInfo = new JLabel("Silakan pilih jenis soal -->");
        lblSoalInfo.setFont(UIStyle.HEADER_FONT);
        lblSoalInfo.setForeground(UIStyle.PRIMARY_BLUE);
        top.add(lblSoalInfo, BorderLayout.WEST);
        lblJenisTimer = new JLabel("--:--");
        lblJenisTimer.setFont(UIStyle.BODY_FONT);
        lblJenisTimer.setForeground(UIStyle.PRIMARY_BLUE);
        top.add(lblJenisTimer, BorderLayout.EAST);
        add(top, BorderLayout.NORTH);

        // Center: question text and options
        JPanel center = new JPanel(new BorderLayout(8,8));
        taSoalText = new JTextArea();
        taSoalText.setEditable(false);
        taSoalText.setLineWrap(true);
        taSoalText.setWrapStyleWord(true);
        taSoalText.setBackground(Color.WHITE);
        taSoalText.setFont(UIStyle.BODY_FONT);
        // add slightly larger left padding so the question text doesn't touch the left edge
        taSoalText.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(UIStyle.PRIMARY_BLUE, 2), BorderFactory.createEmptyBorder(12,18,12,12)));
        JScrollPane sp = new JScrollPane(taSoalText);
        sp.setPreferredSize(new Dimension(400,120));
        center.add(sp, BorderLayout.NORTH);

        JPanel opsiPanel = new JPanel();
        opsiPanel.setLayout(new BoxLayout(opsiPanel, BoxLayout.Y_AXIS));
        opsiPanel.setBackground(UIStyle.PALETTE_LIGHT);
        // shift radio buttons slightly to the right (closer to center)
        opsiPanel.setBorder(BorderFactory.createEmptyBorder(0,18,0,0));
        rbA = new JRadioButton();
        rbB = new JRadioButton();
        rbC = new JRadioButton();
        rbD = new JRadioButton();
        bg = new ButtonGroup();
        bg.add(rbA); bg.add(rbB); bg.add(rbC); bg.add(rbD);
        rbA.setFont(UIStyle.BODY_FONT); rbB.setFont(UIStyle.BODY_FONT); rbC.setFont(UIStyle.BODY_FONT); rbD.setFont(UIStyle.BODY_FONT);
        rbA.setForeground(UIStyle.PRIMARY_BLUE); rbB.setForeground(UIStyle.PRIMARY_BLUE); rbC.setForeground(UIStyle.PRIMARY_BLUE); rbD.setForeground(UIStyle.PRIMARY_BLUE);
        opsiPanel.add(Box.createVerticalStrut(8));
        opsiPanel.add(rbA); opsiPanel.add(Box.createVerticalStrut(10));
        opsiPanel.add(rbB); opsiPanel.add(Box.createVerticalStrut(10));
        opsiPanel.add(rbC); opsiPanel.add(Box.createVerticalStrut(10));
        opsiPanel.add(rbD); opsiPanel.add(Box.createVerticalStrut(8));
        center.add(opsiPanel, BorderLayout.CENTER);

        // place the action button closer to the options by adding it inside the center area
        JPanel actionHolder = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        actionHolder.setBackground(UIStyle.PALETTE_LIGHT);
        btnNext = new JButton("JAWAB & LANJUT");
        btnNext.setBackground(UIStyle.ACCENT);
        btnNext.setForeground(Color.WHITE);
        btnNext.setFont(UIStyle.BODY_FONT);
        btnNext.setFocusPainted(false);
        // give a bit of top margin so the button aligns with the last option
        actionHolder.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 0));
        actionHolder.add(btnNext);
        center.add(actionHolder, BorderLayout.SOUTH);

        add(center, BorderLayout.CENTER);
    }

    public void showSoal(Soal s, int nomor, int total) {
        bg.clearSelection();
        rbA.setEnabled(true); rbB.setEnabled(true); rbC.setEnabled(true); rbD.setEnabled(true);
        lblSoalInfo.setText(String.format("Soal %d dari %d", nomor, total));
        taSoalText.setText(s.teksSoal);
        rbA.setText("A. " + s.opsiA);
        rbB.setText("B. " + s.opsiB);
        rbC.setText("C. " + s.opsiC);
        rbD.setText("D. " + s.opsiD);
        btnNext.setEnabled(true);
        btnNext.setText("JAWAB & LANJUT");
    }

    public void showFinishedJenis(int secondsRemaining) {
        // user has answered all questions in this jenis but timer still running
        bg.clearSelection();
        rbA.setEnabled(false); rbB.setEnabled(false); rbC.setEnabled(false); rbD.setEnabled(false);
        lblSoalInfo.setText("Semua soal pada jenis ini telah dijawab.");
        taSoalText.setText("Tekan SUBMIT untuk mengirim jawaban dan lanjut ke jenis soal berikutnya, atau tunggu sampai waktu habis.");
        btnNext.setText("SUBMIT");
        btnNext.setEnabled(true);
        updateTimerLabel(secondsRemaining);
    }

    public void updateTimerLabel(int seconds) {
        if (seconds < 0) seconds = 0;
        int mins = seconds / 60;
        int secs = seconds % 60;
        lblJenisTimer.setText(String.format("%02d:%02d", mins, secs));
    }

    public void lockOptions() {
        rbA.setEnabled(false); rbB.setEnabled(false); rbC.setEnabled(false); rbD.setEnabled(false);
        btnNext.setEnabled(false);
    }

    public Character getSelected() {
        if (rbA.isSelected()) return 'A';
        if (rbB.isSelected()) return 'B';
        if (rbC.isSelected()) return 'C';
        if (rbD.isSelected()) return 'D';
        return null;
    }

    public void setNextAction(ActionListener l) {
        for (ActionListener al : btnNext.getActionListeners()) btnNext.removeActionListener(al);
        btnNext.addActionListener(l);
    }

    @Override
    public void updateFromController() {
        // implement if controller needs to push updates
    }
}
