package by.home.serviseDelivery.service.impl;

import by.home.serviseDelivery.domain.Client;
import by.home.serviseDelivery.domain.Order;
import by.home.serviseDelivery.service.IClientService;
import by.home.serviseDelivery.service.interfase.FileService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ClientService implements IClientService {
    private final FileService<Client> fileService;
    private final String CLIENT = "client";

    public ClientService(FileService<Client> fileService) {
        this.fileService = fileService;
    }

    @Override
    public Map<Integer, Client> getAll() {
        List<Client> shopList = (List<Client>) fileService.readFile(CLIENT, new Client());
        if (shopList != null) {
            return shopList.stream().collect(Collectors.toMap(Client::getId, client -> client));
        }
        return new HashMap<Integer, Client>();
    }

    private List<Client> getListClient() {
        return (List<Client>) fileService.readFile(CLIENT, new Client());
    }

    @Override
    public Client save(Client entity) {
        Map<Integer, Client> clientMap = getAll();
        List<Client> listClient = new ArrayList<>();
        if (clientMap != null) {
            clientMap.put(entity.getId(), entity);
            List<Client> resultList = new ArrayList<>(clientMap.values());
            fileService.writeFile(resultList, CLIENT);
            return entity;
        }
        listClient.add(entity);
        fileService.writeFile(listClient, CLIENT);
        return entity;
    }

    @Override
    public Client update(Client entity) {
        Map<Integer, Client> clientMap = getAll();
        Client client = clientMap.get(entity.getId());
        client.setAddress(entity.getAddress());
        client.setfName(entity.getfName());
        client.setlName(entity.getlName());
        return save(clientMap.put(entity.getId(), client));
    }

    @Override
    public void deleteEntity(Client entity) {
        Map<Integer, Client> clientMap = getAll();
        clientMap.remove(entity.getId());
        List<Client> clientList = new ArrayList<>(clientMap.values());
        fileService.writeFile(clientList, CLIENT);
    }

    @Override
    public void addOrder(Order order, Integer clientId) {
        Map<Integer, Client> clientMap = getAll();
        Client client = clientMap.get(clientId);
        List<Order> orderList = client.getOrderList();
        if (orderList != null) {
            orderList.add(order);
            client.setOrderList(orderList);
            save(client);
            return;
        }
        List<Order> orderL = new ArrayList<>();
        orderL.add(order);
        client.setOrderList(orderL);
        save(client);
    }

    @Override
    public void editOrder(Order order, Integer clientId) {
        Map<Integer, Client> clientMap = getAll();
        Client client = clientMap.get(clientId);
        List<Order> orderList = client.getOrderList();
        Map<Integer, Order> orderMap = orderList.stream().collect(Collectors.toMap(Order::getId, order1 -> order1));
        orderMap.put(order.getId(), order);
        List<Order> values = new ArrayList<>(orderMap.values());
        client.setOrderList(values);
        clientMap.put(clientId, client);
        fileService.writeFile(new ArrayList<>(clientMap.values()), CLIENT);
    }

    @Override
    public Map<Integer, Order> getOrderList(Integer clientId) {
        Map<Integer, Client> clientMap = getAll();
        Client client = clientMap.get(clientId);
        List<Order> orderList = client.getOrderList();
        Map<Integer, Order> orderMap = new HashMap<>();
        if (orderList != null) {
            orderMap = orderList.stream().collect(Collectors.toMap(Order::getId, order -> order));
        }
        return orderMap;
    }

    @Override
    public void delOrder(Integer clientId, Integer orderId) {
        Map<Integer, Client> clientMap = getAll();
        Client client = clientMap.get(clientId);
        List<Order> orderList = client.getOrderList();
        client.setOrderList(orderList.stream()
                .filter(p -> !p.getId().equals(orderId)).collect(Collectors.toList()));
        save(client);
    }
}
