package com.example.jwtjdbcuserdetailsservicecustomendpoint.repositories;

import com.example.jwtjdbcuserdetailsservicecustomendpoint.entities.Authority;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorityRepository extends CrudRepository<Authority, Long> {

    Optional<Authority> findByName(String name);

}
