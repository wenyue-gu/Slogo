package slogo.view;

import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
  private Button commandButton, helpButton, changesButton;
  TextField textField;

  //Timeline Inputs
  private static final int FRAMES_PER_SECOND = 60;
  private static final double MILLISECOND_DELAY = 10000/FRAMES_PER_SECOND;
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
    this.setMinSize(1200.0, 40.0);
    //this.setMinSize(400.0, 40.0);


    this.getItems().addAll(commandButton, new Separator(),
        turtleLabel, turtleMenu, penLabel, penMenu,
        languageLabel, languageMenu, backgroundLabel, backgroundMenu,  changesButton, new Separator(),
        helpButton);
  }

  public Button getCommandButton(){
    return commandButton;
  }

  public ComboBox getLanguageBox() {return languageMenu; }

  /**
   * Helping methods to import menus and buttons to the toolbar
   */

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

  }

  /** Methods that define the function of each Button */
  private void handleChanges(ActionEvent actionEvent) {

    this.myMainView.getPane().setBackground(new Background(new BackgroundFill(backgroundMenu.getValue(), CornerRadii.EMPTY, new Insets(0))));
    this.myMainView.getTurtle().getPenView().setMyPenColor(penMenu.getValue());

    if(!turtleMenu.getSelectionModel().isEmpty()) {
      myMainView.getTurtle().setImageView(new ImageView(new Image("/slogo/view/imagesFolder/" + turtleMenu.getValue() + ".png")));

      myMainView.getTurtle().myImageView.setLayoutX(myMainView.getTurtle().getMyStartXPos());
      myMainView.getTurtle().myImageView.setLayoutY(myMainView.getTurtle().getMyStartYPos());
      myMainView.getTurtle().myImageView.setFitWidth(myMainView.getTurtleSize());
      myMainView.getTurtle().myImageView.setFitHeight(myMainView.getTurtleSize());

      myMainView.getPane().getChildren().set(0, myMainView.getTurtle().myImageView);
      myMainView.getTurtle().myImageView.setX(myMainView.getTurtle().myImageView.getX() - myMainView.getTurtle().myImageView.getFitWidth() / 2);
      myMainView.getTurtle().myImageView.setY(myMainView.getTurtle().myImageView.getY() - myMainView.getTurtle().myImageView.getFitHeight() / 2);
    }
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



  /** Methods for useful Getters and Setters */

  // Public Set Methods
  public void setTextField(InputFields tf){this.myTextFields = tf;}

}
