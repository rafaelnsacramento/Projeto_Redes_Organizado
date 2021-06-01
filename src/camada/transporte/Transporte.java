package camada.transporte;

import java.util.ArrayList;

import camada.Camada;
import camada.Sessao;
import camada.rede.Rede;

public class Transporte extends Camada implements Runnable { // Gerencia os pacotes, recebe da camada de sessão e adiciona ao cabeçalho o ID do pacote;
	private GerenciadorPacote gerenciador_pacotes;
	
	public Transporte(Camada r, String ip) {
		super(r, ip);
		
		set_send(new Rede(this,ip));
		gerenciador_pacotes = new GerenciadorPacote();
		
	}

	
	

	@Override
	public synchronized void send(String bytes) {
		Imprimir("Transporte", bytes);		
		gerenciador_pacotes.add_pacote(bytes);
		notify();
	}




	@Override
	public synchronized void receive(String Bytes) {
		Imprimir("Transporte Recebendo", Bytes);
		if(((Sessao) receive).check(Bytes)) {
			send.send(Bytes.substring(5,9) + Bytes.substring(1,5) + Bytes.substring(24,40));
		}
		else {
			send.send(Bytes.substring(5,9) + Bytes.substring(1,5) + "1111111111111111");
		}
		
		
	}




	@Override
	public void run() {
		while(true) {
			try {
				String send_ = gerenciador_pacotes.enviar_pacote();
				if(send_.isBlank()) wait(5000);
				else send.send(send_);
				gerenciador_pacotes.timeout_pacotes();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	
	

}
