package org.demo.api.web.rest;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import org.demo.api.domain.Lannister;
import org.demo.api.domain.Stark;
import org.demo.api.repository.LannisterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.Date;
import javax.inject.Inject;

/**
* REST controller for managing Lannisters.
*/
@RestController
@RequestMapping("/lannisters")
@Api(value="/lannisters", description="Get all Lannisters.")
public class LannisterResource {

    private final Logger log = LoggerFactory.getLogger(LannisterResource.class);

    @Inject
    private LannisterRepository lannisterRepository;

    /**
     * GET /lannisters -> get all Lannisters.
     */
    @RequestMapping(value = "",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get the list of Lannisters", notes = "Get the list of Lannisters", response = Lannister.class)
    public Page<Lannister> getAllLannisters(Pageable p) {
        Long requestReceived = new Date().getTime();

        log.debug("** GET Request to get all Lannisters");
        log.debug("Page number value is: {}", p.getPageNumber());
        log.debug("Page size value is: {}", p.getPageSize());

        Page<Lannister> lannisters = null;
        log.debug("Querying results");
        lannisters = lannisterRepository.findAllByOrderByPosition(p);
        log.debug("Got {} Lannisters", lannisters.getNumberOfElements());

        log.debug("Request took {}ms **", (new Date().getTime() - requestReceived));
        
        return lannisters;
    }
    
    /**
     * GET /lannisters/{id} -> get Lannister by id.
     */
    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get a single Lannister by id", notes = "Get a single Lannister by id", response = Lannister.class)
    public Lannister getLannister(@PathVariable Long id) {
        Long requestReceived = new Date().getTime();

        log.debug("** GET Request to get Lannister by id");

        Lannister lannister = null;
        log.debug("Querying results");
        lannister = lannisterRepository.findByOrderById(id);
        log.debug("Got {} Lannister", lannister.getFirstName());

        log.debug("Request took {}ms **", (new Date().getTime() - requestReceived));
        
        return lannister;
    }
}
