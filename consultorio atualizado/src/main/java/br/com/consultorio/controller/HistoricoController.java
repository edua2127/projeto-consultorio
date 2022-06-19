package br.com.consultorio.controller;

import br.com.consultorio.entity.Historico;
import br.com.consultorio.service.HistorioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/historicos")
public class HistoricoController {
    @Autowired
    private HistorioService historioService;

    @PostMapping
    public ResponseEntity<?> insertHistorico(@RequestBody Historico historico) {
        try {
            historioService.insert(historico);
            return ResponseEntity.ok().body("cadastro realizado com sucesso");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
