package gitlet;

// TODO: any imports you need here

import java.sql.Time;
import java.util.Date; // TODO: You'll likely use this in this class
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
    private Time createdTime;
    private String log;
    private List<String> FileList;
    private class Stage {

    }


    /** The message of this Commit. */
    private String message;
    public void Commit(String message){
        this.message = message;
    }


    /* TODO: fill in the rest of this class. */
}
