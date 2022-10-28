package com.zwei.testb.test;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.MalformedJsonException;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Test {
    private static final String CHART = "chart";
    private static final String RESULT = "result";

    // em_place_latitude не описан в схеме и не встречается в документе
    private static final String PRICE = "regularMarketPrice";


    static void parseCrashCoordinates(final JsonReader jsonReader, final ICoordinatesListener listener)
            throws IOException {
        // Считываем { как начало объекта.
        // Если его не считать или считать неверно, выбросится исключение -- это и есть суть pull-метода.
        jsonReader.beginObject();
        // Смотрим имя следующего свойства объекта и сравниваем его с ожидаемым.
        String itemsName = jsonReader.nextName();
        if ( !itemsName.equals(CHART) ) {
            // Не items? Возможно, у нас нет идей как его обработать -- лучше выбросить исключение.
            throw new MalformedJsonException(CHART + " expected but was " + itemsName);
        }
        jsonReader.beginObject();
        itemsName = jsonReader.nextName();
        // Смотрим имя следующего свойства объекта и сравниваем его с ожидаемым.
        if ( !itemsName.equals(RESULT) ) {
            // Не items? Возможно, у нас нет идей как его обработать -- лучше выбросить исключение.
            throw new MalformedJsonException(RESULT + " expected but was " + itemsName);
        }
        // Так же теперь вычитываем [
        jsonReader.beginArray();
        // И читаем каждый элемент массива
        while ( jsonReader.hasNext() ) {
            // Судя по схеме, каждый элемент массива - объект
            jsonReader.beginObject();
            double price = 0;
            // И так же пробегаемся по всех свойствах этого объекта
            while ( jsonReader.hasNext() ) {
                // Теперь просто смотрим, являются ли они нам известными
                final String property = jsonReader.nextName();
                switch ( property ) {
                    // latitude? Запоминаем.
                    case PRICE:
                        price = jsonReader.nextDouble();
                        break;
                    // Иначе просто пропускаем любое значение свойства.
                    default:
                        jsonReader.skipValue();
                        break;
                }
            }
            // Просто делегируем полученные координаты в обработчик
            listener.onCoordinates(price);
            // И говорим, что с текущим элементом массива, именно объектом, покончено.
            jsonReader.endObject();
        }
        // Также закрываем последние ] и }
        jsonReader.endArray();
        jsonReader.endObject();
    }

    public static void main(final String... args)
            throws IOException {
        testOutput();
        testCollecting();
    }
    // Этот тест просто выводит содержимое координат в стандартный поток вывода
// Заметьте: parseCrashCoordinates() сам не решает _что_ делать с координатами -- это целиком наше дело
// Поскольку мы вообще просто передаём данные дальше, нас вообще не волнует размер входных данных
// Теоретически, это может быть бесконечный поток данных -- круто ведь?
    private static void testOutput()
            throws IOException {
        readAndParse((lat) -> System.out.println("(" + lat + ";)"));
    }

    // Здесь мы, напротив, собираем координаты в список.
// Выдержит ли JVM увеличение списка coordinates? Возможно, но не факт: зависит от размера данных и памяти, доступной JVM.
    private static void testCollecting()
            throws IOException {
        final List<Coordinate> coordinates = new ArrayList<>();
        readAndParse((pric) -> coordinates.add(new Coordinate(pric)));
        System.out.println(coordinates.size());
    }

    private static final class Coordinate {

        private final double price;


        private Coordinate(final double latitude) {
            this.price = latitude;
        }

    }

    private static void readAndParse(final ICoordinatesListener listener)
            throws IOException {
        try (final JsonReader jsonReader = new JsonReader(new BufferedReader(new InputStreamReader(new FileInputStream("test.json")))) ) {
            parseCrashCoordinates(jsonReader, listener);
        }
    }
}
