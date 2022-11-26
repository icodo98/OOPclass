package gitlet;

import java.io.File;
import java.io.Serializable;
import java.nio.file.Files;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static gitlet.Repository.*;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

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
    public ZonedDateTime createdTime;
    public String parent;
    public String parent2;
    public String message;


    public Commit(String message){
        this.message = message;
        try {
            Commit parent = Utils.headCommit();
            this.parent = parent.id;
            this.objMaps = parent.objMaps;
        } catch (IllegalArgumentException e){
            this.parent = null;
            this.objMaps = new Blob();
        }
        if(message != "initial commit"){
            this.createdTime = ZonedDateTime.now();
            mergeStagedFile();
            ClearStageArea();
        }
        else {
            this.createdTime = new Timestamp(0).toLocalDateTime().atZone(ZoneId.of("UTC"));
        }
    }
    public Commit(String curBranchName, String givenBranchName){
        this.message = "Merged " + givenBranchName + " into " +
                curBranchName + ".";
        try {
            Commit parent = Utils.headCommit();
            this.parent = parent.id;
            this.parent2 = Utils.readContentsAsString(Utils.join(GITLET_DIR,givenBranchName));
            this.objMaps = parent.objMaps;
        } catch (IllegalArgumentException e){
            this.parent = null;
            this.objMaps = new Blob();
        }
        this.createdTime = ZonedDateTime.now();
        mergeStagedFile();
        ClearStageArea();
    }

    /**
     * merge Staged file with current Commits Maps.
     */
    private void mergeStagedFile(){
        Blob Staged = Utils.readObject(Repository.stageArea_Maps,Blob.class);
        File writeFile;
        File stagedFile;
        if(Staged.Maps.size() == 0 && Staged.removalMaps.size() == 0) Utils.exitWithError("No changes added to the commit.");
        for (File filename: Staged.Maps.keySet()) {
            writeFile = Utils.join(Commit_DIR,Staged.Maps.get(filename));
            stagedFile = Utils.join(stageArea_DIR,Staged.Maps.get(filename));
            try {
                writeFile.createNewFile();
                Files.move(stagedFile.toPath(),writeFile.toPath(),REPLACE_EXISTING);
            } catch (Exception e){
                // Do nothing.
            }
        }
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
    public static String toStringSingle(String curCommitID) {
        if(curCommitID == null) return "";
        Commit curCommit = readFromID(curCommitID);
        StringBuilder returnSB = new StringBuilder("===");
        returnSB.append("\n");
        returnSB.append("commit ");
        returnSB.append(curCommit.id);
        returnSB.append("\n");
        if(curCommit.parent2 != null){
            returnSB.append("Merge: ");
            returnSB.append(curCommit.parent, 0, 7);
            returnSB.append(" ");
            returnSB.append(curCommit.parent2, 0, 7);
            returnSB.append("\n");
        }
        returnSB.append("Date: ");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("eee MMM d HH:mm:ss yyyy xx", Locale.ENGLISH);
        returnSB.append(curCommit.createdTime.format(formatter));
        returnSB.append("\n");

        returnSB.append(curCommit.message);
        returnSB.append("\n\n");
        return returnSB.toString();
    }

    private String toString(String curCommitID) {
        if(curCommitID == null) return"";
        Commit curCommit = readFromID(curCommitID);
        StringBuilder returnSB = new StringBuilder(toStringSingle(curCommitID));
        toString(curCommit.parent,returnSB);
        return returnSB.toString();
    }
    private void toString(String curCommitID,StringBuilder returnSB) {
        if(curCommitID == null) return;
        Commit curCommit = readFromID(curCommitID);
        returnSB.append(toStringSingle(curCommitID));
        toString(curCommit.parent,returnSB);
    }
    public static Commit readFromID(String ID){
        File CommitFile = Utils.join(Repository.Commit_DIR,ID);
        if(!CommitFile.exists()) Utils.exitWithError("No commit with that id exists.");
        return Utils.readObject(CommitFile,Commit.class);
    }
}
