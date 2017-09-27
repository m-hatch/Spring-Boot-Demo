package org.demo.api.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * Starks
 */
@Entity
@Table(name = "starks")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Stark implements Serializable {

    @Id
    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private Long id;

    @Column(name = "position")
    private String position;

    @Column(name = "description")
    private String description;
    
    @Column(name = "is_warg")
    private Boolean isWarg;

}