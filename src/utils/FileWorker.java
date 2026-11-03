package utils;

import model.Otdel;
import model.Ustroystvo;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Утилитный класс для чтения и записи данных в текстовые файлы.
 * Файл отделов: otdely.txt
 * Файл техники: tehnika.txt
 */
public class FileWorker {

    private static final String FILE_OTDELY = "otdely.txt";
    private static final String FILE_TEHNIKA = "tehnika.txt";

    // ======================== ОТДЕЛЫ ========================

    /**
     * Чтение списка отделов из файла.
     * Если файл не существует — возвращает пустой список.
     */
    public static List<Otdel> chitatOtdely() {
        List<Otdel> spisok = new ArrayList<>();
        File file = new File(FILE_OTDELY);
        if (!file.exists()) {
            return spisok;
        }
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(file), "UTF-8"))) {
            String stroka;
            while ((stroka = reader.readLine()) != null) {
                stroka = stroka.trim();
                if (stroka.isEmpty()) {
                    continue;
                }
                Otdel otdel = Otdel.fromFileString(stroka);
                if (otdel != null) {
                    spisok.add(otdel);
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла отделов: " + e.getMessage());
        }
        return spisok;
    }

    /**
     * Запись списка отделов в файл (полная перезапись).
     */
    public static void zapisatOtdely(List<Otdel> spisok) {
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(FILE_OTDELY), "UTF-8"))) {
            for (Otdel otdel : spisok) {
                writer.write(otdel.toFileString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Ошибка записи файла отделов: " + e.getMessage());
        }
    }

    // ======================== ТЕХНИКА ========================

    /**
     * Чтение списка устройств из файла.
     * Если файл не существует — возвращает пустой список.
     */
    public static List<Ustroystvo> chitatTehniku() {
        List<Ustroystvo> spisok = new ArrayList<>();
        File file = new File(FILE_TEHNIKA);
        if (!file.exists()) {
            return spisok;
        }
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(file), "UTF-8"))) {
            String stroka;
            while ((stroka = reader.readLine()) != null) {
                stroka = stroka.trim();
                if (stroka.isEmpty()) {
                    continue;
                }
                Ustroystvo ustr = Ustroystvo.fromFileString(stroka);
                if (ustr != null) {
                    spisok.add(ustr);
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла техники: " + e.getMessage());
        }
        return spisok;
    }

    /**
     * Запись списка устройств в файл (полная перезапись).
     */
    public static void zapisatTehniku(List<Ustroystvo> spisok) {
        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(FILE_TEHNIKA), "UTF-8"))) {
            for (Ustroystvo ustr : spisok) {
                writer.write(ustr.toFileString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Ошибка записи файла техники: " + e.getMessage());
        }
    }
}
