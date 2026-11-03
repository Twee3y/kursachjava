package service;

import model.Otdel;
import model.Ustroystvo;
import utils.FileWorker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

/**
 * Реализация сервиса учёта оргтехники.
 * Содержит всю бизнес-логику: CRUD-операции над отделами и техникой,
 * поиск по различным критериям с подсчётом итоговой стоимости.
 *
 * Реализует интерфейс IUchetService (принцип инверсии зависимостей — SOLID).
 */
public class UchetService implements IUchetService {

    private List<Otdel> otdely;
    private List<Ustroystvo> tehnika;

    public UchetService() {
        // Загрузка данных из файлов при запуске
        otdely = FileWorker.chitatOtdely();
        tehnika = FileWorker.chitatTehniku();
    }

    // ======================== ОТДЕЛЫ ========================

    /**
     * Добавление нового отдела.
     * ID генерируется автоматически (максимальный существующий + 1).
     */
    @Override
    public void dobavitOtdel(String nazvanie, String mol) {
        int novyiId = generovatIdOtdela();
        Otdel novyi = new Otdel(novyiId, nazvanie, mol);
        otdely.add(novyi);
        FileWorker.zapisatOtdely(otdely);
        System.out.println("Отдел успешно добавлен: " + novyi);
    }

    /**
     * Изменение МОЛ (материально-ответственного лица) для отдела.
     */
    @Override
    public void izmenitMol(int otdelId, String novyiMol) {
        Otdel otdel = naytiOtdelPoId(otdelId);
        if (otdel == null) {
            System.out.println("Ошибка: отдел с ID " + otdelId + " не найден.");
            return;
        }
        String staryiMol = otdel.getMol();
        otdel.setMol(novyiMol);
        FileWorker.zapisatOtdely(otdely);
        System.out.println("МОЛ отдела \"" + otdel.getNazvanie() + "\" изменён: "
                + staryiMol + " -> " + novyiMol);
    }

    /**
     * Расформирование отдела (удаление).
     * Разрешено только если в отделе нет закреплённой техники.
     */
    @Override
    public void rasformirovatOtdel(int otdelId) {
        Otdel otdel = naytiOtdelPoId(otdelId);
        if (otdel == null) {
            System.out.println("Ошибка: отдел с ID " + otdelId + " не найден.");
            return;
        }
        // Проверяем, есть ли техника в этом отделе
        for (Ustroystvo ustr : tehnika) {
            if (ustr.getOtdelId() == otdelId) {
                System.out.println("Ошибка: невозможно расформировать отдел \""
                        + otdel.getNazvanie() + "\", в нём числится техника. "
                        + "Сначала спишите или переместите всю технику.");
                return;
            }
        }
        otdely.remove(otdel);
        FileWorker.zapisatOtdely(otdely);
        System.out.println("Отдел \"" + otdel.getNazvanie() + "\" успешно расформирован.");
    }

    /**
     * Вывод списка всех отделов на экран.
     */
    @Override
    public void pokazatVseOtdely() {
        if (otdely.isEmpty()) {
            System.out.println("Список отделов пуст.");
            return;
        }
        System.out.println("=== Список отделов ===");
        for (Otdel otdel : otdely) {
            System.out.println(otdel);
        }
    }

    // ======================== ТЕХНИКА ========================

    /**
     * Поступление нового устройства (добавление в систему).
     */
    @Override
    public void dobavitUstroystvo(int otdelId, String nazvanie, String opisanie,
                                   int stoimost, int godPostupleniya) {
        // Проверяем, что отдел существует
        Otdel otdel = naytiOtdelPoId(otdelId);
        if (otdel == null) {
            System.out.println("Ошибка: отдел с ID " + otdelId + " не найден.");
            return;
        }
        int novyiId = generovatIdUstroystva();
        Ustroystvo novoye = new Ustroystvo(novyiId, otdelId, nazvanie, opisanie,
                stoimost, godPostupleniya);
        tehnika.add(novoye);
        FileWorker.zapisatTehniku(tehnika);
        System.out.println("Устройство успешно добавлено: " + novoye);
    }

    /**
     * Списание устройства (удаление из системы).
     */
    @Override
    public void spisatUstroystvo(int ustroytstvoId) {
        Iterator<Ustroystvo> iterator = tehnika.iterator();
        while (iterator.hasNext()) {
            Ustroystvo ustr = iterator.next();
            if (ustr.getId() == ustroytstvoId) {
                iterator.remove();
                FileWorker.zapisatTehniku(tehnika);
                System.out.println("Устройство списано: " + ustr);
                return;
            }
        }
        System.out.println("Ошибка: устройство с ID " + ustroytstvoId + " не найдено.");
    }

    /**
     * Вывод списка всей техники на экран.
     */
    @Override
    public void pokazatVsyuTehniku() {
        if (tehnika.isEmpty()) {
            System.out.println("Список техники пуст.");
            return;
        }
        System.out.println("=== Список техники ===");
        for (Ustroystvo ustr : tehnika) {
            System.out.println(ustr);
        }
    }

    // ======================== ПОИСК И ОТЧЁТЫ ========================

    /**
     * Поиск техники по МОЛ (материально-ответственному лицу).
     * Выводит список устройств и итоговую стоимость.
     */
    @Override
    public void naytiPoMol(String mol) {
        // Находим все отделы с данным МОЛ
        List<Integer> otdelyIds = new ArrayList<>();
        for (Otdel otdel : otdely) {
            if (otdel.getMol().equalsIgnoreCase(mol)) {
                otdelyIds.add(otdel.getId());
            }
        }
        if (otdelyIds.isEmpty()) {
            System.out.println("Отделы с МОЛ \"" + mol + "\" не найдены.");
            return;
        }
        // Находим технику в этих отделах
        List<Ustroystvo> rezultat = new ArrayList<>();
        for (Ustroystvo ustr : tehnika) {
            if (otdelyIds.contains(ustr.getOtdelId())) {
                rezultat.add(ustr);
            }
        }
        vyvestiRezultatPoiska("Техника, закреплённая за МОЛ \"" + mol + "\"", rezultat);
    }

    /**
     * Поиск техники по названию (частичное совпадение, без учёта регистра).
     * Выводит список устройств и итоговую стоимость.
     */
    @Override
    public void naytiPoNazvaniyu(String nazvanie) {
        List<Ustroystvo> rezultat = new ArrayList<>();
        for (Ustroystvo ustr : tehnika) {
            if (ustr.getNazvanie().toLowerCase().contains(nazvanie.toLowerCase())) {
                rezultat.add(ustr);
            }
        }
        vyvestiRezultatPoiska("Техника с названием \"" + nazvanie + "\"", rezultat);
    }

    /**
     * Поиск техники по отделу (по ID отдела).
     * Выводит список устройств и итоговую стоимость.
     */
    @Override
    public void naytiPoOtdelu(int otdelId) {
        Otdel otdel = naytiOtdelPoId(otdelId);
        if (otdel == null) {
            System.out.println("Ошибка: отдел с ID " + otdelId + " не найден.");
            return;
        }
        List<Ustroystvo> rezultat = new ArrayList<>();
        for (Ustroystvo ustr : tehnika) {
            if (ustr.getOtdelId() == otdelId) {
                rezultat.add(ustr);
            }
        }
        vyvestiRezultatPoiska("Техника отдела \"" + otdel.getNazvanie() + "\"", rezultat);
    }

    /**
     * Поиск морально устаревшей техники (старше 5 лет от текущего года).
     * Выводит список устройств и итоговую стоимость.
     */
    @Override
    public void naytiUstarevshuyuTehniku() {
        int tekushiyGod = Calendar.getInstance().get(Calendar.YEAR);
        List<Ustroystvo> rezultat = new ArrayList<>();
        for (Ustroystvo ustr : tehnika) {
            if ((tekushiyGod - ustr.getGodPostupleniya()) > 5) {
                rezultat.add(ustr);
            }
        }
        vyvestiRezultatPoiska("Морально устаревшая техника (старше 5 лет, текущий год: "
                + tekushiyGod + ")", rezultat);
    }

    // ======================== ВСПОМОГАТЕЛЬНЫЕ МЕТОДЫ ========================

    /**
     * Поиск отдела по ID.
     */
    private Otdel naytiOtdelPoId(int id) {
        for (Otdel otdel : otdely) {
            if (otdel.getId() == id) {
                return otdel;
            }
        }
        return null;
    }

    /**
     * Генерация нового ID для отдела (максимальный + 1).
     */
    private int generovatIdOtdela() {
        int maxId = 0;
        for (Otdel otdel : otdely) {
            if (otdel.getId() > maxId) {
                maxId = otdel.getId();
            }
        }
        return maxId + 1;
    }

    /**
     * Генерация нового ID для устройства (максимальный + 1).
     */
    private int generovatIdUstroystva() {
        int maxId = 0;
        for (Ustroystvo ustr : tehnika) {
            if (ustr.getId() > maxId) {
                maxId = ustr.getId();
            }
        }
        return maxId + 1;
    }

    /**
     * Вывод результатов поиска с подсчётом итоговой стоимости.
     */
    private void vyvestiRezultatPoiska(String zagolovok, List<Ustroystvo> rezultat) {
        System.out.println("=== " + zagolovok + " ===");
        if (rezultat.isEmpty()) {
            System.out.println("Ничего не найдено.");
            return;
        }
        int itogovayaStoimost = 0;
        for (Ustroystvo ustr : rezultat) {
            System.out.println(ustr);
            itogovayaStoimost += ustr.getStoimost();
        }
        System.out.println("--- Итого найдено: " + rezultat.size()
                + " ед. | Общая стоимость: " + itogovayaStoimost + " руб. ---");
    }
}
