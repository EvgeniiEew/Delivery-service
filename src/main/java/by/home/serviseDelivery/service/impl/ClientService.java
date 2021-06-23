package by.home.serviseDelivery.service.impl;

import by.home.serviseDelivery.domain.Client;
import by.home.serviseDelivery.service.IClientService;
import by.home.serviseDelivery.service.interfase.FileService;

import java.util.List;

public class ClientService implements IClientService {
    private final FileService fileService;

    ClientService(FileService fileService) {
        this.fileService = fileService;
    }

    @Override
    public List<Client> getAll() {
        return null;
    }

    @Override
    public Client save(Client entity) {
        return null;
    }

    @Override
    public Client update(Client entity) {
        return null;
    }

    @Override
    public void deleteEntity(Client entity) {

    }
}
