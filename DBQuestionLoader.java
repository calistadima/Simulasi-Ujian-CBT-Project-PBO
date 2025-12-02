import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DBQuestionLoader sekarang menggunakan AbstractDAO sebagai parent untuk
 * mengilustrasikan pewarisan pada layer data access.
 */
public class DBQuestionLoader extends AbstractDAO implements QuestionLoader {

    @Override
    public List<JenisSoal> loadAll() throws SQLException {
        List<JenisSoal> jenisList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            String qJenis = "SELECT id_jenis, nama_jenis, total_waktu FROM jenis_soal";
            ps = conn.prepareStatement(qJenis);
            rs = ps.executeQuery();
            while (rs.next()) {
                JenisSoal j = new JenisSoal(rs.getInt("id_jenis"), rs.getString("nama_jenis"), rs.getInt("total_waktu"));
                jenisList.add(j);
            }
            closeQuietly(rs); rs = null;
            closeQuietly(ps); ps = null;

            String qSoal = "SELECT id_soal, id_jenis, teks_soal, opsi1, opsi2, opsi3, opsi4, kunci_jawaban FROM soal WHERE id_jenis = ? ORDER BY id_soal";
            ps = conn.prepareStatement(qSoal);
            for (JenisSoal j : jenisList) {
                ps.setInt(1, j.idJenis);
                rs = ps.executeQuery();
                while (rs.next()) {
                    Soal s = new Soal(
                            rs.getInt("id_soal"),
                            rs.getInt("id_jenis"),
                            rs.getString("teks_soal"),
                            rs.getString("opsi1"),
                            rs.getString("opsi2"),
                            rs.getString("opsi3"),
                            rs.getString("opsi4"),
                            rs.getString("kunci_jawaban").charAt(0)
                    );
                    j.daftarSoal.add(s);
                }
                closeQuietly(rs); rs = null;
            }
        } finally {
            closeQuietly(rs);
            closeQuietly(ps);
            closeQuietly(conn);
        }
        return jenisList;
    }
}
