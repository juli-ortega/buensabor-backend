package com.example.buensabor.Bases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public class BaseControllerImplementation<D, S extends BaseServiceImplementation<D, ?, Long>> implements IBaseController<D, Long> {

    @Autowired
    protected S service;

    @GetMapping("")
    public ResponseEntity<?> getAll() {
        try {
            return ResponseEntity.ok(service.findAll());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error al obtener los recursos");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(service.findById(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el recurso");
        }
    }

    @PostMapping("")
    public ResponseEntity<?> save(@RequestBody D dto) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.save(dto));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al intentar guardar el recurso: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody D dto) {
        try {
            return ResponseEntity.ok(service.update(id, dto));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al intentar actualizar el recurso");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            boolean deleted = service.delete(id);
            if (deleted) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Recurso no encontrado");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al intentar eliminar el recurso");
        }
    }
}
