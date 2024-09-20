
public class CLICommandExample extends CLICommand {
	@Override public void execute(String[] args) {
		System.out.println(argGet(0));
	}
}
