package app;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class Servidor extends App {

	private List<Cliente> clientes;
	private JFrame janela;
	DefaultListModel<String> resposta ;

	
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
		
		
		janela = new JFrame("Servidor " + ip);
		janela.setSize(400,400);
		janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		janela.setLayout(new GridLayout(1,1,5,5));
		
;  
		
		resposta =  new DefaultListModel<String>();
		JList<String> list = new JList<String>(resposta);
		
		janela.add(new JScrollPane(list));		
		  
		
		janela.setVisible(true);
	}

	@Override
	public void send(String message) {
		String send_my = IP;
		String send_dest = message.substring(0,4);
		String message_ = message.substring(4);
		resposta.addElement("Enviando: "+ send_dest + send_my + message_);
		
		send.send(send_dest + send_my + message_);
		
	}
	
	
	

	@Override
	public void receive(String message) {
		System.out.println(message);
		resposta.addElement("Recebendo: "+ message);
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
