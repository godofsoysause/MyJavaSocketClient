import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SocketClientGet implements Runnable{
	private Thread t;
	private String threadName;
	private Socket server;
	public SocketClientGet(Socket server) {
		threadName = "SocketClientGet";
		this.server = server;
	}
	public void run() {
		try {
			if(server!=null) {
				DataInputStream in = new DataInputStream(server.getInputStream());
		        while(true) {
		        	in.read(buffer,0,buffer.length);
		        	ReadMessage();
	            }
			}
	      }catch (IOException e) {
	         //e.printStackTrace();
	      }catch (Exception e) {
		     //e.printStackTrace();
		  }
	}
	public void start () {
	      System.out.println("Starting " +  threadName );
	      if (t == null) {
	         t = new Thread (this, threadName);
	         t.start ();
	      }
	}
	
	byte[] buffer = new byte[1024];
	int readOffset = 0;
	int messageLength = 0;
	public void ReadMessage() {
		
	}
}
