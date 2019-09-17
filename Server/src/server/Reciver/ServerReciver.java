package server.Reciver;

import java.io.*;
import java.net.Socket;

public class ServerReciver {
    public void recive(Socket client){

        try(DataInputStream d = new DataInputStream(client.getInputStream())){

            System.out.println("Connection established");
            String fileName = d.readUTF();
            File file = new File(".", fileName);
            if(file.createNewFile()){

                try(FileOutputStream outputStream = new FileOutputStream(file)){

                    byte[] buffer = new byte[4096];
                    int readSize;
                    System.out.println("Download started");
                    while((readSize = d.read(buffer)) != -1){
                        outputStream.write(buffer, 0, readSize);
                    }
                    System.out.println("Finished downloading file: " + fileName);
                }
            }
        }
        catch (java.io.IOException e){
            e.printStackTrace();
        }
    }
}
