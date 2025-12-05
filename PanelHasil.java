import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class PanelHasil extends BasePanel {
    private JLabel lblTitle;
    private JLabel lblNilai;
    private JTextArea taDetail;
    private JButton btnTutup;
    private ActionListener closeAction;

    public PanelHasil() {
        setLayout(new BorderLayout());
        setBackground(UIStyle.PALETTE_LIGHT);
        lblTitle = new JLabel("HASIL UJIAN", SwingConstants.CENTER);
        lblTitle.setFont(UIStyle.TITLE_FONT.deriveFont(20f));
        lblTitle.setForeground(UIStyle.PRIMARY_BLUE);
        // build a top area containing title and nilai stacked vertically
        JPanel topArea = new JPanel(new BorderLayout());
        topArea.setBackground(UIStyle.PALETTE_LIGHT);
        topArea.add(lblTitle, BorderLayout.NORTH);
        taDetail = new JTextArea();
        taDetail.setEditable(false);
        taDetail.setFont(UIStyle.BODY_FONT);
        JScrollPane sp = new JScrollPane(taDetail);
        sp.setBorder(BorderFactory.createLineBorder(UIStyle.PRIMARY_BLUE, 2));
        // place the scroll pane inside a centered panel with constrained size
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(UIStyle.PALETTE_LIGHT);
        sp.setPreferredSize(new Dimension(720, 220));
        centerPanel.add(sp);
        // area for nilai: large centered label between title and detail
        lblNilai = new JLabel("", SwingConstants.CENTER);
        lblNilai.setFont(UIStyle.TITLE_FONT.deriveFont(28f));
        lblNilai.setForeground(UIStyle.PRIMARY_BLUE);
        JPanel nilaiPanel = new JPanel(new GridBagLayout());
        nilaiPanel.setBackground(UIStyle.PALETTE_LIGHT);
        nilaiPanel.add(lblNilai);
        topArea.add(nilaiPanel, BorderLayout.CENTER);

        add(topArea, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);

        // larger centered Close button, with extra top padding so it's not flush to bottom
        JPanel bottom = new JPanel(new GridBagLayout());
        bottom.setBackground(UIStyle.PALETTE_LIGHT);
        bottom.setBorder(BorderFactory.createEmptyBorder(18, 0, 28, 0));
        btnTutup = new JButton("TUTUP");
        btnTutup.setBackground(UIStyle.ACCENT);
        btnTutup.setForeground(Color.WHITE);
        btnTutup.setPreferredSize(new Dimension(140, 44));
        // install wrapper listener that asks for confirmation before invoking closeAction
        btnTutup.addActionListener(e -> {
            int sel = JOptionPane.showConfirmDialog(PanelHasil.this, "Yakin ingin keluar?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (sel == JOptionPane.YES_OPTION) {
                if (closeAction != null) closeAction.actionPerformed(e);
            }
        });
        bottom.add(btnTutup);
        add(bottom, BorderLayout.SOUTH);
    }

    public void showResult(java.util.List<ResultPerJenis> perJenisResults, double nilaiSkala100, int totalBenar, int totalSalah, int totalKosong) {
        // Create a stylized card list view similar to the target design (cards on yellow background)
        lblTitle.setText("HASIL UJIAN");
        String nilaiText = String.format("%.1f", nilaiSkala100);
        lblNilai.setText(nilaiText);

        // top layout: keep header title and overall nilai
        removeAll();
        setLayout(new BorderLayout());
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(UIStyle.PALETTE_LIGHT);
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        headerPanel.add(lblTitle, BorderLayout.NORTH);
        lblNilai.setHorizontalAlignment(SwingConstants.CENTER);
        headerPanel.add(lblNilai, BorderLayout.CENTER);
        add(headerPanel, BorderLayout.NORTH);

        // main area uses the app light palette background (#ffe1d7)
        JPanel mainArea = new JPanel();
        mainArea.setBackground(UIStyle.PALETTE_LIGHT);
        mainArea.setLayout(new BoxLayout(mainArea, BoxLayout.Y_AXIS));
        mainArea.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Use plain rectangular panels for cards (no rounded corners)

        for (ResultPerJenis r : perJenisResults) {
            JPanel card = new JPanel(new BorderLayout());
            card.setBackground(Color.WHITE);
            card.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(UIStyle.PRIMARY_BLUE, 1), new EmptyBorder(12, 12, 12, 12)));
            card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 72));

            // left: name
            JLabel name = new JLabel(r.namaJenis);
            name.setFont(UIStyle.HEADER_FONT.deriveFont(16f));
            name.setForeground(UIStyle.PRIMARY_BLUE);
            name.setBorder(new EmptyBorder(2,6,2,6));
            card.add(name, BorderLayout.WEST);

            // center: small stats stack
            JPanel centerStats = new JPanel(new GridLayout(1,3,6,0));
            centerStats.setOpaque(false);
            JLabel lb = new JLabel("Benar: " + r.benar);
            lb.setFont(UIStyle.BODY_FONT);
            JLabel ls = new JLabel("Salah: " + r.salah);
            ls.setFont(UIStyle.BODY_FONT);
            JLabel lk = new JLabel("Kosong: " + r.kosong);
            lk.setFont(UIStyle.BODY_FONT);
            centerStats.add(lb); centerStats.add(ls); centerStats.add(lk);
            card.add(centerStats, BorderLayout.CENTER);

            // right: black pill with nilai per jenis
            JLabel pill = new JLabel(String.format("%.1f", r.nilaiJenis));
            pill.setOpaque(true);
            pill.setBackground(new Color(0x262626));
            pill.setForeground(Color.WHITE);
            pill.setBorder(new EmptyBorder(8,14,8,14));
            pill.setFont(UIStyle.BODY_FONT.deriveFont(14f));
            JPanel rightWrap = new JPanel(new GridBagLayout());
            rightWrap.setOpaque(false);
            rightWrap.add(pill);
            card.add(rightWrap, BorderLayout.EAST);

            mainArea.add(card);
            mainArea.add(Box.createVerticalStrut(12));
        }

        // totals box below
        JPanel totals = new JPanel(new GridLayout(2,1,0,6));
        totals.setBackground(Color.WHITE);
        totals.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(UIStyle.PRIMARY_BLUE,1), new EmptyBorder(12,12,12,12)));
        totals.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));
        JPanel t1 = new JPanel(new FlowLayout(FlowLayout.LEFT)); t1.setOpaque(false);
        t1.add(new JLabel(String.format("TOTAL BENAR: %d", totalBenar)));
        t1.add(Box.createHorizontalStrut(12));
        t1.add(new JLabel(String.format("TOTAL SALAH: %d", totalSalah)));
        JPanel t2 = new JPanel(new FlowLayout(FlowLayout.LEFT)); t2.setOpaque(false);
        t2.add(new JLabel(String.format("TOTAL KOSONG: %d", totalKosong)));
        t2.add(Box.createHorizontalStrut(12));
        t2.add(new JLabel(String.format("Nilai (skala 100): %s", nilaiText)));
        totals.add(t1); totals.add(t2);

        mainArea.add(totals);

        JScrollPane sc = new JScrollPane(mainArea);
        sc.setBorder(BorderFactory.createEmptyBorder());
        add(sc, BorderLayout.CENTER);

        // bottom close button
        JPanel bottom = new JPanel(new GridBagLayout());
        bottom.setBackground(UIStyle.PALETTE_LIGHT);
        bottom.setBorder(BorderFactory.createEmptyBorder(18, 0, 28, 0));
        bottom.add(btnTutup);
        add(bottom, BorderLayout.SOUTH);

        revalidate();
        repaint();
    }

    public void setCloseAction(ActionListener l) {
        this.closeAction = l;
    }

    @Override
    public void updateFromController() {
        // placeholder for future updates
    }
}
