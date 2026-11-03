package model;

/**
 * Абстрактный базовый класс для всех сущностей системы.
 * Содержит общее поле ID и абстрактный метод сериализации в строку файла.
 * Демонстрирует принцип наследования (ООП).
 */
public abstract class BazaModel {

    /** Уникальный идентификатор сущности */
    protected int id;

    public BazaModel(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /**
     * Преобразование объекта в строку для записи в текстовый файл.
     * Каждый наследник реализует собственный формат сериализации.
     */
    public abstract String toFileString();
}
