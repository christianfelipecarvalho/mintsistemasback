package com.mintgestao.Api.Controller;

import com.mintgestao.Application.UseCase.EventoUseCase;
import com.mintgestao.Domain.Entity.Evento;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("gestao/evento")
@Tag(name = "Evento", description = "Endpoint responsável pelo gerenciamento de eventos")
public class EventoController {

    private EventoUseCase eventoUseCase;

    public EventoController(EventoUseCase eventoUseCase) {
        this.eventoUseCase = eventoUseCase;
    }

    @GetMapping("/buscartodos")
    public ResponseEntity obterTodosEventos() {
        try{
            List<Evento> eventos = eventoUseCase.buscarTodos();
            return ResponseEntity.ok(eventos);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/buscarporlocal/{id}")
    public ResponseEntity obterEventosPorLocal(@PathVariable UUID id) {
        try {
            List<Evento> eventos = eventoUseCase.obterEventosPorLocal(id);
            return ResponseEntity.ok(eventos);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/buscarporid/{id}")
    public ResponseEntity obterEventoPorId(@PathVariable UUID id) {
        try {
            Evento evento = eventoUseCase.buscarPorId(id);
            return ResponseEntity.ok(evento);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/buscareventospordata/{id}/{data}")
    public ResponseEntity obterEventosPorData(@PathVariable UUID id, @PathVariable String data) {
        try {
            LocalDate dataFiltro = LocalDate.parse(data);
            List<Evento> eventos = eventoUseCase.buscarEventosPorData(id, dataFiltro);
            return ResponseEntity.ok(eventos);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/criar")
    public ResponseEntity criarEvento(@Valid @RequestBody Evento evento) {
        try {
            Evento novoEvento = eventoUseCase.criar(evento);
            return ResponseEntity.created(null).body(novoEvento);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity atualizarEvento(@Valid @PathVariable UUID id, @RequestBody Evento evento) {
        try {
            eventoUseCase.atualizar(id, evento);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/cancelar/{id}")
    public ResponseEntity cancelarEvento(@Valid @PathVariable UUID id) {
        try {
            eventoUseCase.cancelarEvento(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}