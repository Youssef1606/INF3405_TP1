
public class CLICommandExample extends CLICommand {
	@Override public void execute(CLIApp app) {
		System.out.println(argGet(0));
	}
}
