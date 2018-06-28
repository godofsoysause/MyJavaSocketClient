import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
		        	messageLength = in.read(buffer,0,buffer.length);
		        	ReadMessage();
	            }
			}
	      }catch (IOException e) {
	         //e.printStackTrace();
	    	  System.out.println(e);
	      }catch (Exception e) {
		     //e.printStackTrace();
	    	  System.out.println(e);
		  }
	}
	public void start () {
	      System.out.println("Starting " +  threadName );
	      if (t == null) {
	         t = new Thread (this, threadName);
	         t.start ();
	      }
	}
	
	private byte[] buffer = new byte[2048];
	private int readOffset = 0;
	//信息总长度（可能不止一条信息）
	int messageLength = 0;
	public void ReadMessage() throws UnsupportedEncodingException {
		readOffset = 0;
		byte[] b1_length = new byte[4];
		byte[] type_byte = new byte[4]; 
		int length = 0;
		int type = 0;
		while(messageLength>8) {
			System.arraycopy(buffer,readOffset,b1_length,0,b1_length.length);
			length = (int) ((b1_length[0] & 0xff) | ((b1_length[1] & 0xff) << 8) 
			| ((b1_length[2] & 0xff) << 16) | ((b1_length[3] & 0xff) << 24)); 
			if(length>messageLength-4||length<=4)return;

			readOffset += 4;
			System.arraycopy(buffer,readOffset,type_byte,0,type_byte.length);
			type = (int) ((type_byte[0] & 0xff) | ((type_byte[1] & 0xff) << 8) 
					| ((type_byte[2] & 0xff) << 16) | ((type_byte[3] & 0xff) << 24));
			readOffset += 4;
			
			//不同type后续处理不同
			switch(type) {
				case 0:
					byte[] message = new byte[length-4];
					System.arraycopy(buffer,readOffset,message,0,message.length);
					String s = new String(message,"UTF-8");
					System.out.println(s);
					readOffset += length-4;
					break;
			}
			messageLength -= length+4;
		}
	}
}
