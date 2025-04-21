package cl.sentra.entity;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Entity
@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Phone {
  /*   @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "phone_seq")
    @SequenceGenerator(name = "phone_seq", sequenceName = "phone_sequence", allocationSize = 1, initialValue = 1)
    private Long id;
*/
    private String number;
    private String citycode;
    private String contrycode;

    /* 
    @ManyToOne
    @JoinColumn(name = "user_id")  
    @JsonBackReference
    private User user;

	public Phone(String number, String citycode, String contrycode, User user) {
		this.number = number;
		this.citycode = citycode;
		this.contrycode = contrycode;
		this.user = user;
	}
*/
    // Getters and setters
}