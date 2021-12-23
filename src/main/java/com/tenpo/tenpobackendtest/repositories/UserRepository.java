package com.tenpo.tenpobackendtest.repositories;

import com.tenpo.tenpobackendtest.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUserid(final String user);
}
