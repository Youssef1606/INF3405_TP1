import java.io.IOException;
import java.util.ArrayList;

public class CLICommand {
	private ArrayList<String> args;
	protected String argGet(int index) {
		if (index < args.size()) return args.get(index);
		return "";
	}
	public void argsSet(ArrayList<String> args) {
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
