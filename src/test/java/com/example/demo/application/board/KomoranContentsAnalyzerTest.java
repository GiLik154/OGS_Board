package com.example.demo.application.board;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class KomoranContentsAnalyzerTest {
    private final KomoranContentsAnalyzer komoranContentsAnalyzer;

    @Autowired
    public KomoranContentsAnalyzerTest(KomoranContentsAnalyzer komoranContentsAnalyzer) {
        this.komoranContentsAnalyzer = komoranContentsAnalyzer;
    }

    @Test
    void 형태소_분석기_정상작동() {
        String testString = "아버지가방에들어가시다";

        List<String> stringList = komoranContentsAnalyzer.analyze(testString);

        assertEquals(2, stringList.size());
        assertEquals("아버지", stringList.get(0));
        assertEquals("가방", stringList.get(1));
    }

    @Test
    void 형태소_분석기_정상작동_영어분석() {
        String testString = "아버지Bag에들어가시다";

        List<String> stringList = komoranContentsAnalyzer.analyze(testString);

        assertEquals(2, stringList.size());
        assertEquals("아버지", stringList.get(0));
        assertEquals("Bag", stringList.get(1));
    }

    @Test
    void 형태소_분석기_정상작동_유저사전() {
        String testString = "글로벌널리지";

        List<String> stringList = komoranContentsAnalyzer.analyze(testString);

        assertEquals(1, stringList.size());
        assertEquals("글로벌널리지", stringList.get(0));
    }

    @Test
    void 형태소_분석기_정상작동_없는단어() {
        String testString = "글룽강";

        List<String> stringList = komoranContentsAnalyzer.analyze(testString);

        assertTrue(stringList.isEmpty());
    }


    @Test
    void 형태소_분석기_String_Null() {
        String testString = "";

        List<String> stringList = komoranContentsAnalyzer.analyze(testString);

        assertTrue(stringList.isEmpty());
    }

    @Test
    void 형태소_분석기_String_1글자() {
        String testString = "굴";

        List<String> stringList = komoranContentsAnalyzer.analyze(testString);

        assertTrue(stringList.isEmpty());
    }
}