import java.io.File;

public class CLICommandDelete extends CLICommand {
	
	private final String directorySeparator = "/";
	
	@Override public String execute(CLIApp app) {
		String fileName = argGet(0);
		File fileToDelete = new File(app.cdGet() + directorySeparator + fileName);
		
		// TODO: delete folders with files inside
		
		// if isFile() ... fichier ... supprimé!
		
		// if isDirectory() ... dossier ... supprimé!
		
		
		if (!(fileToDelete.isFile()) && !(fileToDelete.isDirectory())) {
			return fileName + " is not a directory.";
		}
		
		fileToDelete.delete();
		
		return "Successfully deleted " + fileName + "!";
	}
}
