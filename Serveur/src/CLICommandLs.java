import java.io.File;

public class CLICommandLs extends CLICommand{
	
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_BLUE = "\u001B[34m";
	
	@Override public void execute(CLIApp app) {
		File repertory = new File(app.cdGet());
		File[] files = repertory.listFiles();
		
		for(int i = 0; i < files.length ; i++) {
			if (files[i].isFile()) {
				System.out.println(ANSI_BLUE + files[i].getName() + ANSI_RESET);
			}
			else if(files[i].isDirectory()){
				System.out.println(ANSI_GREEN + files[i].getName() + ANSI_RESET);
			}
		}
	
	
	}
}
