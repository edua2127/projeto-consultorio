package br.com.consultorio.controller;

import br.com.consultorio.entity.Especialidacao;
import br.com.consultorio.service.EspecialidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/api/especialidades")
public class EspecializacaoController {

    @Autowired
    private EspecialidadeService especialidadeService;

    //select
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Especialidacao>> select(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(this.especialidadeService.selectById(id));
    }
    //selectAll
    @GetMapping
    public ResponseEntity<Page<Especialidacao>> selectAll(Pageable pageable) {
        return ResponseEntity.ok().body(this.especialidadeService.selectAll(pageable));
    }
    //insert
    @PostMapping
    public ResponseEntity<?> insert(@RequestBody Especialidacao especialidacao) {
        try {
            this.especialidadeService.insertEspecialidade(especialidacao);
            return ResponseEntity.ok().body("cadastro da especialidade realizado com sucesso");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    //update
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody Especialidacao especialidacao) {
        try {
            this.especialidadeService.updateEspecialidade(id, especialidacao);
            return ResponseEntity.ok().body("Atualizacao da especialidade realizada com sucesso");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    //updateStatus
    @PutMapping("/status/{id}")
    public ResponseEntity<?> updateStatus(@PathVariable("id") Long id, @RequestBody Especialidacao especialidade) {
        try {
            this.especialidadeService.updateExcluido(id, especialidade);
            return ResponseEntity.ok().body("especialidade desativada com sucesso");
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
