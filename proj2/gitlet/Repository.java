package gitlet;

import java.io.File;
import java.util.*;

import static gitlet.Utils.*;

/**
 * Represents a gitlet repository.
 * does at a high level.
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
    /** The .gitlet/stageArea directory. */
    public static final File stageArea_Maps = join(GITLET_DIR, "stageArea", "Maps");
    public static final File stageArea_DIR = join(GITLET_DIR, "stageArea");
    public static final File Commit_DIR = join(GITLET_DIR, "Commits");
    public static final File HEAD = join(GITLET_DIR, "HEAD");
    public static final File MASTER = join(GITLET_DIR, "master");

    /**
     * The function for handling init argument.
     * Creates a new Gitlet version-control system in the current directory.
     * Automatically starts with one commit, "initial commit"
     * will have a single branch: master, Timestamp : Unix Epoch
     * if there is already a Gitlet version-control system exists, throw error.
     */
    public static void init() {
        if (GITLET_DIR.exists())
            exitWithError("A Gitlet version-control system already exists in the current directory.");
        GITLET_DIR.mkdir();
        try {
            stageArea_DIR.mkdir();
            stageArea_Maps.createNewFile();
            HEAD.createNewFile();
            writeObject(HEAD, MASTER);
            MASTER.createNewFile();
            Blob fMap = new Blob();
            writeObject(stageArea_Maps, fMap);
            Commit_DIR.mkdir();
        } catch (Exception e) {
            // Do nothing
        }
        commit("initial commit");
    }

    /**
     * Adds a copy of the file as it currently exists to the staging area.
     * Adding an already-existed file overwrites the previous entry in the staging
     * area with the new contents.
     * If current working version is identical with current commit, do not add.
     * Only one file may be added at a time.
     * if the file does not exist, throw error.
     */
    public static void add(String Filename) {
        // check if that init run.
        if (!stageArea_Maps.exists())
            notInitializedError();

        File AddingFile = new File(Filename);
        if (!AddingFile.exists())
            exitWithError("File does not exist.");

        Blob Staged = readObject(stageArea_Maps, Blob.class);
        Commit curCommit = headCommit();
        Map<File, String> CommittedMap = curCommit.objMaps.Maps;
        String AddingFile_sha1 = sha1(readContents(AddingFile));

        if (CommittedMap.containsKey(AddingFile)) {
            if (CommittedMap.get(AddingFile).equals(AddingFile_sha1))
                return;
        }

        if (Staged.Maps.containsKey(AddingFile)) {
            Staged.Maps.replace(AddingFile, AddingFile_sha1);
        } else {
            Staged.Maps.put(AddingFile, AddingFile_sha1);
        }
        writeObject(stageArea_Maps, Staged);
        writeContents(join(stageArea_DIR, AddingFile_sha1), readContents(AddingFile));

    }

    public static void commit(String msg) {
        if (msg.isBlank())
            exitWithError("Please enter a commit message.");
        Commit NextCommit = new Commit(msg);
        NextCommit.id = sha1(serialize(NextCommit));
        File curBranch = readObject(HEAD, File.class);
        writeContents(curBranch, NextCommit.id);
        File CommitFile = join(Commit_DIR, NextCommit.id);
        writeObject(CommitFile, NextCommit);
    }

    /**
     * Unstage the file if it is currently staged for addition.
     * If the file is tracked in the current commit, stage it for removal and remove
     * the file from the working directory
     * if the user has not already done so (do not remove it unless it is tracked in
     * the current commit).
     * 
     * @param filename
     */
    public static void rm(String filename) {
        File rmFile = new File(filename);
        Blob Staged = readObject(stageArea_Maps, Blob.class);
        Commit curCommit = headCommit();
        if (!Staged.Maps.containsKey(rmFile) && !curCommit.objMaps.Maps.containsKey(rmFile))
            exitWithError("No reason to remove the file.");
        if (Staged.Maps.containsKey(rmFile))
            Staged.Maps.remove(rmFile);
        if (curCommit.objMaps.Maps.containsKey(rmFile))
            Staged.removalMaps.add(rmFile);
        writeObject(stageArea_Maps, Staged);
        if (rmFile.exists())
            rmFile.delete();
    }

    public static void log() {
        Commit head = headCommit();
        System.out.println(head);
    }

    public static void globalLog() {
        for (String f : plainFilenamesIn(Commit_DIR)) {
            try {
                System.out.print(Commit.toStringSingle(f));
            } catch (Exception e) {
                // do nothing
            }
        }

    }

    public static void status() {
        Blob Staged = readObject(stageArea_Maps, Blob.class);
        StringBuilder sb = new StringBuilder("=== Branches ===\n");
        String curBranch = readObject(HEAD, File.class).getName();
        List<String> branches = plainFilenamesIn(GITLET_DIR);
        branches.sort(Comparator.naturalOrder());
        for (String s : branches
             ) {
            if(s.equals(curBranch)) sb.append("*");
            if(s.equals("HEAD")) continue;
            sb.append(s);
            sb.append("\n");
        }
        sb.append(Staged.toString());
        sb.append("\n");
        sb.append("=== Modifications Not Staged for Commit ===\n");
        sb.append("\n=== Untracked Files ===\n");
        sb.append("\n");
        System.out.println(sb);
    }
    public static void checkout(String branchName) {
        File branch = join(GITLET_DIR, branchName);
        File head = readObject(HEAD, File.class);
        if (!branch.exists())
            exitWithError("No such branch exists.");
        if (branch.equals(head))
            exitWithError("No need to checkout the current branch.");
        String branchHeadCommitID = readContentsAsString(branch);
        Commit branchHead = Commit.readFromID(branchHeadCommitID);
        Commit curHead = headCommit();
        checkoutFailureCase3(branchHead, curHead);
        checkout3(branchHead,curHead);
        writeObject(HEAD, branch);
    }

    /**
     * Takes two commits. Overwrite files in first Commit with second commit. delete missing ones.
     */
    private static void checkout3(Commit tarCommit1, Commit tarCommit2) {
        Set<File> filesSet = new HashSet<>();
        filesSet.addAll(tarCommit1.objMaps.Maps.keySet());
        filesSet.addAll(tarCommit2.objMaps.Maps.keySet());
        for (File branchFile : filesSet) {
            if (tarCommit1.objMaps.Maps.containsKey(branchFile)) {
                overwriteCheckoutFile(tarCommit1, branchFile);
            } else {
                branchFile.delete();
            }
        }
        ClearStageArea();

    }

    private static void checkoutFailureCase3(Commit branch, Commit current) {
        for (File branchFile : branch.objMaps.Maps.keySet()) {
            if (!branch.objMaps.Maps.get(branchFile).equals(
                    sha1(readContents(branchFile))) &&
                    !current.objMaps.Maps.containsKey(branchFile)) {
                exitWithError("There is an untracked file in the way; delete it, or add and commit it first.");
            }
        }
    }

    public static void checkout(String command, String fileName) {
        if (!command.equals("--"))
            incorrectOperandError();
        Commit curCommit = headCommit();
        overwriteCheckoutFile(curCommit, new File(fileName));
    }

    public static void checkout(String CommitId, String command, String fileName) {
        if (!command.equals("--"))
            incorrectOperandError();
        Commit curCommit = Commit.readFromID(CommitId);
        overwriteCheckoutFile(curCommit, new File(fileName));
    }

    /**
     * overwrite given file with contents in given commmit.
     */
    private static void overwriteCheckoutFile(Commit tarCommit, File fileName) {
        if (!tarCommit.objMaps.Maps.containsKey(fileName))
            exitWithError("File does noe exist in that commit.");
        String checkoutContents = readContentsAsString(
                join(Commit_DIR, tarCommit.objMaps.Maps.get(fileName)));
        if (!fileName.exists()) {
            try {
                fileName.createNewFile();
            } catch (Exception e) {
                // Do nothing.
            }
        }
        writeContents(fileName, checkoutContents);
    }

    public static void find(String commitMessage) {
        Commit curCommit;
        boolean notFound = true;
        for (String f : plainFilenamesIn(Commit_DIR)) {
            try {
                curCommit = Commit.readFromID(f);
                if (commitMessage.equals(curCommit.message)) {
                    System.out.println(curCommit.id);
                    notFound = false;
                }
            } catch (Exception e) {
                // do nothing
            }
        }
        if (notFound)
            exitWithError("Found no commit with that message.");
    }

    public static void branch(String branchName) {
        File branchFile = join(GITLET_DIR, branchName);
        File curBranch = readObject(HEAD, File.class);
        try {
            if (branchFile.createNewFile()) {
                writeContents(branchFile, readContents(curBranch));
            } else {
                exitWithError("A branch with that name already exists.");
            }
        } catch (Exception e) {
            // Do nothing
        }

    }

    public static void rmBranch(String branchName) {
        File branchFile = join(GITLET_DIR, branchName);
        if (branchFile.exists()) {
            if (readObject(HEAD, File.class).equals(branchFile))
                exitWithError("Cannot remove the current branch.");
            else
                branchFile.delete();
        } else {
            exitWithError("A branch with that name does not exist.");
        }
    }

    public static void reset(String CommitID) {
        Commit resetCommit = Commit.readFromID(CommitID);
        Commit curHead = headCommit();
        checkoutFailureCase3(resetCommit, curHead);
        checkout3(resetCommit,curHead);
        File curBranch = readObject(HEAD, File.class);
        writeContents(curBranch,CommitID);
    }
    public static void merge(String branchName){
        Blob Staged = readObject(stageArea_Maps, Blob.class);
        if(Staged.Maps.size() == 0 && Staged.removalMaps.size() == 0) exitWithError("You have uncommitted changes.");
        if(!join(GITLET_DIR,branchName).exists()) exitWithError("A branch with that name does not exist.");
        String cur = headCommit().id;
        String branch = readContentsAsString(join(GITLET_DIR,branchName));
        if(cur.equals(branch)) exitWithError("Cannot merge a branch with itself.");

        String spiltPointID = spiltPoint(branchName);
        if(spiltPointID.equals(cur)) {
            checkout(branchName);
            exitWithError("Current branch fast-forwarded.");
        }
        if(spiltPointID.equals(branch)){
            exitWithError("Given branch is an ancestor of the current branch.");
        }
        Map <File,String> curMap = Commit.readFromID(cur).objMaps.Maps;
        Map <File,String> branchMap = Commit.readFromID(branch).objMaps.Maps;
        Map <File,String> spointMap = Commit.readFromID(spiltPointID).objMaps.Maps;
        Set<File> allFiles = curMap.keySet();
        allFiles.addAll(branchMap.keySet());
        allFiles.addAll(spointMap.keySet());
        String fname,c,b,s;
        for (File f: allFiles
             ) {
            fname = whichMap(f,curMap,branchMap,spointMap);
            c = curMap.get(f);
            b = branchMap.get(f);
            s = spointMap.get(f);
            switch (fname){
                case "cbs":
                    if(!c.equals(b)){
                        if(c.equals(s)) mergeConflict(f,c,b);
                        else {
                            checkout(branch,"--",f.toString());
                            add(f.toString());
                        }
                    }
                    break;
                case "cs":
                    if(c.equals(s)) rm(f.toString());
                    else mergeConflict(f,c,b);
                    break;
                case "sb":
                    if(!s.equals(b)) mergeConflict(f,c,b);
                    break;
                case "cb":
                    if(!c.equals(b)) mergeConflict(f,c,b);
                    break;
                case "s":
                case "c":
                    break;
                case "b":
                    checkout(branch,"--",f.toString());
                    add(f.toString());
                    break;
                default:
                    Utils.exitWithError("no such file in both maps");
            }
        }

        System.out.println("Merged " + branchName + " into " +
                readObject(HEAD,File.class).getName() + ".");
    }
    private static void mergeConflict(File f, String h,String b){
        System.out.println("Encountered a merge conflict.");
        StringBuilder contents = new StringBuilder();
        contents.append("<<<<<<< HEAD\n");
        if(h != null) contents.append(readContentsAsString(join(Commit_DIR,h)));
        contents.append("=======");
        if(b != null) contents.append(readContentsAsString(join(Commit_DIR,b)));
        contents.append(">>>>>>>");
        writeContents(f,contents.toString());
    }
    private static String whichMap(File f, Map<File,String> Map1, Map<File,String> Map2, Map<File,String> Map3){
        boolean cur = Map1.containsKey(f);
        boolean bra = Map2.containsKey(f);
        boolean sp = Map3.containsKey(f);
        if(cur && bra && sp) return "cbs";
        if(cur && !bra && sp) return "cs";
        if(cur && bra && !sp) return "cb";
        if(!cur && bra && sp) return "bs";
        if(!cur && !bra && sp) return "s";
        if(cur && !bra && !sp) return "c";
        if(!cur && bra && !sp) return "b";
        return null;
    }
    private static String spiltPoint(String branchName){
        File cur = readObject(HEAD,File.class);
        File branch = join(GITLET_DIR,branchName);
        ArrayList<String> curHistory = branchHistory(cur);
        ArrayList<String> braHistory = branchHistory(branch);
        curHistory.retainAll(braHistory);
        return curHistory.get(curHistory.size() - 1);
    }
    private static ArrayList<String> branchHistory(File branch){
        String curCommit = readContentsAsString(branch);
        ArrayList<String> returnList = new ArrayList<>();
        branchHistory(curCommit,returnList);
        return returnList;
    }
    private static void branchHistory(String curCommitID, ArrayList<String> Lists){
        if(curCommitID == null) return;
        Commit curCommit = Commit.readFromID(curCommitID);
        Lists.add(curCommit.id);
        branchHistory(curCommit.parent,Lists);
    }
    public static void ClearStageArea() {
        Blob b = new Blob();
        writeObject(stageArea_Maps, b);
    }

}
