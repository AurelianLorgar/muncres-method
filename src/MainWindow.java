// This is a personal academic project. Dear PVS-Studio, please check it.
// PVS-Studio Static Code Analyzer for C, C++, C#, and Java: http://www.viva64.com

import javax.swing.*;

class MainWindow extends JFrame{

    private static final long serialVersionUID = 1L;

    private MainWindow() {

        setTitle("Метод Манкреса");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(980, 450);
        setLocation(250, 75);
        add(new MainField());
        setVisible(true);
        setResizable(false);

    }

    public static void main(final String[] args) {
        new MainWindow();
    }
}
