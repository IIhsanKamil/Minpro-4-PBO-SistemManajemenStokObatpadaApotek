package View;

import controller.ManajemenStok;
import Model.Obat;
import Model.ObatBebas;
import Model.ObatResep;
import Utils.ValidasiInput;
import java.util.ArrayList;
import java.util.Scanner;

public class MainView {
    private final ManajemenStok app;
    private final Scanner scanner;

    public MainView(ManajemenStok app) {
        this.app = app;
        this.scanner = new Scanner(System.in);
    }

    public void tampilkanMenu() {
        boolean running = true;

        while (running) {
            System.out.println("\n=== SISTEM MANAJEMEN STOK OBAT APOTEK ===");
            System.out.println("1. Tampilkan Semua Obat (Read)");
            System.out.println("2. Tambah Obat Baru (Create)");
            System.out.println("3. Ubah Data Obat (Update)");
            System.out.println("4. Hapus Obat (Delete)");
            System.out.println("5. Keluar");
            System.out.print("Pilih menu (1-5): ");

            String pilihan = scanner.nextLine().trim();

            switch (pilihan) {
                case "1":
                    tampilkanTabelObat();
                    break;

                case "2":
                    System.out.println("\n--- TAMBAH OBAT BARU ---");
                    System.out.print("Masukkan ID Obat: ");
                    String id = scanner.nextLine().trim();
                    
                    if (app.cariObatById(id) != null) {
                        System.out.println("ID Obat sudah terdaftar! Gunakan menu update.");
                        break;
                    }

                    System.out.print("Masukkan Nama Obat: ");
                    String nama = scanner.nextLine().trim();
                    
                    // Menggunakan validasi ketat
                    int stok = ValidasiInput.inputIntPositif(scanner, "Masukkan Jumlah Stok: ");
                    double harga = ValidasiInput.inputDoublePositif(scanner, "Masukkan Harga Obat: ");

                    System.out.println("\nPilih Kategori Obat:\n1. Bebas\n2. Keras");
                    System.out.print("Pilih (1/2): ");
                    String katPilih = scanner.nextLine().trim();

                    // Menggunakan switch-case untuk pemilihan kategori
                    switch (katPilih) {
                        case "1":
                            System.out.print("Masukkan Efek Samping: ");
                            String efekSamping = scanner.nextLine().trim();
                            app.tambahObat(new ObatBebas(id, nama, stok, harga, efekSamping));
                            System.out.println("Data obat bebas berhasil ditambahkan!");
                            break;
                        case "2":
                            System.out.print("Masukkan Nama Dokter: ");
                            String namaDokter = scanner.nextLine().trim();
                            app.tambahObat(new ObatResep(id, nama, stok, harga, namaDokter));
                            System.out.println("Data obat resep berhasil ditambahkan!");
                            break;
                        default:
                            System.out.println("Kategori tidak valid! Batal menambahkan data.");
                            break;
                    }
                    break;

                    case "3":
                    System.out.println("\n--- UBAH DATA OBAT ---");
                    System.out.print("Masukkan ID Obat yang ingin diubah: ");
                    String idUpdate = scanner.nextLine().trim();
                    
                    if (app.cariObatById(idUpdate) != null) {
                        System.out.println("Pilih Jenis Update:");
                        System.out.println("1. Ubah Seluruh Data (Nama, Stok, Harga)");
                        System.out.println("2. Tambah Stok Saja (Restock)");
                        System.out.print("Pilih (1/2): ");
                        String opsiUpdate = scanner.nextLine().trim();

                        switch (opsiUpdate) {
                            case "1":
                                System.out.print("Masukkan Nama Baru: ");
                                String namaBaru = scanner.nextLine().trim();
                                int stokBaru = ValidasiInput.inputIntPositif(scanner, "Masukkan Stok Baru: ");
                                double hargaBaru = ValidasiInput.inputDoublePositif(scanner, "Masukkan Harga Baru: ");

                                // Memanggil Overloading 1 (4 parameter)
                                if (app.updateObat(idUpdate, namaBaru, stokBaru, hargaBaru)) {
                                    System.out.println("Seluruh data obat berhasil diubah!");
                                }
                                break;
                            
                            case "2":
                                int tambahStok = ValidasiInput.inputIntPositif(scanner, "Masukkan Jumlah Stok yang Ditambahkan: ");
                                
                                // Memanggil Overloading 2 (2 parameter)
                                if (app.updateObat(idUpdate, tambahStok)) {
                                    System.out.println("Stok obat berhasil ditambahkan!");
                                }
                                break;
                                
                            default:
                                System.out.println("Opsi tidak valid! Batal mengubah data.");
                                break;
                        }
                    } else {
                        System.out.println("Gagal Update! ID Obat tidak ditemukan.");
                    }
                    break;

                case "4":
                    System.out.print("Masukkan ID Obat yang ingin dihapus: ");
                    String idHapus = scanner.nextLine().trim();
                    if (app.hapusObat(idHapus)) {
                        System.out.println("Obat berhasil dihapus!");
                    } else {
                        System.out.println("Gagal Hapus! ID Obat tidak ditemukan.");
                    }
                    break;

                case "5":
                    running = false;
                    System.out.println("Terima kasih!");
                    break;

                default:
                    System.out.println("Input tidak dikenali! Harap masukkan angka 1-5.");
            }
        }
    }

    private void tampilkanTabelObat() {
        ArrayList<Obat> daftar = app.getSemuaObat();
        
        if (daftar.isEmpty()) {
            System.out.println("Stok obat masih kosong.");
            return;
        }

        System.out.println("\n--------------------------------------------------------------------------------------------------");
        System.out.printf("| %-8s | %-18s | %-15s | %-6s | %-13s | %-22s |\n", "ID", "Nama Obat", "Kategori", "Stok", "Harga", "Keterangan Khusus");
        System.out.println("--------------------------------------------------------------------------------------------------");

        for (Obat o : daftar) {
            o.tampilkanInfo();
        }
        System.out.println("--------------------------------------------------------------------------------------------------");
    }
}