package forms.table.model;

import org.apache.commons.collections4.CollectionUtils;

import javax.swing.table.DefaultTableModel;
import java.util.List;

/**
 * @author liu_wp
 * @date 2020/11/4
 * @see
 */
public class CustomTableModel extends DefaultTableModel {
    private List<String> columnNames;
    private List<List<Object>> data;

    public CustomTableModel(List<String> columnNames, List<List<Object>> data) {
        super(tableData(data), columnNames(columnNames));
        this.columnNames = columnNames;
        this.data = data;
    }

    @Override
    public boolean isCellEditable(final int row, final int column) {
        if (column == 0 || column == 2) {
            return false;
        }
        return super.isCellEditable(row, column);
    }

    public List<String> getColumnNames() {
        return columnNames;
    }

    public void setColumnNames(final List<String> columnNames) {
        this.columnNames = columnNames;
    }

    public List<List<Object>> getData() {
        return data;
    }

    public void setData(final List<List<Object>> data) {
        if (this.columnNames == null) {
            throw new RuntimeException("table columnNames is null");
        }
        super.setDataVector(tableData(data), columnNames(columnNames));
        this.data = data;
    }

    public static Object[] columnNames(List<String> columnNames) {
        if (CollectionUtils.isNotEmpty(columnNames)) {
            return columnNames.toArray();
        }
        return null;
    }

    public static Object[][] tableData(List<List<Object>> dataList) {
        if (CollectionUtils.isNotEmpty(dataList)) {
            int size = dataList.get(0).size();
            Object[][] objects = new Object[dataList.size()][size];
            for (int i = 0; i < objects.length; i++) {
                List<Object> data = dataList.get(i);
                for (int j = 0; j < data.size(); j++) {
                    objects[i][j] = data.get(j);
                }
            }
            return objects;
        }
        return null;
    }
}
