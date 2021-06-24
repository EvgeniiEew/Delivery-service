package by.home.serviseDelivery.service.impl;

import by.home.serviseDelivery.domain.Category;
import by.home.serviseDelivery.service.ICategoryService;
import by.home.serviseDelivery.service.interfase.FileService;

import java.util.Map;

public class CategoryService implements ICategoryService {
    private final FileService fileService;

    CategoryService(FileService fileService) {
        this.fileService = fileService;
    }

    @Override
    public Map<Integer, Category> getAll() {
        return null;
    }

    @Override
    public Category save(Category entity) {
        return null;
    }

    @Override
    public Category update(Category entity) {
        return null;
    }

    @Override
    public void deleteEntity(Category entity) {

    }

}
