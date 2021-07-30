package ui.utilities;

import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextInputDialog;

import java.util.Optional;

public class MessageDialog {
        public static void ShowDialog(String title, String message, String type) {
            Dialog<String> dialog = new Dialog<>();
            dialog.setTitle(title);
            dialog.setContentText(message);
            ButtonType buttonType = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().add(buttonType);
            dialog.showAndWait();
        }

        public static String showInputDialog(String dflt, String prompt) throws InputCancelledException {
            TextInputDialog td = new TextInputDialog(dflt);
            td.setContentText(prompt);
            Optional<String> result = td.showAndWait();
            if(result.isPresent()) {
                return td.getEditor().getText();
            }else {
                throw new InputCancelledException();
            }
        }
}
