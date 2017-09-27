package org.demo.api.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.demo.api.domain.util.CustomDateTimeDeserializer;
import org.demo.api.domain.util.CustomDateTimeSerializer;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "api_track_requests")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ApiTrackRequests implements Serializable {

    @Id
    @Column(name="uuid")
    String uuid;

    @Column(name="request_url")
    String requestUrl;

    @Column(name="request_param")
    String requestParam;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "request_start", nullable = false)
    private DateTime requestStart;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "request_end", nullable = false)
    private DateTime requestEnd;

    @Column(name="ip_origin")
    String ipOrigin;

    @Column(name="api_key")
    String apiKey;

    @Column(name="client_no")
    String clientNo;
}
