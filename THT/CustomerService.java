// KELAS: CustomerService
// Entitas mandiri. Berinteraksi dengan Nasabah via ASOSIASI (tidak ada
// kepemilikan; kedua objek berdiri sendiri dan saling tidak bergantung).
public class CustomerService {
    private String namaOfficer;
    private String nomorExt;

    public CustomerService(String namaOfficer, String nomorExt) {
        this.namaOfficer = namaOfficer;
        this.nomorExt = nomorExt;
    }

    // ASOSIASI: Menerima data Nasabah sebagai parameter (bukan menyimpannya)
    public void terimaKeluhan(String namaNasabah, String keluhan) {
        System.out.println("\n  ╔══════════════════════════════════════╗");
        System.out.println("  ║      LAPORAN KELUHAN NASABAH         ║");
        System.out.println("  ╠══════════════════════════════════════╣");
        System.out.println("  ║ Officer  : " + namaOfficer + " (Ext. " + nomorExt + ")");
        System.out.println("  ║ Nasabah  : " + namaNasabah);
        System.out.println("  ║ Keluhan  : " + keluhan);
        System.out.println("  ╠══════════════════════════════════════╣");
        System.out.println("  ║ Status   : Keluhan diterima & dicatat ║");
        System.out.println("  ╚══════════════════════════════════════╝");
        System.out.println("  Terima kasih, " + namaNasabah
                + ". Tim kami akan menindaklanjuti dalam 1x24 jam.");
    }

    public String getNamaOfficer() { return namaOfficer; }
}