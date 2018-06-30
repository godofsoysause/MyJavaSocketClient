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
}
