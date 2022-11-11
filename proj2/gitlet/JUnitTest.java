package gitlet;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

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
    public void addTest(){
        File f1 = Utils.join(Repository.CWD,"Makefile");
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
    public void CommitSaveClassTest() throws IOException {
        File CWD = new File(System.getProperty("user.dir"));
        File t1 = Utils.join(CWD,"makefile");

        Commit c1 = new Commit("messgae");
        File t2 = Utils.join(CWD,"test1");
        System.out.println(t2.getCanonicalPath());
        Utils.writeObject(t2,c1);
        t2.renameTo(Utils.join(CWD,
                Utils.sha1(Utils.readContents(t2))));

        Commit c2 = Utils.readObject(t2,Commit.class);
    }
    @Test
    public void rmTest(){
        File CWD = new File(System.getProperty("user.dir"));
        File t1 = Utils.join(CWD,"Test");
        //Repository.rm("Test");
        Repository.commit("TEst2");
    }
    @Test
    public void logTest(){
        //Repository.init();
        //Repository.add("test1");
        //Repository.commit("Test3");
        //Repository.rm("test1");
        //Repository.commit("Test2");
        //Repository.log();
        Repository.checkout("fbf593e2d89bc99dc8f8753dc19cd7ee498d08a4","test1");
        //Repository.status();
    }
    @Test
    public void saveFileTest() throws IOException{
        File CWD = new File(System.getProperty("user.dir"));
        //File t1 = Utils.join(CWD,"Makefile");
        File t1 = new File("Makefile");
        File t2 = Utils.join(CWD,"Test");
        String s =  t1.getCanonicalPath();
        String s1 = t1.getName();
//        File t3 = Utils.join(CWD,"gitlet","Makefile");
        File t3 = Utils.join("gitlet","Makefile");

        String s2 = t3.getCanonicalPath();
        String s3 = t3.getName();

        System.out.printf("fff");
    }

}