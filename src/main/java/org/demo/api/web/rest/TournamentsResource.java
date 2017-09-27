package org.demo.api.web.rest;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import org.demo.api.domain.Tournament;
import org.demo.api.domain.TournamentWinner;
import org.demo.api.repository.TournamentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import javax.inject.Inject;
import java.util.Date;

/**
* REST controller for managing Tournaments.
*/
@RestController
@RequestMapping("/tournaments")
@Api(value="/tournaments", description="Operations about Tournaments.")
public class TournamentsResource {

    private final Logger log = LoggerFactory.getLogger(TournamentsResource.class);

    @Inject
    private TournamentRepository tournamentRepository;

    /**
     * GET /tournaments -> get all Tournaments.
     */
    @RequestMapping(value = "",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get the list of Tournaments", notes = "Get the list of Tournaments", response = Tournament.class)
    public Page<Tournament> getAllTourneys(Pageable p) {
        Long requestReceived = new Date().getTime();

        log.debug("** GET Request to get all Tournaments");
        log.debug("Page number value is: {}", p.getPageNumber());
        log.debug("Page size value is: {}", p.getPageSize());

        Page<Tournament> tourneys = null;
        log.debug("Querying results");
        p = new PageRequest(0, (int)tournamentRepository.count());

        tourneys = tournamentRepository.findAllByOrderByPrestigeAsc(p);
        log.debug("Got {} tournaments", tourneys.getNumberOfElements());

        log.debug("Request took {}ms **", (new Date().getTime() - requestReceived));
        
        return tourneys;
    }

    /**
     * GET /tournaments/{tourneyId}/latest -> get all Latest tournament Winners.
     */
    @RequestMapping(value = "/{tourneyId}/latest",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get the list of Latest Tournament Winners", notes = "Get the list of Latest Tournament Winners", response = TournamentWinner.class)
    public Page<TournamentWinner> getLatestTourneyWinners(@PathVariable Long tourneyId, Pageable p) {
        Long requestReceived = new Date().getTime();

        log.debug("** GET Request to get all Latest Tournament Winners");
        log.debug("Page number value is: {}", p.getPageNumber());
        log.debug("Page size value is: {}", p.getPageSize());

        Page<TournamentWinner> latestTourneyWinners = null;
        log.debug("Querying results");
        p = new PageRequest(0, (int)tournamentRepository.count());

        latestTourneyWinners = tournamentRepository.findWinnerByTourneyIdOrderByYearDesc(tourneyId, p);
        log.debug("Got {} winners", latestTourneyWinners.getNumberOfElements());

        log.debug("Request took {}ms **", (new Date().getTime() - requestReceived));

        return latestTourneyWinners;
    }
}
