import java.io.File;

public class CLICommandLs extends CLICommand{
	
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_BLUE = "\u001B[34m";
	
	@Override public String execute(CLIApp app) {
		File repertory = new File(app.cdGet());
		String result = "";
		if (repertory.isDirectory()) {
			File[] files = repertory.listFiles();
			
			for(int i = 0; i < files.length ; i++) {
				if (files[i].isFile()) {
					result += ANSI_BLUE + "[File] " + files[i].getName() + ANSI_RESET + "\n";
				}
				else if(files[i].isDirectory()){
					result += ANSI_GREEN + "[Folder] " + files[i].getName() + ANSI_RESET + "\n";
				}
			}
		}
		else {
			result = "Vous ne vous trouvez pas dans un repertoire.";
		}
		return result;
	}
}
