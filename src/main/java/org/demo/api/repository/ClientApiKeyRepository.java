package org.demo.api.repository;

import org.demo.api.domain.ClientApiKey;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the ClientApiKey entity.
 */
public interface ClientApiKeyRepository extends JpaRepository<ClientApiKey,String>{

}
