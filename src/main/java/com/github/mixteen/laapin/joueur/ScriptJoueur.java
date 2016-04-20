package com.github.mixteen.laapin.joueur;

import com.github.mixteen.laapin.Resources;

import javax.script.*;
import java.io.IOException;
import java.io.Reader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScriptJoueur implements Joueur {
    private static final ScriptEngineManager ENGINE_MANAGER = engineManager();
    private final ScriptEngine moteur;
    private final String nom;
    private CompiledScript script;
    private static final Pattern EXTENSION_PATTERN = Pattern.compile("^.*?\\.([A-Za-z]+)$");
    private Action action;

    public ScriptJoueur(String nom) {
        this.nom = nom;
        String extension = scriptExtension(nom);
        moteur = ENGINE_MANAGER.getEngineByExtension(extension);
        System.out.println(String.format("Script %s %s %s", nom, extension, moteur.getClass().getSimpleName()));
        if (moteur instanceof Compilable) {
            Compilable compilable = (Compilable) moteur;
            try (Reader reader = Resources.openReader(nom)) {
                script = compilable.compile(reader);
            } catch (IOException | ScriptException e) {
                throw new IllegalStateException("Erreur compilation du script " + nom, e);
            }
        }
    }

    private static ScriptEngineManager engineManager() {
        ScriptEngineManager engineManager = new ScriptEngineManager();
        for (ScriptEngineFactory engineFactory : engineManager.getEngineFactories()) {
            StringBuilder engineDescription = new StringBuilder("Moteur de script ");
            engineDescription.append(engineFactory.getEngineName()).append(" v").append(engineFactory.getEngineVersion()).append(", ");
            engineDescription.append(engineFactory.getLanguageName()).append(" v").append(engineFactory.getLanguageVersion()).append(", ");
            for (String extension : engineFactory.getExtensions()) {
                engineDescription.append(extension).append(' ');
            }
            System.out.println(engineDescription);
        }
        return engineManager;
    }

    private static String scriptExtension(String script) {
        Matcher matcher = EXTENSION_PATTERN.matcher(script);
        if (matcher.matches()) {
            return matcher.group(1);
        }
        throw new IllegalArgumentException("Pas dans d'extension dans le script " + script);
    }

    @Override
    public Action reflechir(Lapin monLapin, Grille grille) {
        try {
            if (script != null) {
                script.eval(preparerBindings(monLapin, grille));
            } else {
                try (Reader reader = Resources.openReader(nom)) {
                    moteur.eval(reader, preparerBindings(monLapin, grille));
                } catch (IOException e) {
                    throw new IllegalStateException("Erreur lecture script " + nom, e);
                }
            }
            return action;
        } catch (ScriptException e) {
            throw new IllegalStateException("Erreur execution script " + nom, e);
        }
    }

    private Bindings preparerBindings(Lapin monLapin, Grille grille) {
        Bindings bindings = new SimpleBindings();
        bindings.put("monLapin", monLapin);
        bindings.put("grille", grille);
        action = null;
        bindings.put("joueur", this);
        return bindings;
    }

    public class ActionBuilder extends Action.Builder {

        public ActionBuilder(TypeAction typeAction) {
            super(typeAction);
        }

        @Override
        public Action vers(Direction direction) {
            Action action = super.vers(direction);
            agir(action);
            return action;
        }
    }

    public void agir(Action action) {
        this.action = action;
    }

    public ActionBuilder avancer() {
        return new ActionBuilder(TypeAction.AVANCER);
    }

    public ActionBuilder frapper() {
        return new ActionBuilder(TypeAction.FRAPPER);
    }

    public ActionBuilder sauter() {
        return new ActionBuilder(TypeAction.SAUTER);
    }

    public Action seReposer() {
        Action action = new Action(TypeAction.SE_REPOSER, null);
        agir(action);
        return action;
    }
}
