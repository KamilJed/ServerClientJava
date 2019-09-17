package ClientApp.Controller;

import ClientApp.Tasks.FileSendTask;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AppController {
    @FXML private Label statusLabel;
    @FXML private ProgressBar progressBar;
    @FXML private TextField portField;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    @FXML
    private void chooseFile(){

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open file to send");
        File file = fileChooser.showOpenDialog(statusLabel.getScene().getWindow());

        if(file != null){

            try{
                int port = Integer.parseInt(portField.getText());
                FileSendTask fileSendTask = new FileSendTask(file, port);
                statusLabel.textProperty().bind(fileSendTask.messageProperty());
                progressBar.progressProperty().bind(fileSendTask.progressProperty());
                executorService.submit(fileSendTask);
            }
            catch (NumberFormatException e) {
                statusLabel.setText("Value of port must be a number!");
            }
        }
    }
}
