package org.demo.api.repository;

import org.demo.api.domain.Lannister;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the house Lannister.
 */
public interface LannisterRepository extends JpaRepository<Lannister, Long>{

	Lannister findByOrderById(Long id);
	Page<Lannister> findAllByOrderByPosition(Pageable p);
}


