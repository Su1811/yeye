package Application;

import Management.DictionaryManagement;
import Management.Word;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class Controller implements Initializable {

    DictionaryManagement NewDictionary = new DictionaryManagement();
    ObservableList<Word> newWord = FXCollections.observableArrayList();

    @FXML
    private TextField SearchBar;

    @FXML
    private ListView<Word> listView;

    @FXML
    void search(ActionEvent event) {
        if (SearchBar.getText().length() > 0) {
            listView.getItems().clear();
            //listView.getItems().addAll(searchList(SearchBar.getText(), words));
        }
        else listView.getItems().clear();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            NewDictionary.insertFromFile();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        for (Word word : NewDictionary.myList.word_list) {
            newWord.add(word);
            //listView.getItems().add(word.getWord_target());
        }

        //listView.getItems().addAll(words);

        FilteredList<Word> filteredData = new FilteredList<>(newWord, b -> true);

        //filter list
        SearchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(word -> {
                //if empty return list
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                //so sanh xau nhap vao
                String lowerCaseFilter = newValue.toLowerCase();

                if (word.getWord_target().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                }
                else
                    return false;
            });
        });
        SortedList<Word> sortedData = new SortedList<>(filteredData);

        //sortedData.comparatorProperty().bind(listView.cellFactoryProperty());

        listView.setItems(sortedData);

        listView.setCellFactory(new Callback<ListView<Word>, ListCell<Word>>() {
            @Override
            public ListCell<Word> call(ListView<Word> wordListView) {
                final ListCell<Word> word = new ListCell<Word>() {
                    @Override
                    public void updateItem(Word item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item.getWord_target());
                        }
                        else setText("");
                    }
                };
                return word;
            }
        });

    }

    private List<String> searchList(String searchWord, List<String> ListOfStrings) {
        List<String> searchWordArray = Arrays.asList(searchWord.trim().split(" "));

        return ListOfStrings.stream().filter(input -> {
            return searchWordArray.stream().allMatch(word ->
                    input.toLowerCase().contains(word.toLowerCase()));
        }).collect(Collectors.toList());
    }
}