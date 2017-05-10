import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class SocketServer implements Runnable {

   public static final int ServerPort = 3016; //prot Number
   private boolean FLAG_CONNECTION = false;   // Connected return
   private static String ClientIp = null;      // IP Create
   private ServerSocket serverSocket;         //ServerSocket Create
   private Socket client;                  //Socket Create
   private GPIOController controller;         //Controller Create

   // If you want to know status of socket, you receive true or false by this (Function)
   public boolean isConnected() {
      return FLAG_CONNECTION;
   }

   // When socket is connected by client, it's ip is saved into this variable. (IP Number)
   public void setClient(String client_ip) {
      ClientIp = client_ip;
   }

   // Main function to run server.
   @Override
   public void run() {
      try {
         // Initialize server socket for communication.
         serverSocket = new ServerSocket(ServerPort);

         // create controller to control GPIO pin in raspberry pi.
         controller = new GPIOController();

         // show success message to ready for running program after creating socket and controller.
         Util.showMessage(" Ready for communication.");

         // It is always running except occur exception.
         while (true) {
            // Waiting for accepting from client.
            client = serverSocket.accept();

            // When server is accepted with client, client's ip is saved into ClientIp.
            ClientIp = client.getInetAddress().toString();

            // Set communication status is true.
            FLAG_CONNECTION = true;

            // Complete saving ip and accepting process, show success message into screen.
            Util.showMessage(" Accepted from " + ClientIp);

            // Initialize Stream variable for communication message with client.
            InputStream in = client.getInputStream();
            DataInputStream dis = new DataInputStream(in);

            // It is running until be can communication with client.
            while (FLAG_CONNECTION) {
               try {
                  // data size is fixed 100.
                  byte[] buf = new byte[100];

                  // read message data sending from client.
                  dis.read(buf);

                  // buffer is recreated as String type.
                  String str = new String(buf);

                  // delete invalid data like white-space.
                  str = str.trim();

                  // If received data is Close, client wants to disconnect with server. CONNECTION false setting 
                  if (str.equals("Close")) {
                     client.close();
                     FLAG_CONNECTION = false;
                     Util.showMessage(" Disconnet with " + ClientIp + " Client.");
                     break;
                  }
                  else {
                     // send string data into controller. executed GPIOController.java
                     Util.showMessage(str);
                     controller.control(str);
                  }
               } 
               catch (Exception e) {
                  // When occur exception GPIO pins set value to false and connection is connected.
                  controller.reset();
                  FLAG_CONNECTION = false;
                  client.close();
                  Util.showMessage(" disconnect with client.");
                  break;
               }
            }
         }
      
   }
      // When occur exception Program , close serverSocket.
      catch (Exception e) {
         e.printStackTrace();
         try {
            serverSocket.close();
         } catch (IOException e1) {
            e1.printStackTrace();
         }
      }
   }

}