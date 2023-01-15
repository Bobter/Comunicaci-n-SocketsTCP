package pe.edu.uni.parc;

import java.util.Scanner;

public class Servidor50 {
   TCPServer50 mTcpServer;
   Scanner sc;
   public static void main(String[] args) {
       Servidor50 objser = new Servidor50();
       objser.iniciar();
   }
   void iniciar(){
       new Thread(
            new Runnable() {

                @Override
                public void run() {
                      mTcpServer = new TCPServer50(
                        new TCPServer50.OnMessageReceived(){
                            @Override
                            public void messageReceived(String message){
                                ServidorRecibe(message);
                                if (message.contains("#")){
                                    //ServidorRecibe(message);
                                    //mTcpServer.sendMessageTCPServer(message);
                                    ServidorEnvia(message);
                                }
                            }
                        }
                    );
                    mTcpServer.run();                   
                }
            }
        ).start();
        //-----------------
        String salir = "n";
        sc = new Scanner(System.in);
        System.out.println("Servidor");
        while( !salir.equals("s")){
            salir = sc.nextLine();
            ServidorEnvia(salir);
       }
       System.out.println("SERVIDOR40 bandera 02");
   
   }
   void ServidorRecibe(String llego){
       System.out.println("SERVIDOR40 El mensaje:" + llego);
   }
   void ServidorEnvia(String envia){
        if (mTcpServer != null) {
            mTcpServer.sendMessageTCPServer(envia);
        }
   }
}
