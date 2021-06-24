//package by.home.serviseDelivery.domain;
//
//import com.google.gson.Gson;
//import com.google.gson.TypeAdapter;
//import com.google.gson.TypeAdapterFactory;
//import com.google.gson.reflect.TypeToken;
//
//import java.util.LinkedHashMap;
//import java.util.Map;
//
//public class InheritanceTypeAdapterFactory implements TypeAdapterFactory {
//
//    private Map<Class<?>, TypeAdapter<?>> adapters = new LinkedHashMap<>();
//
//    {
//        adapters.put(Shop.class, new ShopTypeAdapter());
//        adapters.put(Dog.class, new DogTypeAdapter());
//    }
//
//    @SuppressWarnings("unchecked")
//    @Override
//    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
//        TypeAdapter<T> typeAdapter = null;
//        Class<?> currentType = Object.class;
//        for (Class<?> type : adapters.keySet()) {
//            if (type.isAssignableFrom(typeToken.getRawType())) {
//                if (currentType.isAssignableFrom(type)) {
//                    currentType = type;
//                    typeAdapter = (TypeAdapter<T>)adapters.get(type);
//                }
//            }
//        }
//        return typeAdapter;
//    }
//
//}