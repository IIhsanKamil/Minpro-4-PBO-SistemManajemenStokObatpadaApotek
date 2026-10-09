package Model;

public class ObatBebas extends Obat {
    private String efekSamping;

    public ObatBebas(String idObat, String namaObat, int stok, double harga, String efekSamping) {
        super(idObat, namaObat, stok, harga);
        this.efekSamping = efekSamping;
    }
    
    public String getEfekSamping() {
        return efekSamping;
    }
    @Override
    public String getKategoriString() {
        return "Obat Bebas";
    }

    @Override
    public void tampilkanInfo() {
        super.tampilkanInfo();
        System.out.printf(" %-22s |\n", "Efek: " + efekSamping);
    }
}