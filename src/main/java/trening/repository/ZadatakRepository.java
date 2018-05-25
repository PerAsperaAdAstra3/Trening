package trening.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import trening.model.Zadatak;

public interface ZadatakRepository extends JpaRepository<Zadatak, Long> {

}
