package br.com.consultorio.controller;

import br.com.consultorio.entity.Agenda;
import br.com.consultorio.entity.Secretaria;
import br.com.consultorio.entity.StatusAgendamento;
import br.com.consultorio.service.AgendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/api/agendas")
public class AgendaController {
    @Autowired
    private AgendaService agendaService;

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Agenda>> selectById(@PathVariable Long id) {
        return ResponseEntity.ok().body(this.agendaService.selectById(id));
    }

    @GetMapping
    public ResponseEntity<Page<Agenda>> selectAll(Pageable pageable) {
        return ResponseEntity.ok().body(this.agendaService.selectAll(pageable));
    }

    @PostMapping
    public ResponseEntity<?> insert(@RequestBody Agenda agenda, @RequestBody Secretaria secretaria) {
        try {
            this.agendaService.insert(agenda, secretaria);
            return ResponseEntity.ok().body("agenda cadastrada com sucesso");
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody Agenda agenda) {
        try {
            this.agendaService.update(id, agenda);
            return ResponseEntity.ok().body("agenda atualizada com sucesso");
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/desativar/{id}")
    public ResponseEntity<?> desativar(@PathVariable("id") Long id, @RequestBody Agenda agenda) {
        try {
            this.agendaService.excluido(id, agenda);
            return ResponseEntity.ok().body("agendamento desativado com sucesso");
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PutMapping("/status/{id}")
    public ResponseEntity<?> updateStatus(
            @PathVariable("id") Long idAgendamento,
            @RequestBody Agenda agenda,
            @RequestBody Secretaria secretaria,
            @RequestBody StatusAgendamento statusAgendamento) {
        try {
            this.agendaService.updateStatus(agenda, secretaria, statusAgendamento);
            return ResponseEntity.ok().body("a mudanca de status foi realizada com sucesso");
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
}
