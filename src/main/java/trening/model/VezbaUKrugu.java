package trening.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

public class VezbaUKrugu {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long vezUKrug_Id;
	
	@Column(name="BrojPonavljanja")
	private int brojPonavljanja;
	
	@Column(name="Tezina")
	private String tezina; // mozda bi mogao biti 
	
	@OneToMany(mappedBy="vezba_Id")
	private List<Long> vez_id;

	public List<Long> getVez_id() {
		return vez_id;
	}

	public void setVez_id(List<Long> vez_id) {
		this.vez_id = vez_id;
	}

	public int getBrojPonavljanja() {
		return brojPonavljanja;
	}

	public void setBrojPonavljanja(int brojPonavljanja) {
		this.brojPonavljanja = brojPonavljanja;
	}

	public String getTezina() {
		return tezina;
	}

	public void setTezina(String tezina) {
		this.tezina = tezina;
	}

	public VezbaUKrugu(int brojPonavljanja, String tezina, List<Long> vez_id) {
		super();
		this.brojPonavljanja = brojPonavljanja;
		this.tezina = tezina;
		this.vez_id = vez_id;
	}

	
}
