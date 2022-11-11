package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.TreeMap;


public class Blob implements Serializable {
    public TreeMap<File, String> Maps = new TreeMap<>();
    public ArrayList<File> removalMaps = new ArrayList<>();
    @Override
    public String toString(){
        StringBuilder returnSB = new StringBuilder();
        return returnSB.toString();
    }

}
