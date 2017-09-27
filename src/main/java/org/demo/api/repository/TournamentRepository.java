package org.demo.api.repository;

import org.demo.api.domain.Tournament;
import org.demo.api.domain.TournamentWinner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the Tournament entity.
 */
public interface TournamentRepository extends JpaRepository<Tournament,String>{

    Page<Tournament> findAllByOrderByPrestigeAsc(Pageable p);
    Page<TournamentWinner> findWinnerByTourneyIdOrderByYearDesc(Long tourneyId, Pageable p);
    
}


