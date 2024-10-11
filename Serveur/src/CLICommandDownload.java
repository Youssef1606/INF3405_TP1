import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class CLICommandDownload extends CLICommand {
    @Override
    public String execute(CLIApp app) throws IOException {
        DataOutputStream out = app.getOut();
        String fileName = argGet(0);
        File file = new File(app.cdGet(), fileName);
        
        if (!file.isFile()) {
            out.writeUTF("ERROR");
            out.writeUTF("Le fichier spécifié n'existe pas ou n'est pas un fichier.");
            return "";
        }
        
        out.writeUTF("OK");

        try (FileInputStream fileIn = new FileInputStream(file)) {
            out.writeLong(file.length());
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = fileIn.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }

        return "Le fichier " + fileName + " a bien été téléchargé.";
    }
}
