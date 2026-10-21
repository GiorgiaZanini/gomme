package org.example.gomme.controller;

import org.example.gomme.service.GommeService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/gomme")
public class GommeController {

    private final GommeService gommeService;

    public GommeController(GommeService gommeService) {
        this.gommeService = gommeService;
    }
}
