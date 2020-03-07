package slogo.view;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.animation.SequentialTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.web.PopupFeatures;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.scene.control.*;
//<<<<<<< HEAD
import javax.xml.parsers.ParserConfigurationException;
import slogo.configuration.XMLException;
import slogo.configuration.XMLWriter;
//=======
import slogo.frontendexternal.TurtleView;
//>>>>>>> bc01223582cc31514fb0bf74813f8a6807de2f38
import slogo.view.InputFields.InputFields;

/**
 * @author Shruthi Kumar, Nevzat Sevim
 */

public class Toolbar extends ToolBar {

  //Incorporate View and Text Field
  private MainView myMainView;
  private InputFields myTextFields;

  //The Drop Down Menus Themselves
  private ColorPicker penMenu, backgroundMenu;
  private ComboBox languageMenu, turtleMenu;

  //The Buttons
  private Button commandButton, helpButton, changesButton, savePrefButton;
  private ResourceBundle buttonBundle, labelBundle, languageBundle, turtleSkinBundle;

  public Toolbar(MainView mainview) {
    buttonBundle = ResourceBundle.getBundle("slogo.view.resources.buttons");
    labelBundle = ResourceBundle.getBundle("slogo.view.resources.labels");
    languageBundle = ResourceBundle.getBundle("slogo.view.resources.languages");
    turtleSkinBundle = ResourceBundle.getBundle("slogo.view.resources.turtleSkin");

    this.myMainView = mainview;
    this.myTextFields = myMainView.getTextFields();

    createMenus();
    createButtons();

    Label penLabel = new Label(labelBundle.getString("PenLabel"));
    Label backgroundLabel = new Label(labelBundle.getString("BackgroundLabel"));
    Label turtleLabel = new Label(labelBundle.getString("TurtleLabel"));
    Label languageLabel = new Label(labelBundle.getString("LanguageLabel"));

    this.setMinSize(1010.0, 40.0);
    this.setMaxSize(1010.0, 40.0);
    this.setPrefSize(1010.0, 40.0);

    this.getItems().addAll(commandButton, new Separator(),
        turtleLabel, turtleMenu, penLabel, penMenu,
        languageLabel, languageMenu, backgroundLabel, backgroundMenu,  changesButton,
        savePrefButton, new Separator(), helpButton);
  }

  /** Helping methods to import menus and buttons to the toolbar*/

  private void createMenus() {
    //Color Menus
    this.penMenu = new ColorPicker();
    penMenu.setValue(Color.BLACK);
    penMenu.setMaxWidth(50);

    this.backgroundMenu = new ColorPicker();
    backgroundMenu.setValue(Color.LIGHTGRAY);
    backgroundMenu.setMaxWidth(50);

    //Turtle Menu
    this.turtleMenu = new ComboBox();
    turtleMenu.setPromptText("raphael");
    turtleMenu.getItems().addAll(turtleSkinBundle.getString("Mickey"),
        turtleSkinBundle.getString("Raphael"),
        turtleSkinBundle.getString("Turtle"));

    //Language Menu
    this.languageMenu = new ComboBox();
    languageMenu.setPromptText("English");
    languageMenu.getItems().addAll(languageBundle.getString("English"),
        languageBundle.getString("Chinese"),
        languageBundle.getString("French"),
        languageBundle.getString("German"),
        languageBundle.getString("Italian"),
        languageBundle.getString("Portuguese"),
        languageBundle.getString("Russian"),
        languageBundle.getString("Spanish"),
        languageBundle.getString("Urdu"));
  }

  private void createButtons() {
    this.commandButton = new Button(buttonBundle.getString("Run"));

    this.helpButton = new Button(buttonBundle.getString("Help"));
    helpButton.setOnAction(this:: handleHelp);

    this.changesButton = new Button(buttonBundle.getString("ApplyLabel"));
    changesButton.setOnAction(this::handleChanges);

    this.savePrefButton = new Button(buttonBundle.getString("SavePref"));
    savePrefButton.setOnAction(this::writeOutTab);
  }

  private void applyChanges () {
    this.myMainView.setBackgroundColor(backgroundMenu.getValue());
    this.myMainView.setPenColor(penMenu.getValue());

    if (!turtleMenu.getSelectionModel().isEmpty()) {
      String url = "/slogo/view/imagesFolder/" + turtleMenu.getValue() + ".png";
      myMainView.setTurtleFileName(turtleMenu.getValue().toString());
      myMainView.getTurtles().setImageViews(new ImageView(new Image("" + url)));
      myMainView.setImageViewLayouts();
      myMainView.setPaneImageViews();

      //TODO: REMOVE THIS FAILED ATTEMPT TO CORRECT POSITIONS BELOW
      myMainView.getTurtles().correctPositions().play();
      myMainView.updateViewLocation();
    }
  }

  /** Methods that define the function of each Button */
  private void handleChanges(ActionEvent actionEvent) {
    applyChanges();
  }

  private void handleHelp(ActionEvent actionEvent) {
    WebView wv = new WebView();
    wv.getEngine().setCreatePopupHandler(new Callback<PopupFeatures, WebEngine>() {

      @Override
      public WebEngine call(PopupFeatures p) {
        Stage stage = new Stage(StageStyle.UTILITY);
        WebView wv2 = new WebView();
        stage.setScene(new Scene(wv2));
        stage.show();
        return wv2.getEngine(); }
    });

    StackPane root = new StackPane();
    root.getChildren().add(wv);
    Scene scene = new Scene(root, 1300 , 1000);

    Stage stage = new Stage();
    stage.setTitle("List of Commands");
    stage.setScene(scene);
    stage.show();

    wv.getEngine().load("https://www2.cs.duke.edu/courses/spring20/compsci308/assign/03_parser/commands.php");
  }

  private void writeOutTab(ActionEvent actionEvent) {
    try{
      XMLWriter writer = new XMLWriter(myMainView);
      writer.outputFile();
    } catch (XMLException | ParserConfigurationException e) {
      throw new XMLException("Couldn't parse workspace");
    } catch(Exception e) {
      throw new XMLException("Couldn't write file");
    }

  }
  /** Public Set Methods Called Directly from the Console */

  public void setBackground(int i){
    ObservableList<Color> colorList = backgroundMenu.getCustomColors();
    if(colorList.size()<=0) return;
    if(i>=colorList.size()) i = colorList.size()-1;
    backgroundMenu.setValue(colorList.get(i));
    applyChanges();
  }

  public void setPenColor(int i){
    System.out.println("setPenColor");
    ObservableList<Color> colorList = penMenu.getCustomColors();
    if(colorList.size()<=0) return;
    if(i>=colorList.size()) i = colorList.size()-1;
    penMenu.setValue(colorList.get(i));
    applyChanges();
  }

  public void setShape(int i){
    languageMenu.getSelectionModel().select(i);
    applyChanges();
  }

  /** Public Get Methods */

  public Button getCommandButton(){ return commandButton; }

  public ComboBox getLanguageBox() {return languageMenu; }

  public int getPenColor() {
    return penMenu.getCustomColors().indexOf(penMenu.getValue());
  }

  public int getTurtleShape() {return languageMenu.getSelectionModel().getSelectedIndex();}



}
