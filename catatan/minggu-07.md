# Catatan Minggu 07: Koleksi

## Yang saya kerjakan
Pada minggu ini saya mengimplementasikan empat metode pengelolaan jadwal pada kelas `JadwalPeminjaman`, yaitu `bentrok` untuk mendeteksi tabrakan waktu pada aset yang sama, `semuaUrutMulai` untuk mengurutkan seluruh peminjaman secara menaik berdasarkan waktu mulai, serta `hapus` untuk menghapus peminjaman berdasarkan ID. 

## Keputusan desain
1. Struktur data internal: Saya menggunakan `List<Peminjaman>` (`ArrayList`) sebagai struktur penyimpanan utama di memori karena menjaga urutan data saat dimasukkan (`tambah`), mudah disalin untuk diurutkan secara dinamis dengan `Comparator` pada `semuaUrutMulai()`, dan efisien untuk pengelolaan data skala memori tanpa perlu menyinkronkan dua struktur koleksi terpisah. Untuk pengelompokan pada `perAset()`, saya membangun `HashMap<String, List<Peminjaman>>` secara dinamis menggunakan `computeIfAbsent`.
2. Perilaku di batas waktu (A-01): Saya menetapkan rentang waktu peminjaman sebagai interval setengah terbuka `[mulai, selesai)` menggunakan kondisi `baru.getMulai().isBefore(lama.getSelesai()) && baru.getSelesai().isAfter(lama.getMulai())`. Dengan aturan ini, dua peminjaman yang berbatasan langsung (seperti `09:00–11:00` dan `11:00–13:00`) dinyatakan tidak bentrok dan diuji melalui `BatasBentrokTest.selesaiSamaDenganMulai_tidakBentrok`.

## Kendala

## Penggunaan AI
- Alat yang dipakai: Gemini
- Bagian yang dibantu AI: Penyusunan implementasi metode pada `JadwalPeminjaman.java` dan pembuatan unit test `BatasBentrokTest.java`
- Prompt utama: `"Jelaskan alur pengerjaan tugas minggu 7 tentang metode pengelolaan jadwal"`
- Yang saya ubah atau tolak dari saran AI, dan alasannya: Saya menolak pengecekan `if (baru == null) return false;` pada `bentrok` agar kode lebih ringkas serta menghindari duplikasi validasi yang sudah ada pada konstruktor `Peminjaman`.
- Satu hal yang saya pahami lebih baik sekarang: Pemilihan struktur koleksi (`List` vs `Map`) sangat bergantung pada kebutuhan akses data, dan pendefinisian batas interval waktu secara eksplisit sangat penting untuk mencegah celah logika pada penjadwalan.