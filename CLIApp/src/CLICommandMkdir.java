import java.io.File;

public class CLICommandMkdir extends CLICommand {
	@Override public void execute(CLIApp app) {
	    if (argIsEmpty(0)) {
	    	System.out.println("Please specify a directory.");
	    	return;
	    }
	    String directoryName = argGet(0);
	    if (directoryName.charAt(0) != '/') directoryName = "/" + directoryName;
	    String fullDirectoryName = app.cdGet() + directoryName;
	    // source du code tiré de [1]: https://stackoverflow.com/questions/28947250/create-a-directory-if-it-does-not-exist-and-then-create-the-files-in-that-direct
	    File directory = new File(fullDirectoryName);
	    if (!directory.exists()){
	        directory.mkdirs();
	    }
	    // fin du code tiré de [1]
	    else System.out.println("Directory " + fullDirectoryName + " already exists.");
	}
}
