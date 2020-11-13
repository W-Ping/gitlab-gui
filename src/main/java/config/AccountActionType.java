package config;

/**
 * @author liu_wp
 * @date Created in 2020/11/10 18:02
 * @see
 */
public enum AccountActionType {
    DEFAULT("default"),
    LOGOUT("logout_account"),
    CHANGE("change_account"),
    ;
    private String type;

    AccountActionType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
