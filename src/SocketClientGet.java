import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
///////////////////////////////////////////////////////
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
	public void ReadMessage(){
		try {
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
			System.out.println("收到消息TYPE: "+type);
			//不同type后续处理不同
			switch(type) {
				case 0:
					byte[] message = new byte[length-4];
					System.arraycopy(buffer,readOffset,message,0,message.length);
					String s = new String(message,"UTF-8");
					System.out.println(s);
					readOffset += length-4;
					break;
				case 1:
					UserLoginReturn();
					break;
				case 2:
					BuildRoomReturn();
					break;
				case 3:
					UserJoinRoomReturn();
					break;
				case 4:
					UserSendMessageInRoomReturn();
					break;
				case 5:
					UserLeaveRoomReturn();
					break;
				case 7:
					UserFindAllRoomReturn();
					break;
				case 8:
					UserRegisterReturn();
					break;
				}
				messageLength -= length+4;
			}
		}catch (Exception e) {  }
	}
	
	private void UserLoginReturn() throws UnsupportedEncodingException {
		String succeedOrNot = getStringFromBuffer();
		
		String error = getStringFromBuffer();
		
		GetTool.UserLoginReturn(succeedOrNot, error);
	}
	private void UserRegisterReturn() throws UnsupportedEncodingException {
		String succeedOrNot = getStringFromBuffer();
		
		String error = getStringFromBuffer();
		
		GetTool.UserRegisterReturn(succeedOrNot, error);
	}
	private void BuildRoomReturn() throws UnsupportedEncodingException {

		String succeedOrNot = getStringFromBuffer();
		
		String roomName = getStringFromBuffer();
		
		GetTool.BuildRoomReturn(succeedOrNot, roomName);
	}
	
	private void UserJoinRoomReturn() throws UnsupportedEncodingException {

		String succeedOrNot = getStringFromBuffer();
		
		String userNames = getStringFromBuffer();
		
		String[] allUserNames = userNames.split(",");
		
		GetTool.UserJoinRoomReturn(succeedOrNot, allUserNames);
	}
	
	private void UserSendMessageInRoomReturn() throws UnsupportedEncodingException {
		String userName = getStringFromBuffer();
		
		String stringMessage = getStringFromBuffer();
		GetTool.UserSendMessageInRoomReturn(userName, stringMessage);
	}
	private void UserLeaveRoomReturn() throws UnsupportedEncodingException {
		String unLockRooms = getStringFromBuffer();
		String[] unLockRoomNames;
		if(!unLockRooms.equals("None")) {
			unLockRoomNames = unLockRooms.split(",");
		}else {
			unLockRoomNames = new String[0];
		}
		
		String lockedRooms = getStringFromBuffer();
		String[] lockedRoomNames;
		if(!lockedRooms.equals("None")) {
			lockedRoomNames = lockedRooms.split(",");
		}else {
			lockedRoomNames = new String[0];
		}
		GetTool.UserLeaveRoomReturn(unLockRoomNames, lockedRoomNames);
	}
	private void UserFindAllRoomReturn() throws UnsupportedEncodingException {
		String unLockRooms = getStringFromBuffer();
		String[] unLockRoomNames;
		if(!unLockRooms.equals("None")) {
			unLockRoomNames = unLockRooms.split(",");
		}else {
			unLockRoomNames = new String[0];
		}
		
		String lockedRooms = getStringFromBuffer();
		String[] lockedRoomNames;
		if(!lockedRooms.equals("None")) {
			lockedRoomNames = lockedRooms.split(",");
		}else {
			lockedRoomNames = new String[0];
		}
		
		GetTool.UserFindAllRoomReturn(unLockRoomNames, lockedRoomNames);
	}
	//只能用于有多个值信息
	private String getStringFromBuffer() throws UnsupportedEncodingException {
		byte[] messageL = new byte[4];
		System.arraycopy(buffer,readOffset,messageL,0,messageL.length);
		int byteL = TurnBytesToInt(messageL);
		readOffset += 4;
		byte[] messagebyte = new byte[byteL];
		System.arraycopy(buffer,readOffset,messagebyte,0,messagebyte.length);
		String stringMessage = new String(messagebyte,"UTF-8");
		readOffset += byteL;
		return stringMessage;
	}
	
	private int TurnBytesToInt(byte[] b) {
		int i = (int) ((b[0] & 0xff) | ((b[1] & 0xff) << 8) 
				| ((b[2] & 0xff) << 16) | ((b[3] & 0xff) << 24));
		return i;
	}
}
