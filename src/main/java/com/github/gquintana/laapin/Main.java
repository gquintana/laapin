package com.github.gquintana.laapin;

import com.github.gquintana.laapin.moteur.Grille;
import com.github.gquintana.laapin.moteur.GrilleFactory;
import com.github.gquintana.laapin.moteur.Lapin;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main extends JFrame {

    private Grille grille;
    private GrillePanel grillePanel;
    private Iterator<Lapin> lapinIterator;
    private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

    public Main() throws IOException {
        initMoteur();
        initUI();
    }

    public void demarrer() {
        scheduledExecutorService.scheduleAtFixedRate(this::activerLapin, 0, 3, TimeUnit.SECONDS);
    }

    private void initMoteur() throws IOException {
        Configuration configuration = new Configuration();
        configuration.load();
        GrilleFactory grilleFactory = new GrilleFactory(configuration);
        grille = grilleFactory.creerGrille();

    }

    private void initLapinIterator() {
        List<Lapin> lapinList = new ArrayList<>(grille.lapins);
        Collections.shuffle(lapinList);
        lapinIterator = lapinList.iterator();
    }

    private void activerLapin() {
        if (lapinIterator == null || !lapinIterator.hasNext()) {
            initLapinIterator();
        }
        Lapin lapin = lapinIterator.next();
        grille.reflechir(lapin);
        SwingUtilities.invokeLater(grillePanel::repaint);
    }

    private void initUI() throws IOException {
        setLayout(new BorderLayout());
        grillePanel = new GrillePanel(grille);
        add(grillePanel, BorderLayout.CENTER);
        setTitle("Laapin");
        //setPreferredSize(new Dimension(grillePanel.getWidth()+GrillePanel.RESOLUTION, grillePanel.getHeight()+GrillePanel.RESOLUTION));
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                final Main main = new Main();
                main.setVisible(true);
                main.demarrer();
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, e.getMessage(), e.getClass().getSimpleName(), JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}