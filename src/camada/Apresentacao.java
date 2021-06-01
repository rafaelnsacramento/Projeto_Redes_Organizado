package camada;

public class Apresentacao extends Camada { //A camada de apresentação pega a mensagem da camada de  aplicação, transforma em bytes e envia para de Sessão

	public Apresentacao(Camada receive_, String ip) {
		super(receive_, ip);
		set_send(new Sessao(this,ip));
	}

	@Override
	public void send(String bytes) {
		Imprimir("Apresentação",bytes);
		
		
		String send_Message = bytes.substring(8);
		
		byte [] send_Message_Bytes_ = send_Message.getBytes();
		
		String send_Message_Bytes = "";
		
		
		for (int i = 0; i < send_Message_Bytes_.length; i++)	
			send_Message_Bytes = send_Message_Bytes + (String.format("%8s", Integer.toBinaryString(send_Message_Bytes_[i])).replace(' ', '0'));		
		
		
		send.send(bytes.substring(0,8) + send_Message_Bytes);
		
	}

	@Override
	public void receive(String Bytes) {
		// TODO Auto-generated method stub
		
	}

}
