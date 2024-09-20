
public class CLICommandExample extends CLICommand {
	@Override public void execute() {
		System.out.println(argGet(0));
	}
}
