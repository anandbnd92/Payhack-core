package com.gamification.gamification.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.gamification.gamification.Entity.Score;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {
    @Query(value="select id,subject,totalquestions,correctcount,submittedtime,submitteduser from Score",nativeQuery = true)
    List<Map<String, String>> getscore();
}
