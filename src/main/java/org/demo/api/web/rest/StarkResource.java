package org.demo.api.web.rest;

import java.util.Date;
import javax.inject.Inject;
import org.demo.api.domain.Stark;
import org.demo.api.repository.StarkRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

/**
* REST controller for managing Starks.
*/
@RestController
@RequestMapping("/starks")
@Api(value="/starks", description="Get all Starks.")
public class StarkResource {

    private final Logger log = LoggerFactory.getLogger(StarkResource.class);

    @Inject
    private StarkRepository starkRepository;

    /**
     * GET /starks -> get all Starks.
     */
    @RequestMapping(value = "",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get the list of Starks", notes = "Get the list of Starks", response = Stark.class)
    public Page<Stark> getAllLannisters(Pageable p) {
        Long requestReceived = new Date().getTime();

        log.debug("** GET Request to get all Lannisters");
        log.debug("Page number value is: {}", p.getPageNumber());
        log.debug("Page size value is: {}", p.getPageSize());

        Page<Stark> starks = null;
        log.debug("Querying results");
        starks = starkRepository.findAllByOrderByPosition(p);
        log.debug("Got {} Lannisters", starks.getNumberOfElements());

        log.debug("Request took {}ms **", (new Date().getTime() - requestReceived));
        
        return starks;
    }
}