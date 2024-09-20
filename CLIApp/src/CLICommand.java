
public class CLICommand {
	private String[] args;
	protected String argGet(int index) {
		if (index < args.length) return args[index];
		return "";
	}
	public void argsSet(String[] args) {
		this.args = args;
	}
	public Boolean argIsEmpty(int index) {
		return argGet(index).equals("");
	}
	public void execute() {}
}
