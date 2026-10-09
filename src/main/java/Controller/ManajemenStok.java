package controller;

import Controller.ObatDAO;
import Model.Obat;
import java.util.ArrayList;

public class ManajemenStok {
    private final ObatDAO obatDAO = new ObatDAO();

    public boolean tambahObat(Obat obat) {
        return obatDAO.tambahObat(obat);
    }
    
    public ArrayList<Obat> getSemuaObat() {
        return obatDAO.getSemuaObat();
    }

    // Overloading 1: Mengubah seluruh informasi obat
    public boolean updateObat(String id, String namaBaru, int stokBaru, double hargaBaru) {
        return obatDAO.updateObat(id, namaBaru, stokBaru, hargaBaru);
    }
    
    // Overloading 2: Hanya untuk menambah stok obat (Restock)
    public boolean updateObat(String id, int tambahanStok) {
        return obatDAO.updateStokObat(id, tambahanStok);
    }

    public boolean hapusObat(String id) {
        return obatDAO.hapusObat(id);
    }

    public Obat cariObatById(String id) {
        return obatDAO.cariObatById(id);
    }
}