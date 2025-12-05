import java.time.LocalDateTime;

public class JawabanUser {
    public int idJawaban;
    public int idSoal;
    public Character jawabanDipilih; // 'A','B','C','D' atau null
    public LocalDateTime waktuSimpan;

    public JawabanUser(int idJawaban, int idSoal, Character jawabanDipilih, LocalDateTime waktuSimpan) {
        this.idJawaban = idJawaban;
        this.idSoal = idSoal;
        this.jawabanDipilih = jawabanDipilih;
        this.waktuSimpan = waktuSimpan;
    }
}
