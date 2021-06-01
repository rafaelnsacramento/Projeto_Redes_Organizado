  package camada.aplicacao;

import app.App;
import camada.Apresentacao;
import camada.Camada;

public class AplicacaoCliente extends Camada{
	private App receive;
	
	public AplicacaoCliente(App rec, String ip) {
		super(null, ip);
		set_send(new Apresentacao(this,ip));
		this.receive = rec;
	}
	

	@Override
	public void send(String bytes) {
		Imprimir("Aplicação",bytes);

		String a = bytes.substring(8);
		String send_message = null;
		
		if (a.length() == 1)
			send_message =  a;
		
		switch (a) {
			case "PING":
				send_message = "PN";
				break;
			case "RECOVER":
				send_message = "RC";
				break;
		}
		
		send.send(bytes.substring(0, 8) + send_message);
	}


	@Override
	public void receive(String Bytes) {
		// TODO Auto-generated method stub
		
	}

	

}
