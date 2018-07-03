import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.net.Socket;

public class SocketClientGet implements Runnable{
	private Thread t;
	private String threadName;
	private Socket server;
	DataInputStream in;
	public SocketClientGet(Socket server) {
		threadName = "SocketClientGet";
		this.server = server;
	}
	public void run() {
		try {
			if(server!=null) {
				in = new DataInputStream(server.getInputStream());
		        while(true) {
		        	messageLength += in.read(buffer,messageLength,buffer.length-messageLength);
		        	ReadMessage();
	            }
			}
	      }catch (IOException e) {
	         //e.printStackTrace();
	    	  System.out.println(e);
	      }catch (Exception e) {
		     //e.printStackTrace();
	    	  System.out.println("SocketClientGet.run"+e);
		  }finally {
				try {
					in.close();
					server.close();
				}catch(Exception e) {}
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
		while(true) {
			if(messageLength>8) {
			System.arraycopy(buffer,readOffset,b1_length,0,b1_length.length);
			length = (int) ((b1_length[0] & 0xff) | ((b1_length[1] & 0xff) << 8) 
			| ((b1_length[2] & 0xff) << 16) | ((b1_length[3] & 0xff) << 24)); 
			if(length<=4) {
				readOffset += 4+length;
				messageLength -= length+4;
				continue;
			}

			//解决半包问题
			if(length>messageLength-4) {
				if(length>buffer.length-4) {
					buffer = Arrays.copyOf(buffer, length+4);
				}
				System.arraycopy(buffer,readOffset,buffer,0,messageLength);
				return;
			}

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
				case 9:
					UserSendFileReturn();
					break;
				case 10:
					UserFindAllFileReturn(length);
					break;
				case 11:
					UserGetFileInRoomReturn(length);
					return;
				}
				messageLength -= length+4;
			}else {
				System.arraycopy(buffer,readOffset,buffer,0,messageLength);
				if(buffer.length>2048) {
					buffer = Arrays.copyOf(buffer, 2048);
				}
				//break;
				return;
			}}
		}catch (Exception e) { 
			System.out.println("SocketClientGet.ReadMessage"+e);
		}
	}
	
	private void UserGetFileInRoomReturn(int subLength) {
		byte[] temp_b;
		try {
			String fileName = getStringFromBuffer();
			Long fileLength = getLongFromBuffer();
			messageLength -= subLength+4;
			if(messageLength>0) {
				byte[] bytes = new byte[messageLength];
				System.arraycopy(buffer,readOffset,bytes,0,messageLength);
				temp_b = GetTool.UserGetFileInRoomReturn(in,fileName,fileLength,bytes);
			}else {
				byte[] bytes = new byte[0];
				temp_b = GetTool.UserGetFileInRoomReturn(in,fileName,fileLength,bytes);
			}
			if(temp_b.length>0) {
				System.arraycopy(temp_b,0,buffer,0,temp_b.length);
				messageLength = temp_b.length;
			}else {
				messageLength = 0;
			}
		}catch(Exception e) {
			System.out.println("SocketClientGet.UserGetFileInRoomReturn"+e);
		}
	}
	private void UserFindAllFileReturn(int length) throws UnsupportedEncodingException {
		byte[] message = new byte[length-4];
		System.arraycopy(buffer,readOffset,message,0,message.length);
		String s = new String(message,"UTF-8");
		String[] allFilesName;
		if(!s.equals("None")) {
			allFilesName = s.split(",");
		}else {
			allFilesName = new String[0];
		}
		readOffset += length-4;
		GetTool.UserFindAllFileReturn(allFilesName);
	}
	private void UserSendFileReturn() throws UnsupportedEncodingException {
		String succeedOrNot = getStringFromBuffer();
		
		String error = getStringFromBuffer();
		
		GetTool.UserSendFileReturn(succeedOrNot, error);
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
	
	private Long getLongFromBuffer() throws UnsupportedEncodingException {
		readOffset += 4;
		byte[] messagebyte = new byte[8];
		System.arraycopy(buffer,readOffset,messagebyte,0,messagebyte.length);
		Long l = bytes2Long(messagebyte);
		readOffset += 8;
		return l;
	}
	public static long bytes2Long(byte[] byteNum) {
		long num = 0;
		for (int ix = 0; ix < 8; ++ix) {
			num <<= 8;
			num |= (byteNum[ix] & 0xff);
		}
		return num;
	}
	private int TurnBytesToInt(byte[] b) {
		int i = (int) ((b[0] & 0xff) | ((b[1] & 0xff) << 8) 
				| ((b[2] & 0xff) << 16) | ((b[3] & 0xff) << 24));
		return i;
	}
}
