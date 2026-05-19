// CLASS BAN
class Ban {

    private String merk;
    private int ukuranRing;
    public Ban(String merk, int ukuranRing) {

        this.merk = merk;
        this.ukuranRing = ukuranRing;
    }
    public void tampilBan() {

        System.out.println("Merk Ban : " + merk);
        System.out.println("Ukuran Ring : "
                + ukuranRing);

        System.out.println();
    }
}
// CLASS MESIN
class Mesin {

    private String nomorSeri;
    private int kapasitasCC;

    // Constructor
    public Mesin(String nomorSeri,
                 int kapasitasCC) {

        this.nomorSeri = nomorSeri;
        this.kapasitasCC = kapasitasCC;
    }
    public void tampilMesin() {

        System.out.println("Nomor Seri : "
                + nomorSeri);

        System.out.println("Kapasitas CC : "
                + kapasitasCC);

        System.out.println();
    }
}
// CLASS MONTIR
class Montir {
    private String idMontir;
    private String nama;
    public Montir(String idMontir,
                  String nama) {

        this.idMontir = idMontir;
        this.nama = nama;
    }

    public void lakukanQualityControl(Mobil m) {

        System.out.println("QUALITY CONTROL");

        System.out.println("Montir : " + nama);

        System.out.println("Sedang memeriksa mobil : "
                + m.getMerkMobil());

        System.out.println(
                "Status : Gagal Uji Kelayakan");

        System.out.println();
    }
}
// CLASS MOBIL
class Mobil {

    private String merkMobil;
    private String warna;

    private Mesin mesin;

    private Ban[] daftarBan;

    public Mobil(String merkMobil,
                 String warna,
                 String nomorSeri,
                 int kapasitasCC) {

        this.merkMobil = merkMobil;
        this.warna = warna;
        this.mesin =
                new Mesin(
                        nomorSeri,
                        kapasitasCC);

        // Maksimal 4 ban
        daftarBan = new Ban[4];
    }
    public String getMerkMobil() {
        return merkMobil;
    }

    public void pasangSetBan(Ban[] setBan) {

        for (int i = 0;
             i < setBan.length && i < 4;
             i++) {

            daftarBan[i] = setBan[i];
        }
    }
    public void tampilkanSpesifikasi() {

        System.out.println(
                "SPESIFIKASI MOBIL");

        System.out.println(
                "Merk Mobil : "
                        + merkMobil);

        System.out.println(
                "Warna : "
                        + warna);

        System.out.println();

        System.out.println(
                "DATA MESIN");

        mesin.tampilMesin();

        System.out.println(
                "DATA BAN");

        for (Ban ban : daftarBan) {

            if (ban != null) {

                ban.tampilBan();
            }
        }
    }
}
// MAIN CLASS
public class Main {

    public static void main(String[] args) {

        // Membuat 4 ban mandiri
        Ban ban1 =
                new Ban("Bridgestone", 17);

        Ban ban2 =
                new Ban("Bridgestone", 17);

        Ban ban3 =
                new Ban("Bridgestone", 17);

        Ban ban4 =
                new Ban("Bridgestone", 17);


        // Array ban
        Ban[] setBan =
                {ban1, ban2, ban3, ban4};

        // Membuat mobil
        Mobil mobil1 =
                new Mobil(
                        "Toyota Supra",
                        "Merah",
                        "MSN-001",
                        3000);

        // Pasang ban ke mobil
        mobil1.pasangSetBan(setBan);

        // Tampilkan spesifikasi
        mobil1.tampilkanSpesifikasi();

        // Membuat montir
        Montir montir1 =
                new Montir(
                        "M001",
                        "Bilal");
        // Quality Control
        montir1.lakukanQualityControl(
                mobil1);

        // Simulasi mobil dihancurkan
        mobil1 = null;

        System.out.println(
                "Mobil dihancurkan");

        System.out.println();
        // Ban masih ada
        System.out.println(
                "Ban masih tersimpan:");

        ban1.tampilBan();
        // Mesin ikut hilang
        System.out.println(
                "Mesin ikut musnah "
                        + "bersama mobil");
    }
}