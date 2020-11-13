package forms.table;

/**
 * @author liu_wp
 * @date 2020/11/13
 * @see
 */
public class SelectTableRowCell {
    private Integer focusedRowIndex;
    private Integer focusedColumnIndex;

    public SelectTableRowCell(Integer focusedRowIndex, Integer focusedColumnIndex) {
        this.focusedRowIndex = focusedRowIndex;
        this.focusedColumnIndex = focusedColumnIndex;
    }

    public Integer getFocusedRowIndex() {
        return focusedRowIndex;
    }

    public void setFocusedRowIndex(final Integer focusedRowIndex) {
        this.focusedRowIndex = focusedRowIndex;
    }

    public Integer getFocusedColumnIndex() {
        return focusedColumnIndex;
    }

    public void setFocusedColumnIndex(final Integer focusedColumnIndex) {
        this.focusedColumnIndex = focusedColumnIndex;
    }
}
