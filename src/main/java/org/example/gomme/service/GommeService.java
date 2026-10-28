package org.example.gomme.service;

import org.example.gomme.dto.AddGommeDTO;
import org.example.gomme.mapper.GommeMapper;
import org.example.gomme.model.Gomme;
import org.example.gomme.model.Season;
import org.example.gomme.repository.GommeRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public Gomme create(AddGommeDTO gommaDTO) {
        Gomme gomma = GommeMapper.toEntity(gommaDTO);
        List<Gomme> gommaCheck = gommeRepository.findByTarga(gomma.getTarga());
        if (!gommaCheck.isEmpty()) {
            for (Gomme g : gommaCheck) {
                if (g.getEdificio().equals(gomma.getEdificio()) && g.getArea().equals(gomma.getArea()) && g.getFila() == gomma.getFila()) {
                    return null;
                }
            }
        }
        return gommeRepository.save(gomma);
    }

    public List<Gomme> createAll(List<Gomme> gomme) {
        return gommeRepository.saveAll(gomme);
    }

    public void delete(Gomme gomma) {
        gommeRepository.delete(gomma);
    }

    public void deleteAll(List<Gomme> gomme) {
        gommeRepository.deleteAll(gomme);
    }
}
