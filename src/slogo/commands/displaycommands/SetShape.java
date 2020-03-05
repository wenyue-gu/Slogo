package slogo.commands.displaycommands;

import slogo.backendexternal.TurtleStatus;
import slogo.commands.Command;
import slogo.commands.DisplayCommand;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class SetShape implements DisplayCommand {

    private Consumer<Integer> con;
    private Command arg1;
    private double returnVal;

    public SetShape(Command argA, Consumer<Integer> consumer){
        arg1 = argA;
        con = consumer;
    }


    @Override
    public List<TurtleStatus> execute(TurtleStatus ts) {
        List<TurtleStatus> ret = new ArrayList<>();
        returnVal = DisplayCommand.indexAndAddRunnable(ret, arg1, ts, con);
        return ret;
    }

    @Override
    public double returnValue() {
        return returnVal;
    }
}
