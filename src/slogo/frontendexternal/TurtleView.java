package slogo.frontendexternal;

import java.security.Policy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import javafx.animation.PathTransition;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Polyline;
import javafx.util.Duration;
import slogo.backendexternal.TurtleModel;
import slogo.backendexternal.TurtleStatus;

/**
 * Creates TurtleView
 * @author Shruthi Kumar
 */
public class TurtleView {
  private double myXPos;
  private double myYPos;

  public Image myImage;
  public ImageView myImageView;

  private PenView penView;
  private double myBearing;
  private PathTransition turtlePath;


  /**
   * Constructor for TurtleView object
   */

  public TurtleView() {
    myXPos = 0;
    myYPos = 0;

    myBearing = 0;
    penView = new PenView();

    myImage = new Image("/slogo/view/imagesFolder/raphael.png");
    myImageView =  new ImageView(myImage);
  }

  /**
   *  Executes the command that the user enters by doing the action specified in the command
   * @param t : Turtle status that holds command
   */
  public void executeState(Collection<TurtleStatus> t) {
//    addPenViewLines(t);
    Polyline pathLine = new Polyline();
    Double[] pathPoints = new Double[t.size()*2];
    Iterator<TurtleStatus> iterator = t.iterator();
    int index = 0;

    while(iterator.hasNext()) {
      TurtleStatus temp = iterator.next();
      addPenViewLines(temp);
      pathPoints[index] = this.getMyXPos() + temp.getX();
      setMyXPos(this.getMyXPos() + temp.getX());
      pathPoints[index+1] = this.getMyYPos() + temp.getY();
      setMyYPos(this.getMyYPos() + temp.getY());

      index+=2;
    }

    pathLine.getPoints().addAll(pathPoints);

    turtlePath = new PathTransition();
    turtlePath.setDuration(Duration.millis(1));
    turtlePath.setNode(this.myImageView);

    turtlePath.setPath(pathLine);
  }

  public void playTurtle() {
    turtlePath.play();

  }

  private void addPenViewLines(TurtleStatus t) {
      if(t.getPenDown()) {
        this.penView.updateMyLines(this.getMyXPos(), this.getMyYPos(), this.getMyXPos() + t.getX(), this.getMyYPos() + t.getY());
      }
  }

  /**
   * Gets x position of turtle
   * @return myXPos : x position
   */
  public double getMyXPos() {
    return myXPos;
  }

  /**
   * Gets y position of turtle
   * @return myYPos : y position
   */
  public double getMyYPos() {
    return myYPos;
  }

  /**
   * Gets ImageView of turtle
   * @return myImageView : image view of turtle
   */
  //public ImageView getMyImageView() {
    //return myImageView;
  //}

  /**
   * Gets bearing of turtle
   * @return  : image view of turtle
   */
  public double getMyBearing() {
    return myBearing;
  }

  public PenView getPenView() {
    return penView;
  }

  /**
   * sets x position of turtle
   * @param xPos : x position
   */
  public void setMyXPos(double xPos) {
    myXPos = xPos;
  }

  /**
   * sets y position of turtle
   * @param yPos : y position
   */
  public void setMyYPos(double yPos) {
    myYPos = yPos;
  }

  /**
   * sets image view of turtle
   * @param imageView : image view of turtle
   */

  public void setImageView(ImageView imageView) {
    myImageView = imageView;
  }



  /**
   * sets bearing of turtle
   * @param degrees : new bearing of turtle
   */
  public void setMyBearing(double degrees) {
    myBearing = degrees;
  }

}
