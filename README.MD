# Project PBO 2025  
**Aplikasi Simulasi Ujian CBT dengan Navigasi Soal, Penilaian Otomatis, dan Timer Per Soal Berbasis GUI**
CBT Simple (Java Swing)

Deskripsi
- Aplikasi CBT sederhana menggunakan Swing, JDBC, dan Thread untuk timer per-soal.
- Semua file berada di root (tidak pake package).

Prasyarat
- Java JDK terpasang (javac/java di PATH).
- MariaDB/MySQL JDBC driver (contoh: `mariadb-java-client-<version>.jar`).
- Database sudah dibuat seperti instruksi user (database `cbt_database` dengan tabel `jenis_soal`, `soal`, `jawaban_user`).

Konfigurasi
- Jika credential DB berbeda, edit `DBConnection.java` dan ubah `URL`, `USER`, `PASS`.

Kompilasi (Windows cmd.exe)

javac -cp .;mariadb-java-client-2.7.4.jar *.java

Jalankan

java -cp .;mariadb-java-client-2.7.4.jar Main

Catatan
- Per-soal timer 20 detik diimplementasikan dengan `TimerSoal extends Thread`.
- Penyimpanan jawaban menggunakan `INSERT ... ON DUPLICATE KEY UPDATE` ke tabel `jawaban_user`.
- Skoring: +4 benar, -1 salah, 0 kosong.

Jika mau, saya bisa:
- Tambahkan validasi UI/feedback (mis. highlight jawaban yang dipilih sebelumnya).
- Ganti timer per-soal ke `javax.swing.Timer` untuk lebih aman pada EDT.
- Tambah fitur global timer.

# compile (opsional)
javac -cp ".;lib/*" *.java

# run
java -cp ".;lib/*" Main


