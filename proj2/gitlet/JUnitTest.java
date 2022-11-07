package gitlet;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;

public class JUnitTest {
    @Test
    public void Sh1_algoTest(){
        File CWD = new File(System.getProperty("user.dir"));
        File t1 = Utils.join(CWD,"Makefile");
        File t2 = Utils.join(CWD,"Makefiles");
        if(t1.exists()){
            System.out.println(Utils.sha1(Utils.readContents(t1)));
        }else {
            System.out.println("Not exist");
        }
        //
        // Assert.assertEquals("86c1e224085b6d82831fe981e1d31390b403ff38",Utils.sha1(Utils.readContents(t1)));
        Assert.assertEquals(Utils.sha1(Utils.readContents(t1)),Utils.sha1(Utils.readContents(t2)));

    }
    @Test
    public void FileCopyTest(){
        System.out.println(Repository.CWD);
        Repository.add("Makefile");
    }
    @Test
    public void StringBuilderTest(){
        StringBuilder sb = new StringBuilder();
        File CWD = new File(System.getProperty("user.dir"));
        sb.append(CWD.toString());
        System.out.println(sb);
        System.out.println(sb.toString().substring(0,2));
        System.out.println(sb);

    }
    @Test
    public void CommitSaveFileTest(){
        File CWD = new File(System.getProperty("user.dir"));
        File t1 = Utils.join(CWD,"makefile");
        Commit.saveFile(t1);
    }
    @Test
    public void CommitSaveClassTest(){
        File CWD = new File(System.getProperty("user.dir"));
        File t1 = Utils.join(CWD,"makefile");

        Commit c1 = new Commit("messgae",t1);
        File t2 = Utils.join(CWD,"test1");
        Utils.writeObject(t2,c1);

        Commit c2 = Utils.readObject(t2,Commit.class);


    }
}
