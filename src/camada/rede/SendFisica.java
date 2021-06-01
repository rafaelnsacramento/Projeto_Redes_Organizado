package camada.rede;

import java.util.LinkedList;
import java.util.Queue;

import camada.Camada;

public class SendFisica implements Runnable{
	private Camada send;
	private Queue<String> queue;
	private boolean rs;
	private String buffer;
	
	
	public SendFisica(Camada s) {
		send = s;
		queue = new LinkedList<String>();;
		rs = false;
	}
	
	public String getBuffer() {
		return buffer;
	}
	
	public void offer(String bytes) {
		queue.offer(bytes);
	}
	
	public synchronized void resend() {
		rs = true;
		notify();		
	}
	
	
	public void send() throws InterruptedException {//Tirando da fila e enviando para a camada fisica, antes de enviar o próximo espera a resposta do outro lado pra saber se recebeu corretamente ou é para reenviar
		buffer = queue.poll();
		send.send(buffer);
	}

	
	
	@Override
	public synchronized void run() {
		while(true) {
			while(queue.isEmpty())
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				};
			try {
				if(!queue.isEmpty()) send();
				wait(20000);
				if(!rs) ;
				else {
					System.out.println("Rede Reenviando"+ buffer);
					rs = false;
					queue.add(buffer);
				}
				
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	

	
}
