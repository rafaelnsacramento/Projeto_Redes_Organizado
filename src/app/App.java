package app;

import camada.Camada;
import camada.aplicacao.AplicacaoCliente;
import camada.aplicacao.AplicacaoServidor;

public abstract class App{
	protected String IP;
	protected Camada send;
	
	public App(String ip) {
		this.IP = ip;
		if(ip.equals("0000"))
			send = new AplicacaoServidor(this,ip);
		else send = new AplicacaoCliente(this,ip);
	}
	
	
	public abstract void send(String message);
	public abstract void receive(String message);
	
}
