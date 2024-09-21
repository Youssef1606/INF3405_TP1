import java.util.HashMap;
import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;

public class CLIApp {
	public static void main(String[] args) throws IOException {
		CLIApp app = new CLIApp();
		app.promptSet("> ");
		app.addCommand("example", new CLICommandExample());
		app.addCommand("mkdir", new CLICommandMkdir());
		app.addCommand("ls", new CLICommandLs());
		app.promptPrint();
		app.inputCommand("example hello!");
		app.promptPrint();
		//app.cdSet("C:\\Users\\basti\\OneDrive\\Bureau\\Travail\\A3\\Montreal\\INF3405\\test"); // test avec le chemin de bastien
		//app.inputCommand("mkdir marche");
		//app.inputCommand("ls");
		app.promptPrint();
		app.inputCommand("example hello!");
	}
	private String prompt;
	private HashMap<String, CLICommand> commands;
	final private String inputExit = "exit";
	private String cd;
	public CLIApp() {
		prompt = "";
		cd = "";
		commands = new HashMap<String, CLICommand>();
	}
	public void inputCommand(String input) throws IOException {
		String[] inputStrings = input.split(" ");
		if (0 == inputStrings.length) return;
		if (1 == inputStrings.length && inputStrings[0].equals(inputExit)) {
			System.out.println(inputStrings[0]);
			return;
		}
		CLICommand command = commands.get(inputStrings[0]);
		if (null == command) {
			System.out.println(inputStrings[0] + ": no such command.");
			return;
		}
		String[] args = new String[inputStrings.length - 1];
		System.arraycopy(inputStrings, 1, args, 0, args.length);
		command.argsSet(args);
		command.execute(this);
	}
	public CLICommand addCommand(String name, CLICommand command) {
		commands.put(name, command);
		return command;
	}
	public void promptSet(String prompt) {
		this.prompt = prompt;
	}
	public String promptGet() {
		return prompt;
	}
	public void promptPrint() {
		System.out.print(prompt);
	}
	public String cdGet() {
		return cd;
	}
	public void cdSet(String cd) {
		if (cd.charAt(0) != '/') cd = '/' + cd;
		this.cd = cd;
	}
}
