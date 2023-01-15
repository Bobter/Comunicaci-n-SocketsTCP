package pe.edu.uni.parc;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer50 {
    private String message;
    int nrcli = 0;
    public static final int SERVERPORT = 10000;
    private OnMessageReceived messageListener = null;
    private boolean running = false;
    TCPServerThread50[] sendclis = new TCPServerThread50[30];
    PrintWriter mOut;
    BufferedReader in;
    ServerSocket serverSocket;
    //el constructor pide una interface OnMessageReceived
    public TCPServer50(OnMessageReceived messageListener) {
        this.messageListener = messageListener;
    }
    public OnMessageReceived getMessageListener(){
        return this.messageListener;
    }
    public void sendMessageTCPServer(String message){
        for (int i = 1; i <= nrcli; i++) {
//            if (!message.substring(message.length()-5, message.length()).equalsIgnoreCase(sendclis[i].getPuerto())){
//                sendclis[i].sendMessage(message);
//                System.out.println("ENVIANDO A USUARIO " + (i));
//            }
            sendclis[i].sendMessage(message);
            System.out.println("ENVIANDO A USUARIO " + (i));
        }
    }
    
    public void run(){
        running = true;
        try{
            System.out.println("TCP Server"+"S : Connecting...");
            serverSocket = new ServerSocket(SERVERPORT);
            
            while(running){
                Socket client = serverSocket.accept();
                System.out.println("TCP Server"+"S: Receiving...");
                nrcli++;
                System.out.println("Engendrado " + nrcli);
                System.out.println("Puerto: "+client.getPort());
                sendclis[nrcli] = new TCPServerThread50(client,this,nrcli,sendclis);
                Thread t = new Thread(sendclis[nrcli]);
                t.start();
                System.out.println("Nuevo usuario conectado: Hay"+ nrcli+" usuarios conectados");
            }
            
        }catch( Exception e){
            System.out.println("Error"+e.getMessage());
        }finally{

        }
    }
    public  TCPServerThread50[] getClients(){
        return sendclis;
    } 

    public interface OnMessageReceived {
        public void messageReceived(String message);
    }
}
