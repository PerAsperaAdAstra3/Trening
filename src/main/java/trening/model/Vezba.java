package trening.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name="Vezba")
public class Vezba {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long vezba_Id;
	
	@Column(name="Naziv", columnDefinition="VARCHAR(40)")
	private String naziv;
	
	@Column(name="Opis", columnDefinition="VARCHAR(100)")
	private String opis;

	@ManyToOne
	@JoinColumn(name="grVezbi_id", referencedColumnName="vezba_Id", nullable=false)
	private GrupaVezbi grVezbi;
	
	@JoinColumn(name = "vezba_Id")
	public Long getId() {
		return vezba_Id;
	}

	public void setId(Long id) {
		this.vezba_Id = id;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getOpis() {
		return opis;
	}

	public void setOpis(String opis) {
		this.opis = opis;
	}

	public Vezba(String naziv, String opis) {
		super();
		this.naziv = naziv;
		this.opis = opis;
	}
	
}
