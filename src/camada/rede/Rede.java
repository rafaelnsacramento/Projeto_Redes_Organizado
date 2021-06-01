package camada.rede;


import camada.Camada;
import camada.Fisica;

public class Rede extends Camada{// Gerencia os pacotes a serem enviados para a camada física e se será necessário reenviar o pacote. 
	
	private SendFisica sf;
	public Rede(Camada r, String ip) {
		super(r, ip);
		set_send(new Fisica(this));
		new Thread((Runnable) send).start();
		sf = new SendFisica(send);
		new Thread(sf).start();
		
	}

	
	
	@Override
	public void send(String bytes) {
		Imprimir("Rede", bytes);
		sf.offer(bytes);
	}
	

	
	@Override
	public synchronized void receive(String Bytes) {
		Imprimir("Rede Recebendo", Bytes);
		if(Bytes.length() == 24)  {
			
			if (!Bytes.substring(0,4).equals(get_ip())); // Não é pra mim
			else 
				if(!Bytes.subSequence(8, 24).equals(sf.getBuffer().substring(24,40))) sf.resend();
		}
		else
			if (!Bytes.substring(1,5).equals(get_ip())) ; //Não é pra mim			
			else 
				receive.receive(Bytes);
			
	}

	
	
	



		
		
	
		
		


	
	
	
	
	
	

	

}
