package sample;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import sample.datamodel.TodoItem;

import java.time.LocalDate;

public class EditDialogController {
    @FXML
    private DatePicker newDeadline;

    public void processNewDeadline(TodoItem selectedItem) {
        LocalDate deadlineValue = newDeadline.getValue();
        selectedItem.setDeadLine(deadlineValue);

    }
}
