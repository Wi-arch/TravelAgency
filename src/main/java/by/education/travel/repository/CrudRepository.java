package by.education.travel.repository;

import by.education.travel.exception.DAOException;

import java.util.List;

public interface CrudRepository<Entity, ID> {

    Entity save(Entity entity) throws DAOException;

    Entity getById(ID id) throws DAOException;

    List<Entity> getAll() throws DAOException;

    void delete(Entity entity) throws DAOException;

    void update(Entity entity) throws DAOException;
}
