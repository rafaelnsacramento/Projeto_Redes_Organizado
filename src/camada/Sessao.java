package camada;

import camada.transporte.Transporte;

public class Sessao extends Camada {// A camada de sessão irá separar a mensagem em quadros com cabeçario definido além do checksum  para enviar para camada de transporte
	//		0/1				0000			0000			00000000		0/1			000000
	//	0 = começo		IP destino			IP fonte		Mensagem		1=fim		checksum
	
	

	public Sessao(Camada r, String ip) {
		super(r, ip);
		set_send(new Transporte(this,ip));
		new Thread((Runnable) send).start();
	}

	@Override
	public void send(String bytes) {
		Imprimir("Sessão",bytes);
		String send_message="";
		String ip_dest_font = bytes.substring(0,8);
		String message = bytes.substring(8);		
		for (int i = 0; i< message.length() / 8; i++) {
			String send_ = "";
			if (i == 0) send_ = send_ + "0";
			else send_ = send_ + "1";
			send_ = send_ + ip_dest_font; 
			send_ = send_ + message.substring(i*8,(i+1)*8);
			if (i+1 >= message.length() /8) send_= send_ + "1";
			else send_ = send_ + "0";
			
			send_ = send_ + checksum(send_);
			send_message = send_message + send_;
			
		}
		
		send.send(send_message);
	}
	
	private String checksum(String bytes) {
		String r = "";
		
		String a1 = bytes.substring(0, 6);
		String a2 = bytes.substring(6, 12);
		String a3 = bytes.substring(12, 18);
		
		
		
		int carry = 0;
		for (int i = 0; i < 6; i++) {
			int x = Character.getNumericValue(a1.codePointAt(i))  + Character.getNumericValue(a2.codePointAt(i)) + Character.getNumericValue(a3.codePointAt(i)) + carry;
			r = r + Integer.toString(x & 1);
			carry = (x & 1) >> 1;
		}	
		
		
		return r;
	}

	@Override
	public void receive(String Bytes) {
		// TODO Auto-generated method stub
		
	}
	
	
	public boolean check(String Bytes) {
		String a = Bytes.substring(0,18);
		String checksum = Bytes.substring(18,24);
		return checksum(a).equals(checksum);
		
	}

}
