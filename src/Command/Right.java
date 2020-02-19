package Command;

import BackEndExternal.TurtleStatus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Right implements Command {

    public static final int NUM_ARGS = 1;

    double degree;

    public Right(double turn){
        degree = turn;
    }


    @Override
    public Collection<TurtleStatus> execute(TurtleStatus ts) {
        Collection<TurtleStatus> ret = new ArrayList<>();
        double deltaHeading = degree;
        return Command.turnDeltaHeading(ts, ret, deltaHeading);
    }


    @Override
    public double returnValue() {
        return (double)degree;
    }

    @Override
    public int getNumArguments() { return NUM_ARGS; }
}
