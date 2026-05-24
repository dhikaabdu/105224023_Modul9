// KELAS: Nasabah
// Menerapkan AGREGASI dengan Rekening: nasabah menyimpan array berisi
// maksimal 3 Rekening. Rekening adalah entitas mandiri yang TIDAK hancur
// ketika objek Nasabah dihapus — mereka tetap ada sebagai entitas independen.
public class Nasabah {
    private String idNasabah;
    private String nama;
    private String email;

    // AGREGASI: Array Rekening — loose-coupling, rekening bisa hidup sendiri
    private Rekening[] daftarRekening;
    private int jumlahRekening;
    private static final int MAKS_REKENING = 3;

    public Nasabah(String idNasabah, String nama, String email) {
        this.idNasabah = idNasabah;
        this.nama = nama;
        this.email = email;
        // AGREGASI: Array disiapkan, tapi Rekening diisi dari luar (tidak dibuat di sini)
        this.daftarRekening = new Rekening[MAKS_REKENING];
        this.jumlahRekening = 0;
        System.out.println("  [Nasabah] Profil nasabah " + nama + " (ID: " + idNasabah + ") berhasil dibuat.");
    }

    public boolean tambahRekening(Rekening rekening) {
        if (jumlahRekening >= MAKS_REKENING) {
            System.out.println("  [ERROR] Profil nasabah sudah memiliki " + MAKS_REKENING + " rekening (batas maksimum).");
            return false;
        }
        daftarRekening[jumlahRekening++] = rekening;
        System.out.println("  [OK] Rekening " + rekening.getNomorRekening()
                + " (" + rekening.getJenisRekening() + ") ditambahkan ke profil " + nama + ".");
        return true;
    }

    public Rekening getRekening(int index) {
        if (index < 0 || index >= jumlahRekening) return null;
        return daftarRekening[index];
    }

    public int getJumlahRekening() { return jumlahRekening; }
    public String getNama() { return nama; }
    public String getIdNasabah() { return idNasabah; }
    public String getEmail() { return email; }

    public void tampilkanProfil() {
        System.out.println("\n  ┌─── PROFIL NASABAH ─────────────────────────┐");
        System.out.println("  │ ID      : " + idNasabah);
        System.out.println("  │ Nama    : " + nama);
        System.out.println("  │ Email   : " + email);
        System.out.println("  │ Rekening: " + jumlahRekening + "/" + MAKS_REKENING);
        System.out.println("  └────────────────────────────────────────────┘");
        for (int i = 0; i < jumlahRekening; i++) {
            System.out.println("  [Rekening #" + (i + 1) + "]");
            daftarRekening[i].tampilkanInfo();
        }
    }

    // AGREGASI NOTE: Saat Nasabah "dihapus" (di-null dari Main),
    // objek Rekening di dalam array ini TIDAK ikut hancur karena
    // variabel lain di luar kelas masih bisa menyimpan referensinya.
}