package slogo.backendexternal.parser;

import slogo.commands.TurtleCommand;
import slogo.backendexternal.backendexceptions.InvalidCommandException;
import slogo.backendexternal.TurtleStatus;

import java.io.File;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Class for parsing user input and turning it into Commands and then TurtleStatus instances
 * <p>
 * Class based in part on ProgramParser.java from spike_parser by Robert C. Duvall
 * https://coursework.cs.duke.edu/compsci308_2020spring/spike_parser/blob/master/src/regex/ProgramParser.java *
 * @author Tyler Jang
 */
public class Parse {
    @Deprecated
    private static final String RESOURCES_PACKAGE = Parse.class.getPackageName() + ".resources.";
    //TODO: REPLACE WITH USER CHOICE

    private List<Map.Entry<String, Pattern>> myCommands;

    public Parse() {
        myCommands = new ArrayList<>();
    }

    //language should be something like "English"
    public void addPatterns (String language) {
        File f = new File(RESOURCES_PACKAGE + language);
        ResourceBundle resources = ResourceBundle.getBundle(RESOURCES_PACKAGE + language);
        for (String key : Collections.list(resources.getKeys())) {
            String regex = resources.getString(key);
            myCommands.add(new AbstractMap.SimpleEntry<>(key,
                    // THIS IS THE IMPORTANT LINE
                    Pattern.compile(regex, Pattern.CASE_INSENSITIVE)));
        }
    }

    private String getCommandString (String text) {
        final String ERROR = "NO MATCH";
        for (Map.Entry<String, Pattern> e : myCommands) {
            if (match(text, e.getValue())) {
                return e.getKey();
            }
        }
        // FIXME: perhaps throw an exception instead
        return ERROR;
    }

    private boolean match (String text, Pattern regex) {
        // THIS IS THE IMPORTANT LINE
        return regex.matcher(text).matches();
    }


    public void testParsing() {
        for (Map.Entry<String, Pattern> m: myCommands) {
            System.out.println(m.getKey() + "\t" + m.getValue());
        }
    }

    ///////////////////////////////////////////////////////////////////////
    //Class.forName()
    private Collection<TurtleCommand> parseLine(String s) {
        //check first for Syntax (Syntax.properties):
            //if it's a command, check it with knownCommands (and declared functions from TurtleModel)
            //else throw InvalidCommandException
        //add to stack of commands

        //once end of String reached, retrieve each command one at a time
                //may have to parse if statements and things with brackets specially
            //if missing an argument or something, throw InvalidCommandException
            //else pop off stack, execute one by one using reflection, generating TurtleStatus instances
            //and adding them to a Collection
            //also making sure to pass the return value of each command to the next popped command

        return null;
    }

    //may want to change to account for multiple arguments, and just make this part of parseLine
    public void validateCommand(String s) throws InvalidCommandException {

    }

    public Collection<TurtleStatus> parseCommands(Collection<String> commandStrings) {
        return null;
    }

}