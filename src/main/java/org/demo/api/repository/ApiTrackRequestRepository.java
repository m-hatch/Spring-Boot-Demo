package org.demo.api.repository;

import org.demo.api.domain.ApiTrackRequests;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the ApiTrackRequests entity.
 */
public interface ApiTrackRequestRepository extends JpaRepository<ApiTrackRequests,String>{

}
