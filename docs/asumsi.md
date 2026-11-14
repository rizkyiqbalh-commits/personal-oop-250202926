# Asumsi dan Keputusan Desain

Isi bagian ini sendiri. Satu entri per keputusan. Dinilai dari kejelasan alasan dan konsistensi dengan kode dan tes Anda,
bukan dari jawaban "benar".

Format tiap entri:

```
## A-NN: judul singkat
- Pertanyaan: ...
- Keputusan: ...
- Alasan: ...
- Dampak ke kode: ... (kelas/metode)
- Tes yang membuktikan: ... (nama tes di folder mahasiswa/)
```

## Aturan ambigu dari docs/aturan-bisnis.md

Salin setiap pertanyaan ambigu (nomor 1 sampai 7) ke sini dan jawab. Contoh:

## A-01: batas waktu tidak bentrok
- Pertanyaan: Apakah dua jadwal peminjaman pada aset yang sama dengan waktu berbatasan langsung (misalnya 09:00–11:00 dan 11:00–13:00) dianggap bentrok?
- Keputusan: Tidak bentrok. Setiap jadwal dimodelkan menggunakan interval setengah terbuka `[mulai, selesai)`, sehingga peminjaman baru dapat langsung dimulai tepat pada jam berakhirnya peminjaman sebelumnya.
- Alasan: Tidak ada irisan waktu penggunaan fisik aset di antara kedua sesi tersebut, sekaligus menghindari keharusan menyisipkan jeda waktu kosong yang tidak perlu antar peminjam.
- Dampak ke kode: Metode `JadwalPeminjaman.bentrok` memeriksa tumpang tindih dengan kondisi `baru.getMulai().isBefore(lama.getSelesai()) && baru.getSelesai().isAfter(lama.getMulai())`. Kondisi ini bernilai `false` ketika waktu mulai dan selesai bernilai sama.
- Tes yang membuktikan: `BatasBentrokTest.java` di dalam direktori `core/src/test/java/id/ac/upb/asetpinjam/mahasiswa/`.

## A-02: denda progresif
- Pertanyaan: Bagaimana rumus progresif jika sudah sampai minggu ke-5 atau lebih?
- Keputusan: Hari 1–6 memakai tarif harian biasa (1.0x). Hari 7–13 memakai 1.2x tarif flat, hari 14–29 memakai 1.5x tarif flat, dan hari ke-30 ke atas memakai 2.0x tarif flat.
- Alasan: Skema pengali bertahap ini saya coba untuk memastikan denda semakin berat seiring lamanya keterlambatan, tidak pernah lebih murah dari tarif flat, tidak pernah turun seiring bertambahnya hari, dan pada hari ke-30 dipastikan 2x lipat lebih mahal dari denda flat.
- Dampak ke kode: `DendaProgresif.hitungDenda`, hari negatif ditolak oleh perhitungan aset. Denda tidak lebih murah dari flat dan terus bertambah ketika hari bertambah. Pada hari ke-30, pengguna dikenakan denda tepat sebesar 200% (2.0x) dari total denda flat harian.