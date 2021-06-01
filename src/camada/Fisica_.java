package camada;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;


public class Fisica_ extends Camada implements Runnable{
	
	private DatagramSocket socket_send;
	private InetAddress address;
    private byte[] buf ;
    
    private byte[] buf_receive;
	private DatagramSocket socket_receive;

	private final String [] ips = {"0000","0001","0010","0011","0100","0101","0110","0111","1000","1001","1010","1011","1100","1101","1110","1111"};

	public Fisica_(Camada r, String ip) {
		super(r, ip);
		set_send(null);	
		
		try {
			socket_send = new DatagramSocket();
	        address = InetAddress.getByName("localhost");
	        
	        socket_receive = new DatagramSocket(4445);
			buf_receive = new byte[1024];

		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while(true) {
			receive("");
		}
		
	}

	@Override
	public void send(String bytes) {
		Imprimir("Física",bytes);
		buf = bytes.getBytes();
		
		
		
		
		try {
			for (int i = 0; i<ips.length;i++) {
				DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 10000 + Integer.parseInt(ips[i]));
				socket_send.send(packet);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void receive(String Bytes) {
		DatagramPacket packet 
        = new DatagramPacket(buf_receive, buf_receive.length);
		try {
			socket_receive.receive(packet);
			InetAddress address = packet.getAddress();
            int port = packet.getPort();
            packet = new DatagramPacket(buf_receive, buf_receive.length, address, port);
            String received 
              = new String(packet.getData(), 0, packet.getLength());
            
            //System.out.println("Mensagem Recebida pelo socket: " + received);
            buf_receive = new byte[1024];
           send(received);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
	
	
	public static void main(String[] args) throws IOException {
		Fisica_ b = new Fisica_(null,"");
		Thread a = new Thread(b);
		a.start();
		
		     
		   
	}

}
