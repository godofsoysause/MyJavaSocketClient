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
			
			if(args[0].equals("0")) {
			SendTool.Login("yao","123");
			SendTool.BuildRoom("YAORoom","");
			
			while(true) {
				SendTool.SendMessageInRoom("发一条消息\n换行一下");
				SendTool.GetAllRoom();
				Thread.sleep(5000);
				}
			}else if(args[0].equals("1")){
				SendTool.Login("PPP","123");
				SendTool.GetAllRoom();
				SendTool.JoinRoom("YAORoom","");
				//while(true) {}
			}
			//SendTool.LeaveRoom();
			
		}catch(Exception e) {
			System.out.println(e);
		}
	}   
}

