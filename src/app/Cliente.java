package app;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;


public class Cliente extends App{
	private JFrame janela;
	private JButton enviar;
	private JTextField enviar_;
	DefaultListModel<String> resposta ;
    
	public Cliente(String ip) {
		super(ip);
		janela = new JFrame("Cliente " + ip);
		janela.setSize(400,400);
		janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		janela.setLayout(new GridLayout(3,1,5,5));
		

		enviar = new JButton("Send");
		enviar.addActionListener(new ActionListener(){  
		
			@Override
			public void actionPerformed(ActionEvent e) {
				send(enviar_.getText());
				enviar_.setText("");
				
			}  
	    }); 
		
		
		enviar_ =new JTextField();  
		
		resposta = new DefaultListModel<String>();
		JList<String> list = new JList<String>(resposta);
		
		janela.add(new JScrollPane(list));
		
		janela.add(enviar_);
		
		janela.add(enviar);
		
		  
		
		janela.setVisible(true);

		
	}
	
	
	@Override
	public void send(String A) {
		String send_my = IP;				//4 len
		String send_dest = "0000";
		send.send(send_dest + send_my + A );
		
	}




	@Override
	public synchronized void receive(String message) {
		//janela.remove(resposta);
		resposta.addElement(message);
		//janela.revalidate();
	    //janela.repaint();
	}
	
	
	
	public static void main(String[] args) {
		Cliente a = new Cliente ("1101");
	
		
	}
}
