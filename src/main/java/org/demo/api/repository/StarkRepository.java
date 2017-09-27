package org.demo.api.repository;

import org.demo.api.domain.Stark;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the house Stark.
 */
public interface StarkRepository extends JpaRepository<Stark,String>{

    Page<Stark> findAllByOrderByPosition(Pageable p);
}