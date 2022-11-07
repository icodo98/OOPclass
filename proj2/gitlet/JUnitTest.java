package gitlet;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class JUnitTest {
    @Test
    public void Sh1_algoTest(){
        File CWD = new File(System.getProperty("user.dir"));
        File t1 = Utils.join(CWD,"Makefile");
        if(t1.exists()){
            System.out.println(Utils.sha1(Utils.sha1(Utils.readContents(t1))));
        }else {
            System.out.println("Not exist");
        }
        Assert.assertEquals("86c1e224085b6d82831fe981e1d31390b403ff38",Utils.sha1(Utils.sha1(Utils.readContents(t1))));

    }
    @Test
    public void FileCopyTest(){
        System.out.println(Repository.CWD);
        Repository.add("Makefile");
    }
    @Test
    public void readContentswithDIRTest(){
        File CWD = new File(System.getProperty("user.dir"));
        List<String> filesinCWD = Utils.plainFilenamesIn(CWD);
        List<File> flist = new ArrayList<>();
        File Tfile = Utils.join(CWD,"Test");
        for (String f: filesinCWD) {
            File cuf = Utils.join(CWD,f);
            //Utils.writeObject(Tfile,cuf);
        }
        File T = Utils.readObject(Tfile,File.class);


    }

}
