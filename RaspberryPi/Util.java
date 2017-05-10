import java.text.SimpleDateFormat;
import java.util.Date;

//�� ���ϵ鿡 ��� ����Ϸ��� ���� Ŭ����
public class Util {
   public static enum MSG_TYPE {INFO, ERR};

   // It returns time value in format is declared in code.
   public static String getTime() {
      SimpleDateFormat f = new SimpleDateFormat("[hh:mm:ss]");
      return f.format(new Date());
   }

   // It is write Message into screen for seeing it's status or process.
   public static void showMessage(String msg) {
      System.out.println(getTime() + msg);
   }

   // If you want to write error message into screen, you
   public static void showMessage(MSG_TYPE type, String msg) {
   }
}