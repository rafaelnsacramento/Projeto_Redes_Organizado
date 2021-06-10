package camada.aplicacao;

import app.App;
import camada.Apresentacao;
import camada.Camada;

public class AplicacaoServidor extends Camada{
private App receive;
	
	public AplicacaoServidor(App rec, String ip) {
		super(null, ip);
		set_send(new Apresentacao(this,ip));
		this.receive = rec;
	}
	

	@Override
	public void send(String bytes) {
		Imprimir("Aplicação",bytes);
		send.send(bytes);
	}


	@Override
	public void receive(String Bytes) {
		Imprimir("Aplicação Recebendo",Bytes);
		
		String a = Bytes.substring(8);
		String send_ = Bytes.substring(4, 8);
		if (a.length() == 1) 
			send_ += a;		
		else {
			switch (a) {
			case "PN":
				send_ += "PING";
				receive.receive(send_);
				return;
			case "RC":
				send_ +=  "RECOVER";
				receive.receive(send_);
				return;
			case "LT":
				send_ +=  "LAST";
				receive.receive(send_);
				return;
			}
			
			send_ += "Error, comando não reconhecido";
		}
		receive.receive(send_);
		
	}

}
