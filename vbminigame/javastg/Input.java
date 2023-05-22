/**
 *  標準入力読み込み用
 *  @author okegawa
 *  @date 2001/11/1
 */
public class Input{
  private static final int MAX_LINE_LENGTH = 256;  // arbitrary

  /**
   *  文字列読み込み(%s)
   *  @return String
   */
  public static String readString(){
    String result = "";
    try{
      byte[] buffer = new byte[MAX_LINE_LENGTH];
      System.in.read(buffer);
      result = new String(buffer);
      result = result.trim();
      return result;
    }catch (java.io.IOException e){
      System.err.println(e);
      return result;
    }
  }

  /**
   *  文字読み込み(%c)
   *  @return char
   */
  public static char readChar(){
    char result = '\u0000';
    boolean successful = false;
    while (! successful){
      String s = readString();
      if (s.length() == 1){
        result = s.charAt(0);
        successful = true;
      }else{
        System.err.println("Error:  Must enter a single char.  Please try again.");
      }
    }
    return result;
  }

  /**
   *  数値読み込み(%d)
   *  @return int
   */
  public static int readInt(){
    int result = 0;
    boolean successful = false;
    while (! successful){
      try{
        String s = readString();
        result = Integer.parseInt(s);
        successful = true;
      }catch (NumberFormatException e){
        System.err.println("Error:  Must enter an integer.  Please try again.");
      }
    }
    return result;
  }

  /**
   *  数値読み込み(%f)
   *  @return double
   */
  public static double readDouble(){
    double result = 0.0;
    boolean successful = false;
    while (! successful){
      try{
        String s = readString();
        result = (new Double(s)).doubleValue();
        successful = true;
      }catch (NumberFormatException e){
        System.err.println("Error:  Must enter a floating-point number. Please try again.");
      }
    }
    return result;
  }
}
