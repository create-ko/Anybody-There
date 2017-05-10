import java.net.ServerSocket;
import java.net.Socket;


/*
 * is program to control remote control car using socket communication with other device like android.
 * it is used socket thread and GPIO control library(pi4j)
 */
public class PiCore {

   static Thread server;

   public static void main(String[] args) {
//서버 생성을 소켓서버로 새로 생성했다.
      server = new Thread(new SocketServer());
      System.out.println(Util.getTime() + " Create ServerSocket.");
      server.start();
   }

}