package camada;

import java.util.ArrayList;

public class Transporte extends Camada { // Gerencia os pacotes, recebe da camada de sessão e adiciona ao cabeçalho o ID do pacote;
	private ArrayList<String> send_pacotes;
	private int pacote_id;
	
	public Transporte(Camada r, String ip) {
		super(r, ip);
		
		set_send(new Rede(this,ip));
		send_pacotes = new ArrayList<String>();
		pacote_id = 0;
		
	}

	
	

	@Override
	public synchronized void send(String bytes) {
		Imprimir("Transporte", bytes);
		
		
		
		if (bytes.charAt(0) == '0') send_pacotes.add(bytes + String.format("%8s", Integer.toBinaryString(0)).replace(' ', '0') + String.format("%8s", Integer.toBinaryString(pacote_id++)).replace(' ', '0'));
		else 			
			send_pacotes.set(send_pacotes.size() - 1, send_pacotes.get(send_pacotes.size() - 1) + bytes + String.format("%8s", Integer.toBinaryString(send_pacotes.get(send_pacotes.size() - 1).length()/40   )).replace(' ', '0') +  String.format("%8s", Integer.toBinaryString(pacote_id -1)).replace(' ', '0'));
		
		
		if (bytes.charAt(17) == '1') {
			
			String send_ = send_pacotes.get(send_pacotes.size() - 1);
			send_pacotes.remove(send_pacotes.size() - 1);
			
			for (int i = 0; i < send_.length()/40; i++)
				send.send(send_.substring(i*40, (i+1)*40));
		}
	}




	@Override
	public synchronized void receive(String Bytes) {
		Imprimir("Transporte Recebendo", Bytes);
		if(((Sessao) receive).check(Bytes)) {
			send.send(get_ip() + Bytes.substring(24,40));
		}
		else {
			
		}
		
		
	}
	
	
	

}
