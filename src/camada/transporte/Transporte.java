package camada.transporte;

import camada.Camada;
import camada.Rede;
import camada.Sessao;

public class Transporte extends Camada implements Runnable { // Gerencia os pacotes, recebe da camada de sessão e adiciona ao cabeçalho o ID do pacote;
	private GerenciadorPacote gerenciador_pacotes;
	
	public Transporte(Camada r, String ip) {
		super(r, ip);
		
		set_send(new Rede(this,ip));
		gerenciador_pacotes = new GerenciadorPacote();
		
	}

	
	

	@Override
	public void send(String bytes) {
		Imprimir("Transporte", bytes);		
		gerenciador_pacotes.add_pacote(bytes);
	}




	@Override
	public synchronized void receive(String Bytes) {
		Imprimir("Transporte Recebendo", Bytes);
		if(Bytes.charAt(38) == '1') {
			gerenciador_pacotes.confirmar_pacote(Bytes);
			notify();
			return;
		}
		String send_res = Bytes.substring(0,1) + Bytes.substring(5,9) + Bytes.substring(1,5) + Bytes.substring(9,38);
		if(!((Sessao) receive).check(Bytes)) {
			gerenciador_pacotes.add_pacote(send_res + "10");
			notify();
			return;
		}			
		gerenciador_pacotes.add_pacote(send_res +"11");
		gerenciador_pacotes.add_received_pacote(Bytes);
		notify();
		
		
	}




	@Override
	public synchronized void run() {
		while(true) {
			try {
				String send_ = gerenciador_pacotes.enviar_pacote();
				if(send_ == null) ;
				else send.send(send_);
				gerenciador_pacotes.timeout_pacotes();
				send_ = gerenciador_pacotes.get_pacote_pronto();
				if(send_ == null) ;
				else receive.receive(send_);
				gerenciador_pacotes.limpa();
				wait(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}
	
	
	

}
