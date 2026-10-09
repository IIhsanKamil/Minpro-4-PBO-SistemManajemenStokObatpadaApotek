package Model;

public class ObatResep extends Obat {
    private String namaDokter;

    public ObatResep(String idObat, String namaObat, int stok, double harga, String namaDokter) {
        super(idObat, namaObat, stok, harga);
        this.namaDokter = namaDokter;
    }

    public String getNamaDokter() {
        return namaDokter;
    }

    @Override
    public String getKategoriString() {
        return "Obat Keras";
    }

    @Override
    public void tampilkanInfo() {
        super.tampilkanInfo();
        System.out.printf(" %-22s |\n", "Dokter: " + namaDokter);
    }
}