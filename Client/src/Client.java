import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Scanner;

public class Client {

	public static void main(String[] args) throws UnknownHostException, IOException {
		///// A SUPRR mettre var en private 
	
		String IP = "localhost";
		int Port = 5000;
		
		String Message_Connection = ""; //message reçus du serveur pour indiquer la conexion
		Scanner scan = new Scanner(System.in);
	
		
		System.out.println("Bienvenue dans Conduite, veuiller entrez l'addresse IP du serveur :");
		
		
		//Lecture de l'IP
		IP = IP_Read(scan);
		
		
		// Lecture du Port
		Port = Port_Read(scan);
		
		// Connexion au serveur
		Socket socket = new Socket(IP,Port); //création du socket
		
		//Verification que le serveur est bien trouver
		DataInputStream in = new DataInputStream(socket.getInputStream()); 
		Message_Connection = in.readUTF(); // attente d'un message du serveur
		System.out.println(Message_Connection);
		
		//Envoie de messages au serveur
		
		Run(socket);
		
		socket.close();
	}
	
	
	public static void Run(Socket socket) throws IOException {
		String Message_To_Serv = ""; //message a envoyé au serveur
		String Message_From_Serv=""; //message reçue par le serveur
		String Prompt = "";
		//Méthode qui envoie et gères les actions que veut effectuer le client
		// Pour cela, le client envoie un message au serveur et le serveur enverra en retour un message au client lui indiquant quel méthode utiliser
		DataOutputStream out = new DataOutputStream(socket.getOutputStream()); //création canal d'envoie 
		DataInputStream in = new DataInputStream(socket.getInputStream());
		Scanner scan = new Scanner(System.in);
		while(!Message_To_Serv.equals("quit")) {
			//recevoir prompt du serveur
			Prompt = in.readUTF();
			System.out.print(Prompt);
			Message_To_Serv = scan.nextLine();
			
			if(Message_To_Serv.startsWith("upload")) {
				UploadCommand(Message_To_Serv, out, in);
				Message_From_Serv = in.readUTF();
				System.out.println(Message_From_Serv);
			}
			else if(Message_To_Serv.startsWith("download")) {
				DownloadCommand(Message_To_Serv, out, in);
			}
			else {
				out.writeUTF(Message_To_Serv);
				//Attente de la réponse du serveur
				Message_From_Serv = in.readUTF();
				System.out.println(Message_From_Serv);
			}
		}
		scan.close();
		socket.close();
	}
	
	private static void DownloadCommand(String Message,DataOutputStream out, DataInputStream in) throws IOException {
		String[] Tab_Message = Message.split(" ",2);
		
		String Name_File = Tab_Message[1];
		File file = new File(Name_File);
		
		// TODO : Vérifier que le fichier entré existe dans le Serveur
		
		if (!file.isFile())
			System.out.println("Le fichier rentrer n'est pas valide");
		else if(Tab_Message.length != 2) {
			System.out.println("Pas le bon nombre d'argument.\nEntrez la commande et le chemin absolu du fichier");
		}
		else {
			out.writeUTF("download "+ file.getName());
			if (in.readUTF() == "ok")
				receiveFile(file, in);
		}
	}
	


	public static void UploadCommand(String Message,DataOutputStream out,DataInputStream in) throws IOException {
		String[] Tab_Message = Message.split(" ",2);
		
		String Name_File = Tab_Message[1];
		File file = new File(Name_File);
		
		if (!file.isFile()) {
			System.out.println("Le fichier rentrer n'est pas valide");
		}
		else {
			out.writeUTF("upload "+ file.getName());	
			out.writeLong(file.length());
			sendFile(file,out);
		}
		
	}
	
	private static void sendFile(File file, DataOutputStream out) throws FileNotFoundException, IOException {
		try (FileInputStream fileIn = new FileInputStream(file)) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = fileIn.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }
	}
	
	private static void receiveFile(File file, DataInputStream in) throws FileNotFoundException, IOException {
		try (FileOutputStream fileOut = new FileOutputStream(file)) {
			// long fileLength = in.readLong(); // TODO utiliser longueur du fichier
			byte[] buffer = new byte[4096];
			int bytesRead;
			while ((bytesRead = in.read(buffer)) != -1) {
				fileOut.write(buffer, 0, bytesRead); // on écrit les bytes reçus dans le fichier
			}
		}
	}
	
	public static void Execute_Commande(String Commande) {
		System.out.println(Commande); //Test pour le developement si le serveur comunique
	}
	
 	public static String IP_Read(Scanner scan) {
		//Fonction qui demande a l'utilisateur d'entrer une IP, elle verifie de plus que l'IP est correct
		
		String Result="";
		Boolean IP_correct = false;
		
		
		while (!(IP_correct)) { // on redemande une IP au client tant qu'il n'en rentre une qui n'est pas valide
			
			Result = scan.nextLine();  //lecture de l'entrée du client
			//System.out.println(IP); // test de lecture de l'IP
			IP_correct = Is_IP_Correct(Result);  //verification de l'IP
			if(!(IP_correct)) {
				System.out.println("Entrée une nouvelle IP s.v.p :");
			}
		}
		
		return Result;
	}
	
	
	public static int Port_Read(Scanner scan) {
		// Fonction qui demande a l'utilisateur de rentrer un port et il vérifie s'il est correct
		
		int Result = 0;
		Boolean Port_correct = false;
		String S_Port;
		
		
		System.out.println("Entrez un port entre 5000 et 5050");
		
		while(!(Port_correct)) {
			S_Port = scan.nextLine();  //lecture de l'entrée du client
			try {
				Result = Integer.valueOf(S_Port);
				Port_correct = Is_Port_Correct(Result);  //verification du port
				if(!(Port_correct)) {
					System.out.println("Entrée un nouveau port s.v.p :");
				}
			}
			catch(NumberFormatException e) {
				System.out.println("Veuillez rentre un entier");
			}
		}
		
		return Result;
	}
	
	public static Boolean Is_IP_Correct(String IP) {
		/*	Fonction qui renvoie si  le string envoyé représente une IP valide		 * 
		 * 
		 */
		Boolean result = false;
		// Verification si l'entrée est l'IP local
		if(IP.equals("localhost")) {
			result = true;
		} //Verification si l'IP est valide
		else {
			
			if(IP == null || IP.equals("")) { //Verif si une IP est rentré 
				result = false;
				System.out.println("IP non rentré");
			}
			else if(IP.endsWith(".")) {
				result = false;
				System.out.println("L'IP ne peut pas terminer par un point");
			}
			else { //division du string rentré en plusieur string représentant les 4 partie de l'IP
				String[] IP_split = IP.split("\\.");
				//System.out.println(IP_split);
				if(IP_split.length != 4) { //verification du nombre de partie de la division des IP, il doit être égale a 4
					result = false;
					System.out.println("L'entré ne correspond pas a une IP (il faut 4 partie séparrer par des points)");
				}
				else {
					int number_ok_part = 0; //compte le nombre de partie correct dans l'IP
					for(String i : IP_split) { // on pourrait faire un while avec un compteur mais le for ne fait 4 boucle donc ce n'est pas très grave
						//System.out.println(i);
						try {
							int j = Integer.parseInt(i);
							if(j>=0 && j<=255) { // vérifier si la partie vérifier est compris entre 0 et 255
								number_ok_part++;
								
							}
							else {
								System.out.println("les nombres doivent être compris entre 0 et 255");
							}
						}
						catch(NumberFormatException e) { //si la partie de l'ip n'est pas un integer alors il y a une probleme
							System.out.println("Une partie de l'IP ne corespond pas a des chiffres");
						}
					}
					result = (number_ok_part == 4); // vérifier si il y a bien 4 partie correct
					
					
				}
			}
		}
		
		return result;
	}
	
	
	public static Boolean Is_Port_Correct(int Port) {
		// renvoie true si le port remplie les conditions des ports (être entre 5000 et 5050)
		return (Port <= 5050 && Port >= 5000);
	}

}
