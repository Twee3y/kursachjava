import ui.MainMenu;
import utils.DemoData;

/**
 * Точка входа в программу.
 * Курсовой проект: "Система учета оргтехники в организации" (вариант 21).
 *
 * При первом запуске создаёт демонстрационные данные в файлах.
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("Загрузка системы учета оргтехники...");
        System.out.println();

        // Инициализация демо-данных, если файлы отсутствуют
        DemoData.inicializirovat();

        // Запуск консольного меню
        MainMenu menu = new MainMenu();
        menu.zapustit();
    }
}
