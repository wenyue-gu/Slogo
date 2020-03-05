package slogo.commands.turtlecommands;

import slogo.backendexternal.TurtleStatus;
import slogo.commands.TurtleCommand;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Tyler Jang
 */
public class PenUp implements TurtleCommand {

    public static final int NUM_ARGS = 0;
    private static final int UP_STATUS = 0;

    public PenUp() {}

    @Override
    public List<TurtleStatus> execute(TurtleStatus ts) {
        List<TurtleStatus> ret = new ArrayList<>();
        ret.add(ts);
        ret.add(new TurtleStatus(ts.getID(), ts.getX(), ts.getY(), ts.getBearing(), true, ts.getVisible(), false));
        return (ret);
    }

    @Override
    public double returnValue() { return UP_STATUS; }
}
