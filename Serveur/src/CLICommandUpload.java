import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class CLICommandUpload extends CLICommand {
	@Override public String execute(CLIApp app) {
		String fileName = argsGet();
		//Integer fileLength = ToInteger(argGet(1));
		// reconstituer le fichier à partir d'octets
		
		//Etape 1 : verifier si on peut récuperer
		try {
			
			File Nvfile = new File(app.cdGet() + "\\" + fileName);
			System.out.println(app.cdGet()+ "\\" + fileName);
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "Upload réussi !";
	}
}
