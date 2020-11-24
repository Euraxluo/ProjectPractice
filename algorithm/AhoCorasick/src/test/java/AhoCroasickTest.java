import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

/**
 * StringMatch
 * PACKAGE_NAME
 * AhoCroasickTest
 * 2020/4/11 18:22
 * author:Euraxluo
 * TODO
 */
public class AhoCroasickTest {

    @Test
    public void startOfChurchillSpeech()
    {
        AhoCroasick trie = new AhoCroasick();
        trie.insert("T");
        trie.insert("u");
        trie.insert("ur");
        trie.insert("r");
        trie.insert("urn");
        trie.insert("ni");
        trie.insert("i");
        trie.insert("in");
        trie.insert("n");
        trie.insert("urning");
        trie.insert("urningg");
        Collection<MatchRes> outputs =  trie.outPut("Turningg");
        outputs.forEach(m->System.out.println(m.toString()));
    }
    @Test
    public void caseInsensitive()
    {
        AhoCroasick trie = new AhoCroasick().IgnoreCase();
        trie.insert("turning");
        trie.insert("once");
        trie.insert("again");
        trie.insert("börkü");
        Collection<MatchRes> outputs = trie.parseText("TurninG OnCe AgAiN BÖRKÜ");
        outputs.forEach(m->System.out.println(m.toString()));
    }
    @Test
    public void nonOverlapping()
    {
        AhoCroasick trie = new AhoCroasick();
        trie.addKeyword("ab");
        trie.addKeyword("cba");
        trie.addKeyword("ababc");
        Collection<MatchRes> outputs= trie.parseText("ababcbab");
        outputs.forEach(m->System.out.println(m.toString()));
    }
    @Test
    public void longAndShortOverlappingMatch() {
        AhoCroasick trie = new AhoCroasick();
        trie.addKeyword("he");
        trie.addKeyword("hehehehe");
        Collection<MatchRes> outputs= trie.parseText("hehehehehe");
        outputs.forEach(m->System.out.println(m.toString()));
    }
    @Test
    public void recipes()
    {
        AhoCroasick trie = new AhoCroasick();
        trie.addKeyword("veal");
        trie.addKeyword("cauliflower");
        trie.addKeyword("broccoli");
        trie.addKeyword("tomatoes");
        Collection<MatchRes> outputs = trie.parseText("2 cauliflowers, 3 tomatoes, 4 slices of veal, 100g broccoli");
        outputs.forEach(m->System.out.println(m.toString()));
    }
    @Test
    public void misleadingTest()
    {
        AhoCroasick trie = new AhoCroasick();
        trie.addKeyword("hers");
        Collection<MatchRes> outputs = trie.parseText("h he her hers");
        outputs.forEach(m->System.out.println(m.toString()));
    }
    @Test
    public void ushersTest()
    {
        AhoCroasick trie = new AhoCroasick();
        trie.addKeyword("hers");
        trie.addKeyword("his");
        trie.addKeyword("she");
        trie.addKeyword("he");
        Collection<MatchRes> outputs = trie.parseText("ushers");
        outputs.forEach(m->System.out.println(m.toString()));
    }

    @Test
    public void keywordAndTextAreTheSame()
    {
        AhoCroasick trie = new AhoCroasick();
        trie.addKeyword("abc");
        Collection<MatchRes> outputs = trie.parseText("abc");
        outputs.forEach(m->System.out.println(m.toString()));
    }

    @Test
    public void textIsLongerThanKeyword()
    {
        AhoCroasick trie = new AhoCroasick();
        trie.addKeyword("abc");
        Collection<MatchRes> outputs = trie.parseText(" abc");
        outputs.forEach(m->System.out.println(m.toString()));
    }

    @Test
    public void variousKeywordsOneMatch()
    {
        AhoCroasick trie = new AhoCroasick();
        trie.addKeyword("abc");
        trie.addKeyword("bcd");
        trie.addKeyword("cde");
        Collection<MatchRes> outputs = trie.parseText("bcd");
        outputs.forEach(m->System.out.println(m.toString()));
    }

    @Test
    public void longText()
    {
        AhoCroasick trie = new AhoCroasick().IgnoreCase();
        trie.insert("sun");
        trie.insert("reef");
        trie.insert("lives");
        trie.insert("plants");
        trie.insert("animals");
        trie.insert("changing");
        Collection<MatchRes> outputs = trie.parseText("The first film explores the complex structure of the coral reef itself and the wildlife that lives on it． So vast it is visible from space，the reef is actually built by tiny animals in partnership with microscopic plants． It is a place full of surprises that is always changing， responding to the rhythms of weather， full tide， sun and moon");
        outputs.forEach(m->System.out.println(m.toString()));
    }

    @Test
    public  void dengxiaoxingtest(){
        long start = System.currentTimeMillis();
        String dictPath = "C:\\home\\Repository\\ProjectPractice\\StringMatch\\DoubleArrayTrie\\src\\main\\resources\\data.txt";
        BufferedReader br = null;
        try {
            AhoCroasick trie = new AhoCroasick().IgnoreCase();
            br = new BufferedReader(new InputStreamReader(new FileInputStream(dictPath)));
            List<String> lexicon = br.lines().map(String::trim).collect(Collectors.toList());
            for (String word : lexicon){
                trie.insert(word);
            }
            System.out.println("AC build: " + (System.currentTimeMillis() - start));
            start = System.currentTimeMillis();
            for(int i=0;i<10000;i++){
                Collection<MatchRes> outputs = trie.parseText("默罕默德雪山狮子旗藏青会啦萨穆斯林伊斯兰回民默罕默德");
//                outputs.forEach(m->System.out.println(m.toString()));
//                assertTrue(outputs.size()==8);
            }
            System.out.println("AC select: " + (System.currentTimeMillis() - start));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


}