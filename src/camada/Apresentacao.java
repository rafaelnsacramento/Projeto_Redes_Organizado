package camada;

public class Apresentacao extends Camada { //A camada de apresenta��o pega a mensagem da camada de  aplica��o, transforma em bytes e envia para de Sess�o

	public Apresentacao(Camada receive_, String ip) {
		super(receive_, ip);
		set_send(new Sessao(this,ip));
	}

	@Override
	public void send(String bytes) {
		Imprimir("Apresenta��o",bytes);
		
		
		String send_Message = bytes.substring(8);
		
		byte [] send_Message_Bytes_ = send_Message.getBytes();
		
		String send_Message_Bytes = "";
		
		
		for (int i = 0; i < send_Message_Bytes_.length; i++)	
			send_Message_Bytes = send_Message_Bytes + (String.format("%8s", Integer.toBinaryString((byte)send_Message_Bytes_[i] & 0xFF )).replace(' ', '0'));		
		
		
		send.send(bytes.substring(0,8) + send_Message_Bytes);
		
	}

	@Override
	public void receive(String Bytes) {
		Imprimir("Apresenta��o Recebendo", Bytes);
		String send_ = Bytes.substring(0, 8);
		String a = Bytes.substring(8);
		
		for (int i = 0 ; i < a.length()/8; i++)
			send_ = send_ + (char)Byte.parseByte(a.substring(i*8, (i+1)*8),2);
		receive.receive(send_);
	}

}
