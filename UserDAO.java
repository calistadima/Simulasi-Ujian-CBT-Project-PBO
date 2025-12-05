import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO extends AbstractDAO {
    public boolean authenticate(String examNo, String password) throws SQLException {
        String q = "SELECT password FROM exam_user WHERE exam_no = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(q);
            ps.setString(1, examNo);
            rs = ps.executeQuery();
            if (rs.next()) {
                String pw = rs.getString(1);
                return pw != null && pw.equals(password);
            }
            return false;
        } finally {
            closeQuietly(rs);
            closeQuietly(ps);
            closeQuietly(conn);
        }
    }

    public List<String[]> fetchAll() throws SQLException {
        List<String[]> list = new ArrayList<>();
        String q = "SELECT exam_no, password FROM exam_user ORDER BY exam_no";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(q);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new String[]{rs.getString(1), rs.getString(2)});
            }
        } finally {
            closeQuietly(rs);
            closeQuietly(ps);
            closeQuietly(conn);
        }
        return list;
    }

    public String fetchPasswordByExam(String examNo) throws SQLException {
        String q = "SELECT password FROM exam_user WHERE exam_no = ?";
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            ps = conn.prepareStatement(q);
            ps.setString(1, examNo);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            }
        } finally {
            closeQuietly(rs);
            closeQuietly(ps);
            closeQuietly(conn);
        }
        return null;
    }
}
