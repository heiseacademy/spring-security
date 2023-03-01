package de.trion.training.auth;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
   void deleteByUsername(String username);

   boolean existsByUsername(String username);

   UserEntity findByUsernameIgnoreCase(String username);
}
