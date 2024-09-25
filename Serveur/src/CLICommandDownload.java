import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class CLICommandDownload extends CLICommand {
	public String execute(CLIApp app) {
		String relativeFilePath = argGet(0);
		File downloadedFile = new File(app.cdGet() + relativeFilePath);
		// lire la longueur du fichier et la stocker dans une variable
		Long fileSize = app.getIn().readLong();
		// reçevoir les données du fichier dans un tampon
		try (FileOutputStream fileOut = new FileOutputStream(Nvfile)) {
            byte[] buffer = new byte[4096];
            long bytesReceived = 0;
            int bytesRead;
            // lire le tampon jusqu'à atteindre le nombre d'octets désiré
            while (bytesReceived < fileSize && (bytesRead = app.getIn().read(buffer, 0, (int)Math.min(buffer.length, fileSize - bytesReceived))) != -1) {
            	downloadedFile.write(buffer, 0, bytesRead);
                bytesReceived += bytesRead;
            }
        }
		// envoyer au Serveur une confirmation de réception
		return "Download réussi!";
	}
}
