package app;

import camada.Camada;
import camada.aplicacao.AplicacaoCliente;

public abstract class App{
	protected String IP;
	protected Camada send;
	
	public App(String ip) {
		this.IP = ip;
		send = new AplicacaoCliente(this,ip);
	}
	
	
	public abstract void send(String message);
	
	
}
