import java.io.File;

public class CLICommandMkdir extends CLICommand {
	@Override public String execute(CLIApp app) {
	    if (argIsEmpty(0)) {
	    	return "Spécifiez un dossier.";
	    }
	    String directoryName = argGet(0);
	    String fullDirectoryName = app.cdGet() + File.separator + directoryName;
	    // source du code tiré de [1]: https://stackoverflow.com/questions/28947250/create-a-directory-if-it-does-not-exist-and-then-create-the-files-in-that-direct
	    File directory = new File(fullDirectoryName);
	    if (!directory.exists()){
	        directory.mkdirs();
	        return "Le dossier " + directoryName + " a été créé.";
	    }
	    // fin du code tiré de [1]
	    return "Le dossier " + directoryName + " existe déjà.";
	}
}
