package training.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import training.model.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {

}
