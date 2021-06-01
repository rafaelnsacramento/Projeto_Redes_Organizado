package camada.transporte;

import java.util.ArrayList;
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
		confirmar_pacotes = new ArrayList<String>();
		pacotes_recebidos = new ArrayList<Pacote>();
		pacotes_id = new LinkedList<Integer>();
		for (int i = 0; i< 64; i++) pacotes_id.add(i);
	}
	
	
	
	
	public synchronized void add_pacote(String Bytes) {
		if (!can_add()) {
			espera.add(Bytes);
			return;
		}
		
		if(Bytes.length() == 40 && Bytes.charAt(38) == '1' ) {
			enviar_pacotes.add(Bytes);
			return;
		}
		
		int id = get_packet_id();
		for (int i = 0; i<Bytes.length() / 24; i++) 
			enviar_pacotes.add(Bytes.substring(i*24,(i+1)*24) +	String.format("%8s", Integer.toBinaryString(i)).replace(' ', '0') + String.format("%6s", Integer.toBinaryString(id)).replace(' ', '0') +"00"	);
				
	}
	
	
	public synchronized String enviar_pacote() {
		if(enviar_pacotes.isEmpty()) return null;
		String send = enviar_pacotes.poll();
		if(send.charAt(38) == '0') 
			confirmar_pacotes.add(send + String.valueOf(System.nanoTime()));
		return send;
	}
	
	
	public synchronized void confirmar_pacote(String Bytes) {

		for (int i = 0; i< confirmar_pacotes.size() ; i++) 
			if(confirmar_pacotes.get(i).substring(24,38).equals(Bytes.substring(24, 38))) {
				if(Bytes.charAt(39) == '0')
					enviar_pacotes.add(confirmar_pacotes.get(i).substring(0,40));
				
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
	
	


	
	
	
	
	public synchronized void add_received_pacote(String Bytes) {
		String id_ = Bytes.substring(32,38);
		
		for (int i = 0; i<pacotes_recebidos.size();i++)
			if(pacotes_recebidos.get(i).get_id().equals(id_)) {
				pacotes_recebidos.get(i).add_info(Bytes);
				return;
			}		
		
		pacotes_recebidos.add(new Pacote(id_));
		pacotes_recebidos.get(pacotes_recebidos.size() - 1).add_info(Bytes);
	}
	
	
	
	
	
	public synchronized String get_pacote_pronto() {
		if (pacotes_recebidos.isEmpty()) return null;
		if (pacotes_recebidos.get(0).done()) {
			String r = pacotes_recebidos.get(0).get_pacote();	
			pacotes_recebidos.remove(0);
			notify();
			return r;

		}
		
		return null;
	}
	
	
	
	
	
	
	
	
	
	private boolean can_add() {
		return !pacotes_id.isEmpty();
	}
	
	private int get_packet_id() {
		return pacotes_id.poll();
	}

	
	
	
	



}
