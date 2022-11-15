package gitlet;

import java.io.File;
import java.util.Map;

import static gitlet.Utils.*;

/** Represents a gitlet repository.
 *  does at a high level.
 */
public class Repository {
    /**
     * List all instance variables of the Repository class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided two examples for you.
     */

    /** The current working directory. */
    public static final File CWD = new File(System.getProperty("user.dir"));
    /** The .gitlet directory. */
    public static final File GITLET_DIR = join(CWD, ".gitlet");
    /** The .gitlet/stageArea directory.*/
    public static final File stageArea_Maps = join(GITLET_DIR,"stageArea","Maps");
    public static final File stageArea_DIR = join(GITLET_DIR,"stageArea");
    public static final File Commit_DIR = join(GITLET_DIR,"Commits");
    public static final File HEAD = join(GITLET_DIR,"HEAD");
    public static final File MASTER = join(GITLET_DIR,"MASTER");
    /**
     * The function for handling init argument.
     * Creates a new Gitlet version-control system in the current directory.
     * Automatically starts with one commit, "initial commit"
     * will have a single branch: master, Timestamp : Unix Epoch
     * if there is already a Gitlet version-control system exists, throw error.
     */
    public static void init(){
        if(GITLET_DIR.exists()) exitWithError("A Gitlet version-control system already exists in the current directory.");
        GITLET_DIR.mkdir();
        try{
            stageArea_DIR.mkdir();
            stageArea_Maps.createNewFile();
            HEAD.createNewFile();
            Utils.writeObject(HEAD,MASTER);
            MASTER.createNewFile();
            Blob fMap = new Blob();
            Utils.writeObject(stageArea_Maps,fMap);
            Commit_DIR.mkdir();
        } catch (Exception e){
            // Do nothing
        }
        commit("initial commit");
    }


    /**
     * Adds a copy of the file as it currently exists to the staging area.
     * Adding an already-existed file overwrites the previous entry in the staging area with the new contents.
     * If current working version is identical with current commit, do not add.
     * Only one file may be added at a time.
     * if the file does not exist, throw error.
     */
    public static void add(String Filename){
        // check if that init run.
        if(!stageArea_Maps.exists()) notInitializedError();

        File AddingFile = new File(Filename);
        if(!AddingFile.exists()) exitWithError("File does not exist.");

        Blob Staged = Utils.readObject(stageArea_Maps,Blob.class);
        Commit curCommit = Utils.headCommit();
        Map<File,String> CommittedMap = curCommit.objMaps.Maps;
        String AddingFile_sha1 = sha1(readContents(AddingFile));


        if(CommittedMap.containsKey(AddingFile)){
            if(CommittedMap.get(AddingFile).equals(AddingFile_sha1)) return;
        }

        if(Staged.Maps.containsKey(AddingFile)){
            Staged.Maps.replace(AddingFile, AddingFile_sha1);
        }
        else {
            Staged.Maps.put(AddingFile,AddingFile_sha1);
        }
        Utils.writeObject(stageArea_Maps,Staged);
        Utils.writeContents(Utils.join(stageArea_DIR,AddingFile_sha1),readContents(AddingFile));

    }
    public static void commit(String msg){
        if(msg.isBlank()) Utils.exitWithError("Please enter a commit message.");
        Commit NextCommit = new Commit(msg);
        NextCommit.id = sha1(serialize(NextCommit));
        File curBranch = Utils.readObject(HEAD, File.class);
        writeContents(curBranch,NextCommit.id);
        File CommitFile = Utils.join(Commit_DIR, NextCommit.id);
        try{
            CommitFile.createNewFile();
            writeObject(CommitFile,NextCommit);
        } catch (Exception e){
            // Do nothing
        }

    }

    /**
     * Unstage the file if it is currently staged for addition.
     * If the file is tracked in the current commit, stage it for removal and remove the file from the working directory
     * if the user has not already done so (do not remove it unless it is tracked in the current commit).
     * @param filename
     */
    public static void rm(String filename){
        File rmFile = new File(filename);
        Blob Staged = Utils.readObject(stageArea_Maps,Blob.class);
        Commit curCommit = Utils.headCommit();
        if(!Staged.Maps.containsKey(rmFile) && !curCommit.objMaps.Maps.containsKey(rmFile)) exitWithError("No reason to remove the file.");
        if(Staged.Maps.containsKey(rmFile)) Staged.Maps.remove(rmFile);
        if(curCommit.objMaps.Maps.containsKey(rmFile)) Staged.removalMaps.add(rmFile);
        writeObject(stageArea_Maps,Staged);
        if(rmFile.exists()) rmFile.delete();
    }

    public static void log(){
        Commit head = Utils.headCommit();
        System.out.println(head);
    }
    public static void globalLog(){
        for (String f: Utils.plainFilenamesIn(Commit_DIR)
             ) {
            try{
                System.out.print(Commit.toStringSingle(f));
            } catch (Exception e) {
                //do nothing
            }
        }

    }
    public static void status(){
        String head = Utils.headCommit().id;
        Blob Staged = Utils.readObject(stageArea_Maps,Blob.class);
        StringBuilder sb = new StringBuilder("=== Branches ===\n");
        //sb.append(status(head));
        sb.append("*master\n");
        sb.append(Staged.toString());
        sb.append("\n");
        sb.append("=== Modifications Not Staged for Commit ===\n");
        sb.append("\n=== Untracked Files ===\n");
        sb.append("\n");
        System.out.println(sb);
    }
    public static String status(String curCommitID){
        if(curCommitID == null) return "";
        Commit curCommit = Commit.readFromID(curCommitID);
        StringBuilder retrunSB = new StringBuilder(curCommit.id);
        retrunSB.append("\n");
        return retrunSB + status(curCommit.parent);
    }
    public static void checkout(String fileName){
        Commit curCommit = Utils.headCommit();
        File checkoutFile = new File(fileName);
        if(!curCommit.objMaps.Maps.containsKey(checkoutFile)) Utils.exitWithError("File does noe exist in that commit.");
        String checkoutContents = readContentsAsString(Utils.join(Commit_DIR,curCommit.objMaps.Maps.get(checkoutFile)));
        checkoutFile = Utils.join(CWD,fileName);
        writeContents(checkoutFile,checkoutContents);

    }
    public static void checkout(String CommitId,String fileName){
        Commit curCommit = Commit.readFromID(CommitId);
        File checkoutFile = new File(fileName);
        if(!curCommit.objMaps.Maps.containsKey(checkoutFile)) Utils.exitWithError("File does noe exist in that commit.");
        String checkoutContents = readContentsAsString(Utils.join(Commit_DIR,curCommit.objMaps.Maps.get(checkoutFile)));
        checkoutFile = Utils.join(CWD,fileName);
        writeContents(checkoutFile,checkoutContents);

    }
    public static void find(String commitMessage){
        Commit curCommit;
        boolean notFound = true;
        for (String f: Utils.plainFilenamesIn(Commit_DIR)
        ) {
            try{
                curCommit = Commit.readFromID(f);
                if(commitMessage.equals(curCommit.message)) {
                    System.out.println(curCommit.id);
                    notFound = false;
                }
            } catch (Exception e) {
                //do nothing
            }
        }
        if(notFound) Utils.exitWithError("Found no commit with that message.");
    }
    public static void ClearStageArea(){
        Blob b = new Blob();
        writeObject(stageArea_Maps,b);
    }
}
