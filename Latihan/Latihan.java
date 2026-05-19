// CLASS PASIEN
class Pasien {

    private String nama;
    private int umur;
    public Pasien(String nama, int umur) {
        this.nama = nama;
        this.umur = umur;
    }
    public String getNama() {
        return nama;
    }

    public int getUmur() {
        return umur;
    }
}
// CLASS DOKTER
class Dokter {

    private String nama;
    private String spesialisasi;

    public Dokter(String nama, String spesialisasi) {
        this.nama = nama;
        this.spesialisasi = spesialisasi;
    }
    public String getNama() {
        return nama;
    }
    public void periksaPasien(Pasien pasien) {

        System.out.println("=== REKAM MEDIS ===");

        System.out.println("Dokter : " + nama);
        System.out.println("Spesialisasi : "
                + spesialisasi);

        System.out.println("Pasien : "
                + pasien.getNama());

        System.out.println("Umur Pasien : "
                + pasien.getUmur());

        System.out.println(nama
                + " sedang memeriksa pasien "
                + pasien.getNama());

        System.out.println();
    }
}
// CLASS RUANGAN
class Ruangan {

    private String nomorRuangan;
    private int kapasitas;
    public Ruangan(String nomorRuangan,
                    int kapasitas) {

        this.nomorRuangan = nomorRuangan;
        this.kapasitas = kapasitas;
    }
    public void tampilRuangan() {

        System.out.println("Nomor Ruangan : "
                + nomorRuangan);

        System.out.println("Kapasitas : "
                + kapasitas + " pasien");

        System.out.println();
    }
}
// CLASS RUMAHSAKIT
class RumahSakit {

    private String namaRumahSakit;
    private Ruangan[] daftarRuangan;
    private Dokter[] daftarDokter;
    public RumahSakit(String namaRumahSakit) {

        this.namaRumahSakit = namaRumahSakit;
        daftarRuangan = new Ruangan[2];

        daftarRuangan[0] =
                new Ruangan("R-01", 5);

        daftarRuangan[1] =
                new Ruangan("R-02", 10);
        daftarDokter = new Dokter[2];
    }
    public void tambahDokter(Dokter dokter,
                              int index) {

        daftarDokter[index] = dokter;
    }

    public void tampilkanRuangan() {

        System.out.println("DAFTAR RUANGAN");

        for (Ruangan ruangan : daftarRuangan) {

            ruangan.tampilRuangan();
        }
    }
    public void tampilkanDokter() {

        System.out.println("DAFTAR DOKTER");

        for (Dokter dokter : daftarDokter) {

            if (dokter != null) {

                System.out.println(
                        dokter.getNama());
            }
        }

        System.out.println();
    }
}
// MAIN CLASS
public class Main {

    public static void main(String[] args) {
        // Membuat 2 dokter
        Dokter dokter1 =
                new Dokter("Dr. Tirta",
                        "Jantung");

        Dokter dokter2 =
                new Dokter("Dr. Gia",
                        "Anak");

        // Membuat 2 pasien
        Pasien pasien1 =
                new Pasien("Andhika", 20);

        Pasien pasien2 =
                new Pasien("Alfan", 30);
        dokter1.periksaPasien(pasien1);

        dokter2.periksaPasien(pasien2);
        RumahSakit rs =
                new RumahSakit(
                        "RS Sehat selalu");

        rs.tambahDokter(dokter1, 0);
        rs.tambahDokter(dokter2, 1);

        rs.tampilkanRuangan();

        rs.tampilkanDokter();

        rs = null;

        System.out.println(
                "Objek Rumah Sakit dihapus");
        // Dokter masih ada
        System.out.println(
                "Dokter masih tersimpan:");

        System.out.println(
                dokter1.getNama());

        System.out.println(
                dokter2.getNama());

    }
}