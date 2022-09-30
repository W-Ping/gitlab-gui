package forms.table;

/**
 * @author liu_wp
 * @date 2020/11/6
 * @see
 */
public class TableRow {
    private Long id;
    private String name;
    private String description;
    private String httpUrlToRepo;
    private String lastActivityAt;
    private String defaultBranch;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public String getHttpUrlToRepo() {
        return httpUrlToRepo;
    }

    public void setHttpUrlToRepo(final String httpUrlToRepo) {
        this.httpUrlToRepo = httpUrlToRepo;
    }

    public String getLastActivityAt() {
        return lastActivityAt;
    }

    public void setLastActivityAt(final String lastActivityAt) {
        this.lastActivityAt = lastActivityAt;
    }

    public String getDefaultBranch() {
        return defaultBranch;
    }

    public void setDefaultBranch(final String defaultBranch) {
        this.defaultBranch = defaultBranch;
    }
}
