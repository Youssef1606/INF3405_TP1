import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class CLICommandUpload extends CLICommand {

	@Override public String execute(CLIApp app) {
		String fileName = argGet(0);
		
		String status;
		try {
			status = app.getIn().readUTF();
			if ("ERROR".equals(status)) {
		        // Le client a indiqué une erreur
		        String errorMessage = app.getIn().readUTF();
		        System.out.println("Erreur du client : " + errorMessage);
		        app.getOut().writeUTF("Erreur du client : " + errorMessage);
		        return "";
		    } else if (!"OK".equals(status)) {
		        // Réponse inattendue du client
		        System.out.println("Réponse inattendue du client : " + status);
		        app.getOut().writeUTF("Réponse inattendue du client : " + status);
		        return "";
		    }
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// reconstituer le fichier à partir d'octets
		
		//Etape 1 : verifier si on peut récuperer
		try {
			File Nvfile = new File(app.cdGet() + File.separator + fileName);
			
			Long fileSize = app.getIn().readLong();
			
			try (FileOutputStream fileOut = new FileOutputStream(Nvfile)) {
	            byte[] buffer = new byte[4096];
	            long bytesReceived = 0;
	            int bytesRead;
	            
	            while (bytesReceived < fileSize && (bytesRead = app.getIn().read(buffer, 0, (int)Math.min(buffer.length, fileSize - bytesReceived))) != -1) {
	                fileOut.write(buffer, 0, bytesRead);
	                bytesReceived += bytesRead;
	            }
	        }
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "Le fichier " + fileName + " a bien été téléversé.";
	}
}
