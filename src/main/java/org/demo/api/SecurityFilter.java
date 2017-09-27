package org.demo.api;

import org.demo.api.domain.ClientApiKey;
import org.demo.api.repository.ClientApiKeyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@PropertySource("classpath:application.properties")
public class SecurityFilter implements Filter {

    private final Logger log = LoggerFactory.getLogger(SecurityFilter.class);
    private List<String> apiKeys = new ArrayList<String>();

    @Autowired
    Environment env;

    @Inject
    ClientApiKeyRepository clientApiKeyRepository;

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        try {
            Boolean useApiKeyValidation = Boolean.valueOf(env.getProperty("useApiKeyValidation"));
            log.debug("useApiKeyValidation: {}", useApiKeyValidation);

            Boolean swaggerDoc = (request.getRequestURI().startsWith(request.getContextPath() + "/api") ? true : false);
            log.debug("Is a swagger docs requests: {}", swaggerDoc);

            // If the URL is a resource for Swagger Documentation, do not block it
            if (useApiKeyValidation && !swaggerDoc) {
                String apiKey = request.getHeader("api_key");
                log.debug("api_key value is: {}", apiKey);

                if (apiKeys == null || apiKeys.size() == 0 || apiKey == null || apiKeys.size() == 0) {
                    log.error("No API key loaded! Cannot validate any API requests.");
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("{\"statusCode\":403,\"message\":\"API key is missing.\"}");
                    response.getWriter().flush();
                    response.getWriter().close();
                    return;
                }

                if (!apiKeys.contains(apiKey)) {
                    log.debug("API key: " + apiKey + " is invalid.");
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    response.getWriter().write("{\"statusCode\":403,\"message\":\"API key is invalid.\"}");
                    response.getWriter().flush();
                    response.getWriter().close();
                    return;
                } else {
                    log.debug("API key: " + apiKey + " is valid.");
                }
            }
        } catch (Exception e) {
            log.debug("Error in the doFiler.");
            log.error(e.toString());
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write("{\"statusCode\":500,\"message\":\"Cannot verify API key.\"}");
            response.getWriter().flush();
            response.getWriter().close();
            return;
        }

        chain.doFilter(req, resp);
    }

    public void init(FilterConfig filterConfig) {
        try {
            Boolean useApiKeyValidation = Boolean.valueOf(env.getProperty("useApiKeyValidation"));
            log.debug("useApiKeyValidation: {}", useApiKeyValidation);

            if (useApiKeyValidation) {
                loadApiKeys();
            }
        } catch (Exception e) {
            log.debug("Error while loading the api_key list.");
            log.error(e.toString());
        }
    }

    public void destroy() {}
    
    public void loadApiKeys() {
    	log.debug("Clearing current API keys.");
    	apiKeys.clear();
    	
    	log.debug("Loading the API keys.");
        List<ClientApiKey> clientApiKeys = clientApiKeyRepository.findAll();
        for (ClientApiKey clientApiKey : clientApiKeys) {
            apiKeys.add(0, clientApiKey.getApi_key().trim());
        }

        if (apiKeys != null && apiKeys.size() > 0) {
            log.debug("Found " + apiKeys.size() + " API keys.");
        } else {
            log.error("No API Keys found!");
        }
    }
    
    public boolean isAdmin(String token) {
    	String adminKey = env.getProperty("adminTokenValue");
    	log.debug("** Checking admin_token");
    	//log.debug("Properties admin_token is: {}", adminKey);
    	
    	String adminToken = token;
    	log.debug("admin_token value is: {}", adminToken);
    	
    	if (adminToken.equalsIgnoreCase(adminKey)) {
    		log.debug("admin_token is valid.");
    		return true;
    	} else {
    		log.debug("admin_token is  not valid.");
    		return false;
    	}
    }
}