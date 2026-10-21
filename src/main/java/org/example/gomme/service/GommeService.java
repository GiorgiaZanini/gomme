package org.example.gomme.service;

import org.example.gomme.model.Gomme;
import org.example.gomme.model.Season;
import org.example.gomme.repository.GommeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GommeService {

    private final GommeRepository gommeRepository;

    public GommeService(GommeRepository gommeRepository) {
        this.gommeRepository = gommeRepository;
    }

    public List<Gomme> findAll() {
        return gommeRepository.findAll();
    }

    public List<Gomme> findByTarga(String targa) {
        return gommeRepository.findByTarga(targa);
    }

    public List<Gomme> findByCliente(String cliente) {
        return gommeRepository.findByCliente(cliente);
    }

    public List<Gomme> findByMisura(String misura) {
        return gommeRepository.findByMisura(misura);
    }

    public List<Gomme> findByNoleggio(String noleggio) {
        return gommeRepository.findByNoleggio(noleggio);
    }

    public List<Gomme> findBySeason(Season season) {
        return gommeRepository.findBySeason(season);
    }
}
