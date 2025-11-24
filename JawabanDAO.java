import java.sql.SQLException;
import java.util.List;

public class JawabanDAO {
    public void saveAnswer(int idSoal, Character pilihan) throws SQLException {
        // TODO: Insert atau update jawaban_user
        // TODO: Simpan waktu_simpan
        // TODO: Handle jika pilihan null (soal tidak dijawab)
    }

    public List<AnswerRecord> fetchAllForScoring() throws SQLException {
        // TODO: Join soal dengan jawaban_user
        // TODO: Ambil id_soal, kunci_jawaban, jawaban_dipilih
        // TODO: Return list AnswerRecord untuk perhitungan skor
        return null;
    }
}