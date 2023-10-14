package com.hibernate.project.application;

import java.util.Map;

import com.hibernate.project.Person;

public interface RankingService {
    
    int getRankingFor(String subject, String skill);

    void addRanking(String subject, String observer, String skill, int ranking);

    void updateRanking(String subject, String observer, String skill, int ranking);

    void removeRanking(String subject, String observer, String skill);

    Map<String, Integer> findRankingFor(String skill);

    Person findBestPersonFor(String subject);
}
