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
		app.promptPrint();
		app.inputCommand("example hello!");
		app.promptPrint();
		app.inputCommand("mkdir");
	}
	private String prompt;
	private HashMap<String, CLICommand> commands;
	final private String inputExit = "exit";
	public CLIApp() {
		prompt = "";
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
		command.execute();
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
}
