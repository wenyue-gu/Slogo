package slogo.commands.turtlecommands;

import slogo.backendexternal.TurtleStatus;
import slogo.commands.TurtleCommand;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 *
 * @author Lucy Gu
 */
public class SetPosition implements TurtleCommand {

    public static final int NUM_ARGS = 2;

    private double xPos;
    private double yPos;
    private double distance = 0;

    public SetPosition(double x, double y){
        xPos = x;
        yPos = y;
    }


    @Override
    public Collection<TurtleStatus> execute(TurtleStatus ts) {
        double deltaX = xPos - ts.getX();
        double deltaY = yPos - ts.getY();
        distance = Math.sqrt(Math.pow(deltaX,2)+Math.pow(deltaY,2));
        Collection<TurtleStatus> ret = new ArrayList<>();

        double deltaHeading = Math.tan((deltaX)/(deltaY)) - ts.getBearing();
        TurtleCommand.turnDeltaHeading(ts, ret, deltaHeading);
        TurtleCommand.moveDelta(ts, ret, deltaX, deltaY);
        return Collections.unmodifiableCollection(ret);
    }


    @Override
    public double returnValue() {
        return distance;
    }


}
