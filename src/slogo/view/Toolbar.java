package slogo.view;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import javafx.scene.control.*;

/**
 * @author Shruthi Kumar, Nevzat Sevim
 */

public class Toolbar extends ToolBar {

  //Incorporate View and Text Field
  private MainView myMainView;
  private TextFields myTextFields;

  //Labels for DropDown Menus
  private final Label penLabel = new Label("Pen:");
  private final Label backgroundLabel = new Label("Background:");
  private final Label turtleLabel = new Label("Turtle:");
  private final Label languageLabel = new Label("Language:");

  //The Drop Down Menus Themselves
  private ColorPicker penMenu, backgroundMenu;
  private ComboBox languageMenu, turtleMenu;

  //The Buttons
  private Button commandButton, helpButton, changesButton;

  //Idk what this does
  private static final int FRAMES_PER_SECOND = 60;
  private static final double MILLISECOND_DELAY = 10000/FRAMES_PER_SECOND;
  private Timeline animation;


  public Toolbar(MainView mainview) {
    this.myMainView = mainview;
    this.myTextFields = myMainView.getTextFields();

    createMenus();
    createButtons();

    TextField textField = new TextField("Enter Command: ");
    textField.setOnAction(this:: handleCommand);

    animationFunctions();

    this.getItems().addAll(textField, commandButton, new Separator(),
                            turtleLabel, turtleMenu, penLabel, penMenu,
                            languageLabel, languageMenu, backgroundLabel, backgroundMenu,  changesButton, new Separator(),
                            helpButton);
  }

  /**
   * Helping methods to import menus and buttons to the toolbar
   */

  private void createMenus() {
    this.penMenu = new ColorPicker();
    penMenu.setMaxWidth(50);
    this.backgroundMenu = new ColorPicker();
    backgroundMenu.setMaxWidth(50);

    this.turtleMenu = new ComboBox();
    this.languageMenu = new ComboBox();
  }

  private void createButtons() {
    this.commandButton = new Button("Run");
    commandButton.setOnAction(this:: handleCommand);

    this.helpButton = new Button("?");
    helpButton.setOnAction(this:: handleHelp);

    this.changesButton = new Button("Apply");
    changesButton.setOnAction(this::handleChanges);
  }

  /**
   * Methods that define the function of each Button
   */

  private void handleChanges(ActionEvent actionEvent) {
    this.myMainView.setBackgroundColor(backgroundMenu.getValue());
    this.myMainView.setPenColor(penMenu.getValue());
  }

  private void handleHelp(ActionEvent actionEvent) {
  }

  private void handleCommand(ActionEvent actionEvent) {
    animation.play();
    myTextFields.addText();
  }

  /**
   * Method that sets up the animation, in which the myMainview step method is called every second which updates the
   * grid on the screen.
   */
  public void animationFunctions() {

    KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> {
      try {
        myMainView.step();
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    });
    animation = new Timeline();
    animation.setCycleCount(Timeline.INDEFINITE);
    animation.getKeyFrames().add(frame);
  }

  // Public Set Methods
  public void setTextField(TextFields tf){this.myTextFields = tf;}

}
