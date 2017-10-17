package org.demo.api.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * Tournament Winners
 */
@Entity
@Table(name = "winners")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class TournamentWinner implements Serializable{
	
	@Id
    @Column(name = "winner_id")
    private Long winnerId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "event")
    private String event;
    
    @Column(name = "tourney_id")
    private Long tourneyId;
    
}
