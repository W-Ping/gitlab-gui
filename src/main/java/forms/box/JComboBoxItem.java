package forms.box;

/**
 * @author liu_wp
 * @date 2020/11/4
 * @see
 */
public class JComboBoxItem {
    private String value;
    private String name;

    @Override
    public String toString() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(final String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }
}
