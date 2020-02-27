package sample.datamodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;

public class TodoData {
    private static TodoData instance = new TodoData();
    private static String fileName = "TodoListItems.txt";

    private ObservableList<TodoItem> todoItems;
    private DateTimeFormatter dateTimeFormatter;

    public static TodoData getInstance() {
        return instance;
    }

    private TodoData() {
        dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    }

    public ObservableList<TodoItem> getTodoItems() {
        return todoItems;
    }

    public void addTodoItem(TodoItem item) {
        todoItems.add(item);
    }

    public void loadTodoItems() throws IOException {
        todoItems = FXCollections.observableArrayList();
        Path path = Paths.get(fileName);
        BufferedReader bufferedReader = Files.newBufferedReader(path);

        String input;

        try{
            while ((input = bufferedReader.readLine()) != null) {
                String[] itemPieces = input.split("\t");

                String shortDescription = itemPieces[0];
                String details = itemPieces[1];
                String dateString = itemPieces[2];

                LocalDate date = LocalDate.parse(dateString, dateTimeFormatter);
                TodoItem todoItem = new TodoItem(shortDescription, details, date);
                todoItems.add(todoItem);
            }
        }finally {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
        }
    }

    public void storeTodoItems() throws IOException {
        Path path = Paths.get(fileName);
        BufferedWriter bufferedWriter = Files.newBufferedWriter(path);
        try {
            Iterator<TodoItem> iterator = todoItems.iterator();
            while (iterator.hasNext()) {
                TodoItem item = iterator.next();
                bufferedWriter.write(String.format("%s\t%s\t%s",
                        item.getShortDescription(),
                        item.getDetails(),
                        item.getDeadLine().format(dateTimeFormatter)));
                bufferedWriter.newLine();
            }
        } finally {
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
        }
    }

    public void deleteTodoItem(TodoItem item) {
        todoItems.remove(item);
    }
}
