package slogo.commands.controlcommands;

import slogo.backendexternal.TurtleStatus;
import slogo.backendexternal.parser.Parser;
import slogo.commands.Command;
import slogo.commands.ControlCommand;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * MakeVariable is used for the MAKE or SET commands.
 * The Variable varHolder should be created with the default constructor before calling
 * MakeVariable's constructor. Make sure that this same Variable is passed to future MakeVariable instances
 * so that a variable may have continuity over a program.
 *
 * @author Tyler Jang
 */
public class MakeVariable implements ControlCommand {

    public static final int NUM_ARGS = 1;
    private Command expr;
    private double val;
    Variable varHolder;

    //TODO Dennis: PLEASE CHECK IN THE PARSER TO ENSURE THAT arg1 CONTAINS A VARIABLE INSTANCE
    public MakeVariable(Variable var, Command value) {
        varHolder = var;
        expr = value;
    }

    @Override
    public Collection<TurtleStatus> execute(TurtleStatus ts) {
        List<TurtleStatus> ret = new ArrayList<>();
        ret.addAll(expr.execute(ts));
        val = expr.returnValue();
        varHolder.setVal(val);
        return ret;
    }

    @Override
    public double returnValue() {
        return val;
    }

    //TODO: Remove if possible
    public Variable getVarHolder() {
        return varHolder;
    }
}
