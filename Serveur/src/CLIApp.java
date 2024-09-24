import java.util.HashMap;
import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;

public class CLIApp {
	public static void main(String[] args) throws IOException {
		CLIApp app = new CLIApp();
		//app.cdSet("C:\\Users\\basti\\OneDrive\\Bureau\\Travail\\A3\\Montreal\\INF3405\\test"); // test avec le chemin de bastien
		//app.inputCommand("mkdir marche");
		//app.inputCommand("ls");
		app.promptPrint();
		app.inputCommand("example hello!");
	}
	private String prompt = "";
	private String cd = "";
	private HashMap<String, CLICommand> commands;
	final private String inputExit = "exit";
	public CLIApp() {
		commands = new HashMap<String, CLICommand>();
		this.promptSet("> ");
		this.addCommand("example", new CLICommandExample());
		this.addCommand("mkdir", new CLICommandMkdir());
		this.addCommand("ls", new CLICommandLs());
		this.addCommand("cd", new CLICommandCd());
                this.cdSet(System.getProperty("user.dir"));
		
	}
	public String inputCommand(String input) throws IOException {
		String[] inputStrings = input.split(" ");
		if (0 == inputStrings.length) return "";
		if (1 == inputStrings.length && inputStrings[0].equals(inputExit)) {
			return inputStrings[0];
		}
		CLICommand command = commands.get(inputStrings[0]);
		if (null == command) {
			return inputStrings[0] + ": no such command.";
		}
		String[] args = new String[inputStrings.length - 1];
		System.arraycopy(inputStrings, 1, args, 0, args.length);
		command.argsSet(args);
		return command.execute(this);
	}
	public CLICommand addCommand(String name, CLICommand command) {
		commands.put(name, command);
		return command;
	}
	public void promptSet(String prompt) {
		this.prompt = prompt;
	}
	public String promptGet() {
		return cd + "> ";
	}
	public void promptPrint() {
		System.out.print(prompt);
	}
	public String cdGet() {
		return cd;
	}
	public void cdSet(String cd) {
		this.cd = cd;
	}
}
