package com.github.gquintana.laapin.moteur;

import com.github.gquintana.laapin.Configuration;
import com.github.gquintana.laapin.joueur.Coord;
import com.github.gquintana.laapin.joueur.Joueur;
import javafx.scene.paint.Color;

import java.util.*;

public class GrilleFactory {
    public final Configuration configuration;
    public final Random random;

    public GrilleFactory(Configuration configuration, Random random) {
        this.configuration = configuration;
        this.random = random;
    }

    private Coord random(Coord taille) {
        return new Coord(random.nextInt(taille.x), random.nextInt(taille.y));
    }

    public Grille creerGrille() {
        Coord taille = new Coord(
                configuration.getInt("grille.taille.x", 20),
                configuration.getInt("grille.taille.y", 20));
        int nbCarottes = configuration.getInt("carottes", 5);
        int nbLapins = configuration.getInt("lapins", 1);
        int nbRochers= configuration.getInt("rochers", 0);
        if (nbLapins + nbCarottes > taille.x * taille.y) {
            throw new IllegalArgumentException("Trop de carottes/lapins");
        }
        Set<Coord> coordUtilisees = new HashSet<>();
        List<Carotte> carottes = creerCarottes(taille, nbCarottes, coordUtilisees);
        List<Lapin> lapins = creerLapins(taille, nbLapins, coordUtilisees);
        List<Rocher> rochers = creerRochers(taille, nbRochers, coordUtilisees);
        List<Lutin> lutins = new ArrayList<>(carottes);
        lutins.addAll(lapins);
        lutins.addAll(rochers);
        return new Grille(taille, lutins);
    }

    private interface LutinFactory<T extends Lutin> {
        T creerLutin(Coord coord, int index);
    }
    private <T extends Lutin> List<T> creerLutins(Coord taille, int nbLutins, Set<Coord> coordUtilisees, LutinFactory<T> lutinFactory) {
        List<T> lutins = new ArrayList<>(nbLutins);
        int indexLutin = 0;
        while (lutins.size() < nbLutins) {
            Coord coord = random(taille);
            if (!coordUtilisees.contains(coord)) {
                lutins.add(lutinFactory.creerLutin(coord, indexLutin));
                coordUtilisees.add(coord);
                indexLutin++;
            }
        }
        return lutins;
    }

    private List<Carotte> creerCarottes(Coord taille, int nbCarottes, Set<Coord> coordUtilisees) {
        return creerLutins(taille, nbCarottes, coordUtilisees, (coord, index) -> new Carotte(coord));
    }

    private List<Rocher> creerRochers(Coord taille, int nbRochers, Set<Coord> coordUtilisees) {
        return creerLutins(taille, nbRochers, coordUtilisees, (coord, index) -> new Rocher(coord));
    }

    private List<Lapin> creerLapins(Coord taille, int nbLapins, Set<Coord> coordUtilisees) {
        return creerLutins(taille, nbLapins, coordUtilisees, this::creerLapin);
    }

    private Lapin creerLapin(Coord coord,int indexLapin) {
        String prefixConfig = "lapin." + indexLapin;
        String classConfig = prefixConfig + ".class";
        try {
            Joueur joueur = Joueur.class.cast(configuration.getClass(classConfig).newInstance());
            String nom = configuration.getString(prefixConfig+".nom");
            Color couleur = configuration.getColor(prefixConfig+".couleur");
            return new Lapin(coord, nom, couleur, null, 0, joueur);
        } catch (ReflectiveOperationException e) {
            throw new IllegalArgumentException("Configuration " + classConfig, e);
        }
    }
}
