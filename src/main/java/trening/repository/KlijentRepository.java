package trening.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import trening.model.Klijent;

public interface KlijentRepository extends JpaRepository<Klijent, Long> {

}
