package org.demo.api.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Lannisters
 */
@Entity
@Table(name = "lannisters")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Lannister implements Serializable {

    @Id
    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private Long id;

    @Column(name = "position")
    private String position;

    @Column(name = "description")
    private String description;

}
