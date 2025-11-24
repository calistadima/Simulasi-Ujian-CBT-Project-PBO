class JenisSoal {
    public int idJenis;
    public String namaJenis;
    public int totalWaktu;
    public List<Soal> daftarSoal = new ArrayList<>();
    public boolean completed = false;

    public JenisSoal(int idJenis, String namaJenis, int totalWaktu) {
        this.idJenis = idJenis;
        this.namaJenis = namaJenis;
        this.totalWaktu = totalWaktu;
    }
}