import java.net.Socket;


public class SocketClient {
	public static void main(String args[]) {
		try {
			String address = "127.0.0.1";
			Socket server = new Socket(address,8888); 
			if(server!=null) {
			    SocketClientGet sg = new SocketClientGet(server);
			    SocketClientSend ss = new SocketClientSend(server);
			    sg.start();
			    
			    SendTool.setClientSend(ss);
			}
			
			SendTool.Login("yao","123");
			SendTool.BuildRoom("YAORoom","");
			SendTool.SendMessageInRoom("发一条消息\n换行一下");
			SendTool.LeaveRoom();
			SendTool.GetAllRoom();
		}catch(Exception e) {
			System.out.println(e);
		}
	}   
}

