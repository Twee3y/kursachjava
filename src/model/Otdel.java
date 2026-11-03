package model;

/**
 * Модель данных "Отдел".
 * Наследует BazaModel (общее поле id и абстрактный метод toFileString).
 * Содержит название отдела и материально-ответственное лицо (МОЛ).
 */
public class Otdel extends BazaModel {

    private String nazvanie;
    private String mol; // Материально-ответственное лицо

    public Otdel(int id, String nazvanie, String mol) {
        super(id); // Вызов конструктора базового класса
        this.nazvanie = nazvanie;
        this.mol = mol;
    }

    // --- Геттеры и сеттеры ---

    public String getNazvanie() {
        return nazvanie;
    }

    public void setNazvanie(String nazvanie) {
        this.nazvanie = nazvanie;
    }

    public String getMol() {
        return mol;
    }

    public void setMol(String mol) {
        this.mol = mol;
    }

    /**
     * Преобразование объекта в строку для записи в файл (реализация абстрактного метода).
     * Разделитель полей — точка с запятой.
     */
    @Override
    public String toFileString() {
        return id + ";" + nazvanie + ";" + mol;
    }

    /**
     * Создание объекта Otdel из строки файла.
     * При повреждённых данных возвращает null и выводит предупреждение.
     */
    public static Otdel fromFileString(String stroka) {
        try {
            String[] chasti = stroka.split(";");
            if (chasti.length < 3) {
                System.out.println("Предупреждение: пропущена некорректная строка отдела (мало полей): " + stroka);
                return null;
            }
            int id = Integer.parseInt(chasti[0].trim());
            String nazvanie = chasti[1].trim();
            String mol = chasti[2].trim();
            return new Otdel(id, nazvanie, mol);
        } catch (NumberFormatException e) {
            System.out.println("Предупреждение: пропущена строка отдела с некорректным ID: " + stroka);
            return null;
        }
    }

    @Override
    public String toString() {
        return "ID: " + id + " | Название: " + nazvanie + " | МОЛ: " + mol;
    }
}
