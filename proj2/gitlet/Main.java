package gitlet;


/** Driver class for Gitlet, a subset of the Git version-control system.
 *  @author TODO
 */
public class Main {

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND1> <OPERAND2> ... 
     */
    public static void main(String[] args) {
        //if args is empty, exit.
        if(args.length ==0){
            Utils.exitWithError("Please enter a command.");
        }

        String firstArg = args[0];
        switch(firstArg) {
            case "init":
                // handle the `init` command
                if(args.length != 1) Utils.incorrectOperandError();
                Repository.init();
                break;
            case "add":
                // handle the `add [filename]` command
                if(args.length != 2) Utils.incorrectOperandError();
                String Filename = args[1];
                Repository.add(Filename);
                break;
            case "commit":
                // hadle the 'commit [mssage]' command
                if(args.length != 2) Utils.incorrectOperandError();
                String msg = args[1];
                Repository.commit(msg);
                break;
            case "rm":
                // handle the 'rm [filename]' command
                if(args.length != 2) Utils.incorrectOperandError();
                String filename = args[1];
                Repository.rm(filename);
                break;
            case "log":
                // hadle the 'log' command
                if(args.length != 1) Utils.incorrectOperandError();
                Repository.log();
                break;
            case "status":
                // hande the 'status' command
                if(args.length != 1) Utils.incorrectOperandError();
                Repository.status();
                break;
            case "checkout":
                switch (args.length) {
                    case 3:
                        Repository.checkout(args[2]);
                        break;
                    case 4:
                        Repository.checkout(args[2],args[3]);
                        break;
                    default:
                        Utils.incorrectOperandError();
                }


                break;
            // TODO: FILL THE REST IN
            default:
                Utils.exitWithError("No command with that name exists.");
        }
    }
}
