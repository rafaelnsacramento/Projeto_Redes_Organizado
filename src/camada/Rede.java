package camada;



public class Rede extends Camada {// Gerencia os pacotes a serem enviados para a camada física e se será necessário reenviar o pacote. 

	

	public Rede(Camada r, String ip) {
		super(r, ip);
		set_send(new Fisica(this));
		
		new Thread((Runnable) send).start();
	}

	@Override
	public void send(String bytes) {
		Imprimir("Rede", bytes);
		send.send(bytes);
	}
	

	

	@Override
	public synchronized void receive(String Bytes) {
		Imprimir("Rede Recebendo", Bytes);
		if (Bytes.substring(1,6).equals(get_ip()))
			//System.out.println("Não é pra mim")
			;
		
		else {
			receive.receive(Bytes);
		}
	}

	
	
	
	
	
	

	

}
