//ÉLÅ[ä«óù
public class Keymanager{
	private static boolean rightflag;
	private static boolean leftflag;
	private static boolean upflag;
	private static boolean downflag;
	private static boolean zflag;
	
	Keymanager(){
		this.clear();
		zflag = false;
	}
	
	public static void clear(){
		rightflag = false;
		leftflag = false;
		upflag = false;
		downflag = false;
	}
	
	public static void setRight(){
		rightflag = true;
	}
	public static void setLeft(){
		leftflag = true;
	}
	public static void setUp(){
		upflag = true;
	}
	public static void setDown(){
		downflag = true;
	}
	public static void clearRight(){
		rightflag = false;
	}
	public static void clearLeft(){
		leftflag = false;
	}
	public static void clearUp(){
		upflag = false;
	}
	public static void clearDown(){
		downflag = false;
	}
	
	public static void setZ(){
		zflag = true;
	}
	public static void clearZ(){
		zflag = false;
	}
	public static boolean getZ(){
		return zflag;
	}
	
	public static boolean getRight(){
		return rightflag;
	}
	public static boolean getLeft(){
		return leftflag;
	}
	public static boolean getUp(){
		return upflag;
	}
	public static boolean getDown(){
		return downflag;
	}
}
