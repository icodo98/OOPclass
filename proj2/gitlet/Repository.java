package gitlet;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import static gitlet.Utils.*;

// TODO: any imports you need here

/** Represents a gitlet repository.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author TODO
 */
public class Repository {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Repository class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided two examples for you.
     */

    /** The current working directory. */
    public static final File CWD = new File(System.getProperty("user.dir"));
    /** The .gitlet directory. */
    public static final File GITLET_DIR = join(CWD, ".gitlet");
    /** The .gitlet/stageArea directory.*/
    public static final File stageArea_DIR = join(GITLET_DIR,"stageArea");
    public static final File Commit_DIR = join(GITLET_DIR,"Commit");
    /* TODO: fill in the rest of this class. */
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
        stageArea_DIR.mkdir();
        Commit_DIR.mkdir();
        commit("initial commit");
    }


    /**
     * Adds a copy of the file as it currently exists to the staging area.
     * Adding an already-existed file overwrites the previous entry in the staging area with the new contents.
     * If current working version is identical with current commit, do not add.
     * Only one file may be added at a time.
     * if the file does not exist, throw error.
     * @param Filename
     */
    public static void add(String Filename){
        // check if that init run.
        if(!stageArea_DIR.exists()) notInitializedError();

        File AddingFile = join(CWD,Filename);
        File StagedFile = join(stageArea_DIR,Filename);

        if(!AddingFile.exists()) exitWithError("File does not exist.");
        try {
            Files.copy(AddingFile.toPath(),StagedFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e){
            exitWithError("Staging the file fails");
        }

    }
    private static boolean isFileSame(File f1, File f2){
        return  sha1(readContents(f1)) == sha1(readContents(f1));
    }

    /**
     *
     * @param msg
     */
    public static void commit(String msg){

    }
}
