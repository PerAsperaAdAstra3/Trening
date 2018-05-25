package trening.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

public class Zadatak {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToMany(mappedBy="grVezbi")
	private Set<GrupaVezbi> grupaVezbi = new HashSet<GrupaVezbi>();
	
	@Column(name="Kruzni")
	private boolean kruzni;
	
	
}
