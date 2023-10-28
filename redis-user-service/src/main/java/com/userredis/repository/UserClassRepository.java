package com.userredis.repository;

import com.userredis.entity.UserClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserClassRepository extends JpaRepository<UserClass, Integer> {

}
