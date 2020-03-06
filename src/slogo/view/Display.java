package slogo.view;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;

/**
 * @author Shruthi Kumar, Nevzat Sevim
 */
public class Display {
  private Scene myScene;
  private MainView myMainView;

  private TabPane tabPane = new TabPane();
  private Tab tab = new Tab("SLogo 0");
  private VBox vBox;

  private List<MainView> myMainViewList;
  private Button addTabButton, addTabFromPreferences;
  private ResourceBundle buttonBundle;
  public static final double SCREEN_WIDTH = (int) Screen.getPrimary().getBounds().getWidth() - 100;
  public static final double SCREEN_HEIGHT = (int) Screen.getPrimary().getBounds().getHeight() - 100;


  private int tabNo;

  public Display() {

    myMainViewList = new ArrayList<>();
    buttonBundle = ResourceBundle.getBundle("slogo.view.resources.buttons");
    myMainViewList = new ArrayList<>();
    addTabButton = new Button(buttonBundle.getString("AddTab"));
    addTabFromPreferences = new Button(buttonBundle.getString("AddTabPreferences"));

    vBox = new VBox();
    vBox.setMinSize(SCREEN_WIDTH, SCREEN_HEIGHT);

    myMainView = new MainView();
    tab.setGraphic(myMainView);
    tab.setClosable(false);
    tabPane.getTabs().addAll(tab);

    HBox hBox = new HBox();
    hBox.setSpacing(10.0);
    hBox.getChildren().addAll(addTabButton, addTabFromPreferences);

    vBox.getChildren().addAll(hBox, tabPane);

    tabPane.setTabMaxHeight(760);
    tabPane.setTabMaxWidth(1050);
    tabPane.setTabMinHeight(760);
    tabPane.setTabMinWidth(1050);

    vBox.setAlignment(Pos.TOP_CENTER);
    //addTabFromPreferences.setAlignment(Pos.TOP_LEFT);

    myScene = new Scene(vBox);
  }


  /**
   * Returns display scene
   */
  public Scene getScene(){
    return myScene;
  }

  public TabPane getTabPane(){ return tabPane; }

  public MainView getMainView(){
    Tab tab = tabPane.getSelectionModel().getSelectedItem();
    return (MainView) tab.getGraphic();
  }

  public Button getAddTabButton() {
    return addTabButton;
  }

  public Button getAddTabFromPreferencesButton() {
    return addTabFromPreferences;
  }

  public void addTab(MainView newMainView) {
    tabNo++;
    if(newMainView == null) {
      newMainView = new MainView();
    }
    Tab newTab = new Tab("SLogo " + tabNo);
    newTab.setGraphic(newMainView);
    tabPane.getTabs().add(newTab);
    //selectionModel.select(newTab);
  }

}
