// CONCRETE CLASS: RekeningPrioritas
// Turunan dari Rekening. Bebas biaya admin, tapi minimum tarik Rp 500.000.
// POLIMORFISME: Override metode tarik()
public class RekeningPrioritas extends Rekening {

    private static final double MIN_PENARIKAN = 500000.0;
    private static final double SALDO_MINIMUM = 1000000.0;

    public RekeningPrioritas(String nomorRekening, String namaPemilik,
                             double saldoAwal, String pin) {
        super(nomorRekening, namaPemilik, saldoAwal, pin);
    }

    @Override
    public String getJenisRekening() {
        return "Prioritas";
    }

    // POLIMORFISME: Bebas biaya admin, tapi ada batas minimum penarikan
    @Override
    public void tarik(double jumlah) {
        if (jumlah <= 0) {
            System.out.println("  [ERROR] Jumlah penarikan harus lebih dari 0.");
            return;
        }
        if (jumlah < MIN_PENARIKAN) {
            System.out.println("  [GAGAL] Penarikan ditolak. Rekening Prioritas mensyaratkan");
            System.out.println("         minimum penarikan Rp " + String.format("%,.0f", MIN_PENARIKAN) + ".");
            return;
        }
        if (getSaldo() - jumlah < SALDO_MINIMUM) {
            System.out.println("  [GAGAL] Saldo tidak mencukupi.");
            System.out.println("         Saldo minimum Rekening Prioritas: Rp "
                    + String.format("%,.0f", SALDO_MINIMUM));
            System.out.println("         Saldo setelah tarik: Rp "
                    + String.format("%,.0f", getSaldo() - jumlah));
            return;
        }
        kurangiSaldo(jumlah);
        catatTransaksiKeMutasi("TARIK (PRIORITAS - BEBAS ADMIN)", jumlah);
        System.out.println("  [OK] Penarikan Rp " + String.format("%,.0f", jumlah)
                + " berhasil (bebas biaya admin). Saldo: Rp "
                + String.format("%,.0f", getSaldo()));
    }
}