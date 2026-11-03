package utils;

import model.Otdel;
import model.Ustroystvo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Утилитный класс для создания демонстрационных данных при первом запуске.
 * Если файлы otdely.txt и tehnika.txt не существуют,
 * заполняет их примерами отделов и устройств.
 */
public class DemoData {

    /**
     * Проверяет, существуют ли файлы данных.
     * Если нет — создаёт их с демонстрационным набором.
     */
    public static void inicializirovat() {
        File fileOtdely = new File("otdely.txt");
        File fileTehnika = new File("tehnika.txt");

        // Если оба файла уже существуют — ничего не делаем
        if (fileOtdely.exists() && fileTehnika.exists()) {
            return;
        }

        System.out.println("Файлы данных не найдены. Создаю демонстрационный набор...");

        // --- Демо-отделы ---
        List<Otdel> otdely = new ArrayList<>();
        otdely.add(new Otdel(1, "Бухгалтерия", "Иванова А.П."));
        otdely.add(new Otdel(2, "IT-отдел", "Сидоров К.М."));
        otdely.add(new Otdel(3, "Отдел кадров", "Петрова Н.В."));
        otdely.add(new Otdel(4, "Канцелярия", "Козлов Д.С."));

        // --- Демо-техника ---
        // Включаем устройства старше 5 лет (год <= 2021) для проверки поиска устаревшей техники
        List<Ustroystvo> tehnika = new ArrayList<>();
        tehnika.add(new Ustroystvo(1, 1, "Принтер HP LaserJet",
                "Лазерный ч/б принтер", 15000, 2019));
        tehnika.add(new Ustroystvo(2, 1, "МФУ Canon",
                "Многофункциональное устройство", 25000, 2022));
        tehnika.add(new Ustroystvo(3, 2, "Монитор Dell 27",
                "IPS монитор 27 дюймов", 22000, 2023));
        tehnika.add(new Ustroystvo(4, 2, "Сервер Dell PowerEdge",
                "Файловый сервер", 180000, 2018));
        tehnika.add(new Ustroystvo(5, 3, "Принтер Epson L3150",
                "Струйный цветной принтер", 12000, 2020));
        tehnika.add(new Ustroystvo(6, 4, "Сканер Canon LiDE",
                "Планшетный сканер А4", 8000, 2017));

        FileWorker.zapisatOtdely(otdely);
        FileWorker.zapisatTehniku(tehnika);

        System.out.println("Создано отделов: " + otdely.size());
        System.out.println("Создано единиц техники: " + tehnika.size());
        System.out.println();
    }
}
