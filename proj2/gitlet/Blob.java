package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;


public class Blob implements Serializable {
    public HashMap<File, String> Maps = new HashMap<>();
    public ArrayList<File> removalMaps = new ArrayList<>();

}
