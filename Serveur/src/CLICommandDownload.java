import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class CLICommandDownload extends CLICommand {
    public String execute(CLIApp app) throws IOException {
        DataOutputStream out = app.getOut();
        File file = new File(app.cdGet(), argGet(0));
        if (!file.isFile()) {
            // Envoyer un code de statut pour indiquer l'échec
            out.writeUTF("ERROR");
            out.writeUTF("Le fichier entré n'est pas valide.");
            return ""; // Pas besoin de message supplémentaire
        }

        // Envoyer un code de statut pour indiquer le succès
        out.writeUTF("OK");
        try (FileInputStream fileIn = new FileInputStream(file)) {
            out.writeLong(file.length());
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = fileIn.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }
	    // pas de messages supplementaire ici pour eviter une desynchronisation
        return ""; 
    }
}
