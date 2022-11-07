package gitlet;

// TODO: any imports you need here

import java.io.File;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private Date createdTime;
    private String log;
    private List<File> FileList;
    private String message;
    private Map<File, String> objMaps;

    public Commit(String message, File f){
        this.message = message;
        this.objMaps = new HashMap<>();
        objMaps.put(f,Utils.sha1(Utils.readContentsAsString(f)));
    }
    public static void saveFile(File f){

        String f_hash = Utils.sha1(Utils.readContentsAsString(f));
        File obj_dir = Repository.Obj_DIR;
        obj_dir = Utils.join(obj_dir,f_hash.substring(0,2));

        if(!obj_dir.exists()) obj_dir.mkdir();
        File fname = Utils.join(obj_dir, f_hash.substring(2));

        try {
            fname.createNewFile();
        } catch (Exception e){
            Utils.exitWithError("File save with commit fails");
        }
    }


    /* TODO: fill in the rest of this class. */
}
