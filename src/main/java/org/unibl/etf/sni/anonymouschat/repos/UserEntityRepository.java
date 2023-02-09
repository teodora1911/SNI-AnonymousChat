package org.unibl.etf.sni.anonymouschat.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.unibl.etf.sni.anonymouschat.models.entities.UserEntity;

import java.util.Optional;

public interface UserEntityRepository extends JpaRepository<UserEntity, Integer> {

    Optional<UserEntity> findByUsername(String username);

    @Query("select u.username from UserEntity u where u.id= :id")
    String getUsernameById(@Param("id") Integer id);
//    @Query("select u.publicKeyString from UserEntity u where u.username= :username")
//    String getPublicKeyByUsername(@Param("username") String username);
}
