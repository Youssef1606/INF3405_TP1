import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
		String messageToServer = ""; //message a envoyé au serveur
		String messageFromServer=""; //message reçue par le serveur
		String Prompt = "";
		Boolean Want_To_Exit = false;
		
		//Méthode qui envoie et gères les actions que veut effectuer le client
		// Pour cela, le client envoie un message au serveur et le serveur enverra en retour un message au client lui indiquant quel méthode utiliser
		DataOutputStream out = new DataOutputStream(socket.getOutputStream()); //création canal d'envoie 
		DataInputStream in = new DataInputStream(socket.getInputStream());
		Scanner scan = new Scanner(System.in);
		while(!Want_To_Exit) {
			//recevoir prompt du serveur
			try {
				Prompt = in.readUTF();
				System.out.print(Prompt);
				
			} catch (IOException ioe) {
                ioe.printStackTrace();
            }
			messageToServer = scan.nextLine();
			if(messageToServer.startsWith("upload")) {
				UploadCommand(messageToServer, out, in);
				try {
					messageFromServer = in.readUTF();
					System.out.println(messageFromServer);
				} catch (IOException ioe) {
	                ioe.printStackTrace();
	            }
			}
			else if(messageToServer.startsWith("download")) {
				DownloadCommand(messageToServer, out, in);
			}
			else if(messageToServer.equals("exit")) {
				Want_To_Exit = true;
			}
			else {
				out.writeUTF(messageToServer);
				try {
					messageFromServer = in.readUTF();
					System.out.println(messageFromServer);
				} catch (IOException ioe) {
	                ioe.printStackTrace();
	            }
			}
		}
		scan.close();
		socket.close();
		System.out.println("Vous avez été déconnecté avec succès.");
	}

	public static ArrayList<String> argsExtract(String input) {
		ArrayList<String> inputStrings = new ArrayList<String>();
		Matcher matcher = Pattern.compile("([^\"]\\S*|\".+?\")\\s*").matcher(input);
		while (matcher.find())
		    inputStrings.add(matcher.group(1));

		for (int i = 0; i < inputStrings.size(); i++) {
			String arg = inputStrings.get(i);
			if (arg.charAt(0) == '\"' && 
				arg.charAt(arg.length() - 1) == '\"')
					inputStrings.set(i, arg.substring(1, arg.length() - 1));
		}
		
		return inputStrings;
	}

	public static void UploadCommand(String Message, DataOutputStream out, DataInputStream in) throws IOException {
		ArrayList<String> Tab_Message = argsExtract(Message);
		
		String fileName = "";
		if (Tab_Message.size() > 1) fileName = Tab_Message.get(1);
		
		File file = new File(fileName);
		out.writeUTF("upload \""+ file.getName() + "\"");
		
		if (!file.isFile()) {
			out.writeUTF("ERROR");
            out.writeUTF("Le fichier spécifié n'existe pas ou n'est pas un fichier.");
            return;
		}
		out.writeUTF("OK");
		
		out.writeLong(file.length());
		
		// send a file
		try (FileInputStream fileIn = new FileInputStream(file)) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = fileIn.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }
	}
	
private static void DownloadCommand(String Message, DataOutputStream out, DataInputStream in) throws IOException {
    ArrayList<String> Tab_Message = argsExtract(Message);

    String fileName = "";
	if (Tab_Message.size() > 1) fileName = Tab_Message.get(1);

	File file = new File(fileName);
    out.writeUTF("download \""+ file.getName() + "\"");

    // Lire le code de statut du serveur
    String status = in.readUTF();
    if ("ERROR".equals(status)) {
        // Le serveur a indiqué une erreur
        String errorMessage = in.readUTF();
        System.out.println("Erreur du serveur : " + errorMessage);
        return;
    } else if (!"OK".equals(status)) {
        // Réponse inattendue du serveur
        System.out.println("Réponse inattendue du serveur : " + status);
        return;
    }
    
    // Recevoir le fichier
    try (FileOutputStream fileOut = new FileOutputStream(file)) {
        long fileSize = in.readLong();
        byte[] buffer = new byte[4096];
        long bytesReceived = 0;
        int bytesRead;

        while (bytesReceived < fileSize && (bytesRead = in.read(buffer, 0, 
                (int)Math.min(buffer.length, fileSize - bytesReceived))) != -1) {
            fileOut.write(buffer, 0, bytesRead);
            bytesReceived += bytesRead;
        }
    }

    // Lire la confirmation du serveur
    String messageFromServer = in.readUTF();
    System.out.println(messageFromServer);
}

	
	public static void ExecuteCommand(String command) {
		System.out.println(command); //Test pour le developement si le serveur comunique
	}
	
 	public static String IP_Read(Scanner scan) {
		String IPAddress = "";
		Boolean IPAddressIsCorrect = false;
		
		while (!(IPAddressIsCorrect)) {
			IPAddress = scan.nextLine();
			IPAddressIsCorrect = isIPAddress(IPAddress);  //verification de l'IP
			if(!(IPAddressIsCorrect)) {
				System.out.println("Entrée une nouvelle IP s.v.p :");
			}
		}
		return IPAddress;
	}
	
 	// Fonction qui demande a l'utilisateur de rentrer un port et il vérifie s'il est correct
	public static int Port_Read(Scanner scan) {
		Boolean portNumIsCorrect = false;
		int portNum = 0;
		
		System.out.println("Entrez un port entre 5000 et 5050");
		
		while(!(portNumIsCorrect)) {
			String input = scan.nextLine();
			try {
				portNum = Integer.valueOf(input);
				portNumIsCorrect = isValidPortNum(portNum);
				if (!portNumIsCorrect) {
					System.out.println("Le port doit être entre 5000 et 5050");
				}
			}
			catch(NumberFormatException e) {
				System.out.println("Veuillez entrez un entier.");
			}
		}
		return portNum;
	}
	
	/*	Fonction qui renvoie si  le string envoyé représente une IP valide		 * 
	 * 
	 */
	public static Boolean isIPAddress(String IP) {
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
	
	// renvoie true si le port remplie les conditions des ports (être entre 5000 et 5050)
	public static Boolean isValidPortNum(int portNum) {
		return (portNum <= 5050 && portNum >= 5000);
	}
}
