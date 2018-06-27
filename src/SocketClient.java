import java.net.Socket;


public class SocketClient {
	 public static void main(String args[]) {
		try {
			Socket server = new Socket("127.0.0.1",8888); 
			if(server!=null) {
			    SocketClientGet sg = new SocketClientGet(server);
			    SocketClientSend ss = new SocketClientSend(server);
			    sg.start();
			    ss.start();
			}
		}catch(Exception e) {
			System.out.println(e);
		}
	 }   
}

