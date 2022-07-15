package br.com.consultorio.controller;

import br.com.consultorio.entity.Convenio;
import br.com.consultorio.service.ConvenioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@CrossOrigin
@RequestMapping("/api/convenios")
public class ConvenioController {
    @Autowired
    private ConvenioService convenioService;

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Convenio>> select(@PathVariable Long id) {
        return ResponseEntity.ok().body(this.convenioService.selectById(id));
    }

    @GetMapping
    public ResponseEntity<Page<Convenio>> selectAll(Pageable pageable) {
        return ResponseEntity.ok().body(this.convenioService.selectAll(pageable));
    }

    @PostMapping
    public ResponseEntity<?> insert(@RequestBody Convenio convenio) {
        try {
            this.convenioService.insert(convenio);
            return ResponseEntity.ok().body("cadastro realizado com sucesso");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody Convenio convenio) {
        try {
            this.convenioService.update(id, convenio);
            return ResponseEntity.ok().body("atualizacao realizada com sucesso");
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("/desativar/{id}")
    public ResponseEntity<?> updateStatus(@PathVariable("id") Long id, @RequestBody Convenio convenio) {
        try {
            this.convenioService.updateExcluido(id, convenio);
            return ResponseEntity.ok().body("o convenio foi desativado com sucesso");
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
