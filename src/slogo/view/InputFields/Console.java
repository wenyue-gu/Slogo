package slogo.view.InputFields;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class Console {
  private static final Label comLabel = new Label("Console:");
  private VBox box;
  private TextField entry;
  private ScrollPane pane;
  private List<String> history;

  public Console(){ this(200, 300); }

  public Console(double height, double width){
    history = new ArrayList<>();
    box = new VBox();
    box.setPrefHeight(height);
    box.setPrefWidth(width);
    setDetails();
    pane = new ScrollPane(box);
  }

  public String getText(){
    return entry.getText();
  }

  public void addHistory(){
    history.add(entry.getText());
    ListIterator<String> iter = history.listIterator(history.size());
    box.getChildren().clear();
    addEditable();
    while(iter.hasPrevious()){
      String past = "> " + iter.previous();
      addUneditable(past);
    }
  }

  public VBox getBox(){
    return box;
  }

  public void clear(){
    box.getChildren().removeAll();
    setDetails();
  }

  private void setDetails(){
    Background backing = new Background(new BackgroundFill(Color.BLACK, new CornerRadii(0), new Insets(0)));
    box.setBackground(backing);
    box.getChildren().addAll(comLabel);
    addEditable();
  }

  private void addUneditable(String input){
    TextField current = new TextField(input);
    current.setBackground(new Background(new BackgroundFill(Color.BLACK, new CornerRadii(0), new Insets(0))));
    current.setPrefWidth(box.getWidth());
    current.setStyle("-fx-text-fill: green");
    box.getChildren().add(current);
  }

  private void addEditable(){
    entry = new TextField();
    entry.setBackground(new Background(new BackgroundFill(Color.BLACK, new CornerRadii(0), new Insets(0, 0, 0, 20))));
    entry.setPrefWidth(box.getWidth());
    entry.setStyle("-fx-text-fill: green");
    box.getChildren().add(entry);
  }

  public void promptUser(String input){
  }
}
