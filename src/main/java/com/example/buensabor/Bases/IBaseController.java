package com.example.buensabor.Bases;

import java.io.Serializable;

import org.springframework.http.ResponseEntity;

public interface IBaseController <D, ID extends Serializable> {
    ResponseEntity<?> getAll();
    ResponseEntity<?> getById(ID id);
    ResponseEntity<?> save(D dto);
    ResponseEntity<?> update(ID id, D dto);
    ResponseEntity<?> delete(ID id);
}