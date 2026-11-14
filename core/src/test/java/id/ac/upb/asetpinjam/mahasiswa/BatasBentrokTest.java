package id.ac.upb.asetpinjam.mahasiswa;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import id.ac.upb.asetpinjam.TestSupport;
import id.ac.upb.asetpinjam.jadwal.JadwalPeminjaman;
import id.ac.upb.asetpinjam.model.Aset;
import id.ac.upb.asetpinjam.model.Peminjam;
import org.junit.jupiter.api.Test;

/**
 * FOLDER INI MILIK ANDA. Tulis tes Anda sendiri di sini (minggu 7 dan seterusnya).
 *
 * <p>Tes dosen sengaja tidak menentukan: apakah peminjaman 09:00-11:00 dan 11:00-13:00 pada aset yang sama
 * bentrok? Putuskan, catat alasannya di docs/asumsi.md, lalu tuliskan tesnya di sini.
 * Hapus @Disabled setelah Anda mengisinya.
 *
 * <p>Syarat minggu 10: folder ini memuat minimal 8 tes (tidak termasuk contoh bawaan ini).
 */
class BatasBentrokTest {

    @Test
    void selesaiSamaDenganMulai_tidakBentrok() {
        JadwalPeminjaman jadwal = new JadwalPeminjaman(5);
        Aset ruang = TestSupport.ruang("R-101");
        Peminjam p1 = TestSupport.peminjam("U-01");
        Peminjam p2 = TestSupport.peminjam("U-02");

        // Jadwal pertama: 09:00 - 11:00
        jadwal.tambah(TestSupport.pinjam("P-01", p1, ruang, 9, 11));

        // Jadwal kedua tepat setelah selesai: 11:00 - 13:00 (tidak bentrok)
        assertFalse(jadwal.bentrok(TestSupport.pinjam("P-02", p2, ruang, 11, 13)));

        // Jadwal ketiga tepat sebelum mulai: 07:00 - 09:00 (tidak bentrok)
        assertFalse(jadwal.bentrok(TestSupport.pinjam("P-03", p2, ruang, 7, 9)));
    }

    @Test
    void tumpangTindihSebagianAtauPenuh_bentrok() {
        JadwalPeminjaman jadwal = new JadwalPeminjaman(5);
        Aset ruang = TestSupport.ruang("R-101");
        Peminjam p1 = TestSupport.peminjam("U-01");
        Peminjam p2 = TestSupport.peminjam("U-02");

        // Jadwal pertama: 09:00 - 11:00
        jadwal.tambah(TestSupport.pinjam("P-01", p1, ruang, 9, 11));

        // Tumpang tindih sebagian di akhir (10:00 - 12:00) -> bentrok
        assertTrue(jadwal.bentrok(TestSupport.pinjam("P-02", p2, ruang, 10, 12)));

        // Tumpang tindih penuh di dalam rentang (09:00 - 11:00) -> bentrok
        assertTrue(jadwal.bentrok(TestSupport.pinjam("P-03", p2, ruang, 9, 11)));
    }
}