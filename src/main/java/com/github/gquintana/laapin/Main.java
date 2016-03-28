package com.github.gquintana.laapin;

import com.github.gquintana.laapin.moteur.*;

import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;

public class Main extends JFrame {
    private final String[] args;
    private Moteur moteur;
    private GrillePanel grillePanel;
    private Grille grille;

    public Main(String[] args) throws IOException {
        this.args = args;
        initMoteur();
        initUI();
    }

    private void initMoteur() throws IOException {
        final Configuration configuration = new Configuration();
        configuration.load();
        if (args.length > 0) {
            try(FileInputStream inputStream = new FileInputStream(args[0])) {
                configuration.load(inputStream);
            }
        }
        moteur = new Moteur(configuration, new MoteurListener() {
            @Override
            public void onDemarrer(Grille grille) {
                Main.this.grille = grille;
            }

            @Override
            public void onAgir(Grille grille, Lapin lapin, ResultatAction resultatAction) {
                if (grillePanel != null) {
                    SwingUtilities.invokeLater(grillePanel::repaint);
                }
            }

            @Override
            public void onArreter(Grille grille) {

            }
        });
        moteur.demarrer();
    }


    private void initUI() throws IOException {
        setLayout(new BorderLayout());
        grillePanel = new GrillePanel(grille);
        add(grillePanel, BorderLayout.CENTER);
        setTitle("Laapin");
        setIconImage(grillePanel.imageLapin);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) throws IOException {
        SwingUtilities.invokeLater(() -> {
            try {
                final Main main = new Main(args);
                main.setVisible(true);
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, e.getMessage(), e.getClass().getSimpleName(), JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}