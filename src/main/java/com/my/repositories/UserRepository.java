package com.my.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.my.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);

	boolean existsByUsername(String username);

	@Query("SELECT COUNT(DISTINCT u.id) FROM User u")
	long countDistinctById();

	@Query("SELECT u FROM User u WHERE NOT EXISTS (SELECT 1 FROM Employee e WHERE e.user.id = u.id)")
	List<User> findUsersWithoutEmployee();
}
