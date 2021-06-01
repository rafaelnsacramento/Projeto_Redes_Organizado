package backup;

import java.util.LinkedList;
import java.util.Queue;

import camada.Camada;
import camada.Fisica;

public class Rede_Backup extends Camada implements Runnable{// Gerencia os pacotes a serem enviados para a camada física e se será necessário reenviar o pacote. 
	
	private Thread t1;

	private class Rede_send implements Runnable{
		private Queue<String> queue;
		public Rede_send(Queue<String> x) {
			queue = x;
		}
		
		public synchronized void offer(String bytes) {
			queue.offer(bytes);
			notify();
		}
		
		public synchronized void send() {
			while (!queue.isEmpty()) {
				String r = queue.poll();
				System.out.println(r);
			}
		}
		
		
		@Override
		public synchronized void run() {
			while(true) {
				send();
				try {
					wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		
	}
	
	private Rede_send send;	
	private Queue<String> queue;


	public Rede_Backup(Camada r) {
		super(r, null);
		set_send(new Fisica(this));
		queue =  new LinkedList<String>();
		send = new Rede_send( new LinkedList<String>());
		t1 = new Thread(send);
		t1.start();
	}

	@Override
	public void send(String bytes) {
		Imprimir("Rede", bytes);
		send.offer(bytes);
	}
	

	
	public synchronized void read() {
		
	}

	@Override
	public void receive(String Bytes) {
		// TODO Auto-generated method stub
		
	}

	
	
	
	@Override
	public synchronized void run() {
		while(true) {
			read();
			
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}

	

}
