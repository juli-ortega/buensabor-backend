package com.example.buensabor.Bases;

import java.io.Serializable;
import java.util.List;

public interface IBaseService<D, ID extends Serializable> {
    List<D> findAll() throws Exception;
    D findById(ID id) throws Exception;
    D save(D dto) throws Exception;
    D update(ID id, D dto) throws Exception;
    boolean delete(ID id) throws Exception;
}

