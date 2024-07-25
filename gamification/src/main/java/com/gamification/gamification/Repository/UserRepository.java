package com.gamification.gamification.Repository;

import com.gamification.gamification.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    @Query(value="select username from users  where username=:username",nativeQuery = true)
    String isuserExist(String username);
}
