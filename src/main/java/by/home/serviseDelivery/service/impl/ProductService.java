package by.home.serviseDelivery.service.impl;

import by.home.serviseDelivery.domain.Product;
import by.home.serviseDelivery.service.IProductService;
import by.home.serviseDelivery.service.interfase.FileService;

import java.util.Map;

public class ProductService implements IProductService {
    private final FileService fileService;

    ProductService(FileService fileService) {
        this.fileService = fileService;
    }

    @Override
    public Map<Integer, Product> getAll() {
        return null;
    }

    @Override
    public Product save(Product entity) {
        return null;
    }

    @Override
    public Product update(Product entity) {
        return null;
    }

    @Override
    public void deleteEntity(Product entity) {

    }
}
