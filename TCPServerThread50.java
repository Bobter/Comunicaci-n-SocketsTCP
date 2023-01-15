package pe.edu.uni.parc;

import java.io.DataInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.Integer;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;


public class TCPServerThread50 extends Thread{
    
    private Socket client;
    private TCPServer50 tcpserver;
    private int clientID;                 
    private boolean running = false;
    public PrintWriter mOut;
    public BufferedReader in;
    public DataInputStream in2;
    public DataOutputStream mout2;
    private TCPServer50.OnMessageReceived messageListener = null;
    private String message;
    TCPServerThread50[] cli_users;

    public TCPServerThread50(Socket client_, TCPServer50 tcpserver_, int clientID_,TCPServerThread50[] cli_ami_) {
        this.client = client_;
        this.tcpserver = tcpserver_;
        this.clientID = clientID_;
        this.cli_users = cli_ami_;
    }
    
    public String getPuerto(){
        Integer x=client.getPort();
        return x.toString();
    }
    
    public void run() {
        running = true;
        try {
            try {
                mOut = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())), true);

                mout2 = new DataOutputStream(client.getOutputStream());
                mout2.writeUTF("Thank You For Connecting.");
                
                System.out.println("TCP Server id cli" + clientID  + " 2C: Sent.");
                messageListener = tcpserver.getMessageListener();
                
                in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                in2 = new DataInputStream(client.getInputStream());
                
                while (running) {
                    message = in2.readUTF();
                    
                    if (message != null && messageListener != null) {
                        message=message;//+client.getPort();
                        messageListener.messageReceived(message + " ["+clientID+"]");
                    }

                    message = null;
                }
                System.out.println("RESPONSE FROM CLIENT"+ "S: Received Message: '" + message + "'");
            } catch (Exception e) {
                System.out.println("TCP Server"+ "S: Error"+ e);
            } finally {
                client.close();
            }

        } catch (Exception e) {
            System.out.println("TCP Server"+ "C: Error"+ e);
        }
    }
    
    public void stopClient(){
        running = false;
    }
    
    public void sendMessage(String message){//funcion de trabajo
        if (mout2 != null && !mOut.checkError()) {
            try {
                mout2.writeUTF(message);
                mOut.flush();
            } catch (IOException ex) {
                Logger.getLogger(TCPServerThread50.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
