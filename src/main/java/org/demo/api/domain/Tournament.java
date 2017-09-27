package org.demo.api.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Tournaments
 */
@Entity
@Table(name = "tournaments")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Tournament implements Serializable {

    @Id
    @Column(name = "tourney_id")
    private Long tourneyId;

    @Column(name = "name")
    private String name;

    @Column(name = "events")
    private List<String> events;

    @Column(name = "winner")
    private String winner;
    
    @Column(name = "prestige")
    private String prestige;
    
    @Column(name = "year")
    private Long year;

}
