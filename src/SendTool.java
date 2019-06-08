import java.io.File;
import java.io.FileInputStream;

public class SendTool {
	private static SocketClientSend clientSend;
	
	public static void setClientSend(SocketClientSend clientSend) {
		SendTool.clientSend = clientSend;
	}
	
	public static void sendUrgentData() {
		if (clientSend==null)return;
		clientSend.SendUrgentData();
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
	
	public static void SendMessageInRoom(String message) {
		if (clientSend==null)return;
		if(message.equals("")||message==null)return;
		clientSend.UserInput(message, 4);
	}
	public static void LeaveRoom() {
		if (clientSend==null)return;
		clientSend.UserInput("leave", 5);
	}
	//6是服务器发送系统信息，客户端发送没有type 6
	public static void GetAllRoom() {
		if (clientSend==null)return;
		clientSend.UserInput("allRoom", 7);
	}
	public static void GetAllFileInRoom() {
		if (clientSend==null)return;
		clientSend.UserInput("allFile", 10);
	}
	public static void GetFileInRoom(String fileName) {
		if (clientSend==null)return;
		clientSend.UserInput(fileName, 11);
	}
	public static void SendFileInRoom(String FileAddress) {
		if (clientSend==null)return;
		if(FileAddress.equals("")||FileAddress==null)return;

	    FileInputStream fis;
		try {
			//File file = new File("E:\\JDK1.6中文参考手册(JDK_API_1_6_zh_CN).CHM");
			File file = new File(FileAddress);
			if(file.exists()) {
				if(file.isDirectory())return;
                fis = new FileInputStream(file);
                String fileName = file.getName();
                if(fileName.equals("")||fileName==null)return;
                long fileLength = file.length();
        		clientSend.UserInput(fileName,fileLength, 9);
        		
        		byte[] bytes = new byte[2048];
                int length = 0;
                long progress = 0;
                while((length = fis.read(bytes, 0, bytes.length)) != -1) {
                	clientSend.UserInput(bytes,length);
                    
                    //打印传输的百分比
                    progress += length;
                    System.out.print("| " + (100*progress/file.length()) + "% |");
                }
                System.out.println(" ");
                fis.close();
            }
		}catch(Exception e) {}
	}
}
