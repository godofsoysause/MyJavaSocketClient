public class SendTool {
	private static SocketClientSend clientSend;

	public static void setClientSend(SocketClientSend clientSend) {
		SendTool.clientSend = clientSend;
	}
	
	public static void Login(String userName,String password) {
		if (clientSend==null)return;
		if(userName.equals("")||userName==null)return;
		if (password==null)password="";
		clientSend.UserInput(userName, password, 1);
	}
	
	public static void Register(String userName,String password) {
		if (clientSend==null)return;
		if(userName.equals("")||userName==null)return;
		if (password==null)password="";
		clientSend.UserInput(userName, password, 8);
	}
	
	public static void BuildRoom(String roomName,String password) {
		if (clientSend==null)return;
		if(roomName.equals("")||roomName==null)return;
		if (password==null)password="";
		clientSend.UserInput(roomName, password, 2);
	}
	
	public static void JoinRoom(String roomName,String password) {
		if (clientSend==null)return;
		if(roomName.equals("")||roomName==null)return;
		if (password==null)password="";
		clientSend.UserInput(roomName, password, 3);
	}
	
	public static void sendMessageInRoom(String message) {
		if (clientSend==null)return;
		if(message.equals("")||message==null)return;
		clientSend.UserInput(message, 4);
	}
	public static void leaveRoom() {
		if (clientSend==null)return;
		clientSend.UserInput("leave", 5);
	}
	//6是服务器发送系统信息，客户端发送没有type 6
	public static void getAllRoom() {
		if (clientSend==null)return;
		clientSend.UserInput("allRoom", 7);
	}
}
