package app;

import java.util.ArrayList;
import java.util.List;

public class Servidor extends App {

	private List<Cliente> clientes;
	
	
	class Cliente{
		private String IP;
		private String Modo;
		private String LAST;
		
		public Cliente(String I) {
			Modo = "PING";
			LAST = "";
			IP = I;
			
		}
		
		
		public String alterar_modo(String m) {
			if (m.equals(Modo)) return "Já está no modo " + m;
			Modo = m;
			return "Alterado para modo " + m;
		}
		
		
		public String get_resposta(String m) {
			if (m.equals("LAST")) {
				if (Modo.equals("RECOVER")) {
					if (LAST.equals("")) return "Ainda não enviou nenhum caracter";
					return LAST;
				}
				return "Nao esta no modo RECOVER";
			}
			else {
				if (!Modo.equals("PING")) return "Comando não reconhecido no modo RECOVER";
				LAST = m;
				return m;
			}
		}
		
		public String get_ip() {
			return IP;
		}
	}
	
	
	
	
	public Servidor(String ip) {
		super(ip);
		clientes = new ArrayList<Cliente>();
	}

	@Override
	public void send(String message) {
		String send_my = IP;
		String send_dest = message.substring(0,4);
		String message_ = message.substring(4);
		
		send.send(send_dest + send_my + message_);
		
	}
	
	
	

	@Override
	public void receive(String message) {
		System.out.println(message);
		
		String cliente = message.substring(0,4);
		String message_ = message.substring(4);
		Cliente c = get_cliente(cliente);
		
		if (message_.equals("Error, comando não reconhecido")) send(cliente + message_);
		else if (message_.equals("RECOVER") || message_.equals("PING")) send(cliente + c.alterar_modo(message_));
		else send(cliente + c.get_resposta(message_));
		
		
	}
	
	
	public Cliente get_cliente(String ip_cliente) {
		for (Cliente c: clientes) 
			if (c.get_ip().equals(ip_cliente)) return c;
		Cliente r = new Cliente(ip_cliente);
		clientes.add(r);
		return r;
	}
	
	

	
	public static void main(String[] args) {
		Servidor a = new Servidor("0000");
	}

}
