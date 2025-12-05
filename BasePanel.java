import javax.swing.*;

/**
 * Kelas abstrak dasar untuk panel GUI aplikasi.
 * Menyediakan helper layout/font dan contract untuk panels yang membutuhkan
 * aksi close atau update dari controller.
 */
public abstract class BasePanel extends JPanel {
    public BasePanel() {
        setBackground(UIStyle.PALETTE_LIGHT);
    }

    /**
     * Jalankan update yang mungkin datang dari controller. Dipanggil di EDT.
     */
    public void updateFromController() {
        // default noop; override bila perlu
    }
}
