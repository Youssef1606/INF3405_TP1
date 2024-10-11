import java.io.File;
public class CLICommandCd extends CLICommand {
	
	@Override public String execute(CLIApp app) {
        if (argIsEmpty(0)) {
            return "Veuillez spécifier un dossier.";
        }
        
        String target = argGet(0);
        String currentPath = app.cdGet();
        File newDirectory;

        if (target.equals("..")) {
            // Se déplacer vers le répertoire parent
            File currentDir = new File(currentPath);
            File parentDir = currentDir.getParentFile();
            if (parentDir != null && parentDir.exists()) {
                newDirectory = parentDir;
                target = parentDir.getName();
            } else {
                return "Vous êtes déjà dans le répertoire racine.";
            }
        } else {
            // Se déplacer vers un répertoire enfant ou absolu
            File potentialDir;
            if (target.startsWith(File.separator) || target.startsWith("\\")) {
                // Chemin absolu
                potentialDir = new File(target);
            } else {
                // Chemin relatif
                potentialDir = new File(currentPath, target);
            }

            if (potentialDir.exists() && potentialDir.isDirectory()) {
                newDirectory = potentialDir;
            } else {
                return "Le répertoire spécifié n'existe pas.";
            }
        }
        // Mettre à jour le répertoire courant
        app.cdSet(newDirectory.getAbsolutePath());
        return "Vous êtes dans le dossier " + target + ".";
    }
}

