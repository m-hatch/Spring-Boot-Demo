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
@Table(name = "client_api_keys")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ClientApiKey implements Serializable {

    @Id
    @Column(name = "api_key")
    private String api_key;

    @Column(name = "client_no")
    private String client_no;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "created_date", nullable = false)
    private DateTime createdDate;
}
