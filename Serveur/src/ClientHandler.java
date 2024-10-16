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
		System.out.println("Connection with client #" + Client + " closed");
		}
		
	}


 

	
	public void Receive_Commande(DataInputStream in, DataOutputStream out) throws IOException {
		String messageFromClient = "";
		
		CLIApp app = new CLIApp(in,out);
		app.cdSet(System.getProperty("user.dir"));
		
	    try {
	        while (!messageFromClient.equalsIgnoreCase("exit")) {
	            out.writeUTF(app.promptGet());
	            messageFromClient = in.readUTF();
	
	            showClientInput(messageFromClient);
	
	            if (!messageFromClient.equalsIgnoreCase("exit")) {
	                String response = app.inputCommand(messageFromClient);
	                // N'envoyez la réponse que si elle n'est pas vide
	                if (response != null && !response.isEmpty()) {
	                    out.writeUTF(response);
	                }
	            }
	        }
	    } catch (IOException e) {
	        System.out.println("Connection lost with client #" + this.Client);
	    }
	}


	@SuppressWarnings("deprecation")
	private void showClientInput(String messageFromClient) {
		Date date = new Date();
		Integer Year = date.getYear() + 1900;
		Integer Mounth = date.getMonth() +1 ;
		
		
 		String ClientIp = socket.getInetAddress().toString().substring(1);
		
		System.out.println("[" + ClientIp + ":" + socket.getLocalPort()+ " - " + Year + '-' + Mounth + '-' + date.getDate() +'@'+ date.getHours() + ':' + date.getMinutes() + ':' + date.getSeconds() + "]: " + messageFromClient);
		
	}
}
