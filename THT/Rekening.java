// ABSTRACT CLASS: Rekening
// Mengimplementasikan interface Otorisasi.
// Menyimpan data inti rekening dengan enkapsulasi ketat.
// Menerapkan KOMPOSISI dengan BukuMutasi (dibuat di constructor, hancur bersama).
public abstract class Rekening implements Otorisasi {

    // Atribut dienkapsulasi dengan modifier private
    private String nomorRekening;
    private String namaPemilik;
    private double saldo;           // Saldo TIDAK BISA diakses langsung dari luar
    private String pin;

    // KOMPOSISI: BukuMutasi diinstansiasi otomatis di dalam constructor
    // Objek ini adalah bagian tak terpisahkan dari Rekening
    private BukuMutasi bukuMutasi;

    // Constructor: BukuMutasi langsung dibuat saat Rekening dibuat
    public Rekening(String nomorRekening, String namaPemilik, double saldoAwal, String pin) {
        this.nomorRekening = nomorRekening;
        this.namaPemilik = namaPemilik;
        this.saldo = saldoAwal;
        this.pin = pin;
        // KOMPOSISI: Instansiasi otomatis di dalam constructor
        this.bukuMutasi = new BukuMutasi(nomorRekening);
        System.out.println("  [Rekening] Rekening " + nomorRekening
                + " atas nama " + namaPemilik + " berhasil dibuat.");
    }

    // Implementasi interface Otorisasi
    @Override
    public boolean verifikasiPIN(String pin) {
        return this.pin.equals(pin);
    }

    // Enkapsulasi: Getter untuk saldo (read-only dari luar)
    // Saldo HANYA bisa berubah melalui setor() dan tarik()
    public double getSaldo() { return saldo; }
    public String getNomorRekening() { return nomorRekening; }
    public String getNamaPemilik() { return namaPemilik; }

    // Metode setor(): Saldo hanya bisa bertambah lewat sini
    public void setor(double jumlah) {
        if (jumlah <= 0) {
            System.out.println("  [ERROR] Jumlah setoran harus lebih dari 0.");
            return;
        }
        saldo += jumlah;
        bukuMutasi.catatMutasi("SETOR", jumlah, saldo);
        System.out.println("  [OK] Setoran Rp " + String.format("%,.0f", jumlah)
                + " berhasil. Saldo sekarang: Rp " + String.format("%,.0f", saldo));
    }

    // Metode tarik() bersifat ABSTRACT: setiap subclass wajib override
    // Polimorfisme diterapkan di sini
    public abstract void tarik(double jumlah);

    // Helper protected: Subclass bisa mengakses saldo melalui ini
    protected void kurangiSaldo(double jumlah) {
        saldo -= jumlah;
    }

    protected void catatTransaksiKeMutasi(String jenis, double jumlah) {
        bukuMutasi.catatMutasi(jenis, jumlah, saldo);
    }

    // Metode tutupRekening(): Menghancurkan BukuMutasi bersama Rekening
    // Ini membuktikan siklus hidup KOMPOSISI
    public void tutupRekening() {
        System.out.println("  [Rekening] Menutup rekening " + nomorRekening + "...");
        bukuMutasi.tampilkanHistori();
        bukuMutasi.tutup();       // BukuMutasi dihancurkan bersama Rekening
        bukuMutasi = null;        // Referensi dilepas: objek menunggu GC
        System.out.println("  [Rekening] Rekening " + nomorRekening + " resmi ditutup.");
    }

    public void tampilkanInfo() {
        System.out.println("  +--------------------------------------------------+");
        System.out.println("  | No. Rekening  : " + nomorRekening);
        System.out.println("  | Nama Pemilik  : " + namaPemilik);
        System.out.println("  | Jenis         : " + getJenisRekening());
        System.out.println("  | Saldo         : Rp " + String.format("%,.0f", saldo));
        System.out.println("  +--------------------------------------------------+");
    }

    // Subclass wajib mendefinisikan jenis rekeningnya
    public abstract String getJenisRekening();
}