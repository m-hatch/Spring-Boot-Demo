package org.demo.api.web.rest;

import java.util.Date;
import javax.inject.Inject;
import org.demo.api.SecurityFilter;
import org.demo.api.domain.ApiKeyRefresh;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;

/**
* API interface for refreshing the API Key ArrayList.
*/
@RestController
@RequestMapping("/key_refresh")
@Api(value="/key_refresh", description="Refresh API Key list.")
public class ApiKeyRefreshResource {
	
	private final Logger log = LoggerFactory.getLogger(ApiKeyRefreshResource.class);

    @Inject
    private SecurityFilter securityFilter;
    
    @RequestMapping(value = "",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Refresh API Key list", notes = "Refresh API Key list")
    public ApiKeyRefresh getApiKeyRefresh(
    		@RequestHeader(value="admin_token", defaultValue="") String adminToken) {
        Long requestReceived = new Date().getTime();

        log.debug("** Refreshing API Key list");

        // refresh API Keys
        ApiKeyRefresh response = new ApiKeyRefresh();
        
        // check if admin_token value is valid
        if (securityFilter.isAdmin(adminToken)) {
        	securityFilter.loadApiKeys();
            response.setMessage("API Keys refreshed.");
        } else {
        	response.setMessage("API Keys not refreshed.");
        }
		
		log.debug("Request took {}ms **", (new Date().getTime() - requestReceived));
		
        return response;
    }
}
