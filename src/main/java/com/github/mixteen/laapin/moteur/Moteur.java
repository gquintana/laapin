package com.github.mixteen.laapin.moteur;

import com.github.mixteen.laapin.Configuration;
import com.github.mixteen.laapin.joueur.Action;
import com.github.mixteen.laapin.joueur.TypeAction;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Boucle principale du jeu
 */
public class Moteur {
    private Grille grille;
    private final Configuration configuration;
    private Random random;
    private Iterator<Lapin> lapinIterator;
    private final ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
    private ScheduledFuture<?> scheduledFuture;
    private final MoteurListener listener;
    private final Map<TypeAction, ActionImpl> commandes;
    private boolean estEnMarche = false;

    public Moteur(Configuration configuration, MoteurListener listener) {
        this.configuration = configuration;
        this.listener = listener;
        Map<TypeAction, ActionImpl> lCommandes = new HashMap<>();
        lCommandes.put(TypeAction.AVANCER, new AvancerActionImpl());
        lCommandes.put(TypeAction.FRAPPER, new FrapperActionImpl());
        lCommandes.put(TypeAction.SAUTER, new SauterActionImpl());
        lCommandes.put(TypeAction.SE_REPOSER, new SeReposerActionImpl());
        commandes = Collections.unmodifiableMap(lCommandes);
    }

    Moteur(Configuration configuration, MoteurListener listener, Grille grille) {
        this(configuration, listener);
        this.grille = grille;
    }

    public void initialiser() {
        int randomSeed = configuration.getInt("random", -1);
        this.random = randomSeed < 0 ? new Random() : new Random(randomSeed);
        GrilleFactory grilleFactory = new GrilleFactory(configuration, random);
        grille = grilleFactory.creerGrille();
        listener.onInitialiser(grille);
    }

    public boolean estEnMarche() {
        return estEnMarche;
    }

    public void demarrer() {
        scheduledFuture = scheduledExecutorService.scheduleAtFixedRate(this::avancer, 0, configuration.getInt("moteur.periode", 2000), TimeUnit.MILLISECONDS);
        if (grille.lapins().isEmpty()) {
            listener.onArreter(grille);
        } else {
            estEnMarche = true;
            initLapinIterator();
        }
    }

    private void initLapinIterator() {
        List<Lapin> lapinList = new ArrayList<>(grille.lapins());
        if (configuration.getBoolean("moteur.shuffle", true)) {
            Collections.shuffle(lapinList, random);
        }
        lapinIterator = lapinList.iterator();
    }

    public void avancer() {
        if (lapinIterator == null || !lapinIterator.hasNext()) {
            initLapinIterator();
        }
        Lapin lapin = lapinIterator.next();
        reflechir(lapin);
    }

    ResultatAction reflechir(Lapin leLapin) {
        Action action = null;
        boolean seReposer = leLapin.estFatigue();
        String messageErreur = null;
        // Détermine l'action a effectuer
        if (!seReposer) {
            try {
                com.github.mixteen.laapin.joueur.Grille grillePhoto = grille.photographier();
                com.github.mixteen.laapin.joueur.Lapin leLapinPhoto = leLapin.photographier();
                System.out.println(leLapin + " reflechir");
                action = leLapin.joueur.reflechir(leLapinPhoto, grillePhoto);
            } catch (Exception e) {
                e.printStackTrace();
                messageErreur = e.getMessage() == null ? e.getClass().getSimpleName() : e.getMessage();
                seReposer = true;
            }
        }
        if (seReposer || action == null) {
            action = new Action(TypeAction.SE_REPOSER, null);
        }
        // Applique l'action
        ActionImpl actionImpl = commandes.get(action.type);
        ResultatAction resultatAction = actionImpl.executer(leLapin, grille, action);
        if (messageErreur != null) {
            resultatAction = new ResultatAction(leLapin, resultatAction.action, messageErreur);
        }
        System.out.println(String.format("Lapin %s: %s %s %s", leLapin.nom, action.type, action.direction, resultatAction.message));
        leLapin.agir(action);
        listener.onAgir(grille, leLapin, resultatAction);
        if (!grille.lutinStream(Carotte.class).findAny().isPresent()) {
            stopper();
            listener.onArreter(grille);
        }
        return resultatAction;
    }

    public void stopper() {
        if (scheduledFuture != null && !scheduledFuture.isCancelled()) {
            scheduledFuture.cancel(false);
        }
        estEnMarche = false;
        grille.lutinStream(Lapin.class).sorted(Comparator.comparingInt(Lapin::score).reversed())
                .forEach(l -> System.out.println(l + " " + l.score()));
    }

    public void arreter() {
        stopper();
        scheduledExecutorService.shutdown();
    }

}
