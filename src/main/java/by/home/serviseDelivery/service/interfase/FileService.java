package by.home.serviseDelivery.service.interfase;


import java.util.Collection;
import java.util.List;

public interface FileService<T> {
    void writeFile(List<T> obj, String fileName);

    Collection<T> readFile(String fileName, T type);
}
