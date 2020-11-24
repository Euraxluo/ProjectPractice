import junit.framework.TestCase;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * DoubleArrayTrie
 * PACKAGE_NAME
 * DoubleArrayTrieTest
 * 2020/4/19 22:55
 * author:Euraxluo
 * TODO
 */
public class DoubleArrayTrieTest extends TestCase {
    // TODO: 2020/4/19 have a bug
    public void testSerialize() {
        long start = System.currentTimeMillis();
        String dictPath = "C:\\home\\Repository\\ProjectPractice\\StringMatch\\DoubleArrayTrie\\src\\main\\resources\\data.txt";
        String binPath = "C:\\home\\Repository\\ProjectPractice\\StringMatch\\DoubleArrayTrie\\src\\main\\resources\\data.bin";
        DoubleArrayTrie expect = null;
        try {

            DoubleArrayTrie dat = DoubleArrayTrie.make(dictPath);
            System.out.println("DAT build: " + (System.currentTimeMillis() - start));
            start = System.currentTimeMillis();
//            expect.serialize(binPath);
//            DoubleArrayTrie dat = DoubleArrayTrie.loadDat(binPath);
            for(int i=0;i<10000;i++){
                assertTrue(dat.isWordMatched("默罕默德"));
                assertTrue(dat.isWordMatched("雪山狮子旗"));
                assertTrue(dat.isWordMatched("藏青会"));
                assertTrue(dat.isWordMatched("啦萨"));
                assertTrue(dat.isWordMatched("穆斯林"));
                assertTrue(dat.isWordMatched("伊斯兰"));
                assertTrue(dat.isWordMatched("回民"));
                assertTrue(dat.isWordMatched("默罕默德"));
            }
            System.out.println("DAT select: " + (System.currentTimeMillis() - start));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void testLoadDat() {
    }

    public void testMake() {
        String path = "C:\\home\\Repository\\ProjectPractice\\StringMatch\\DoubleArrayTrie\\src\\main\\resources\\data.txt";
        try {
            List<String> lexicon = Files.lines(Paths.get(path))
                    .map(String::trim).collect(Collectors.toList());
            DoubleArrayTrie dat = DoubleArrayTrie.make(path);
            for (String word:lexicon){
                System.out.println(word);
                dat.isWordMatched(word);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}