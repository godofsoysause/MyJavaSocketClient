import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


public class SocketClientSend implements Runnable{
	private Thread t;
	private String threadName;
	private Socket server;
	
	public SocketClientSend(Socket server) {
		threadName = "SocketClientSend";
		this.server = server;
	}
	public void run() {
		try {
			if(server!=null) {
				DataOutputStream out = new DataOutputStream(server.getOutputStream());
				while(true) {
		           	 out.write(buffer,0,messageLength);
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
	private String UserInput() {
		return "";
	}
	
	byte[] buffer = new byte[1024];
	int messageLength = 0;
	private int BuildMessage() {
		//buffer
		return messageLength;
	}
}
