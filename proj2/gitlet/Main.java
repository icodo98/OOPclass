package gitlet;

/** Driver class for Gitlet, a subset of the Git version-control system.
 *  @author TODO
 */
public class Main {

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND1> <OPERAND2> ... 
     */
    public static void main(String[] args) {
        // TODO: what if args is empty?
        if(args.length ==0){
            Utils.exitWithError("Please enter a command.");
        }

        String firstArg = args[0];
        switch(firstArg) {
            case "init":
                // TODO: handle the `init` command

                break;
            case "add":
                // TODO: handle the `add [filename]` command

                break;
            // TODO: FILL THE REST IN
            default:
                Utils.exitWithError("No command with that name exists.");
        }
    }
}
