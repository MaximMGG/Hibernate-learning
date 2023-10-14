package com.hibernate.project.application;

import static org.testng.Assert.*;

import java.util.Map;

import org.testng.annotations.Test;

public class FindAllRankingTest {
    RankingService service = new HibernateRankingService();

    @Test
    public void findAllRankingsEmptySet() {
        assertEquals(service.getRankingFor("Nobody", "Java"), 0);
        assertEquals(service.getRankingFor("Nobody", "Python"), 0);
        Map<String ,Integer> rankings = service.findRankingFor("Nobody");
        assertEquals(rankings.size(), 0);
    }

    @Test
    public void findAllRanking() {
        assertEquals(service.getRankingFor("Nobody", "Java"), 0);
        assertEquals(service.getRankingFor("Nobody", "Python"), 0);
        service.addRanking("Somebody", "Nobody", "Java" , 9);
        service.addRanking("Somebody", "Nobody", "Java" , 7);
        service.addRanking("Somebody", "Nobody", "Python" , 7);
        service.addRanking("Somebody", "Nobody", "Python" , 5);
        Map<String ,Integer> rankings = service.findRankingFor("Somebody");
        assertEquals(rankings.size(), 2);
        assertNotNull(rankings.get("Java"));
        assertEquals(rankings.get("Java"), Integer.valueOf(8));
        assertNotNull(rankings.get("Python"));
        assertEquals(rankings.get("Python"), Integer.valueOf(6));
    }
}
