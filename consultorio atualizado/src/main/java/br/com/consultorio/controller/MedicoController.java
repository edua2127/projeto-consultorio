package br.com.consultorio.controller;

import br.com.consultorio.entity.Medico;
import br.com.consultorio.service.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/api/medicos")
public class MedicoController {
    @Autowired
    private MedicoService medicoService;

    //select
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Medico>> selectById(@PathVariable Long id) {
        return ResponseEntity.ok().body(this.medicoService.selectById(id));
    }
    //selectAll
    @GetMapping
    public ResponseEntity<Page<Medico>> selectAll(Pageable pageable) {
        return ResponseEntity.ok().body(this.medicoService.selectAll(pageable));
    }
    //insert
    @PostMapping
    public ResponseEntity<?> insert(@RequestBody Medico medico) {
        try {
            this.medicoService.insert(medico);
            return ResponseEntity.ok().body("cadastro do medico foi realizado com sucesso");
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    //update
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody Medico medico) {
        try {
            this.medicoService.updateMedico(id, medico);
            return ResponseEntity.ok().body("o medico foi atualizado com sucesso");
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    //updateStatus
    @PutMapping("/desativar/{id}")
    public ResponseEntity<?> desativar(@PathVariable("id") Long id, @RequestBody Medico medico) {
        try {
            this.medicoService.updateMedico(id, medico);
            return ResponseEntity.ok().body("o medico foi desativado com sucesso");
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
