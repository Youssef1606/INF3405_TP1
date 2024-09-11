import java.util.HashMap;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CLIApp {
	public static void main(String[] args) throws IOException {
		CLIApp app = new CLIApp();
		app.promptSet("> ");
		app.addCommand("example", new CLICommandExample());
		while (app.running == true) {
			app.inputCommand();
		}
	}
	
	private String prompt = "";
	private HashMap<String, CLICommand> commands;
	private BufferedReader bufferedReader;
	private boolean running = true;
	final private String inputExit = "exit";
	public CLIApp() {
		commands = new HashMap<String, CLICommand>();
		bufferedReader = new BufferedReader(
				new InputStreamReader(System.in));
	}
	public void inputCommand() throws IOException {
		System.out.print(prompt);
		String[] inputStrings = bufferedReader.readLine().split(" ");
		if (0 == inputStrings.length) return;
		if (1 == inputStrings.length && inputStrings[0].equals(inputExit)) {
			running = false;
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
		command.args_set(args);
		command.execute(args);
	}
	public CLICommand addCommand(String name, CLICommand command) {
		commands.put(name, command);
		return command;
	}
	public void promptSet(String prompt) {
		this.prompt = prompt;
	}
}
