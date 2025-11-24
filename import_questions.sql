-- SQL import for CBT Simple
-- Creates jenis_soal entries and soal rows for the supplied question set
-- Adjust database name / table prefixes if needed

-- NOTE: run this on your MariaDB/MySQL server where the app connects (database cbt_database assumed)

-- 1) Create jenis_soal entries (if not exists)
INSERT INTO jenis_soal (id_jenis, nama_jenis, total_waktu) VALUES
 (1, 'Penalaran Verbal', 600),
 (2, 'Penalaran Numerik', 600),
 (3, 'Penalaran Logika', 600),
 (4, 'Penalaran Figural', 600)
ON DUPLICATE KEY UPDATE nama_jenis = VALUES(nama_jenis), total_waktu = VALUES(total_waktu);

-- 2) Insert soal rows
-- IDs chosen starting at 1001, 2001, 3001, 4001 for readability

-- Penalaran Verbal
INSERT INTO soal (id_soal, id_jenis, teks_soal, opsi_a, opsi_b, opsi_c, opsi_d, kunci_jawaban) VALUES
(1001, 1, 'Panas : Api = Basah : ...', 'Air', 'Es', 'Minyak', 'Awan', 'A'),
(1002, 1, 'Kerja : Kantor = Belajar : ...', 'Sekolah', 'Gudang', 'Pabrik', 'Kampus', 'A'),
(1003, 1, 'Dokter : Pasien = Guru : ...', 'Murid', 'Orang', 'Karyawan', 'Teman', 'A'),
(1004, 1, 'Garam : Asin = Gula : ...', 'Manis', 'Pahit', 'Asin', 'Getir', 'A'),
(1005, 1, 'Nyanyi : Penyanyi = Tulis : ...', 'Penulis', 'Pembaca', 'Penerjemah', 'Editor', 'A'),
(1006, 1, 'Pohon : Hutan = Buku : ...', 'Perpustakaan', 'Rak', 'Gudang', 'Meja', 'A'),
(1007, 1, 'Penulis : Novel = Pelukis : ...', 'Lukisan', 'Kanvas', 'Cat', 'Galeri', 'A'),
(1008, 1, 'Sapu : Bersih = Payung : ...', 'Hujan', 'Panas', 'Angin', 'Awan', 'A'),
(1009, 1, 'Makan : Kenyang = Tidur : ...', 'Segar', 'Lelah', 'Gelap', 'Panas', 'A'),
(1010, 1, 'Dokumen : Arsip = Uang : ...', 'Tabungan', 'Dompet', 'ATM', 'Kas', 'A')
ON DUPLICATE KEY UPDATE teks_soal = VALUES(teks_soal);

-- Penalaran Numerik
INSERT INTO soal (id_soal, id_jenis, teks_soal, opsi_a, opsi_b, opsi_c, opsi_d, kunci_jawaban) VALUES
(2001, 2, '2, 4, 8, 16, ... ? (lanjutkan deret)', '24', '30', '32', '20', 'C'),
(2002, 2, '3, 6, 9, 12, ... ? (lanjutkan deret)', '15', '14', '18', '21', 'A'),
(2003, 2, '1, 4, 9, 16, ... ? (lanjutkan kuadrat)', '20', '25', '30', '36', 'B'),
(2004, 2, '7 + 5 × 2 = ? (operasi berurutan)', '24', '17', '19', '27', 'B'),
(2005, 2, '24 ÷ 3 + 2 = ?', '8', '10', '12', '6', 'B'),
(2006, 2, 'Jika 20% dari X = 30, maka X = ?', '150', '60', '130', '180', 'A'),
(2007, 2, 'Harga = 100. Diskon 10% → Harga akhir?', '90', '100', '80', '85', 'A'),
(2008, 2, '16, 15, 13, 10, ... ? (pola penurunan: -1,-2,-3,-4)', '8', '6', '5', '7', 'B'),
(2009, 2, '(4^2 + 6) ÷ 2 = ?', '10', '11', '12', '9', 'B'),
(2010, 2, 'Jika 3 kotak = 18 apel, 1 kotak berisi berapa?', '6', '3', '9', '12', 'A')
ON DUPLICATE KEY UPDATE teks_soal = VALUES(teks_soal);

-- Penalaran Logika
INSERT INTO soal (id_soal, id_jenis, teks_soal, opsi_a, opsi_b, opsi_c, opsi_d, kunci_jawaban) VALUES
(3001, 3, 'Semua A adalah B. Semua B adalah C. Maka...', 'Semua A adalah C', 'Semua C adalah A', 'A dan C tidak berhubungan', 'Tidak dapat disimpulkan', 'A'),
(3002, 3, 'Semua siswa rajin. Rani adalah siswa. Maka...', 'Rani rajin', 'Rani tidak rajin', 'Rani guru', 'Tidak dapat disimpulkan', 'A'),
(3003, 3, 'Jika hujan maka jalan basah. Hujan. Maka...', 'Jalan basah', 'Jalan kering', 'Tidak dapat disimpulkan', 'Jalan licin', 'A'),
(3004, 3, 'Semua burung punya sayap. Elang adalah burung. Maka...', 'Elang punya sayap', 'Elang tidak punya sayap', 'Elang hewan air', 'Elang bukan burung', 'A'),
(3005, 3, 'Jika belajar rutin → nilai tinggi. Budi tidak belajar. Maka...', 'Tidak dapat disimpulkan', 'Nilai Budi rendah', 'Budi pasti pintar', 'Budi tidak masuk kelas', 'A'),
(3006, 3, 'A > B, B > C ⇒ ?', 'A > C', 'C > A', 'A = C', 'Tidak dapat disimpulkan', 'A'),
(3007, 3, 'Semua pemain sepakbola atlet. Tidak semua atlet pemain sepakbola. Pernyataan mana yang benar?', 'Benar', 'Salah', 'Tidak dapat disimpulkan', 'Bersyarat', 'A'),
(3008, 3, 'Hanya dosen yang boleh masuk ruang A. Rafi bukan dosen. Maka...', 'Rafi tidak boleh masuk ruang A', 'Rafi boleh masuk', 'Rafi adalah dosen', 'Tidak dapat disimpulkan', 'A'),
(3009, 3, 'Sebagian mahasiswa suka kopi.', 'Tidak semua mahasiswa suka kopi', 'Semua mahasiswa suka kopi', 'Tidak ada yang suka kopi', 'Semua tidak suka kopi', 'A'),
(3010, 3, 'Jika X maka Y. Y terjadi. Maka...', 'Tidak dapat disimpulkan X', 'X pasti terjadi', 'X tidak terjadi', 'Y bukan akibat X', 'A')
ON DUPLICATE KEY UPDATE teks_soal = VALUES(teks_soal);

-- Penalaran Figural (diubah ke bentuk verbal / numerik karena format teks)
INSERT INTO soal (id_soal, id_jenis, teks_soal, opsi_a, opsi_b, opsi_c, opsi_d, kunci_jawaban) VALUES
(4001, 4, 'Segitiga → 3 sisi. Persegi → ? sisi', '4 sisi', '5 sisi', '3 sisi', '6 sisi', 'A'),
(4002, 4, 'Persegi 2×2 → 4 kotak. Persegi 3×3 → ? kotak', '6', '9', '8', '12', 'B'),
(4003, 4, 'Rotasi 90° dari L → bentuk?', '┘ (arah berubah)', '┐', '└', '┌', 'A'),
(4004, 4, 'Pola: ▲, ●, ▲, ●, … berikutnya adalah?', '▲', '●', '■', '▼', 'A'),
(4005, 4, '▲ besar → ▲ kecil → ▲ besar → … berikutnya?', '▲ kecil', '▲ besar', '● besar', '▲ sedang', 'A'),
(4006, 4, 'Kotak → lingkaran → segitiga → kotak → … berikutnya?', 'Lingkaran', 'Segitiga', 'Kotak', 'Oval', 'A'),
(4007, 4, 'Sisi meningkat: 3,4,5,6,... berikutnya?', '7 sisi', '8 sisi', '6 sisi', '5 sisi', 'A'),
(4008, 4, 'Jumlah titik: 1,3,6,10,... berikutnya?', '15', '12', '14', '20', 'A'),
(4009, 4, 'Pola cermin: L ↔ J. Transformasi disebut?', 'Refleksi horizontal', 'Rotasi 90°', 'Translasi', 'Skala', 'A'),
(4010, 4, 'Kotak solid → kotak garis luar → kotak solid → … berikutnya?', 'Kotak garis luar', 'Kotak solid', 'Lingkaran', 'Segitiga', 'A')
ON DUPLICATE KEY UPDATE teks_soal = VALUES(teks_soal);

-- Commit if needed
COMMIT;
