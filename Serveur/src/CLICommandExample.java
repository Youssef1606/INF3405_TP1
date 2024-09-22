
public class CLICommandExample extends CLICommand {
	@Override public String execute(CLIApp app) {
		return argGet(0);
	}
}
