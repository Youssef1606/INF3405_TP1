import java.io.IOException;

public class CLICommand {
	private String[] args;
	protected String argGet(int index) {
		if (index < args.length) return args.getItem(index);
		return "";
	}
	public void argsSet(String[] args) {
		this.args = args;
	}
	public Boolean argIsEmpty(int index) {
		return argGet(index).equals("");
	}
	public String execute(CLIApp app) throws IOException {
		return "";
	}
	protected ArrayList<String> argsGet() {
		return this.args;
	}
}
