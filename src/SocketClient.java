import java.net.Socket;



public class SocketClient {
	public static void main(String args[]) {
		try {
			String address = "192.168.43.163";//"127.0.0.1";
			Socket server = new Socket(address,8888); 
			server.setOOBInline(true);  
			if(server!=null) {
			    SocketClientGet sg = new SocketClientGet(server);
			    SocketClientSend ss = new SocketClientSend(server);
			    sg.start();
			    
			    SendTool.setClientSend(ss);
			}

			SendTool.Login("xiaokang","123");
			SendTool.BuildRoom("YAORoom","");
			while(true) {
			Thread.sleep(5000);
			System.out.println("Send");
			SendTool.sendUrgentData();}
			//Thread.sleep(5000);
			/*Thread.sleep(1000);
			SendTool.SendFileInRoom("C:\\Users\\dell\\Desktop\\NEWpicture\\pokemmos.jpg");
			Thread.sleep(1000);
			SendTool.SendFileInRoom("C:\\Users\\dell\\Desktop\\NEWpicture\\timg.jpg");
			SendTool.GetAllFileInRoom();
			Thread.sleep(1000);
			SendTool.GetFileInRoom("pokemmos.jpg");
			SendTool.GetAllRoom();
			SendTool.GetAllFileInRoom();*/
			/*while(true) {
				SendTool.SendMessageInRoom("发一条消息\n换行一下");
				SendTool.GetAllRoom();
				Thread.sleep(1000);
				}*/
			/*if(args[0].equals("0")) {
			
			/*while(true) {
				SendTool.SendMessageInRoom("发一条消息\n换行一下");
				SendTool.GetAllRoom();
				Thread.sleep(5000);
				}*/
			/*}else if(args[0].equals("1")){
				SendTool.Login("PPP","123");
				SendTool.GetAllRoom();
				SendTool.JoinRoom("YAORoom","");
				//while(true) {}
			}*/
			//SendTool.LeaveRoom();
			
		}catch(Exception e) {
			System.out.println(e);
		}
	}   
}

