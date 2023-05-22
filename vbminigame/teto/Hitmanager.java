//“–‚½‚è”»’èŠÇ—
class Hitmanager{
	public static boolean hit(int x1, int y1, int width1, int height1, int x2, int y2, int width2, int height2){
		if( x1 + width1 > x2 &&
			x1 < x2 + width2 &&
			y1 + height1 > y2 &&
			y1 < y2 + height2){
			return true;
		}
		return false;
	}
}
