package org.example.gomme.repository;

import org.example.gomme.model.Gomme;
import org.example.gomme.model.Season;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GommeRepository extends JpaRepository<Gomme, Long> {

    List<Gomme> findByTarga(String targa);

    List<Gomme> findByCliente(String cliente);

    List<Gomme> findByMisura(String misura);

    List<Gomme> findByNoleggio(String noleggio);

    List<Gomme> findBySeason(Season season);

    void deleteByTarga(String targa);
}