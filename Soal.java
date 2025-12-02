public class Soal {
    public int idSoal;
    public int idJenis;
    public String teksSoal;
    public String opsiA;
    public String opsiB;
    public String opsiC;
    public String opsiD;
    public char kunci;

    public Soal(int idSoal, int idJenis, String teksSoal, String opsiA, String opsiB, String opsiC, String opsiD, char kunci) {
        this.idSoal = idSoal;
        this.idJenis = idJenis;
        this.teksSoal = teksSoal;
        this.opsiA = opsiA;
        this.opsiB = opsiB;
        this.opsiC = opsiC;
        this.opsiD = opsiD;
        this.kunci = kunci;
    }
}
