package camada;

public abstract class Camada {
	protected Camada send;
	protected Camada receive;
	protected String my_ip;
	
	
	public abstract void send(String bytes);
	public abstract void receive(String Bytes);
	
	protected void set_send(Camada send_) {
		send=send_;
	}
	
	protected String get_ip() {
		return my_ip;
	}
	
	public Camada(Camada r, String ip) {
		receive = r;
		my_ip = ip;
	}
	
	
	public void Imprimir(String Camada, String bytes) {
		System.out.println("Camada de "+Camada + ": " + bytes);
	}
	
	
	
}
