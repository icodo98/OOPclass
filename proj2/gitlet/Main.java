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
                // handle the 'status' command
                if(args.length != 1) Utils.incorrectOperandError();
                Repository.status();
                break;
            case "checkout":
                // handle the checkout command
                switch (args.length) {
                    case 2 -> Repository.checkout(args[1]); //checkout [branch name]
                    case 3 -> Repository.checkout(args[1], args[2]); //checkout -- [file name]
                    case 4 -> Repository.checkout(args[1],args[2], args[3]); //checkout [commit id] -- [file name]
                    default -> Utils.incorrectOperandError();
                }
                break;
            case "global-log":
                // handel the 'global-log' command
                if(args.length != 1) Utils.incorrectOperandError();
                Repository.globalLog();
                break;
            case "find":
                // handle the "find [commit msg]" command
                if(args.length != 2) Utils.incorrectOperandError();
                Repository.find(args[1]);
                break;
            case "branch":
                // handle the "branch [branch name]" command
                if(args.length != 2) Utils.incorrectOperandError();
                Repository.branch(args[1]);
                break;
            case "rm-branch":
                // handle the "rm-branch [branch name]" command
                if(args.length != 2) Utils.incorrectOperandError();
                Repository.rmBranch(args[1]);
                break;
            case "reset":
                // handle the "reset [commit id]" command
                if(args.length != 2) Utils.incorrectOperandError();
                Repository.reset(args[1]);
                break;
            default:
                Utils.exitWithError("No command with that name exists.");
        }
    }
}
