
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import server.Reciver.ServerReciver;

public class Server {

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(4);
        Scanner scanner = new Scanner(System.in);
        String portRead = scanner.nextLine();
        int port = Integer.parseInt(portRead);

        try(ServerSocket serverSocket = new ServerSocket(port)){
            System.out.println("Started listenig at port " + port);
            while(true){
                final Socket socket = serverSocket.accept();
                executorService.submit(()-> new ServerReciver().recive(socket));
            }
        }
        catch (java.io.IOException e){
            e.printStackTrace();
        }
    }
}
