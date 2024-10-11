import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.IOException;
import java.io.DataInputStream;
import java.io.DataOutputStream;

public class CLIApp {
	public static void main(String[] args) throws IOException {
		//CLIApp app = new CLIApp(null, null);
		//System.out.println(app.inputCommand("example \"hey everyone!\""));
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
		this.addCommand("download", new CLICommandDownload());
		this.addCommand("delete", new CLICommandDelete());
        this.cdSet(System.getProperty("user.dir"));
	}
	public ArrayList<String> argsExtract(String input) {
		ArrayList<String> inputStrings = new ArrayList<String>();
		// source du code [1] : https://stackoverflow.com/questions/366202/regex-for-splitting-a-string-using-space-when-not-surrounded-by-single-or-double
		Matcher matcher = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(input);
		while (matcher.find())
		    inputStrings.add(matcher.group(1));
		// fin du code de [1]

		for (int i = 0; i < inputStrings.size(); i++) {
			String arg = inputStrings.get(i);
			if (arg.charAt(0) == '\"' && 
				arg.charAt(arg.length() - 1) == '\"')
					inputStrings.set(i, arg.substring(1, arg.length() - 1));
		}
		
		return inputStrings;
	}
	public String inputCommand(String input) throws IOException {
		ArrayList<String> inputStrings = argsExtract(input);
		if (0 == inputStrings.size()) return "";
		CLICommand command = commands.get(inputStrings.get(0));
		if (null == command) {
			return inputStrings.get(0) + ": no such command.";
		}

		inputStrings.remove(0);

		command.argsSet(inputStrings);
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
