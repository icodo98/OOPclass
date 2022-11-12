package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.*;


public class Blob implements Serializable {
    public TreeMap<File, String> Maps = new TreeMap<>();
    public ArrayList<File> removalMaps = new ArrayList<>();
    @Override
    public String toString(){
        StringBuilder returnSB = new StringBuilder("=== Staged Files ===\n");
        List<String> fileNames = new ArrayList<>();
        List<String> removedFiles = new ArrayList<>();
        for (File f: this.Maps.keySet()
             ) {
            fileNames.add(f.toString());
        }
        for (File f: this.removalMaps
             ) {
            removedFiles.add(f.toString());
        }
        fileNames.sort(Comparator.naturalOrder());
        removedFiles.sort(Comparator.naturalOrder());
        for (String Name: fileNames
             ) {
            returnSB.append(Name);
            returnSB.append("\n");
        }
        returnSB.append("=== Removed Files ===\n");
        for (String Name: removedFiles
             ) {
            returnSB.append(Name);
            returnSB.append("\n");
        }

        return returnSB.toString();
    }
}
