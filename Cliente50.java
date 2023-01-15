package pe.edu.uni.parc;
import java.io.IOException;
import java.util.Scanner;

class Cliente50{
    TCPClient50 mTcpClient;
    Scanner sc;

    String ServerIP= "192.168.1.1";

    public static void main(String[] args) throws IOException  {
        Cliente50 objcli = new Cliente50();
        objcli.iniciar();
    }
    void iniciar() throws IOException{
       new Thread(
            new Runnable() {

                @Override
                public void run() {
                    mTcpClient = new TCPClient50(ServerIP,
                        new TCPClient50.OnMessageReceived(){
                            @Override
                            public void messageReceived(String message){
                                ClienteRecibe(message);
                            }
                        }
                    );
                    mTcpClient.run();                   
                }
            }
        ).start();

        String salir = "n";
        sc = new Scanner(System.in);
        System.out.println("Cliente");
        while( !salir.equals("s")){
            salir = sc.nextLine();
            ClienteEnvia(salir);
        }
        System.out.println("CLIENTE50 bandera 02");
    
    }
    void ClienteRecibe(String llego){
        System.out.println("CLIENTE50 El mensaje::" + llego);
        // formato de recepcio
        // <comando> <parametro1> <parametro2> <parametro3> ...
        // envia 123
        if (llego.trim().contains("envia")){
            System.out.println("llego mensaje: "+llego);
            tarea();
        }

    }
    void ClienteEnvia(String envia) throws IOException{
        if (mTcpClient != null) {
            mTcpClient.sendMessage(envia);
        }
    }
    void tarea(){
        //ejecutan tarea
    } 

}
