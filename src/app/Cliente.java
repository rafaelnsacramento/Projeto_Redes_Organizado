package app;
import java.util.Scanner;

public class Cliente extends App{

	public Cliente(String ip) {
		super(ip);
	}
	
	
	@Override
	public void send(String A) {
		String send_my = IP;				//4 len
		String send_dest = "0000";
		send.send(send_dest + send_my + A );
		
	}




	@Override
	public synchronized void receive(String message) {
		System.out.println(message);
		
	}
	
	
	
	public static void main(String[] args) {
		Cliente a = new Cliente ("1111");
		Scanner sc1 = new Scanner(System.in);
		
		while (true) {
			a.send(sc1.nextLine());
		}
		
	
		
	}
}
