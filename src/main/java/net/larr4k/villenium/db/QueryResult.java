package net.larr4k.villenium.db;

import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class QueryResult {

    @Setter
    private List<Column> columns;

    @Setter
    private List<Row> rows;
    private Map<String, Integer> columnIndices = null;

    /**
     * Получить номер столбца в строках результата по его названию.
     * @param name название столбца.
     * @return номер столбца.
     */
    public int getColumnIndex(String name) {
        if (this.columnIndices == null) {
            this.columnIndices = new HashMap<>();
            for (int i = 0; i < this.columns.size(); i++) {
                this.columnIndices.put(this.columns.get(i).getName(), i);
            }
        }
        Integer index = this.columnIndices.get(name);
        if (index == null) {
            throw new IllegalArgumentException("Field " + name + " not found");
        }
        return index + 1;
    }

    /**
     * Пустой ли результат?
     * @return true, если в результате нет ни одной строки; false иначе.
     */
    public boolean isEmpty() {
        return this.rows.isEmpty();
    }

    /**
     * Получить количество строк в результате.
     * @return количество строк в результате.
     */
    public int getRowCount() {
        return this.rows.size();
    }

    /**
     * Получить строки результата.
     * @return строки результата.
     */
    public List<Row> getRows() {
        return this.rows;
    }

    /**
     * Получить количество столбцов в результате.
     * @return количество столбцов в результате.
     */
    public int getColumnCount() {
        return this.columns.size();
    }

    /**
     * Получить столбцы результата.
     * @return столбцы результата.
     */
    public List<Column> getColumns() {
        return this.columns;
    }

    /**
     * Получить первую возвращенную строку результата.
     * @return первая возвращенная строка результата.
     */
    public Row getFirst() {
        return this.rows.isEmpty() ? null : this.rows.get(0);
    }

}
