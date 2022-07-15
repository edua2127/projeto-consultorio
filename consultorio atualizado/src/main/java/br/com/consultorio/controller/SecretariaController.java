package br.com.consultorio.controller;

import br.com.consultorio.entity.Secretaria;
import br.com.consultorio.service.SecretariaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
@CrossOrigin
@Controller
@RequestMapping("/api/secretarias")
public class SecretariaController {
    @Autowired
    private SecretariaService secretariaService;

    //selectByid
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Secretaria>> selectById(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(this.secretariaService.selectById(id));
    }
    //selecall
    @GetMapping
    public ResponseEntity<Page<Secretaria>> selectAll(Pageable pageable) {
        return ResponseEntity.ok().body(this.secretariaService.selectAll(pageable));
    }
    //insert
    @PostMapping
    public ResponseEntity<?> insert(@RequestBody Secretaria secretaria) {
        try {
            this.secretariaService.insertSecretaria(secretaria);
            return ResponseEntity.ok().body("o cadastro da secretaria foi realizado com sucesso");
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    //update
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody Secretaria secretaria) {
        try {
            this.secretariaService.updateSecretaria(id, secretaria);
            return ResponseEntity.ok().body("a atualizacao da secretaria foi realizada com sucesso");
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    //updatestatus
    @PutMapping("/desativar/{id}")
    public ResponseEntity<?> desativar(@PathVariable("id") Long id, @RequestBody Secretaria secretaria) {
        try {
            this.secretariaService.updateExcluido(id, secretaria);
            return ResponseEntity.ok().body("a Secretaria foi desativada com sucesso");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
