package com.github.gquintana.laapin.moteur;

import com.github.gquintana.laapin.Configuration;
import com.github.gquintana.laapin.joueur.Coord;
import com.github.gquintana.laapin.joueur.Joueur;

import java.awt.*;
import java.util.*;
import java.util.List;

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
        if (nbLapins + nbCarottes > taille.x * taille.y) {
            throw new IllegalArgumentException("Trop de carottes/lapins");
        }
        Set<Coord> coordUtilisees = new HashSet<>();
        List<Carotte> carottes = creerCarottes(taille, nbCarottes, coordUtilisees);
        List<Lapin> lapins = creerLapins(taille, nbLapins, coordUtilisees);
        return new Grille(taille, lapins, carottes);
    }

    private List<Carotte> creerCarottes(Coord taille, int nbCarottes, Set<Coord> coordUtilisees) {
        List<Carotte> carottes = new ArrayList<>(nbCarottes);
        while (carottes.size() < nbCarottes) {
            Coord coord = random(taille);
            if (!coordUtilisees.contains(coord)) {
                carottes.add(new Carotte(coord));
                coordUtilisees.add(coord);
            }
        }
        return carottes;
    }

    private List<Lapin> creerLapins(Coord taille, int nbLapins, Set<Coord> coordUtilisees) {
        List<Lapin> lapins = new ArrayList<>(nbLapins);
        int indexLapin = 0;
        while (lapins.size() < nbLapins) {
            Coord coord = random(taille);
            if (!coordUtilisees.contains(coord)) {
                String prefixConfig = "lapin." + indexLapin;
                String classConfig = prefixConfig + ".class";
                try {
                    Joueur joueur = Joueur.class.cast(configuration.getClass(classConfig).newInstance());
                    String nom = configuration.getString(prefixConfig+".nom");
                    Color couleur = configuration.getColor(prefixConfig+".couleur");
                    lapins.add(new Lapin(coord, nom, couleur, null, 0, joueur));
                    coordUtilisees.add(coord);
                } catch (ReflectiveOperationException e) {
                    throw new IllegalArgumentException("Configuration " + classConfig, e);
                }
                indexLapin++;
            }
        }
        return lapins;
    }
}
