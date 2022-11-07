package gitlet;

// TODO: any imports you need here

import java.io.File;
import java.io.Serializable;
import java.util.Date;
import gitlet.Repository.*;

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
    private Date createdTime;
    private String log;
    private Commit parent;
    private String message;


    public Commit(String message){
        this.message = message;
        this.createdTime = new java.util.Date();
        try {
            this.parent = Utils.readObject(Repository.HEAD,Commit.class);
            this.objMaps = this.parent.objMaps;
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


    /* TODO: fill in the rest of this class. */
}
