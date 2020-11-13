package actions;

import forms.table.CustomTable;
import forms.table.TableCell;
import forms.table.TableRow;
import forms.table.model.CustomTableModel;
import org.gitlab4j.api.models.Project;
import utils.GitLabApiUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

/**
 * @author liu_wp
 * @date 2020/11/4
 * @see
 */
public class QueryProjectAction extends AbstractActionListener<JTextField, CustomTable<TableRow>> {

    private Component parentComponent;

    public QueryProjectAction(final JTextField condition, final CustomTable resComponent, final Component parentComponent) {
        super(condition, resComponent);
        this.parentComponent = parentComponent;
    }

    @Override
    protected Object action(final ActionEvent event, final JTextField condition) {
        String text = condition.getText();
        List<Project> projects = GitLabApiUtil.getProjects(text != null ? text.trim() : null);
        return projects;
    }

    @Override
    protected void bindObject(final CustomTable<TableRow> customTable, final Object result) {
        CustomTableModel customTableModel = (CustomTableModel) customTable.getModel();
        List<Project> projects = (List<Project>) result;
        List<TableCell> columnNames = customTable.getColumnNames();
        List<List<Object>> data = customTable.projectToTable(projects, columnNames, TableRow.class);
        customTableModel.setData(data);
        customTable.setProjects(projects);
        customTable.updateCell();
        customTable.fitTableByColumnNames();
        customTable.updateUI();
    }
}
