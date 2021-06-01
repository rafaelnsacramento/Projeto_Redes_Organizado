package app;


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

	public static void main(String[] args) {
		Cliente a = new Cliente ("1111");
		a.send("PING");
		a.send("A");
		//a.send("RECOVER");
	}

}
