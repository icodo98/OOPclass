package gitlet;

import java.io.File;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static gitlet.Repository.ClearStageArea;

/** Represents a gitlet commit object.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author TODO
 */
public class Commit implements Serializable {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Commit class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided one example for `message`.
     */
    public String id;
    public Blob objMaps;
    private ZonedDateTime createdTime;
    public String parent;
    private String message;


    public Commit(String message){
        this.message = message;
        this.createdTime = ZonedDateTime.now();
        try {
            Commit parent = Utils.readObject(Repository.HEAD,Commit.class);
            this.parent = parent.id;
            this.objMaps = parent.objMaps;
        } catch (IllegalArgumentException e){
            this.parent = null;
            this.objMaps = new Blob();
        }
        if(message != "initial commit"){
            mergeStagedFile();
            ClearStageArea();
        }
    }


    /**
     * merge Staged file with current Commits Maps.
     */
    private void mergeStagedFile(){
        Blob Staged = Utils.readObject(Repository.stageArea_DIR,Blob.class);
        if(Staged.Maps.size() == 0 && Staged.removalMaps.size() == 0) Utils.exitWithError("No changes added to the commit.");
        this.objMaps.Maps.putAll(Staged.Maps);
        for (File f : Staged.removalMaps
             ) {
            this.objMaps.Maps.remove(f);
        }
    }

    /**
     * Starting at the current head commit, display information about each commit
     * backwards along the commit tree until the initial commit,
     * following the first parent commit links, ignoring any second parents found in merge commits.
     * This set of commit nodes is called the commit history.
     * For every node in this history,
     * the information it should display is the commit id, the time the commit was made, and the commit message.
     * @return
     */
    @Override
    public String toString(){
        return toString(this.id);
    }

    private String toString(String curCommitID) {
        if(curCommitID == null) return "";
        Commit curCommit = readFromID(curCommitID);
        StringBuilder returnSB = new StringBuilder("===");
        returnSB.append("\n");
        returnSB.append("commit ");
        returnSB.append(curCommit.id);
        returnSB.append("\n");

        returnSB.append("Date: ");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("eee MMM d HH:mm:ss yyyy xx", Locale.ENGLISH);
        returnSB.append(curCommit.createdTime.format(formatter));
        returnSB.append("\n");

        returnSB.append(curCommit.message);
        returnSB.append("\n\n");
        return returnSB + toString(curCommit.parent);
    }
    public static Commit readFromID(String ID){
        File CommitFile = Utils.join(Repository.Commit_DIR,ID);
        return Utils.readObject(CommitFile,Commit.class);
    }


    /* TODO: fill in the rest of this class. */
}
