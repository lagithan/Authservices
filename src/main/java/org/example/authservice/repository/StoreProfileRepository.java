package org.example.authservice.repository;
import org.example.authservice.entity.StoreProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreProfileRepository extends JpaRepository<StoreProfile,Long> {

}
