package gitlet;

// TODO: any imports you need here

import java.io.File;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

/** Represents a gitlet commit object.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author TODO
 */
public class Commit {
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
    public Commit(String message){
        this.message = message;


    }


    /* TODO: fill in the rest of this class. */
}
