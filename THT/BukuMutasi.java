// KELAS PEMBANTU: BukuMutasi
// Bertanggung jawab mencatat histori transaksi sebuah rekening.
// Relasi KOMPOSISI: BukuMutasi hanya bisa hidup di dalam Rekening.
// Siklus hidupnya sepenuhnya dikendalikan oleh objek Rekening pemiliknya.
public class BukuMutasi {
    private String nomorRekening;
    private int jumlahMutasi;

    public BukuMutasi(String nomorRekening) {
        this.nomorRekening = nomorRekening;
        this.jumlahMutasi = 0;
        System.out.println("  [BukuMutasi] Buku mutasi untuk rekening " + nomorRekening + " telah dibuat.");
    }

    public void catatMutasi(String jenis, double jumlah, double saldoAkhir) {
        jumlahMutasi++;
        System.out.println("  [MUTASI #" + jumlahMutasi + "] Rek: " + nomorRekening
                + " | " + jenis
                + " | Jumlah: Rp " + String.format("%,.0f", jumlah)
                + " | Saldo Akhir: Rp " + String.format("%,.0f", saldoAkhir));
    }

    public void tampilkanHistori() {
        System.out.println("  [BukuMutasi] Total " + jumlahMutasi
                + " transaksi tercatat untuk rekening " + nomorRekening + ".");
    }

    // Dipanggil saat rekening ditutup (simulasi destruktor)
    public void tutup() {
        System.out.println("  [BukuMutasi] Buku mutasi rekening " + nomorRekening
                + " ditutup dan dihancurkan bersama rekeningnya. (KOMPOSISI)");
    }
}