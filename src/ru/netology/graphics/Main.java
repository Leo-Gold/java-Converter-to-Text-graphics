package ru.netology.graphics;

import ru.netology.graphics.image.Convert;
import ru.netology.graphics.image.Schema;
import ru.netology.graphics.image.TextGraphicsConverter;
import ru.netology.graphics.server.GServer;

import java.io.File;
import java.io.PrintWriter;

public class Main {
    public static void main(String[] args) throws Exception {
        TextGraphicsConverter converter = new Convert(); // Создайте тут объект вашего класса конвертера
        converter.setTextColorSchema(new Schema());

        GServer server = new GServer(converter); // Создаём объект сервера
        server.start(); // Запускаем

        // Или то же, но с выводом на экран:
//        converter.setMaxWidth(30);
//        converter.setMaxHeight(50);
//        converter.setMaxRatio(3);
//        String url = "https://i.ibb.co/6DYM05G/edu0.jpg";
//        String imgTxt = converter.convert(url);
//        System.out.println(imgTxt);
    }
}
