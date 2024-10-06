import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class CLICommandDownload extends CLICommand {
	public String execute(CLIApp app) throws IOException {
		File file = new File(argGet(0));
		if (!file.isFile()) {
			return "Le fichier entré n'est pas valide.";
		}
		try (FileInputStream fileIn = new FileInputStream(file)) {
	        byte[] buffer = new byte[4096];
	        int bytesRead;
	        app.getOut().writeLong(file.length());
	        while ((bytesRead = fileIn.read(buffer)) != -1) {
	            app.getOut().write(buffer, 0, bytesRead);
	        }
		}
		return "Download réussi!";
	}
}
