import java.util.Scanner;
// KELAS UTAMA: NeoBank (Main)
// Antarmuka pengguna berbasis terminal dengan menu interaktif.
public class NeoBank {

    static Scanner scanner = new Scanner(System.in);
    static Nasabah nasabah = null;
    static Rekening rekeningLogin = null;
    static CustomerService cs = new CustomerService("Budi Santoso", "101");

    // Counter untuk generate nomor rekening otomatis
    static int counterRekening = 1000;

    public static void main(String[] args) {

        System.out.println("╔══════════════════════════════════════════════════╗");
        System.out.println("║         SELAMAT DATANG DI NEOBANK SYSTEM         ║");
        System.out.println("║         Digital Banking — Secure & Smart         ║");
        System.out.println("╚══════════════════════════════════════════════════╝");

        boolean jalan = true;
        while (jalan) {
            tampilkanMenuUtama();
            int pilihan = bacaInt("Pilihan Anda: ");

            switch (pilihan) {
                case 1 -> registrasiNasabah();
                case 2 -> bukaPilihanRekening();
                case 3 -> loginRekening();
                case 4 -> menuTransaksi();
                case 5 -> hubungiCS();
                case 6 -> tampilkanProfil();
                case 7 -> {
                    simulasiPenutupanAkun();
                    jalan = false;
                }
                default -> System.out.println("  [!] Pilihan tidak valid. Silakan coba lagi.");
            }
        }

        scanner.close();
        System.out.println("\n  Terima kasih telah menggunakan NeoBank. Sampai jumpa!");
    }

    // MENU UTAMA
    static void tampilkanMenuUtama() {
        System.out.println("\n══════════════════════════════════════════");
        System.out.println("  MENU UTAMA NEOBANK");
        System.out.println("══════════════════════════════════════════");
        System.out.println("  1. Registrasi Nasabah Baru");
        System.out.println("  2. Buka Rekening Baru");
        System.out.println("  3. Login ke Rekening");
        System.out.println("  4. Transaksi (Setor / Tarik)");
        System.out.println("  5. Hubungi Customer Service");
        System.out.println("  6. Lihat Profil Nasabah");
        System.out.println("  7. Tutup Sesi & Keluar");
        System.out.println("══════════════════════════════════════════");
    }

    // FITUR 1: Registrasi Nasabah
    static void registrasiNasabah() {
        System.out.println("\n--- REGISTRASI NASABAH BARU ---");
        if (nasabah != null) {
            System.out.println("  [!] Sesi sudah ada profil nasabah: " + nasabah.getNama());
            System.out.println("  [!] Silakan tutup sesi untuk mendaftarkan nasabah baru.");
            return;
        }
        System.out.print("  Nama lengkap : ");
        String nama = scanner.nextLine().trim();
        System.out.print("  Email        : ");
        String email = scanner.nextLine().trim();

        String id = "NSB-" + System.currentTimeMillis() % 10000;
        nasabah = new Nasabah(id, nama, email);
        System.out.println("  [SUKSES] Profil nasabah berhasil dibuat!");
    }

    // FITUR 2: Buka Rekening
    static void bukaPilihanRekening() {
        System.out.println("\n--- BUKA REKENING BARU ---");
        if (nasabah == null) {
            System.out.println("  [!] Harap registrasi profil nasabah terlebih dahulu (Menu 1).");
            return;
        }
        System.out.println("  Pilih Jenis Rekening:");
        System.out.println("  1. Rekening Reguler  (biaya admin Rp 2.500/tarik, min saldo Rp 50.000)");
        System.out.println("  2. Rekening Prioritas (bebas admin, min tarik Rp 500.000, min saldo Rp 1.000.000)");
        int pilihan = bacaInt("  Pilihan: ");

        System.out.print("  Buat PIN (6 digit): ");
        String pin = scanner.nextLine().trim();
        double saldoAwal = bacaDouble("  Setoran awal (Rp): ");

        String noRek = "NEO-" + (++counterRekening);
        Rekening rekening;

        if (pilihan == 1) {
            rekening = new RekeningReguler(noRek, nasabah.getNama(), saldoAwal, pin);
        } else if (pilihan == 2) {
            rekening = new RekeningPrioritas(noRek, nasabah.getNama(), saldoAwal, pin);
        } else {
            System.out.println("  [ERROR] Pilihan tidak valid.");
            return;
        }

        nasabah.tambahRekening(rekening);
        System.out.println("  [SUKSES] Rekening " + noRek + " telah ditambahkan ke profil Anda.");
    }

    // FITUR 3: Login ke Rekening
    static void loginRekening() {
        System.out.println("\n--- LOGIN KE REKENING ---");
        if (nasabah == null || nasabah.getJumlahRekening() == 0) {
            System.out.println("  [!] Tidak ada rekening yang terdaftar.");
            return;
        }

        System.out.println("  Daftar rekening Anda:");
        for (int i = 0; i < nasabah.getJumlahRekening(); i++) {
            Rekening r = nasabah.getRekening(i);
            System.out.println("  " + (i + 1) + ". " + r.getNomorRekening()
                    + " [" + r.getJenisRekening() + "]");
        }

        int idx = bacaInt("  Pilih nomor rekening: ") - 1;
        Rekening target = nasabah.getRekening(idx);
        if (target == null) {
            System.out.println("  [ERROR] Pilihan tidak valid.");
            return;
        }

        System.out.print("  Masukkan PIN: ");
        String pin = scanner.nextLine().trim();

        // Memanggil metode verifikasiPIN() dari interface Otorisasi
        if (target.verifikasiPIN(pin)) {
            rekeningLogin = target;
            System.out.println("  [SUKSES] Login berhasil! Selamat datang, "
                    + rekeningLogin.getNamaPemilik() + ".");
        } else {
            System.out.println("  [GAGAL] PIN salah. Akses ditolak.");
        }
    }

    // FITUR 4: Menu Transaksi (Setor & Tarik)
    static void menuTransaksi() {
        System.out.println("\n--- MENU TRANSAKSI ---");
        if (rekeningLogin == null) {
            System.out.println("  [!] Anda belum login ke rekening manapun (Menu 3).");
            return;
        }

        System.out.println("  Rekening aktif: " + rekeningLogin.getNomorRekening()
                + " [" + rekeningLogin.getJenisRekening() + "]");
        System.out.println("  Saldo saat ini: Rp "
                + String.format("%,.0f", rekeningLogin.getSaldo()));
        System.out.println();
        System.out.println("  1. Setor Dana");
        System.out.println("  2. Tarik Dana");
        System.out.println("  3. Kembali ke Menu Utama");
        int pilihan = bacaInt("  Pilihan: ");

        if (pilihan == 1) {
            double jumlah = bacaDouble("  Jumlah setoran (Rp): ");
            rekeningLogin.setor(jumlah);
        } else if (pilihan == 2) {
            double jumlah = bacaDouble("  Jumlah penarikan (Rp): ");
            // POLIMORFISME: Memanggil tarik() — JVM akan memanggil
            // implementasi yang sesuai (Reguler atau Prioritas) secara otomatis
            rekeningLogin.tarik(jumlah);
        } else if (pilihan == 3) {
            return;
        } else {
            System.out.println("  [!] Pilihan tidak valid.");
        }
    }

    // FITUR 5: Hubungi Customer Service (ASOSIASI)
    static void hubungiCS() {
        System.out.println("\n--- HUBUNGI CUSTOMER SERVICE ---");
        if (nasabah == null) {
            System.out.println("  [!] Harap registrasi profil nasabah terlebih dahulu.");
            return;
        }

        System.out.println("  Terhubung dengan CS Officer: " + cs.getNamaOfficer());
        System.out.print("  Tuliskan keluhan Anda: ");
        String keluhan = scanner.nextLine().trim();

        // ASOSIASI: Nasabah berinteraksi dengan CS tanpa saling memiliki.
        // cs.terimaKeluhan() hanya menerima data String, bukan menyimpan referensi Nasabah.
        cs.terimaKeluhan(nasabah.getNama(), keluhan);
    }

    // FITUR 6: Tampilkan Profil
    static void tampilkanProfil() {
        System.out.println("\n--- PROFIL NASABAH ---");
        if (nasabah == null) {
            System.out.println("  [!] Belum ada profil nasabah yang aktif.");
            return;
        }
        nasabah.tampilkanProfil();
    }

    // FITUR 7: Simulasi Penutupan Akun & Analisis Siklus Hidup Objek
    static void simulasiPenutupanAkun() {

        System.out.println("\n╔══════════════════════════════════════════════════════════╗");
        System.out.println("║     SIMULASI PENUTUPAN AKUN & ANALISIS SIKLUS HIDUP      ║");
        System.out.println("╚══════════════════════════════════════════════════════════╝");

        if (nasabah == null) {
            System.out.println("  [!] Tidak ada sesi aktif untuk ditutup.");
            return;
        }

        System.out.println("\n[LANGKAH 1] Simpan referensi rekening secara independen.");
        System.out.println("  Ini mensimulasikan prinsip AGREGASI: rekening adalah entitas");
        System.out.println("  mandiri yang bisa disimpan di luar profil nasabah.\n");

        // AGREGASI — Simpan referensi rekening ke variabel lokal SEBELUM nasabah di-null.
        // Ini membuktikan bahwa Rekening tidak bergantung pada siklus hidup Nasabah.
        Rekening[] rekeningIndependen = new Rekening[nasabah.getJumlahRekening()];
        for (int i = 0; i < nasabah.getJumlahRekening(); i++) {
            rekeningIndependen[i] = nasabah.getRekening(i);
            System.out.println("  -> Referensi rekening " + rekeningIndependen[i].getNomorRekening()
                    + " disimpan secara independen di memori.");
        }

        System.out.println("\n[LANGKAH 2] Menutup rekening (KOMPOSISI): BukuMutasi dihancurkan bersama.");
        System.out.println("  Saat tutupRekening() dipanggil, BukuMutasi yang ada di dalamnya");
        System.out.println("  akan di-null dan ikut musnah bersama Rekening (prinsip KOMPOSISI).\n");

        for (Rekening r : rekeningIndependen) {
            if (r != null) {
                r.tutupRekening();
                // Setelah tutupRekening(), BukuMutasi internal sudah null (dihancurkan).
                // Ini membuktikan: BukuMutasi tidak bisa hidup tanpa Rekening = KOMPOSISI.
            }
        }

        System.out.println("\n[LANGKAH 3] Menghapus profil nasabah dari memori (di-set null).");
        System.out.println("  Profil Nasabah: " + nasabah.getNama() + " akan dihapus...\n");

        nasabah = null; // <-- Profil nasabah dihancurkan / dihapus dari memori aktif
        rekeningLogin = null;

        System.out.println("  -> nasabah = null; (profil nasabah dihapus dari sesi aktif)");
        System.out.println("  -> rekeningLogin = null;");

        System.out.println("\n[LANGKAH 4] Verifikasi AGREGASI — rekening MASIH BISA diakses.");
        System.out.println("  Meskipun profil nasabah sudah null, rekening yang tersimpan di");
        System.out.println("  variabel lokal masih dapat diakses. Ini MEMBUKTIKAN AGREGASI:\n");

        for (Rekening r : rekeningIndependen) {
            if (r != null) {
                System.out.println("  [AGREGASI TERBUKTI] Rekening " + r.getNomorRekening()
                        + " (" + r.getJenisRekening() + ") masih accessible!");
                System.out.println("  Saldo terakhir: Rp " + String.format("%,.0f", r.getSaldo()));
            }
        }

        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║                  RINGKASAN ANALISIS OOP                     ║");
        System.out.println("╠══════════════════════════════════════════════════════════════╣");
        System.out.println("║ KOMPOSISI (Rekening ◆── BukuMutasi):                        ║");
        System.out.println("║   BukuMutasi dibuat OTOMATIS di dalam constructor Rekening. ║");
        System.out.println("║   Saat tutupRekening() dipanggil, BukuMutasi di-null dan    ║");
        System.out.println("║   menunggu Garbage Collector. BukuMutasi tidak bisa berdiri ║");
        System.out.println("║   sendiri tanpa Rekening = hubungan KUAT (strong ownership).║");
        System.out.println("╠══════════════════════════════════════════════════════════════╣");
        System.out.println("║ AGREGASI (Nasabah ◇── Rekening):                            ║");
        System.out.println("║   Rekening adalah entitas mandiri. Meskipun objek Nasabah   ║");
        System.out.println("║   di-set null, referensi Rekening di variabel lain masih    ║");
        System.out.println("║   valid dan dapat diakses = hubungan LEMAH (loose coupling).║");
        System.out.println("╠══════════════════════════════════════════════════════════════╣");
        System.out.println("║ ASOSIASI (Nasabah ── CustomerService):                      ║");
        System.out.println("║   Nasabah dan CS adalah entitas sepenuhnya independen.      ║");
        System.out.println("║   Interaksi terjadi melalui parameter metode, bukan melalui ║");
        System.out.println("║   kepemilikan. Keduanya bisa ada tanpa satu sama lain.      ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
    }

    // HELPER: Baca input integer dengan validasi
    static int bacaInt(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int val = Integer.parseInt(scanner.nextLine().trim());
                return val;
            } catch (NumberFormatException e) {
                System.out.println("  [!] Input harus berupa angka.");
            }
        }
    }

    // HELPER: Baca input double dengan validasi
    static double bacaDouble(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                double val = Double.parseDouble(scanner.nextLine().trim().replace(",", ""));
                return val;
            } catch (NumberFormatException e) {
                System.out.println("  [!] Input harus berupa angka.");
            }
        }
    }
}