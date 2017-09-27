package org.demo.api.domain;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

/**
 * This is the java response object for the ApiKeyRefresh Resource.
 * 
 */

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiKeyRefresh {
	
	private String message;
	
}
