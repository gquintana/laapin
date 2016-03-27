package com.github.gquintana.laapin.moteur;

import com.github.gquintana.laapin.joueur.Action;
import com.github.gquintana.laapin.joueur.Coord;
import com.github.gquintana.laapin.joueur.TypeAction;

import java.util.*;
import java.util.stream.Collectors;

public class Grille {
    public final Coord taille;
    public final List<Lapin> lapins;
    public final List<Carotte> carottes;
    public Lapin dernierLapin;
    public String dernierMessage;
    private final Map<TypeAction, Commande> commandes;

    public Grille(Coord taille, List<Lapin> lapins, List<Carotte> carottes) {
        this.taille = taille;
        this.lapins = new ArrayList<>(lapins);
        this.carottes = new ArrayList<>(carottes);
        Map<TypeAction, Commande> lCommandes = new HashMap<>();
        lCommandes.put(TypeAction.AVANCER, new AvancerCommande());
        lCommandes.put(TypeAction.FRAPPER, new FrapperCommande());
        lCommandes.put(TypeAction.SAUTER, new SauterCommande());
        lCommandes.put(TypeAction.SE_REPOSER, new SeReposerCommande());
        commandes = Collections.unmodifiableMap(lCommandes);
    }

    public boolean contient(Coord coord) {
        return coord.x >= 0 && coord.y >= 0 && coord.x < taille.x && coord.y < taille.y;
    }

    public Lapin getLapin(Coord coord) {
        return lapins.stream().filter(l -> l.coord.equals(coord)).findFirst().orElse(null);
    }

    public Carotte getCarotte(Coord coord) {
        return carottes.stream().filter(l -> l.coord.equals(coord)).findFirst().orElse(null);
    }

    public void reflechir(Lapin leLapin) {
        Action action = null;
        boolean seReposer = leLapin.fatigue > 0;
        dernierMessage = null;
        if (!seReposer) {
            try {
                com.github.gquintana.laapin.joueur.Grille grillePhoto = photographier();
                com.github.gquintana.laapin.joueur.Lapin leLapinPhoto = leLapin.photographier();
                System.out.println(leLapin + " reflechir");
                action = leLapin.joueur.reflechir(leLapinPhoto, grillePhoto);
            } catch (Exception e) {
                e.printStackTrace();
                setDernierMessage(e.getMessage());
                seReposer = true;
            }
        }
        if (seReposer || action == null) {
            action = new Action(TypeAction.SE_REPOSER, null);
        }
        Commande commande = commandes.get(action.type);
        commande.executer(leLapin, this, action);
        System.out.println(String.format("Lapin %s: %s %s %s", leLapin.nom, action.type, action.direction, dernierMessage));
        leLapin.derniereAction = action;
    }

    public com.github.gquintana.laapin.joueur.Grille photographier() {
        List<com.github.gquintana.laapin.joueur.Lapin> lapinsPhoto = lapins.stream()
                .map(Lapin::photographier)
                .collect(Collectors.toList());
        List<com.github.gquintana.laapin.joueur.Carotte> carottesPhoto = carottes.stream()
                .map(Carotte::photographier)
                .collect(Collectors.toList());
        return new com.github.gquintana.laapin.joueur.Grille(taille, lapinsPhoto, carottesPhoto);
    }

    public void supprimerCarotte(Carotte carotte) {
        carottes.remove(carotte);
    }

    public void setDernierMessage(String dernierMessage) {
        this.dernierMessage = dernierMessage;
    }
}
