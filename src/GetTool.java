import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;

public class GetTool {
	public static void UserLoginReturn(String succeedOrNot,String error) {
		System.out.println("登录成功了吗："+succeedOrNot+"    error: "+error);
	}
	public static void UserRegisterReturn(String succeedOrNot,String error) {
		System.out.println("注册成功了吗："+succeedOrNot+"    error: "+error);
	}
	public static void BuildRoomReturn(String succeedOrNot,String roomName) {
		System.out.println("创建房间成功了吗："+succeedOrNot+"    房间名: "+roomName);
	}
	
	public static void UserJoinRoomReturn(String succeedOrNot,String[] allUserNames) {
		String users = "";
		for(int i=0;i<allUserNames.length;i++) {
			users += allUserNames[i];
			users += " ";
		}
		System.out.println("进入房间成功了吗："+succeedOrNot+"    房间中的用户名称: "+users);
	}
	
	public static void UserSendMessageInRoomReturn(String userName,String stringMessage) {
		System.out.println("用户名："+userName+"    发来消息: "+stringMessage);
	}
	
	public static void UserLeaveRoomReturn(String[] unLockRoomNames,String[] lockedRoomNames) {
		String unLockRoom = "";
		for(int i=0;i<unLockRoomNames.length;i++) {
			unLockRoom += unLockRoomNames[i];
			unLockRoom += "|";
		}
		String LockRoom = "";
		for(int i=0;i<lockedRoomNames.length;i++) {
			LockRoom += lockedRoomNames[i];
			LockRoom += "|";
		}
		System.out.println("离开了房间，没密码的房间有: "+unLockRoom);
		System.out.println("有密码的房间有: "+LockRoom);
	}
	
	public static void UserFindAllRoomReturn(String[] unLockRoomNames,String[] lockedRoomNames) {
		String unLockRoom = "";
		for(int i=0;i<unLockRoomNames.length;i++) {
			unLockRoom += unLockRoomNames[i];
			unLockRoom += "|";
		}
		String LockRoom = "";
		for(int i=0;i<lockedRoomNames.length;i++) {
			LockRoom += lockedRoomNames[i];
			LockRoom += "|";
		}
		System.out.println("查找房间，没密码的房间有: "+unLockRoom);
		System.out.println("有密码的房间有: "+LockRoom);
	}
	public static void UserFindAllFileReturn(String[] allFilesName) {
		String filesName = "";
		for(int i=0;i<allFilesName.length;i++) {
			filesName += allFilesName[i];
			filesName += "|";
		}
		System.out.println("所有文件名: "+filesName);
	}
	public static void UserSendFileReturn(String succeedOrNot,String error) {
		System.out.println("文件发送成功了吗："+succeedOrNot+"    error: "+error);
	}
	
	static FileOutputStream fos;
	public static byte[] UserGetFileInRoomReturn(DataInputStream in,String fileName,Long fileLength,byte[] b) {
		System.out.println("接收文件："+fileName);
		byte[] t_b = new byte[0];
		if(fileLength==-1) {
			System.out.println("接收文件失败："+fileName);
			return t_b;
		}
		try {
		File directory = new File(".\\filesFromRoom");
        if(!directory.exists()) {
            directory.mkdir();
        }
        File file = new File(directory.getAbsolutePath() + File.separatorChar + fileName);
        fos = new FileOutputStream(file);
        // 开始接收文件
        byte[] bytes = new byte[2048];
        int length = 0;
        if(b.length>0) {
        	fos.write(b, 0, b.length);
            fos.flush();
            fileLength -= b.length;
        }
        while((length = in.read(bytes, 0, bytes.length)) != -1) {
        	//System.out.println("length值："+length);
        	if(fileLength>=length) {
        		fos.write(bytes, 0, length);
        		fos.flush();
        		fileLength -= length;
        		System.out.println("fileLength: "+fileLength);
        		if(fileLength<=0)break;
            }else {
            	fos.write(bytes, 0, fileLength.intValue());
        		fos.flush();
        		length -= fileLength.intValue();
        		System.out.println("fileLength: "+fileLength + " length-fileLength: "+length);
        		t_b = new byte[length];
        		System.arraycopy(bytes,fileLength.intValue(),t_b,0,t_b.length);
        		break;
			}
        }
        System.out.println("接收文件完成：");
		}catch(Exception e) {
			System.out.println("接收文件出错了："+e.toString());
		}
		finally {
		 try {
             if(fos != null)
                 fos.close();
         } catch (Exception e) {}
		}
		return t_b;
	}
}
