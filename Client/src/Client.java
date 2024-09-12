import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
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
		
		//Méthode qui envoie et gères les actions que veut effectuer le client
		// Pour cela, le client envoie un message au serveur et le serveur enverra en retour un message au client lui indiquant quel méthode utiliser
		DataOutputStream out = new DataOutputStream(socket.getOutputStream()); //création canal d'envoie 
		DataInputStream in = new DataInputStream(socket.getInputStream());
		Scanner scan = new Scanner(System.in);
		while(!Message_To_Serv.equals("quit")) {
			//Envoie de message au serveur
			System.out.println("Envoyé une commande au serveur :");
			Message_To_Serv = scan.nextLine();
			out.writeUTF(Message_To_Serv);
			//Attente de la réponse du serveur
			Message_From_Serv = in.readUTF();
			Execute_Commande(Message_From_Serv);
		}
		scan.close();
		socket.close();
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
