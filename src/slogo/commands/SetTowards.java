package slogo.commands;

import slogo.backendexternal.TurtleStatus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class SetTowards implements Command {

    public static final int NUM_ARGS = 2;

    private double xFacing;
    private double yFacing;
    private double degreeMoved = 0;

    public SetTowards(double x, double y){
        xFacing = x;
        yFacing = y;
    }


    @Override
    public Collection<TurtleStatus> execute(TurtleStatus ts) {
        double newDirection = Math.toDegrees(Math.atan((ts.getX()-xFacing)/(ts.getY()-yFacing)));
        degreeMoved = newDirection - ts.getBearing();
        Collection<TurtleStatus> ret = new ArrayList<>();
        return Collections.unmodifiableCollection(Command.turnDeltaHeading(ts, ret, degreeMoved));
    }


    @Override
    public double returnValue() {
        return degreeMoved;
    }

}