package com.sam.api.db.repository;

import com.sam.api.db.entity.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends BaseRepository<User, Long> {

    Optional<User> findByUsername(String username);

}
