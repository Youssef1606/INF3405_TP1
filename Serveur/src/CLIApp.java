import java.util.HashMap;
import java.awt.List;
import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;

public class CLIApp {
	public static void main(String[] args) throws IOException {
		//CLIApp app = new CLIApp();
		//app.cdSet("C:\\Users\\basti\\OneDrive\\Bureau\\Travail\\A3\\Montreal\\INF3405\\test"); // test avec le chemin de bastien
		//app.inputCommand("mkdir marche");
		//app.inputCommand("ls");
		//app.promptPrint();
		//app.inputCommand("example hello!");
	}
	private String prompt = "";
	private String cd = "";
	private DataInputStream in;
	private DataOutputStream out;
	private HashMap<String, CLICommand> commands;
	final private String inputExit = "exit";
	public CLIApp(DataInputStream in, DataOutputStream out) {
		this.in = in;
		this.out = out;
		commands = new HashMap<String, CLICommand>();
		this.promptSet("> ");
		this.addCommand("example", new CLICommandExample());
		this.addCommand("mkdir", new CLICommandMkdir());
		this.addCommand("ls", new CLICommandLs());
		this.addCommand("cd", new CLICommandCd());
		this.addCommand("upload", new CLICommandUpload());
		//this.addCommand("download", new CLICommandDownload());
        this.cdSet(System.getProperty("user.dir"));
	}
	public String inputCommand(String input) throws IOException {
		List<String> inputStrings = new ArrayList<String>();
		Matcher m = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(input);
		while (m.find())
		    list.add(m.group(1));
		
		if (0 == inputStrings.length) return "";
		if (1 == inputStrings.length && inputStrings.get(0).equals(inputExit)) {
			return inputStrings.get(0);
		}
		CLICommand command = commands.get(inputStrings.get(0));
		if (null == command) {
			return inputStrings.get(0) + ": no such command.";
		}
		command.argsSet(inputStrings.remove());
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
	public DataInputStream getIn() {
		return this.in;
	}
	public DataOutputStream getOut() {
		return this.out;
	}
}
