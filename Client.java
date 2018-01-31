import java.io.*;
import java.net.*;

class ReceiveAndConsole extends Thread{

    BufferedReader br;
    String receivedMessage;
    ReceiveAndConsole(Socket socket) throws Exception
    {
        super("Printing Thread");
        this.br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        start();
    }
    public void run() 
    {
        while(true)
        {
            try{
            receivedMessage = br.readLine();
            if( receivedMessage == null || receivedMessage.equals("null"))
                break;
            System.out.println();
            System.out.println("Opposite side: " + receivedMessage);
            } 
            catch(Exception e){
                System.out.println(this + " Exception occured: " + e);
                break;
            }
        }
        System.exit(0);
    }
}
class KeyboardAndSend extends Thread{

    PrintWriter pw;
    BufferedReader br;
    String sendMessage;
    KeyboardAndSend(Socket socket) throws Exception
    {
        super("Sending Thread");
        br = new BufferedReader(new InputStreamReader(System.in));
        pw = new PrintWriter(socket.getOutputStream(),true);
        start();
    }
    public void run() 
    {
        while(true){
        try{
        sendMessage = br.readLine();
        pw.println(sendMessage);
        }
        catch(Exception e)
        {
            System.out.println(this + " Exception occured: " + e);
            break;
        }
      }
        System.exit(0);   
    }
}
public class Client{

    public static void main(String args[]) throws Exception
    {
        String ipAddress = "192.168.31.59" // edit this ip address as of your local Server to whom you want to make connection
        Socket socket =new Socket(ipAddress,3001);
        System.out.println("Starting the connection...");

        new ReceiveAndConsole(socket);
        new KeyboardAndSend(socket);
    }

}
