import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class CLICommandDelete extends CLICommand {
	
	private final String directorySeparator = "/";
	
	@Override public String execute(CLIApp app) {
		String fileName = argGet(0);
		File fileToDelete = new File(app.cdGet() + directorySeparator + fileName);
		boolean isFile = fileToDelete.isFile();
		
		Path pathToDelete = fileToDelete.toPath();
		
		try {
			// source du code [1] : https://stackoverflow.com/questions/779519/delete-directories-recursively-in-java
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
			// fin du code de [1]
		} catch (IOException e) {
			e.printStackTrace();
			return fileName + " is not a directory.";
		}
		
		if (isFile) {
			return "Fichier " + fileName + " supprimé!";
		}
		else {
			return "Dossier " + fileName + " supprimé!";
		}
	}
}
