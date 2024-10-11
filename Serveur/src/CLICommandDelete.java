import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class CLICommandDelete extends CLICommand {
	
	@Override public String execute(CLIApp app) {
		String fileName = argGet(0);
		
		if (fileName == "") {
			return "Veuillez spécifier un dossier.";
		}
		
		File fileToDelete = new File(app.cdGet() + File.separator + fileName);
		boolean isFile = fileToDelete.isFile();
		
		if (!isFile && !fileToDelete.isDirectory()) {
			return "Le dossier spécifié n'existe pas.";
		}
		
		Path pathToDelete = fileToDelete.toPath();
		
		// source du code [1] : https://stackoverflow.com/questions/779519/delete-directories-recursively-in-java
		try {
			Files.walkFileTree(pathToDelete, new SimpleFileVisitor<Path>() {
				   @Override
				   public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
				       Files.delete(file);
				       return FileVisitResult.CONTINUE;
				   }

				   @Override
				   public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
				       Files.delete(dir);
				       return FileVisitResult.CONTINUE;
				   }
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		// fin du code de [1]
		
		if (isFile) {
			return "Le fichier " + fileName + " a bien été supprimé.";
		}
		else {
			return "Le dossier " + fileName + " a bien été supprimé.";
		}
	}
}
