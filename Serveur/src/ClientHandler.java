import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Date;

public class ClientHandler extends Thread {
	private int Client;
	private Socket socket;
	
	public ClientHandler(Socket socket, int Nb_Client) {
		this.Client = Nb_Client;
		this.socket = socket;
	}
	
	//override
	public void run() {

		try {
			DataOutputStream out = new DataOutputStream(socket.getOutputStream()); // création de canal d’envoi pour indiquer la connexion
			DataInputStream in = new DataInputStream(socket.getInputStream()); // création d'un canal pour recevoir des information
			out.writeUTF("Bonjour bienvenue dans Conduite - vous êtes le client numéro#" + Client);
			
			
			Receive_Commande(in,out);
			
			out.close();
			in.close();
		} catch (IOException e) {
			
			System.out.println("Error handling client# " + Client + ": " + e);
		} // envoi de message
		finally {
			try {
				socket.close();
		} 
		catch (IOException e) {
			System.out.println("Couldn't close a socket, what's going on?");
		}
		System.out.println("Connection with client# " + Client + " closed");
		}
		
	}
	
	@SuppressWarnings("deprecation")
	public void Receive_Commande(DataInputStream in, DataOutputStream out) throws IOException {
		// fonction qui recois les commande du client et lance les procédure nécessaire
		String Message_From_Client = "";
		String Message_To_Client = "";
		CLIApp app = new CLIApp(in,out);
		//app.cdSet("C:\\Users\\basti\\OneDrive\\Bureau\\Travail\\A3\\Montreal\\INF3405\\test");
		
		// obtenir le chemin absolu où se trouve le Serveur
		app.cdSet(System.getProperty("user.dir"));
		
		while(!Message_From_Client.equals("exit")) {
			
			/// Affichage comme dans le TP
			out.writeUTF(app.promptGet());
			Message_From_Client = in.readUTF();
			
			ShowClientInput(Message_From_Client);
			
			if(!Message_From_Client.equals("exit")) {
				out.writeUTF(app.inputCommand(Message_From_Client));
			}
			
			
			//executer des méthodes avec la commande reçue et envoyé un message au client pour lui dire quoi faire
			
		}
	}

	if (!Message_From_Client.equals("exit")) {
    String response = app.inputCommand(Message_From_Client);
    // N'envoyez la réponse que si elle n'est pas vide
    if (response != null && !response.isEmpty()) {
        out.writeUTF(response);
    }
}


	@SuppressWarnings("deprecation")
	private void ShowClientInput(String message_From_Client) {
		Date date = new Date();
		Integer Year = date.getYear() + 1900;
		Integer Mounth = date.getMonth() +1 ;
		
		
		
 		String ClientIp = socket.getInetAddress().toString().substring(1);
		
		System.out.println("[" + ClientIp + ":" + socket.getLocalPort()+ " - " + Year + '-' + Mounth + '-' + date.getDate() +'@'+ date.getHours() + ':' + date.getMinutes() + ':' + date.getSeconds() + "]: " + message_From_Client);
		
	}
}
