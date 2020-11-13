package pojo;

import org.gitlab4j.api.models.Branch;
import org.gitlab4j.api.models.Project;

import java.util.List;

/**
 * @author liu_wp
 * @date 2020/11/13
 * @see
 */
public class GitlabDetailInfo {
    private Project project;
    private List<Branch> branches;

    public GitlabDetailInfo(Project project, List<Branch> branches) {
        this.project = project;
        this.branches = branches;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(final Project project) {
        this.project = project;
    }

    public List<Branch> getBranches() {
        return branches;
    }

    public void setBranches(final List<Branch> branches) {
        this.branches = branches;
    }
}
