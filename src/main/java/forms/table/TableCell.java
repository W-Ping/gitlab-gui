package forms.table;

/**
 * @author liu_wp
 * @date 2020/11/5
 * @see
 */
public class TableCell {
    private String name;
    private Integer minWith;
    private Integer minHeight;
    /**
     * 获取数据属性 （首字母大写）
     */
    private String valueField;

    private String showName;
    private boolean hide;

    public TableCell(String name, String valueField, Integer minWith) {
        this(name, valueField, null, minWith, null, false);
    }

    public TableCell(String name, String valueField, Integer minWith, boolean hide) {
        this(name, valueField, null, minWith, null, hide);
    }

    public TableCell(String name, String valueField, String showName, Integer minWith) {
        this(name, valueField, showName, minWith, null, false);
    }

    public TableCell(String name, String valueField, Integer minWith, Integer minHeight) {
        this(name, valueField, null, minWith, minHeight, false);
    }

    public TableCell(String name, String valueField, String showName, Integer minWith, Integer minHeight, boolean hide) {
        this.name = name;
        this.valueField = valueField;
        this.showName = showName;
        this.minWith = minWith;
        this.minHeight = minHeight;
        this.hide = hide;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Integer getMinWith() {
        return minWith;
    }

    public void setMinWith(final Integer minWith) {
        this.minWith = minWith;
    }

    public Integer getMinHeight() {
        return minHeight;
    }

    public void setMinHeight(Integer minHeight) {
        this.minHeight = minHeight;
    }

    public String getValueField() {
        return valueField;
    }

    public void setValueField(final String valueField) {
        this.valueField = valueField;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(final String showName) {
        this.showName = showName;
    }

    public boolean isHide() {
        return hide;
    }

    public void setHide(final boolean hide) {
        this.hide = hide;
    }
}
