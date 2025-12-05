import java.sql.Connection;
import java.sql.SQLException;

/**
 * Kelas abstrak dasar untuk semua DAO dalam aplikasi.
 * Menyediakan helper untuk mendapatkan koneksi dan utilitas sederhana.
 */
public abstract class AbstractDAO implements DaoMarker {
    protected Connection getConnection() throws SQLException {
        return DBConnection.getConnection();
    }

    /**
     * Tutup resource AutoCloseable tanpa melempar exception ke caller.
     */
    protected void closeQuietly(AutoCloseable ac) {
        if (ac == null) return;
        try {
            ac.close();
        } catch (Exception e) {
            // ignore
        }
    }
}
