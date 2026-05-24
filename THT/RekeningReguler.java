// CONCRETE CLASS: RekeningReguler
// Turunan dari Rekening. Dikenakan biaya admin Rp 2.500 setiap penarikan.
// POLIMORFISME: Override metode tarik()
public class RekeningReguler extends Rekening {

    private static final double BIAYA_ADMIN = 2500.0;
    private static final double SALDO_MINIMUM = 50000.0;

    public RekeningReguler(String nomorRekening, String namaPemilik,
                           double saldoAwal, String pin) {
        super(nomorRekening, namaPemilik, saldoAwal, pin);
    }

    @Override
    public String getJenisRekening() {
        return "Reguler";
    }

    // POLIMORFISME: Penarikan dikenakan biaya admin tetap Rp 2.500
    @Override
    public void tarik(double jumlah) {
        double totalPotong = jumlah + BIAYA_ADMIN;
        if (jumlah <= 0) {
            System.out.println("  [ERROR] Jumlah penarikan harus lebih dari 0.");
            return;
        }
        if (getSaldo() - totalPotong < SALDO_MINIMUM) {
            System.out.println("  [GAGAL] Saldo tidak mencukupi.");
            System.out.println("         Dibutuhkan: Rp " + String.format("%,.0f", totalPotong)
                    + " (termasuk biaya admin Rp " + String.format("%,.0f", BIAYA_ADMIN) + ")");
            System.out.println("         Saldo saat ini: Rp " + String.format("%,.0f", getSaldo())
                    + " | Saldo minimum: Rp " + String.format("%,.0f", SALDO_MINIMUM));
            return;
        }
        kurangiSaldo(totalPotong);
        catatTransaksiKeMutasi("TARIK", jumlah);
        catatTransaksiKeMutasi("BIAYA ADMIN", BIAYA_ADMIN);
        System.out.println("  [OK] Penarikan Rp " + String.format("%,.0f", jumlah)
                + " + biaya admin Rp " + String.format("%,.0f", BIAYA_ADMIN)
                + " berhasil. Saldo: Rp " + String.format("%,.0f", getSaldo()));
    }
}