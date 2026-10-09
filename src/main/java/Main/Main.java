package Main;

import controller.ManajemenStok;
import View.MainView;

public class Main {
    public static void main(String[] args) {
        ManajemenStok app = new ManajemenStok();
        MainView view = new MainView(app);
        view.tampilkanMenu();
    }
}