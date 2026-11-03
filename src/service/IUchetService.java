package service;

import java.util.List;
import model.Otdel;
import model.Ustroystvo;

/**
 * Интерфейс сервиса учёта оргтехники.
 * Определяет контракт для всей бизнес-логики системы.
 * Соблюдает принцип инверсии зависимостей (Dependency Inversion, буква D в SOLID):
 * модуль UI зависит от абстракции (интерфейса), а не от конкретной реализации.
 */
public interface IUchetService {

    // ====== Операции с отделами ======

    /** Добавление нового отдела */
    void dobavitOtdel(String nazvanie, String mol);

    /** Изменение МОЛ (материально-ответственного лица) отдела */
    void izmenitMol(int otdelId, String novyiMol);

    /** Расформирование отдела (только если в нём нет техники) */
    void rasformirovatOtdel(int otdelId);

    /** Вывод списка всех отделов */
    void pokazatVseOtdely();

    // ====== Операции с техникой ======

    /** Поступление нового устройства */
    void dobavitUstroystvo(int otdelId, String nazvanie, String opisanie,
                           int stoimost, int godPostupleniya);

    /** Списание устройства */
    void spisatUstroystvo(int ustroytstvoId);

    /** Вывод списка всей техники */
    void pokazatVsyuTehniku();

    // ====== Поиск и отчёты ======

    /** Поиск техники по МОЛ (с итоговой стоимостью) */
    void naytiPoMol(String mol);

    /** Поиск техники по названию (с итоговой стоимостью) */
    void naytiPoNazvaniyu(String nazvanie);

    /** Поиск техники по отделу (с итоговой стоимостью) */
    void naytiPoOtdelu(int otdelId);

    /** Поиск морально устаревшей техники (старше 5 лет) */
    void naytiUstarevshuyuTehniku();
}
