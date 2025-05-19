package com.example.buensabor.Bases;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseServiceImplementation <E extends BaseEntity, ID extends Serializable> implements IBaseService<E, ID> {
    
    protected BaseRepository<E, ID> baseRepository;

    @Override
    @Transactional
    public List<E> findAll() throws Exception{
        try {
            List<E> entities = baseRepository.findAll();
            return entities;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public E findById(ID id) throws Exception{
        try {
            Optional<E> entity = baseRepository.findById(id);
            return entity.get();
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public E save(E entity) throws Exception{
        try {
            E entityCreated = baseRepository.save(entity);
            return entityCreated;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public E update(ID id, E entity) throws Exception{
        try {
            Optional<E> entityOptional = baseRepository.findById(id);
            E entityUpdated = entityOptional.get();
            entityUpdated = baseRepository.save(entity);
            return entityUpdated;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public boolean delete(ID id) throws Exception{
        try {
            if (baseRepository.existsById(id)) {
                baseRepository.deleteById(id);
                return true;
            }else{
                throw new Exception();
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

}