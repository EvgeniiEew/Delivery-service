package by.home.serviseDelivery.service.interfase;


import by.home.serviseDelivery.domain.Shop;

import java.util.List;
import java.util.Map;

public interface CRUDService<ID, T> {
    Map<ID, T> getAll();

    T save(T entity);

    T update(T entity);

    void deleteEntity(T entity);

}
