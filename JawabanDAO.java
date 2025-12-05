import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class JawabanDAO extends AbstractDAO {
    public void saveAnswer(int idSoal, Character pilihan) throws SQLException {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = getConnection();
            String sql = "INSERT INTO jawaban_user (id_soal, jawaban_dipilih, waktu_simpan) VALUES (?, ?, ?) " +
                    "ON DUPLICATE KEY UPDATE jawaban_dipilih = VALUES(jawaban_dipilih), waktu_simpan = VALUES(waktu_simpan)";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, idSoal);
            if (pilihan == null) ps.setNull(2, java.sql.Types.CHAR);
            else ps.setString(2, pilihan.toString());
            ps.setObject(3, LocalDateTime.now());
            ps.executeUpdate();
        } finally {
            closeQuietly(ps);
            closeQuietly(conn);
        }
    }

    public List<AnswerRecord> fetchAllForScoring() throws SQLException {
        List<AnswerRecord> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            String q = "SELECT s.id_soal, s.id_jenis, s.kunci_jawaban, ju.jawaban_dipilih FROM soal s LEFT JOIN jawaban_user ju ON s.id_soal = ju.id_soal";
            ps = conn.prepareStatement(q);
            rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt(1);
                int idJenis = rs.getInt(2);
                char k = rs.getString(3).charAt(0);
                String j = rs.getString(4);
                Character user = (j == null) ? null : j.charAt(0);
                list.add(new AnswerRecord(id, idJenis, k, user));
            }
        } finally {
            closeQuietly(rs);
            closeQuietly(ps);
            closeQuietly(conn);
        }
        return list;
    }
}
