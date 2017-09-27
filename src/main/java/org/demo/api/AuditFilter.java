package org.demo.api;

import org.demo.api.domain.ApiTrackRequests;
import org.demo.api.repository.ApiTrackRequestRepository;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.util.*;

@Component
public class AuditFilter implements Filter {

    private final Logger log = LoggerFactory.getLogger(AuditFilter.class);
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");

    @Inject
    ApiTrackRequestRepository apiTrackRequestRepository;

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;

        ApiTrackRequests audit = new ApiTrackRequests();

        try {
            audit.setUuid(UUID.randomUUID().toString());
            audit.setRequestUrl(request.getRequestURI());

            String params = "";
            Map<String, String[]> parameters = request.getParameterMap();
            Iterator iter = parameters.entrySet().iterator();
            if (iter.hasNext()) {
                params = "?";
            }
            while (iter.hasNext()) {
                Map.Entry item = (Map.Entry)iter.next();
                String[] ff = (String[])item.getValue();
                params += item.getKey() + "=" + ff[0];

                iter.remove();
                if (iter.hasNext()) {
                    params += "&";
                }
            }

            audit.setRequestParam(params);
            audit.setRequestStart(DateTime.parse(dateTimeFormatter.print(DateTime.now())));

            if (request.getRemoteHost().equals("127.0.0.1") || request.getRemoteHost().equals("0:0:0:0:0:0:0:1")){
                audit.setIpOrigin("38.104.251.226");
            } else {
                audit.setIpOrigin(request.getRemoteHost());
            }
            String apiKey = request.getHeader("api_key");
            audit.setApiKey((apiKey == null ? "" : apiKey));
            audit.setClientNo("");

            chain.doFilter(request, response);

            audit.setRequestEnd(DateTime.parse(dateTimeFormatter.print(DateTime.now())));
            apiTrackRequestRepository.save(audit);
        } catch (Exception e) {
            log.error(e.toString());
        }
    }

    public void init(FilterConfig filterConfig) {
    }

    public void destroy() {}
}