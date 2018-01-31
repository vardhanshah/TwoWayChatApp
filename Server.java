import java.io.*;
import java.net.*;

class KeyboardAndSend extends Thread{
    String sendMessage;
    PrintWriter pw;
    BufferedReader br;
    KeyboardAndSend(Socket socket) throws Exception
    {
        br = new BufferedReader(new InputStreamReader(System.in));
        pw = new PrintWriter(socket.getOutputStream(),true);
        start();
    }
    public void run()
    {
        while(true)
        {
        try{
        sendMessage = br.readLine();
        pw.println(sendMessage);
        }
        catch(Exception e)
        {
            System.out.println("Exception Occurred: " + e);
            break;
        }
        }
        System.exit(0);       
    }

}
class ReceiveAndConsole extends Thread{

    BufferedReader br;
    String receivedMessage;
    ReceiveAndConsole(Socket socket) throws Exception
    {
        br= new BufferedReader(new InputStreamReader(socket.getInputStream()));
        start();
    }
    public void run()
    {
        while(true)
        {
            try{
                receivedMessage = br.readLine();
                if(receivedMessage == null||receivedMessage.equals("null"))
                    break;
                System.out.println();
                System.out.println("Your Opposite Side: " + receivedMessage);
            }
            catch(Exception e)
            {
                System.out.println("Exception occured: " + e);
                break;
            }
        }
        System.exit(0);
    }
}
public class Server{
    public static void main(String args[]) throws Exception
    {
        ServerSocket servsock= new ServerSocket(3001);
        System.out.println("Server is ready for chatting...");
        Socket sock = servsock.accept();
        new KeyboardAndSend(sock);
        new ReceiveAndConsole(sock);
    }
}
