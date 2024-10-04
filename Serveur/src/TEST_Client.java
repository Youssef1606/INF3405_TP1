
public class TEST_Client {

	public static void main(String[] args) {
		//Test de la fonction Is_IP_Corect
		
		//Print_Test("true",String.valueOf(Client.Is_IP_Correct("100.100.100.100")),"Is_Ip_Correct avec 100.100.100.100");
		//Print_Test("true",String.valueOf(Client.Is_IP_Correct("100.100.100.100")),"Is_Ip_Correct avec localhost");
		//Print_Test("false",String.valueOf(Client.Is_IP_Correct("-1.0.0.0")),"Is_Ip_Correct avec -1.0.0.0");
		//Print_Test("false",String.valueOf(Client.Is_IP_Correct("bonjour")),"Is_Ip_Correct avec bonjour");
		//Print_Test("false",String.valueOf(Client.Is_IP_Correct("0.0.0.0.")),"Is_Ip_Correct avec 0.0.0.0.");
		//Print_Test("false",String.valueOf(Client.Is_IP_Correct("300.100.100.100")),"Is_Ip_Correct avec 300.100.100.100");
		//Print_Test("false",String.valueOf(Client.Is_IP_Correct("100.100.100")),"Is_Ip_Correct avec 100.100.100");
		//Print_Test("false",String.valueOf(Client.Is_IP_Correct("100.oui.100.100")),"Is_Ip_Correct avec 100.oui.100.100");
		
		
		
	}
	
	public static void Print_Test(String attendu, String result, String test) {
		System.out.println("Test de la fonction " + test + "\n Attendu : "+ attendu + "\n Resultat : "+ result+"\n");
	}
	
}
