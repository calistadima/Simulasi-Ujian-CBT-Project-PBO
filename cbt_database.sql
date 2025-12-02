-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 28 Nov 2025 pada 08.27
-- Versi server: 10.4.32-MariaDB
-- Versi PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `cbt_database`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `exam_user`
--

CREATE TABLE `exam_user` (
  `exam_no` varchar(16) NOT NULL,
  `password` varchar(64) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `exam_user`
--

INSERT INTO `exam_user` (`exam_no`, `password`) VALUES
('f1d01', 'pw0a3k'),
('f1d02', 'rt92mx'),
('f1d03', 'h1k20s'),
('f1d04', 'a99qwe'),
('f1d05', 'b7t1kp'),
('f1d06', 'pp3l9a'),
('f1d07', 'cx82rt'),
('f1d08', 'k0lpp2'),
('f1d09', 'z1p9sd'),
('f1d10', 'as72km'),
('f1d11', 'qp44lx'),
('f1d12', 'k33mbs'),
('f1d13', 'tt1z8p'),
('f1d14', 'cd79qa'),
('f1d15', 'l19vxs'),
('f1d16', 'bv7n3e'),
('f1d17', 'x23lkp'),
('f1d18', 'ff2z02'),
('f1d19', 'vv9i1k'),
('f1d20', 'ss81lp'),
('f1d21', 'qa3s8z'),
('f1d22', 'ty6b1d'),
('f1d23', 'pm91ss'),
('f1d24', 'mmq21p'),
('f1d25', 'f8l93z'),
('f1d26', 'lm9a01'),
('f1d27', 'st88ap'),
('f1d28', 'k1l3ns'),
('f1d29', 'bc93q2'),
('f1d30', 'a12txm'),
('f1d31', 'wk77h2'),
('f1d32', 'zx1p3s'),
('f1d33', 'cd9s0l'),
('f1d34', 'pl1xx8'),
('f1d35', 'pd2kw7'),
('f1d36', 'hj6m4k'),
('f1d37', 'as909l'),
('f1d38', 'tr3bxs'),
('f1d39', 'nn7p8j'),
('f1d40', 'z2l4pt'),
('f1d41', 'x4bqp1'),
('f1d42', 'cq74mh'),
('f1d43', 'xx1opd'),
('f1d44', 'rt93pk'),
('f1d45', 'mm1a3t'),
('f1d46', 'sk4l2x'),
('f1d47', 'mt8osq'),
('f1d48', 'ww0pl9'),
('f1d49', 'pp22zi'),
('f1d50', 'qa3b11'),
('f1d51', 'l1m4tt'),
('f1d52', 'z9x81p'),
('f1d53', 'hr7q5s'),
('f1d54', 'cs21mb'),
('f1d55', 'bc7x4l'),
('f1d56', 'jp86k2'),
('f1d57', 'h0l1qp'),
('f1d58', 'mn92sy'),
('f1d59', 'a8w30s'),
('f1d60', 'tt9l7p'),
('f1d61', 'qk77ds'),
('f1d62', 'pp2kwm'),
('f1d63', 'as18lo'),
('f1d64', 'w8m92x'),
('f1d65', 'cb11pp'),
('f1d66', 'xx710s'),
('f1d67', 'ld93k2'),
('f1d68', 'pw321s'),
('f1d69', 'nn4swt'),
('f1d70', 'sk85pz'),
('f1d71', 'cs7l0k'),
('f1d72', 'bm9lsq'),
('f1d73', 'wl4b80'),
('f1d74', 'j9m21s'),
('f1d75', 'qp86dd'),
('f1d76', 'as5xx1'),
('f1d77', 'ty77pq'),
('f1d78', 'lm08z1'),
('f1d79', 'ss92nb'),
('f1d80', 'qt11mk'),
('f1d81', 'wa44bd'),
('f1d82', 'xp93kk'),
('f1d83', 'cd17pa'),
('f1d84', 'ak88n2'),
('f1d85', 'hh71lw'),
('f1d86', 'ox2p90'),
('f1d87', 'pp1qnb'),
('f1d88', 'bk50ss'),
('f1d89', 'st9a41'),
('f1d90', 'lx72pm'),
('f1d91', 'zk21xa'),
('f1d92', 'qa92ym'),
('f1d93', 'pp31ck'),
('f1d94', 'sd1pn8'),
('f1d95', 'mm0aqs'),
('f1d96', 'kz82qp'),
('f1d97', 'qw43as'),
('f1d98', 'bd9l2i'),
('f1d99', 'pl71rr');

-- --------------------------------------------------------

--
-- Struktur dari tabel `jawaban_user`
--

CREATE TABLE `jawaban_user` (
  `id_jawaban` int(11) NOT NULL,
  `id_soal` int(11) NOT NULL,
  `jawaban_dipilih` char(1) DEFAULT NULL,
  `waktu_simpan` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `jawaban_user`
--

INSERT INTO `jawaban_user` (`id_jawaban`, `id_soal`, `jawaban_dipilih`, `waktu_simpan`) VALUES
(79, 56, 'D', '2025-11-28 15:25:08'),
(80, 57, 'C', '2025-11-28 15:25:10'),
(81, 58, 'B', '2025-11-28 15:25:11'),
(82, 59, 'D', '2025-11-28 15:25:14'),
(83, 60, 'A', '2025-11-28 15:25:16'),
(84, 76, 'D', '2025-11-28 15:25:19'),
(85, 77, 'D', '2025-11-28 15:25:20'),
(86, 78, 'C', '2025-11-28 15:25:22'),
(87, 79, 'D', '2025-11-28 15:25:23'),
(88, 80, 'B', '2025-11-28 15:25:25'),
(89, 66, 'D', '2025-11-28 15:25:28'),
(90, 67, 'D', '2025-11-28 15:25:32'),
(91, 68, 'D', '2025-11-28 15:25:33'),
(92, 69, 'D', '2025-11-28 15:25:35'),
(93, 70, 'D', '2025-11-28 15:25:36');

-- --------------------------------------------------------

--
-- Struktur dari tabel `jenis_soal`
--

CREATE TABLE `jenis_soal` (
  `id_jenis` int(11) NOT NULL,
  `nama_jenis` varchar(50) NOT NULL,
  `total_waktu` int(11) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `jenis_soal`
--

INSERT INTO `jenis_soal` (`id_jenis`, `nama_jenis`, `total_waktu`) VALUES
(4, 'Penalaran Verbal', 1800),
(5, 'Penalaran Numerik', 1800),
(6, 'Penalaran Logika', 1800);

-- --------------------------------------------------------

--
-- Struktur dari tabel `soal`
--

CREATE TABLE `soal` (
  `id_soal` int(11) NOT NULL,
  `id_jenis` int(11) NOT NULL,
  `teks_soal` text NOT NULL,
  `opsi1` varchar(255) DEFAULT NULL,
  `opsi2` varchar(255) DEFAULT NULL,
  `opsi3` varchar(255) DEFAULT NULL,
  `opsi4` varchar(255) DEFAULT NULL,
  `kunci_jawaban` char(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data untuk tabel `soal`
--

INSERT INTO `soal` (`id_soal`, `id_jenis`, `teks_soal`, `opsi1`, `opsi2`, `opsi3`, `opsi4`, `kunci_jawaban`) VALUES
(56, 5, 'Berapa hasil dari 12 + 8?', '18', '20', '22', '24', 'B'),
(57, 5, 'Jika 6 ? 4 = ?', '20', '22', '24', '26', 'C'),
(58, 5, 'Hasil 45 ? 5 adalah?', '7', '8', '9', '10', 'C'),
(59, 5, 'Jika 9 + 6 - 3 = ?', '10', '11', '12', '13', 'C'),
(60, 5, 'Berapa nilai 3? + 4?', '9', '10', '13', '14', 'C'),
(61, 5, 'Hasil 15 ? 3 ? 2 adalah?', '8', '9', '10', '12', 'D'),
(62, 5, 'Jika 5, 10, 15, 20 patternnya adalah?', 'Tambah 4', 'Tambah 5', 'Tambah 6', 'Tambah 7', 'B'),
(63, 5, 'Nilai 7 ? 8 - 10?', '36', '46', '50', '56', 'B'),
(64, 5, 'Jika x = 4, maka 2x + 6 = ?', '10', '12', '14', '16', 'C'),
(65, 5, 'Berapa hasil 100 - 25 ? 5?', '80', '85', '90', '95', 'B'),
(66, 6, 'Semua A adalah B. Semua B adalah C. Maka?', 'Semua A adalah C', 'Semua C adalah A', 'A bukan C', 'Tidak dapat disimpulkan', 'A'),
(67, 6, 'Jika 2 pernyataan benar dan 1 salah. Pernyataan: A benar, B benar, C salah. Maka jumlah benar?', '1', '2', '3', 'Tidak diketahui', 'B'),
(68, 6, 'Ani lebih tinggi dari Budi. Budi lebih tinggi dari Candra. Siapa yang paling pendek?', 'Ani', 'Budi', 'Candra', 'Tidak diketahui', 'C'),
(69, 6, 'Semua burung bisa terbang. Penguin adalah burung. Maka?', 'Penguin bisa terbang', 'Penguin tidak bisa terbang', 'Tidak dapat disimpulkan', 'Burung tidak bisa terbang', 'C'),
(70, 6, 'Jika 8 adalah genap dan genap selalu habis dibagi 2, maka 8?', 'Hanya habis dibagi 4', 'Tidak bisa dibagi 2', 'Habis dibagi 2', 'Tidak diketahui', 'C'),
(71, 6, 'Jika hari ini Senin, 3 hari setelah besok adalah?', 'Kamis', 'Jumat', 'Sabtu', 'Rabu', 'A'),
(72, 6, 'Jika semua pemain sepak bola adalah atlet, dan Rudi atlet. Maka?', 'Rudi pemain sepak bola', 'Rudi bukan pemain sepak bola', 'Tidak dapat disimpulkan', 'Semua atlet pemain sepak bola', 'C'),
(73, 6, 'Pola: 2 \Z 4 \Z 8 \Z 16. Langkah logis berikutnya?', '18', '20', '24', '32', 'D'),
(74, 6, 'Jika A < B dan B < C maka?', 'A < C', 'A = C', 'C < A', 'Tidak diketahui', 'A'),
(75, 6, 'Semua mahasiswa rajin. Beberapa anak rajin bukan mahasiswa. Maka?', 'Semua anak mahasiswa', 'Tidak ada anak mahasiswa', 'Beberapa bukan mahasiswa rajin', 'Tidak dapat disimpulkan', 'C'),
(76, 4, 'Sinonim dari kata \"stabil\" adalah.', 'kukuh', 'rapuh', 'kacau', 'rawan', 'A'),
(77, 4, 'Antonim dari kata \"naik\" adalah.', 'turun', 'tinggi', 'bertambah', 'melonjak', 'A'),
(78, 4, 'Kata yang memiliki makna sama dengan \"mandiri\" adalah.', 'bergantung', 'otonom', 'lemah', 'pasif', 'B'),
(79, 4, '\"Pasif\" berlawanan arti dengan.', 'aktif', 'diam', 'tidak bergerak', 'menunggu', 'A'),
(80, 4, 'Antonim dari kata \"sementara\" adalah.', 'instant', 'permanen', 'cepat', 'terbatas', 'B'),
(81, 4, 'Sinonim dari kata \"cerdas\" adalah.', 'buruk', 'pintar', 'bodoh', 'lelah', 'B'),
(82, 4, 'Kata yang berlawanan arti dengan \"optimis\" adalah.', 'positif', 'percaya', 'pesimis', 'tenang', 'C'),
(83, 4, 'Sinonim kata \"mulai\" adalah.', 'selesai', 'berhenti', 'awal', 'akhir', 'C'),
(84, 4, 'Antonim kata \"ramai\" adalah.', 'sunyi', 'ramah', 'banyak', 'padat', 'A'),
(85, 4, 'Kata yang setara makna dengan \"menunda\" adalah.', 'mempercepat', 'menunggu', 'mengulur waktu', 'memulai', 'C');

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `exam_user`
--
ALTER TABLE `exam_user`
  ADD PRIMARY KEY (`exam_no`);

--
-- Indeks untuk tabel `jawaban_user`
--
ALTER TABLE `jawaban_user`
  ADD PRIMARY KEY (`id_jawaban`),
  ADD UNIQUE KEY `unique_soal` (`id_soal`);

--
-- Indeks untuk tabel `jenis_soal`
--
ALTER TABLE `jenis_soal`
  ADD PRIMARY KEY (`id_jenis`);

--
-- Indeks untuk tabel `soal`
--
ALTER TABLE `soal`
  ADD PRIMARY KEY (`id_soal`),
  ADD KEY `id_jenis` (`id_jenis`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `jawaban_user`
--
ALTER TABLE `jawaban_user`
  MODIFY `id_jawaban` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=199;

--
-- AUTO_INCREMENT untuk tabel `jenis_soal`
--
ALTER TABLE `jenis_soal`
  MODIFY `id_jenis` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT untuk tabel `soal`
--
ALTER TABLE `soal`
  MODIFY `id_soal` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=86;

--
-- Ketidakleluasaan untuk tabel pelimpahan (Dumped Tables)
--

--
-- Ketidakleluasaan untuk tabel `jawaban_user`
--
ALTER TABLE `jawaban_user`
  ADD CONSTRAINT `jawaban_user_ibfk_1` FOREIGN KEY (`id_soal`) REFERENCES `soal` (`id_soal`);

--
-- Ketidakleluasaan untuk tabel `soal`
--
ALTER TABLE `soal`
  ADD CONSTRAINT `soal_ibfk_1` FOREIGN KEY (`id_jenis`) REFERENCES `jenis_soal` (`id_jenis`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
