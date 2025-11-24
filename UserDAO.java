import java.sql.SQLException;
import java.util.List;

public class UserDAO {
    public boolean authenticate(String examNo, String password) throws SQLException {
        // TODO: Query password berdasarkan exam_no
        // TODO: Bandingkan password input dengan database
        // TODO: Return true jika cocok, false jika tidak
        return false;
    }

    public List<String[]> fetchAll() throws SQLException {
        // TODO: Query semua exam_no dan password
        // TODO: Return sebagai list array string
        return null;
    }

    public String fetchPasswordByExam(String examNo) throws SQLException {
        // TODO: Query password berdasarkan exam_no
        // TODO: Return password atau null jika tidak ditemukan
        return null;
    }
}