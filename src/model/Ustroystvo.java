package model;

/**
 * Модель данных "Устройство" (единица оргтехники).
 * Наследует BazaModel (общее поле id и абстрактный метод toFileString).
 * Содержит ID отдела, название, описание, стоимость и год поступления.
 */
public class Ustroystvo extends BazaModel {

    private int otdelId;       // ID отдела, к которому привязано устройство
    private String nazvanie;
    private String opisanie;
    private int stoimost;      // Стоимость (целое число)
    private int godPostupleniya; // Год поступления

    public Ustroystvo(int id, int otdelId, String nazvanie, String opisanie,
                      int stoimost, int godPostupleniya) {
        super(id); // Вызов конструктора базового класса
        this.otdelId = otdelId;
        this.nazvanie = nazvanie;
        this.opisanie = opisanie;
        this.stoimost = stoimost;
        this.godPostupleniya = godPostupleniya;
    }

    // --- Геттеры и сеттеры ---

    public int getOtdelId() {
        return otdelId;
    }

    public void setOtdelId(int otdelId) {
        this.otdelId = otdelId;
    }

    public String getNazvanie() {
        return nazvanie;
    }

    public void setNazvanie(String nazvanie) {
        this.nazvanie = nazvanie;
    }

    public String getOpisanie() {
        return opisanie;
    }

    public void setOpisanie(String opisanie) {
        this.opisanie = opisanie;
    }

    public int getStoimost() {
        return stoimost;
    }

    public void setStoimost(int stoimost) {
        this.stoimost = stoimost;
    }

    public int getGodPostupleniya() {
        return godPostupleniya;
    }

    public void setGodPostupleniya(int godPostupleniya) {
        this.godPostupleniya = godPostupleniya;
    }

    /**
     * Преобразование объекта в строку для записи в файл (реализация абстрактного метода).
     * Разделитель полей — точка с запятой.
     */
    @Override
    public String toFileString() {
        return id + ";" + otdelId + ";" + nazvanie + ";" + opisanie + ";" + stoimost + ";" + godPostupleniya;
    }

    /**
     * Создание объекта Ustroystvo из строки файла.
     * При повреждённых данных возвращает null и выводит предупреждение.
     */
    public static Ustroystvo fromFileString(String stroka) {
        try {
            String[] chasti = stroka.split(";");
            if (chasti.length < 6) {
                System.out.println("Предупреждение: пропущена некорректная строка техники (мало полей): " + stroka);
                return null;
            }
            int id = Integer.parseInt(chasti[0].trim());
            int otdelId = Integer.parseInt(chasti[1].trim());
            String nazvanie = chasti[2].trim();
            String opisanie = chasti[3].trim();
            int stoimost = Integer.parseInt(chasti[4].trim());
            int godPostupleniya = Integer.parseInt(chasti[5].trim());
            return new Ustroystvo(id, otdelId, nazvanie, opisanie, stoimost, godPostupleniya);
        } catch (NumberFormatException e) {
            System.out.println("Предупреждение: пропущена строка техники с некорректными числами: " + stroka);
            return null;
        }
    }

    @Override
    public String toString() {
        return "ID: " + id + " | Отдел ID: " + otdelId + " | Название: " + nazvanie
                + " | Описание: " + opisanie + " | Стоимость: " + stoimost
                + " руб. | Год: " + godPostupleniya;
    }
}
