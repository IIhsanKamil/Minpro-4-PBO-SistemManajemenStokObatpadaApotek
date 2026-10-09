package Controller;

import config.KoneksiDatabase;
import Model.Obat;
import Model.ObatBebas;
import Model.ObatResep;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ObatDAO {

    // CREATE
    public boolean tambahObat(Obat obat) {
        String query = "INSERT INTO obat (id_obat, nama_obat, stok, harga, kategori, keterangan_khusus) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = KoneksiDatabase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, obat.getIdObat());
            pstmt.setString(2, obat.getNamaObat());
            pstmt.setInt(3, obat.getStok());
            pstmt.setDouble(4, obat.getHarga());
            pstmt.setString(5, obat.getKategoriString());
            
            // Menyimpan keterangan khusus berdasarkan jenis turunannya
            if (obat instanceof ObatBebas) {
                pstmt.setString(6, ((ObatBebas) obat).getEfekSamping());
            } else if (obat instanceof ObatResep) {
                pstmt.setString(6, ((ObatResep) obat).getNamaDokter());
            } else {
                pstmt.setString(6, "-");
            }
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Gagal menambah obat: " + e.getMessage());
            return false;
        }
    }

    // READ
    public ArrayList<Obat> getSemuaObat() {
        ArrayList<Obat> daftarObat = new ArrayList<>();
        String query = "SELECT * FROM obat";
        
        try (Connection conn = KoneksiDatabase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                String id = rs.getString("id_obat");
                String nama = rs.getString("nama_obat");
                int stok = rs.getInt("stok");
                double harga = rs.getDouble("harga");
                String kategori = rs.getString("kategori");
                String ketKhusus = rs.getString("keterangan_khusus");
                
                // Instansiasi objek berdasarkan kategori
                if (kategori.equalsIgnoreCase("Obat Bebas")) {
                    daftarObat.add(new ObatBebas(id, nama, stok, harga, ketKhusus));
                } else {
                    daftarObat.add(new ObatResep(id, nama, stok, harga, ketKhusus));
                }
            }
        } catch (SQLException e) {
            System.out.println("Gagal mengambil data obat: " + e.getMessage());
        }
        return daftarObat;
    }

    // Cari data berdasarkan ID
    public Obat cariObatById(String idCari) {
        String query = "SELECT * FROM obat WHERE id_obat = ?";
        try (Connection conn = KoneksiDatabase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, idCari);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String id = rs.getString("id_obat");
                    String nama = rs.getString("nama_obat");
                    int stok = rs.getInt("stok");
                    double harga = rs.getDouble("harga");
                    String kategori = rs.getString("kategori");
                    String ketKhusus = rs.getString("keterangan_khusus");
                    
                    if (kategori.equalsIgnoreCase("Obat Bebas")) {
                        return new ObatBebas(id, nama, stok, harga, ketKhusus);
                    } else {
                        return new ObatResep(id, nama, stok, harga, ketKhusus);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Gagal mencari obat: " + e.getMessage());
        }
        return null;
    }

    // UPDATE 1: Mengubah seluruh informasi obat
    public boolean updateObat(String id, String namaBaru, int stokBaru, double hargaBaru) {
        String query = "UPDATE obat SET nama_obat = ?, stok = ?, harga = ? WHERE id_obat = ?";
        try (Connection conn = KoneksiDatabase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, namaBaru);
            pstmt.setInt(2, stokBaru);
            pstmt.setDouble(3, hargaBaru);
            pstmt.setString(4, id);
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Gagal update obat: " + e.getMessage());
            return false;
        }
    }

    // UPDATE 2: Restock (Menambah stok saja)
    public boolean updateStokObat(String id, int tambahanStok) {
        String query = "UPDATE obat SET stok = stok + ? WHERE id_obat = ?";
        try (Connection conn = KoneksiDatabase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, tambahanStok);
            pstmt.setString(2, id);
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Gagal restock obat: " + e.getMessage());
            return false;
        }
    }

    // DELETE
    public boolean hapusObat(String id) {
        String query = "DELETE FROM obat WHERE id_obat = ?";
        try (Connection conn = KoneksiDatabase.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Gagal menghapus obat: " + e.getMessage());
            return false;
        }
    }
}