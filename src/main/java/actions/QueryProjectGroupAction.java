package actions;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.GroupApi;
import org.gitlab4j.api.models.Group;
import forms.box.JComboBoxItem;
import utils.GitLabApiUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

/**
 * @author liu_wp
 * @date 2020/11/3
 * @see
 */
public class QueryProjectGroupAction extends AbstractActionListener<Component, JComboBox> {

    public QueryProjectGroupAction(final Component condition, final JComboBox resComponent) {
        super(condition, resComponent);
    }

    @Override
    protected Object action(final ActionEvent event, final Component condition) {
        GroupApi groupApi = GitLabApiUtil.getGroupApi();
        return groupApi;
    }

    @Override
    protected void bindObject(final JComboBox resComponent, final Object result) {
        try {
            resComponent.removeAllItems();
            GroupApi groupApi = (GroupApi) result;
            List<Group> groups = groupApi.getGroups();
            if (CollectionUtils.isNotEmpty(groups)) {
                for (int i = 0; i < groups.size(); i++) {
                    Group group = groups.get(i);
                    String description = group.getDescription();
                    String fullName = group.getFullName();
                    if (StringUtils.isNotBlank(description)) {
                        fullName += ("【" + description + "】");
                    }
                    JComboBoxItem jComboBoxItem = new JComboBoxItem();
                    jComboBoxItem.setName(fullName);
                    jComboBoxItem.setValue(group.getId().toString());
                    resComponent.addItem(jComboBoxItem);
                }
            }
        } catch (GitLabApiException e) {
            e.printStackTrace();
        }
    }
}
