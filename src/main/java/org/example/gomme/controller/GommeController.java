package org.example.gomme.controller;

import jakarta.validation.Valid;
import org.example.gomme.dto.AddGommeDTO;
import org.example.gomme.model.Gomme;
import org.example.gomme.model.Season;
import org.example.gomme.service.GommeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gomme")
public class GommeController {

    private final GommeService gommeService;

    public GommeController(GommeService gommeService) {
        this.gommeService = gommeService;
    }

    @GetMapping
    public ResponseEntity<List<Gomme>> findAll() {
        return ResponseEntity.ok(gommeService.findAll());
    }

    @GetMapping("/targa/{targa}")
    public ResponseEntity<List<Gomme>> findByTarga(@PathVariable(name = "targa") String targa) {
        return ResponseEntity.ok(gommeService.findByTarga(targa));
    }

    @GetMapping("/cliente/{cliente}")
    public ResponseEntity<List<Gomme>> findByCliente(@PathVariable String cliente) {
        return ResponseEntity.ok(gommeService.findByCliente(cliente));
    }

    /*
    @GetMapping("/misura")
    public ResponseEntity<List<Gomme>> findByMisura(@RequestParam(name = "misura") String misura) {
        return ResponseEntity.ok(gommeService.findByMisura(misura));
    }
    */

    @GetMapping("/misura/{misura}")
    public ResponseEntity<List<Gomme>> findByMisura(@PathVariable String misura) {
        String misuraVera = misura.replace("-", "/");
        return ResponseEntity.ok(gommeService.findByMisura(misuraVera));
    }

    @GetMapping("/noleggio/{noleggio}")
    public ResponseEntity<List<Gomme>> findByNoleggio(@PathVariable String noleggio) {
        return ResponseEntity.ok(gommeService.findByNoleggio(noleggio));
    }

    @GetMapping("/stagione/{season}")
    public ResponseEntity<List<Gomme>> findBySeason(@PathVariable Season season) {
        return ResponseEntity.ok(gommeService.findBySeason(season));
    }

    @PostMapping
    public ResponseEntity<Gomme> create(@Valid @RequestBody AddGommeDTO gommaDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(gommeService.create(gommaDto));
    }

}
