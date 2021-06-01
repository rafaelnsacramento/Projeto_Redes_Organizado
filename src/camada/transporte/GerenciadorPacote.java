package camada.transporte;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class GerenciadorPacote{
	private Queue<String> espera;
	private Queue<String> enviar_pacotes;
	private List<String> confirmar_pacotes;
	private List<Pacote> pacotes_recebidos;
	
	private Queue<Integer> pacotes_id;
	
	public GerenciadorPacote() { //0 0000 0000 00000000 000000						00000000						000000					00
								//												numero do dataframe				id do pacote			10=ack error 11=ack
		espera = new LinkedList<String>();
		enviar_pacotes = new LinkedList<String>();
		confirmar_pacotes = new LinkedList<String>();
		pacotes_recebidos = new LinkedList<Pacote>();
		pacotes_id = new LinkedList<Integer>();
		for (int i = 0; i< 64; i++) pacotes_id.add(i);
	}
	
	
	
	
	public synchronized void add_pacote(String Bytes) {
		if (!can_add()) {
			espera.add(Bytes);
			return;
		}
		
		for (int i = 0; i<Bytes.length() / 24; i++) 
			enviar_pacotes.add(Bytes.substring(i*24,(i+1)*24) +	String.format("%8s", Integer.toBinaryString(i)).replace(' ', '0') + String.format("%6s", Integer.toBinaryString(get_packet_id())).replace(' ', '0') +"00"	);
				
	}
	
	
	public synchronized String enviar_pacote() {
		if(enviar_pacotes.isEmpty()) return "";
		String send = enviar_pacotes.poll();
		confirmar_pacotes.add(send + String.valueOf(System.nanoTime()));
		notify();
		return send;
	}
	
	
	public synchronized void confirmar_pacote(String Bytes) {
		if(confirmar_pacotes.isEmpty()) return;
		for (int i = 0; i< confirmar_pacotes.size() ; i++) 
			if(confirmar_pacotes.get(i).substring(24,38).equals(Bytes.substring(24, 38))) {
				if(Bytes.charAt(39) == '0')
					enviar_pacotes.add(confirmar_pacotes.get(i));
				
				confirmar_pacotes.remove(i);				
				return;
			}		
	}
	
	
	
	public synchronized void timeout_pacotes() {
		if(confirmar_pacotes.isEmpty()) return;
		
		double elapsed = (double)(System.nanoTime()  - Long.parseLong(confirmar_pacotes.get(0).substring(40)))  / 1_000_000_000.0;
		while(elapsed > 20) {
			enviar_pacotes.add(confirmar_pacotes.get(0).substring(0, 40));
			confirmar_pacotes.remove(0);				
			try{
				elapsed = (double)(System.nanoTime()  - Long.parseLong(confirmar_pacotes.get(0).substring(40)));
			}catch (IndexOutOfBoundsException e) {
				return;
			}
		}
	}
	
	
	
	private boolean can_add() {
		return !pacotes_id.isEmpty();
	}
	
	private int get_packet_id() {
		return pacotes_id.poll();
	}




}
