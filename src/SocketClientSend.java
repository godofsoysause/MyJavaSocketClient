import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.*;

public class SocketClientSend implements Runnable{
	private Thread t;
	private String threadName;
	private Socket server;
	private Scanner scanner;
	private boolean send = false;
	public SocketClientSend(Socket server) {
		threadName = "SocketClientSend";
		this.server = server;
		scanner =new Scanner(System.in);
	}
	public void run() {
		try {
			if(server!=null) {
				DataOutputStream out = new DataOutputStream(server.getOutputStream());
				while(true) {
					//仅命令行版本使用  UserInput()
					UserInput();
					if(send) {
			           	out.write(buffer,0,messageLength);
			           	send = false;
					}
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
	//命令行输入
	private void UserInput() {
		String message = scanner.next();
		BuildMessage(message,0);
	}
	
	byte[] buffer = new byte[1024];
	int messageLength = 0;
	//type什么类型的消息，0是命令行输入  可以外部调用
	public int BuildMessage(String s,int type) {
		//buffer
		send = true;
		return messageLength;
	}

}
