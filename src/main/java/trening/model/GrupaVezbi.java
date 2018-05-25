package trening.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity(name="GrupaVezbi")
public class GrupaVezbi {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="Naziv",columnDefinition="VARCHAR(40)")
	private String naziv;
	
	@OneToMany(mappedBy="grVezbi")
	private Set<Vezba> grupaVezbi = new HashSet<Vezba>();

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public Set<Vezba> getGrupaVezbi() {
		return grupaVezbi;
	}

	public void setGrupaVezbi(Set<Vezba> grupaVezbi) {
		this.grupaVezbi = grupaVezbi;
	}

	public GrupaVezbi(String naziv, Set<Vezba> grupaVezbi) {
		super();
		this.naziv = naziv;
		this.grupaVezbi = grupaVezbi;
	}
	
}
