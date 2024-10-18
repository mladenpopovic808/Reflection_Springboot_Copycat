package domaci2.server;

import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static final int PORT=8000;


    public static void main(String[] args) {
        try {
            ServerSocket serverSocket=new ServerSocket(PORT);
            System.out.println("Server is running at PORT : "+PORT);

            //da pri pokretanju programa pronadje sve rute i ispise ih.
            DiscoveryMechanism.getInstance().printRoutes();

            while (true){

                Socket socket=serverSocket.accept();
                new Thread(new ServerThread(socket)).start();
            }

        }catch (Exception e){
            e.printStackTrace();

        }
    }


}
