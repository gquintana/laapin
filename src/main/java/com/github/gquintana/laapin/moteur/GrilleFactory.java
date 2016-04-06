package com.github.gquintana.laapin.moteur;

import com.github.gquintana.laapin.Configuration;
import com.github.gquintana.laapin.Resources;
import com.github.gquintana.laapin.joueur.Coord;
import com.github.gquintana.laapin.joueur.Joueur;
import com.github.gquintana.laapin.joueur.ScriptJoueur;
import javafx.scene.paint.Color;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

class GrilleFactory {
    private final Configuration configuration;
    private final Random random;

    public GrilleFactory(Configuration configuration, Random random) {
        this.configuration = configuration;
        this.random = random;
    }

    private Coord randomCoord(Coord taille) {
        return new Coord(random.nextInt(taille.x), random.nextInt(taille.y));
    }

    private Coord randomCoord(Coord taille, Set<Coord> coordUtilisees) {
        for (int i = 0; i < 2 * taille.x * taille.y; i++) {
            Coord coord = randomCoord(taille);
            if (!coordUtilisees.contains(coord)) {
                return coord;
            }
        }
        throw new IllegalStateException("Cellulle vide introuvable");
    }

    /**
     * Crée ou charge un grille à partir de la configuration
     */
    public Grille creerGrille() {
        Coord taille = new Coord(
                configuration.getInt("grille.taille.x", 20),
                configuration.getInt("grille.taille.y", 20));
        List<Lutin> lutins = new ArrayList<>();
        Set<Coord> coordUtilisees = new HashSet<>();
        // Chargement de la grille
        String modele = configuration.getString("grille.modele");
        if (modele != null) {
            try (InputStream inputStream = Resources.openStream(modele)) {
                lutins.addAll(chargerLutins(taille, inputStream, coordUtilisees));
            } catch (IOException ioExc) {
                throw new IllegalArgumentException("Erreur lecture grille " + modele);
            }
        }
        int nbLutins = lutins.size();
        // Génération aléatoire de la grille
        int nbCarottes = configuration.getInt("carottes", modele == null ? 5 : 0);
        int nbLapins = configuration.getInt("lapins", modele == null ? 1 : 0);
        int nbRochers = configuration.getInt("rochers", 0);
        if (nbLutins + nbLapins + nbCarottes > taille.x * taille.y) {
            throw new IllegalArgumentException("Trop de lutins");
        }
        lutins.addAll(creerCarottes(taille, nbCarottes, coordUtilisees));
        int indexLapin = (int) lutins.stream().filter(l -> l instanceof Lapin).count();
        lutins.addAll(creerLapins(taille, indexLapin, nbLapins, coordUtilisees));
        lutins.addAll(creerRochers(taille, nbRochers, coordUtilisees));
        return new Grille(taille, lutins);
    }

    /**
     * Charge une grille depuis une fichier texte <ul>
     * <li>C pour carotte</li>
     * <li>R pour rocher</li>
     * <li>L pour lapin</li>
     * </ul>
     */
    private List<Lutin> chargerLutins(Coord taille, InputStream inputStream, Set<Coord> coordUtilisees) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        String ligne;
        int y = 0;
        List<Lutin> lutins = new ArrayList<>();
        int indexLapin = 0;
        while ((ligne = reader.readLine()) != null && y < taille.y) {
            for (int x = 0; x < taille.x && x < ligne.length(); x++) {
                char c = Character.toUpperCase(ligne.charAt(x));
                Coord coord = new Coord(x, y);
                Lutin lutin = null;
                switch (c) {
                    case 'C':
                        lutin = creerCarotte(coord, 0);
                        break;
                    case 'R':
                        lutin = creerRocher(coord, 0);
                        break;
                    case 'L':
                        lutin = creerLapin(coord, indexLapin++);
                        break;
                }
                if (lutin != null) {
                    lutins.add(lutin);
                    coordUtilisees.add(coord);
                }
            }
            y++;
        }
        return lutins;
    }

    private interface LutinFactory<T extends Lutin> {
        T creerLutin(Coord coord, int index);
    }

    private <T extends Lutin> List<T> creerLutins(Coord taille, int indexLutin, int nbLutins, Set<Coord> coordUtilisees, LutinFactory<T> lutinFactory) {
        List<T> lutins = new ArrayList<>(nbLutins);
        while (lutins.size() < nbLutins) {
            Coord coord = randomCoord(taille, coordUtilisees);
            lutins.add(lutinFactory.creerLutin(coord, indexLutin));
            coordUtilisees.add(coord);
            indexLutin++;
        }
        return lutins;
    }

    private List<Carotte> creerCarottes(Coord taille, int nbCarottes, Set<Coord> coordUtilisees) {
        return creerLutins(taille, 0, nbCarottes, coordUtilisees, this::creerCarotte);
    }

    private Carotte creerCarotte(Coord coord, int index) {
        return new Carotte(coord);
    }

    private List<Rocher> creerRochers(Coord taille, int nbRochers, Set<Coord> coordUtilisees) {
        return creerLutins(taille, 0, nbRochers, coordUtilisees, this::creerRocher);
    }

    private Rocher creerRocher(Coord coord, int index) {
        return new Rocher(coord);
    }

    private List<Lapin> creerLapins(Coord taille, int indexLapin, int nbLapins, Set<Coord> coordUtilisees) {
        return creerLutins(taille, indexLapin, nbLapins, coordUtilisees, this::creerLapin);
    }

    private Lapin creerLapin(Coord coord, int indexLapin) {
        String prefixConfig = "lapin." + indexLapin;
        String classConfig = prefixConfig + ".class";
        try {
            @SuppressWarnings("unchecked")
            Class<Joueur> classeJoueur = configuration.getClass(classConfig);
            Joueur joueur;
            if (classeJoueur == null || classeJoueur.equals(ScriptJoueur.class)) {
                String scriptConfig = prefixConfig + ".script";
                String nomScript = configuration.getString(scriptConfig);
                if (nomScript == null) {
                    throw new IllegalArgumentException("Configuration "+scriptConfig + " manquante");
                }
                joueur = new ScriptJoueur(nomScript);
            } else {
                joueur = Joueur.class.cast(classeJoueur.newInstance());
            }
            String nom = configuration.getString(prefixConfig + ".nom");
            Color couleur = configuration.getColor(prefixConfig + ".couleur");
            return new Lapin(coord, nom, couleur, null, 0, joueur);
        } catch (ReflectiveOperationException e) {
            throw new IllegalArgumentException("Configuration " + classConfig, e);
        }
    }
}
