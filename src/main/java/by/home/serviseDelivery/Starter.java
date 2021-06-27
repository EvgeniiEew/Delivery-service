package by.home.serviseDelivery;

import by.home.serviseDelivery.service.JsonFileService;
import by.home.serviseDelivery.service.impl.CenterControlService;


public class Starter {
    public static void main(String[] args) {
        JsonFileService fileService = new JsonFileService();
        CenterControlService centerControlService = new CenterControlService(fileService);
        centerControlService.showMainMenu();
    }
}
