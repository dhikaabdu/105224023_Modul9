// INTERFACE: Otorisasi
// Menjadi kontrak yang mewajibkan semua kelas implementor
// untuk menyediakan metode verifikasiPIN().
public interface Otorisasi {
    boolean verifikasiPIN(String pin);
}