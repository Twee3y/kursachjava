package ui;

import service.IUchetService;
import service.UchetService;

import java.util.Scanner;

/**
 * Класс главного консольного меню.
 * Обеспечивает взаимодействие с пользователем через текстовый интерфейс.
 *
 * Зависит от интерфейса IUchetService, а не от конкретной реализации
 * (принцип инверсии зависимостей — SOLID).
 */
public class MainMenu {

    private IUchetService service; // Зависимость от интерфейса, а не от реализации
    private Scanner scanner;

    public MainMenu() {
        service = new UchetService(); // Внедрение конкретной реализации
        scanner = new Scanner(System.in, "UTF-8");
    }

    /**
     * Запуск главного цикла меню.
     */
    public void zapustit() {
        boolean rabotaet = true;

        while (rabotaet) {
            pokazatGlavnoeMenu();
            int vybor = chitatCeloye("Ваш выбор: ");

            switch (vybor) {
                case 1:
                    menuOtdely();
                    break;
                case 2:
                    menuTehnika();
                    break;
                case 3:
                    menuPoiskOtchety();
                    break;
                case 0:
                    rabotaet = false;
                    System.out.println("Программа завершена. До свидания!");
                    break;
                default:
                    System.out.println("Неверный пункт меню. Попробуйте ещё раз.");
            }
        }
        scanner.close();
    }

    /**
     * Вывод главного меню.
     */
    private void pokazatGlavnoeMenu() {
        System.out.println();
        System.out.println("╔══════════════════════════════════════════════╗");
        System.out.println("║   СИСТЕМА УЧЕТА ОРГТЕХНИКИ В ОРГАНИЗАЦИИ   ║");
        System.out.println("╠══════════════════════════════════════════════╣");
        System.out.println("║  1. Управление отделами                    ║");
        System.out.println("║  2. Управление техникой                    ║");
        System.out.println("║  3. Поиск и отчёты                         ║");
        System.out.println("║  0. Выход                                  ║");
        System.out.println("╚══════════════════════════════════════════════╝");
    }

    // ======================== ПОДМЕНЮ ОТДЕЛОВ ========================

    /**
     * Подменю управления отделами.
     */
    private void menuOtdely() {
        boolean rabotaet = true;
        while (rabotaet) {
            System.out.println();
            System.out.println("--- Управление отделами ---");
            System.out.println("1. Показать все отделы");
            System.out.println("2. Добавить отдел");
            System.out.println("3. Изменить МОЛ отдела");
            System.out.println("4. Расформировать отдел");
            System.out.println("0. Назад");

            int vybor = chitatCeloye("Ваш выбор: ");
            switch (vybor) {
                case 1:
                    service.pokazatVseOtdely();
                    break;
                case 2:
                    dobavitOtdel();
                    break;
                case 3:
                    izmenitMol();
                    break;
                case 4:
                    rasformirovatOtdel();
                    break;
                case 0:
                    rabotaet = false;
                    break;
                default:
                    System.out.println("Неверный пункт меню.");
            }
        }
    }

    /**
     * Диалог добавления нового отдела.
     */
    private void dobavitOtdel() {
        System.out.println();
        System.out.print("Название отдела: ");
        String nazvanie = scanner.nextLine().trim();
        if (nazvanie.isEmpty()) {
            System.out.println("Ошибка: название не может быть пустым.");
            return;
        }
        System.out.print("МОЛ (ФИО ответственного): ");
        String mol = scanner.nextLine().trim();
        if (mol.isEmpty()) {
            System.out.println("Ошибка: МОЛ не может быть пустым.");
            return;
        }
        service.dobavitOtdel(nazvanie, mol);
    }

    /**
     * Диалог изменения МОЛ.
     */
    private void izmenitMol() {
        System.out.println();
        service.pokazatVseOtdely();
        int id = chitatCeloye("ID отдела для изменения МОЛ: ");
        System.out.print("Новый МОЛ (ФИО): ");
        String novyiMol = scanner.nextLine().trim();
        if (novyiMol.isEmpty()) {
            System.out.println("Ошибка: МОЛ не может быть пустым.");
            return;
        }
        service.izmenitMol(id, novyiMol);
    }

    /**
     * Диалог расформирования отдела.
     */
    private void rasformirovatOtdel() {
        System.out.println();
        service.pokazatVseOtdely();
        int id = chitatCeloye("ID отдела для расформирования: ");
        service.rasformirovatOtdel(id);
    }

    // ======================== ПОДМЕНЮ ТЕХНИКИ ========================

    /**
     * Подменю управления техникой.
     */
    private void menuTehnika() {
        boolean rabotaet = true;
        while (rabotaet) {
            System.out.println();
            System.out.println("--- Управление техникой ---");
            System.out.println("1. Показать всю технику");
            System.out.println("2. Поступление нового устройства");
            System.out.println("3. Списание устройства");
            System.out.println("0. Назад");

            int vybor = chitatCeloye("Ваш выбор: ");
            switch (vybor) {
                case 1:
                    service.pokazatVsyuTehniku();
                    break;
                case 2:
                    dobavitUstroystvo();
                    break;
                case 3:
                    spisatUstroystvo();
                    break;
                case 0:
                    rabotaet = false;
                    break;
                default:
                    System.out.println("Неверный пункт меню.");
            }
        }
    }

    /**
     * Диалог добавления нового устройства.
     */
    private void dobavitUstroystvo() {
        System.out.println();
        service.pokazatVseOtdely();
        int otdelId = chitatCeloye("ID отдела: ");
        System.out.print("Название устройства: ");
        String nazvanie = scanner.nextLine().trim();
        if (nazvanie.isEmpty()) {
            System.out.println("Ошибка: название не может быть пустым.");
            return;
        }
        System.out.print("Описание: ");
        String opisanie = scanner.nextLine().trim();
        if (opisanie.isEmpty()) {
            System.out.println("Ошибка: описание не может быть пустым.");
            return;
        }
        int stoimost = chitatCeloye("Стоимость (руб.): ");
        if (stoimost <= 0) {
            System.out.println("Ошибка: стоимость должна быть положительным числом.");
            return;
        }
        int god = chitatCeloye("Год поступления: ");
        if (god < 1900 || god > 2100) {
            System.out.println("Ошибка: некорректный год.");
            return;
        }
        service.dobavitUstroystvo(otdelId, nazvanie, opisanie, stoimost, god);
    }

    /**
     * Диалог списания устройства.
     */
    private void spisatUstroystvo() {
        System.out.println();
        service.pokazatVsyuTehniku();
        int id = chitatCeloye("ID устройства для списания: ");
        service.spisatUstroystvo(id);
    }

    // ======================== ПОДМЕНЮ ПОИСКА И ОТЧЁТОВ ========================

    /**
     * Подменю поиска и отчётов.
     */
    private void menuPoiskOtchety() {
        boolean rabotaet = true;
        while (rabotaet) {
            System.out.println();
            System.out.println("--- Поиск и отчёты ---");
            System.out.println("1. Поиск техники по МОЛ");
            System.out.println("2. Поиск техники по названию");
            System.out.println("3. Поиск техники по отделу");
            System.out.println("4. Морально устаревшая техника (старше 5 лет)");
            System.out.println("0. Назад");

            int vybor = chitatCeloye("Ваш выбор: ");
            switch (vybor) {
                case 1:
                    poiskPoMol();
                    break;
                case 2:
                    poiskPoNazvaniyu();
                    break;
                case 3:
                    poiskPoOtdelu();
                    break;
                case 4:
                    service.naytiUstarevshuyuTehniku();
                    break;
                case 0:
                    rabotaet = false;
                    break;
                default:
                    System.out.println("Неверный пункт меню.");
            }
        }
    }

    /**
     * Диалог поиска по МОЛ.
     */
    private void poiskPoMol() {
        System.out.println();
        System.out.print("Введите ФИО МОЛ: ");
        String mol = scanner.nextLine().trim();
        if (mol.isEmpty()) {
            System.out.println("Ошибка: введите ФИО.");
            return;
        }
        service.naytiPoMol(mol);
    }

    /**
     * Диалог поиска по названию.
     */
    private void poiskPoNazvaniyu() {
        System.out.println();
        System.out.print("Введите название устройства (или часть): ");
        String nazvanie = scanner.nextLine().trim();
        if (nazvanie.isEmpty()) {
            System.out.println("Ошибка: введите название.");
            return;
        }
        service.naytiPoNazvaniyu(nazvanie);
    }

    /**
     * Диалог поиска по отделу.
     */
    private void poiskPoOtdelu() {
        System.out.println();
        service.pokazatVseOtdely();
        int otdelId = chitatCeloye("ID отдела: ");
        service.naytiPoOtdelu(otdelId);
    }

    // ======================== ВСПОМОГАТЕЛЬНЫЕ МЕТОДЫ ========================

    /**
     * Безопасное чтение целого числа с обработкой ошибок.
     */
    private int chitatCeloye(String podskazka) {
        while (true) {
            try {
                System.out.print(podskazka);
                String vvod = scanner.nextLine().trim();
                return Integer.parseInt(vvod);
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: введите целое число.");
            }
        }
    }
}
