package actions;

import org.gitlab4j.api.models.Branch;
import utils.GitLabApiUtil;
import utils.JSONUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

/**
 * @author liu_wp
 * @date 2020/11/3
 * @see
 */
public class QueryCodeBranchAction extends AbstractActionListener<Component, JTextArea> {


    public QueryCodeBranchAction(Component projectId, JTextArea jTextArea) {
        super(projectId, jTextArea);

    }

    @Override
    protected Object action(final ActionEvent event, final Component condition) {
        List<Branch> branches = GitLabApiUtil.getBranchByProjectId(condition.toString());
        return branches;
    }

    @Override
    protected void bindObject(final JTextArea resComponent, final Object result) {
        String s = JSONUtil.Object2JSON(result);
        resComponent.setText(s);
    }

}
