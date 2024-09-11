
public class CLICommand {
	protected String[] args;
	protected String arg_get(int index) {
		if (index < args.length) return args[index];
		return "";
	}
	public void args_set(String[] args) {
		this.args = args;
	}
	public void execute(String[] args) {
		
	}
}
