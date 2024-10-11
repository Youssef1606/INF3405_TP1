import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.util.Scanner;

public class Serveur {

	
	private static ServerSocket Listener;
	
	
	public static void main(String[] args) throws IOException {
		
		String serverAddress = "127.0.0.1";
		Boolean Port_correct = false;
		Scanner scan = new Scanner(System.in);
		String S_Port;
		int Port = 5000;
		int clientNumber = 0;
		
		System.out.println("Entrez un port entre 5000 et 5050:");
		
		while(!(Port_correct)) {
			S_Port = scan.nextLine();  //lecture de l'entrée du client
			try {
				Port = Integer.valueOf(S_Port);
				Port_correct = portIsValid(Port);  //verification du port
				if(!(Port_correct)) {
					System.out.println("Entrez un port entre 5000 et 5050:");
				}
			}
			catch(NumberFormatException e) {
				System.out.println("Veuillez entrer un entier:");
			}
		}
		scan.close();
		
		Listener = new ServerSocket();
		Listener.setReuseAddress(true);
		
		InetAddress serverIP = InetAddress.getByName(serverAddress);
		
		Listener.bind(new InetSocketAddress(serverIP, Port));
		System.out.format("The server is running on %s:%d%n", serverAddress, Port);
		
		try {
			// Prise en charge des clients
			while (true) {
				//Attente de la conexion d'un nouveau client
				new ClientHandler(Listener.accept(), clientNumber++).start();
			}
		} finally {
			// Fermeture de la connexion
			Listener.close();
		}
	}
	
	public static Boolean portIsValid(int Port) {
		return (Port <= 5050 && Port >= 5000);
	}

}
