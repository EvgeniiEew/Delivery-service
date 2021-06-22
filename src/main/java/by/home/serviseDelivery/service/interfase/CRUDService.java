package by.home.serviseDelivery.service.interfase;

import java.util.List;

public interface CRUDService<T> {
    List<T> getAll();

    T save(T entity);

    T update(T entity);

    void deleteEntity(T entity);

//    void deleteById(Integer ID);
}
