package by.home.serviseDelivery.service;

import by.home.serviseDelivery.domain.Shop;
import by.home.serviseDelivery.service.interfase.FileService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class JsonFileService<T> implements FileService<T> {

    @Override
    public void writeFile(List<T> obj, String fileName) {
        Gson gson = new Gson();
        String jsonString = gson.toJson(obj);
        File file = new File("resources/" + fileName + ".json");
        String absolutePath = file.getAbsolutePath();
        try (FileWriter writer = new FileWriter(absolutePath, false)) {
            writer.write(jsonString);
            writer.append('\n');
            writer.flush();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public Collection<T> readFile(String fileName,  T type) {
        Gson gson = new Gson();
        File file = new File("resources/"+ fileName + ".json");
        String absolutePath = file.getAbsolutePath();
        try {
            String line = Files.lines(Paths.get(absolutePath)).reduce("", String::concat);
            Type userListType = TypeToken.getParameterized(Collection.class, type.getClass()).getType();
            return gson.fromJson(line, userListType);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
}
