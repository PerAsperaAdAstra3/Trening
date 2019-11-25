package training.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.data.annotation.Id;

@Entity(name = "Package")
public class Package {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Column(name = "itemsList")
	private List<String> itemsList;
		
	//	@Column(name = "")
	@OneToMany(mappedBy = "exerciseGroup")
	private 
}
