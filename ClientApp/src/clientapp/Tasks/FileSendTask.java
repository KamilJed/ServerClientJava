package ClientApp.Tasks;

import javafx.concurrent.Task;

import java.io.*;
import java.net.Socket;

public class FileSendTask extends Task<Void> {

    private File file;
    int port;


    public FileSendTask(File file, int port){
        this.file = file;
        this.port = port;
    }

    @Override
    protected Void call(){
        updateMessage("Connecting...");

        try(Socket socket = new Socket("localhost", port);
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            FileInputStream inputStream = new FileInputStream(file)){

            if(socket.isConnected())
                updateMessage("Connection established");
            else{
                updateMessage("Couldn't connect to server");
                return null;
            }

            long totalSize = file.length();
            byte[] buffer = new byte[4096];
            int readBytes;
            int totalSend = 0;
            outputStream.writeUTF(file.getName());

            updateMessage("Sending file " + file.getName() + "...");

            while((readBytes = inputStream.read(buffer)) > 0){
                outputStream.write(buffer, 0, readBytes);
                totalSend += readBytes;
                updateProgress(totalSend, totalSize);
            }
            updateMessage("File successfully uploaded");
            return null;
        }
        catch (java.io.IOException e){
            updateMessage("Error occured while establishig connection");
            return null;
        }
    }
}
