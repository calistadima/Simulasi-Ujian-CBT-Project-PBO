public class AnswerRecord {
    public int idSoal;
    public int idJenis;
    public char kunci;
    public Character jawabanUser; // nullable

    public AnswerRecord(int idSoal, int idJenis, char kunci, Character jawabanUser) {
        this.idSoal = idSoal;
        this.idJenis = idJenis;
        this.kunci = kunci;
        this.jawabanUser = jawabanUser;
    }
}
