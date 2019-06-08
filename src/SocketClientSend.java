import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.*;

public class SocketClientSend{
	private Socket server;
	private Scanner scanner;
	DataOutputStream out;
	public SocketClientSend(Socket server) {
		this.server = server;
		scanner =new Scanner(System.in);
		scanner.useDelimiter("\n");
		try {
			if(this.server!=null) {
				out = new DataOutputStream(this.server.getOutputStream());
			}       
	      }catch (IOException e) {	}
		   catch (Exception e) {   }
	}

	//命令行输入
	private void UserInput() {
		//scanner.nextLine();
		String message = scanner.next();
		BuildMessage(message,0);
		try {
			out.write(buffer,0,messageLength);
		}catch(Exception e) {}
	}
	//外部调用
	//发送紧急数据，判断连接是否断开
	public void SendUrgentData() {
		try {
			server.sendUrgentData(0xFF);
		}catch(Exception e){}
	}
	//传输文件
	public void UserInput(String fileName,long fileLength,int type) {
		try {
			BuildMessage(fileName,fileLength,type);
			out.write(buffer,0,messageLength);
		}catch(Exception e) {}
	}
	public void UserInput(byte[] bytes,int length) {
		try {
			out.write(bytes,0,length);
		}catch(Exception e) {}
	}
	//信息传输
	public void UserInput(String message,int type) {
		BuildMessage(message,type);
		try {
			out.write(buffer,0,messageLength);
		}catch(Exception e) {}
	}
	public void UserInput(String message,String message2,int type) {
		BuildMessage(message,message2,type);
		try {
			out.write(buffer,0,messageLength);
		}catch(Exception e) {}
	}
	
	private byte[] buffer = new byte[2048];
	private int messageLength = 0;
	//type什么类型的消息，0是命令行输入  可以外部调用
	//只传一个值的BuildMessage
	private int BuildMessage(String s,int type) {
		try {
		byte[] b1 = s.getBytes("UTF-8");
		if(b1.length > buffer.length-40||b1.length==0)return 0;
		int i = 0;

		messageLength = b1.length+8;
		//开头4字节是整个有效信息的长度
		i = messageLength-4;
		
		byte[] b1_length = new byte[4]; 
		b1_length[0] = (byte) (i & 0xff); 
		b1_length[1] = (byte) ((i >> 8) & 0xff); 
		b1_length[2] = (byte) ((i >> 16) & 0xff); 
		b1_length[3] = (byte) ((i >> 24) & 0xff); 
		
		byte[] type_byte = new byte[4]; 
		type_byte[0] = (byte) (type & 0xff); 
		type_byte[1] = (byte) ((type >> 8) & 0xff); 
		type_byte[2] = (byte) ((type >> 16) & 0xff); 
		type_byte[3] = (byte) ((type >> 24) & 0xff); 
		
		System.arraycopy(b1_length,0,buffer,0,b1_length.length);
		System.arraycopy(type_byte,0,buffer,b1_length.length,4);
				/*
			      public static void arraycopy(Object src, int srcPos, Object dest, int destPos, int length)
					代码解释:
		　　			Object src : 原数组
		   			int srcPos : 从元数据的起始位置开始
		　　			Object dest : 目标数组
		　　			int destPos : 目标数组的开始起始位置
		　　			int length  : 要copy的数组的长度
				  */
		System.arraycopy(b1,0,buffer,b1_length.length+4,b1.length);

		}catch(Exception e) {
			
		}
		return messageLength;
	}

	//传两个值的BuildMessage
	private int BuildMessage(String s,String s2,int type) {
		try {
		byte[] b1 = s.getBytes("UTF-8");
		byte[] b2 = s2.getBytes("UTF-8");
		if((b2.length==0 && b1.length==0)||b2.length+b1.length>buffer.length-40)return 0;
		int i = 0;
		int b1_l = 0;
		int b2_l = 0;
		
		messageLength = b1.length + b2.length + 16;
		b1_l = b1.length;
		b2_l = b2.length;
		i = messageLength-4;
		
		byte[] b_length = TurnIntToBytes(i); 	
		byte[] type_byte = TurnIntToBytes(type); 
		byte[] b1_length = TurnIntToBytes(b1_l);
		byte[] b2_length = TurnIntToBytes(b2_l);
		
		System.arraycopy(b_length,0,buffer,0,b_length.length);
		System.arraycopy(type_byte,0,buffer,
				b_length.length,type_byte.length);
		System.arraycopy(b1_length,0,buffer,
				b_length.length+type_byte.length,b1_length.length);
		System.arraycopy(b1,0,buffer,
				b_length.length+type_byte.length+b1_length.length,b1.length);
		System.arraycopy(b2_length,0,buffer,
				b_length.length+type_byte.length+b1_length.length+b1.length,b2_length.length);
		System.arraycopy(b2,0,buffer,
				b_length.length+type_byte.length+b1_length.length+b1.length+b2_length.length,b2.length);
		
		}catch(Exception e) {
			
		}
		return messageLength;
	}
	private int BuildMessage(String s,long l,int type) {
		try {
		byte[] b1 = s.getBytes("UTF-8");
		byte[] b2 = long2Bytes(l);
		
		int i = 0;
		int b1_l = 0;
		int b2_l = 0;
		
		messageLength = b1.length + b2.length + 16;
		b1_l = b1.length;
		b2_l = b2.length;
		i = messageLength-4;
		
		byte[] b_length = TurnIntToBytes(i); 	
		byte[] type_byte = TurnIntToBytes(type); 
		byte[] b1_length = TurnIntToBytes(b1_l);
		byte[] b2_length = TurnIntToBytes(b2_l);
		
		System.arraycopy(b_length,0,buffer,0,b_length.length);
		System.arraycopy(type_byte,0,buffer,
				b_length.length,type_byte.length);
		System.arraycopy(b1_length,0,buffer,
				b_length.length+type_byte.length,b1_length.length);
		System.arraycopy(b1,0,buffer,
				b_length.length+type_byte.length+b1_length.length,b1.length);
		System.arraycopy(b2_length,0,buffer,
				b_length.length+type_byte.length+b1_length.length+b1.length,b2_length.length);
		System.arraycopy(b2,0,buffer,
				b_length.length+type_byte.length+b1_length.length+b1.length+b2_length.length,b2.length);
		}catch(Exception e) {
			
		}
		return messageLength;
	}
	public byte[] long2Bytes(long num) {
		byte[] byteNum = new byte[8];
		for (int ix = 0; ix < 8; ++ix) {
			int offset = 64 - (ix + 1) * 8;
			byteNum[ix] = (byte) ((num >> offset) & 0xff);
		}
		return byteNum;
	}
	private byte[] TurnIntToBytes(int i) {
		byte[] b = new byte[4]; 
		b[0] = (byte) (i & 0xff); 
		b[1] = (byte) ((i >> 8) & 0xff); 
		b[2] = (byte) ((i >> 16) & 0xff); 
		b[3] = (byte) ((i >> 24) & 0xff); 
		return b;
	}
}
