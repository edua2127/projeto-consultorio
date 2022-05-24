package br.com.consultorio.controller;

import br.com.consultorio.entity.Paciente;
import br.com.consultorio.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/api/Pacientes")
public class PacienteController {
    @Autowired
    private PacienteService pacienteService;

    //selectByid
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Paciente>> selectById(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(this.pacienteService.selectById(id));
    }
    //selectAll
    @GetMapping()
    public ResponseEntity<Page<Paciente>> selectAll(Pageable pageable) {
        return ResponseEntity.ok().body(this.pacienteService.selectAll(pageable));
    }
    //insert
    @PostMapping
    public ResponseEntity<?> insert(@RequestBody Paciente paciente) {
        try {
            this.pacienteService.insert(paciente);
            return ResponseEntity.ok().body("cadastro do paciente realizado com sucesso");
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    //update
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody Paciente paciente) {
        try {
            this.pacienteService.update(id, paciente);
            return ResponseEntity.ok().body("paciente atualizado com sucessp");
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    //updateStatus
    @PutMapping("/status/{id}")
    public ResponseEntity<?> updateStatus(@PathVariable("id") Long id, @RequestBody Paciente paciente) {
        try {
            this.pacienteService.updateStatusExcluido(id, paciente);
            return ResponseEntity.ok().body("paciente foi desativado com sucesso");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
