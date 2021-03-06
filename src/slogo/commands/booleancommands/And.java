package slogo.commands.booleancommands;

import slogo.backendexternal.TurtleManifest;
import slogo.backendexternal.TurtleStatus;
import slogo.commands.BooleanCommand;
import slogo.commands.Command;

import java.util.ArrayList;
import java.util.List;

/**
 * Check if two values are both none zero
 * @author Lucy Gu
 */
public class And implements BooleanCommand{
    private Command arg1;
    private Command arg2;
    private double returnVal;
    public static final int NUM_ARGS = 2;

    /**
     * Takes in two commands as arguments: the return values of the two commands will be used for the AND operation
     *
     * @param argA  first input command (executed first)
     * @param argB  second input command(executed after first command)
     */
    public And(Command argA, Command argB){
        arg1 = argA;
        arg2 = argB;
    }

    /**
     * Create an empty list of turtle status, fill the list up with status from the arguments
     *  using twoArgOperation, and set return value to be 1 if two argument commands both evaluate to non-zero result
     *
     * @param manifest a TurtleManifest containing information about all the turtles
     * @return a List of TurtleStatus instances, given by the execution of this and any subsequent commands.
     *              (this operation itself does not generate new turtle status)
     */
    @Override
    public List<TurtleStatus> execute(TurtleManifest manifest){
        List<TurtleStatus> ret = new ArrayList<>();
        double[] val = BooleanCommand.twoArgOperation(ret, manifest, arg1, arg2);
        returnVal = (val[0]!=0 && val[1]!=0) ? TRUE : FALSE;
        return ret;
    }

    /**
     * @return      the return value set during the execution of this operation
     */
    @Override
    public double returnValue() {
        return returnVal;
    }

}
