package com.example.buensabor.Bases;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public abstract class BaseServiceImplementation<D, E extends BaseEntity, ID extends Serializable> implements IBaseService<D, ID> {

    protected BaseRepository<E, ID> baseRepository;
    protected BaseMapper<E, D> baseMapper;

    @Override
    @Transactional
    public List<D> findAll() throws Exception {
        try {
            List<E> entities = baseRepository.findAll();
            return entities.stream()
                    .map(baseMapper::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new Exception("Message" + e.getMessage());
        }
    }

    @Override
    @Transactional
    public D findById(ID id) throws Exception {
        try {
            Optional<E> entity = baseRepository.findById(id);
            if (entity.isPresent()) {
                return baseMapper.toDTO(entity.get());
            } else {
                throw new Exception("No se encontró la entidad con ID " + id);
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public D save(D dto) throws Exception {
        try {
            E entity = baseMapper.toEntity(dto);
            E savedEntity = baseRepository.save(entity);
            return baseMapper.toDTO(savedEntity);
        } catch (Exception e) {
            throw new Exception("Message " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public D update(ID id, D dto) throws Exception {
        try {
            Optional<E> entityOptional = baseRepository.findById(id);
            if (entityOptional.isPresent()) {
                E entityToUpdate = baseMapper.toEntity(dto);
                entityToUpdate.setId((Long) id); // asumiendo Long como ID
                E updatedEntity = baseRepository.save(entityToUpdate);
                return baseMapper.toDTO(updatedEntity);
            } else {
                throw new Exception("Message: No se encontró la entidad con ID " + id);
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Override
    @Transactional
    public boolean delete(ID id) throws Exception {
        try {
            Optional<E> entityOptional = baseRepository.findById(id);
            if (entityOptional.isPresent()) {
                E entity = entityOptional.get();
                entity.setIsActive(false);
                baseRepository.save(entity);
                return true;
            } else {
                throw new Exception("No se encontró la entidad con ID " + id);
            }
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
