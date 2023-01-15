package pe.edu.uni.parc;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;


public class TCPClient50 {

    private String servermsj;
    public  String SERVERIP;
    public static final int SERVERPORT = 10000;
    private OnMessageReceived mMessageListener = null;
    private boolean mRun = false;

    PrintWriter out;
    BufferedReader in;
    
    DataInputStream in2;
    DataOutputStream mout2;

    public TCPClient50(String ip,OnMessageReceived listener) {
        SERVERIP = ip;
        mMessageListener = listener;
    }
    public void sendMessage(String message) throws IOException{
        if (out != null && !out.checkError()) {
            mout2.writeUTF(message);
            mout2.flush();
        }
    }
    public void stopClient(){
        mRun = false;
    }
    public void run() {
        mRun = true;
        try {
            InetAddress serverAddr = InetAddress.getByName(SERVERIP);
            Socket socket = new Socket(serverAddr, SERVERPORT);
            System.out.println("TCP Client"+": Conectando...");
            try {
                out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
                
                mout2 = new DataOutputStream(socket.getOutputStream());
                
                System.out.println("TCP Client"+ "C: Sent.");
                System.out.println("TCP Client"+ "C: Done.");
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                in2 = new DataInputStream(socket.getInputStream());
                while (mRun) {
                    servermsj = in2.readUTF();
                    if (servermsj != null && mMessageListener != null) {
                        mMessageListener.messageReceived(servermsj);
                    }
                    servermsj = null;
                }
            } catch (Exception e) {
                System.out.println("TCP"+ "S: Error"+e);

            } finally {
                socket.close();
            }
        } catch (Exception e) {
            System.out.println("TCP"+ "C: Error"+ e);
        }
    }
    public interface OnMessageReceived {
        public void messageReceived(String message);
    }
}