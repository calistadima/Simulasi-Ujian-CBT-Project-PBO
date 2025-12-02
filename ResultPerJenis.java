public class ResultPerJenis {
    public int idJenis;
    public String namaJenis;
    public int benar;
    public int salah;
    public int kosong;
    public double nilaiJenis; // 0-100 scale for this jenis

    public ResultPerJenis(int idJenis, String namaJenis, int benar, int salah, int kosong, double nilaiJenis) {
        this.idJenis = idJenis;
        this.namaJenis = namaJenis;
        this.benar = benar;
        this.salah = salah;
        this.kosong = kosong;
        this.nilaiJenis = nilaiJenis;
    }
}