package forms.table;

import forms.panel.RightPanel;
import forms.table.model.CustomTableModel;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.gitlab4j.api.models.Branch;
import org.gitlab4j.api.models.Project;
import pojo.GitlabDetailInfo;
import utils.*;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Method;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author liu_wp
 * @date 2020/11/4
 * @see
 */
public class CustomTable<R> extends JTable {
    private List<? extends AbstractCustomCellRenderer> customCellRenderers;
    private List<? extends AbstractCustomCellEditor> customCellEditors;
    private List<TableCell> columnNames;
    private RightPanel bindPanel;
    private List<R> tableRows;
    private JPopupMenu jPopupMenu;
    private SelectTableRowCell selectTableRowCell;
    private List<Project> projects = new ArrayList<>();


    /**
     * @param cellList
     * @param data
     * @param dimension
     */
    public CustomTable(List<TableCell> columnNames, List<List<Object>> data, Dimension dimension) {
        super(new CustomTableModel(columnNames(columnNames), data));
//        setColumnSelectionAllowed(false);
//        setRowSelectionAllowed(false);
        createPopupMenu();
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                jTable1MouseClicked(e);
            }
        });
        if (dimension != null) {
            setPreferredScrollableViewportSize(dimension);
        }
        for (int i = 0; i < columnNames.size(); i++) {
            TableCell tableCell = columnNames.get(i);
            if (tableCell.isHide()) {
                this.removeColumn(this.getColumnModel().getColumn(i));
            }
        }

        this.columnNames = columnNames;
    }

    /**
     * @param cellList
     * @param customTable
     * @return
     */
    private static List<String> columnNames(List<TableCell> cellList) {
        return cellList.stream().map(TableCell::getName).collect(Collectors.toList());
    }

    @Override
    public String getToolTipText(MouseEvent e) {
        String toolTipText = super.getToolTipText(e);
        return toolTipText;
    }

    private void createPopupMenu() {
        jPopupMenu = new JPopupMenu();
        JMenuItem detailMenItem = new JMenuItem();
        detailMenItem.setText("  查看详情  ");
        detailMenItem.addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent evt) {
                if (selectTableRowCell != null) {
                    Project project = projects.get(selectTableRowCell.getFocusedRowIndex());
                    if (project != null) {
                        GitlabDetailInfo gitlabDetailInfo = new GitlabDetailInfo(project, null);
                        MessageDialogUtil.createGitlabDetailDialog(bindPanel.getFrame(), gitlabDetailInfo);
                    }
                }
            }
        });
        JMenu jMenuSub = new JMenu();
        jMenuSub.setText("  查看分支详情  ");
        jMenuSub.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(final MouseEvent e) {
                jMenuSub.removeAll();
                if (projects != null) {
                    Integer focusedRowIndex = selectTableRowCell.getFocusedRowIndex();
                    Project project = projects.get(focusedRowIndex);
                    String projectId = project.getId().toString();
                    List<Branch> branches = GitLabApiUtil.getBranchByProjectId(projectId);
                    if (CollectionUtils.isNotEmpty(branches)) {
                        for (int i = 0; i < branches.size(); i++) {
                            Branch branch = branches.get(i);
                            JMenuItem sbu1 = new JMenuItem();
                            sbu1.setText("  " + branch.getName() + "  ");
                            sbu1.addActionListener(new AbstractAction() {
                                @Override
                                public void actionPerformed(final ActionEvent e) {
                                    System.out.println("查看：" + branch.getName());
                                    GitlabDetailInfo gitlabDetailInfo = new GitlabDetailInfo(project, Arrays.asList(branch));
                                    MessageDialogUtil.createGitlabBranchesDialog(bindPanel.getFrame(), gitlabDetailInfo);
                                }

                            });
                            jMenuSub.add(sbu1);
                        }
                    } else {
                        JMenuItem sbu1 = new JMenuItem();
                        sbu1.setText("  没有分支  ");
                        jMenuSub.add(sbu1);
                    }
                }
            }

        });
        JMenuItem copyMenItem = new JMenuItem();
        copyMenItem.setText("  复制  ");
        copyMenItem.addActionListener(new AbstractAction() {
            public void actionPerformed(ActionEvent evt) {
                if (selectTableRowCell != null) {
                    Object selectValue = getValueAt(selectTableRowCell.getFocusedRowIndex(), selectTableRowCell.getFocusedColumnIndex());
                    if (selectValue != null) {
                        SystemUtil.setClipboardString(selectValue.toString());
                    }
                }

            }
        });
        this.jPopupMenu.add(detailMenItem);
        this.jPopupMenu.add(jMenuSub);
        this.jPopupMenu.add(copyMenItem);
    }

    private void jTable1MouseClicked(MouseEvent evt) {
        mouseRightButtonClick(evt);
    }

    private void mouseRightButtonClick(java.awt.event.MouseEvent evt) {
        //判断是否为鼠标的BUTTON3按钮，BUTTON3为鼠标右键
        if (evt.getButton() == java.awt.event.MouseEvent.BUTTON3) {
            //通过点击位置找到点击为表格中的行
            int focusedColumnIndex = this.columnAtPoint(evt.getPoint());
            int focusedRowIndex = this.rowAtPoint(evt.getPoint());
            System.out.println("focusedColumnIndex:" + focusedColumnIndex + " focusedRowIndex:" + focusedRowIndex);
            if (focusedRowIndex == -1 || focusedColumnIndex == 2) {
                return;
            }
            selectTableRowCell = new SelectTableRowCell(focusedRowIndex, focusedColumnIndex);

            //将表格所选项设为当前右键点击的行
//            this.setRowSelectionInterval(focusedRowIndex, focusedRowIndex);
            this.setColumnSelectionInterval(focusedColumnIndex, focusedColumnIndex);
            jPopupMenu.show(this, evt.getX(), evt.getY());
        }

    }

    /**
     * @param projects
     * @param tableCells
     * @param rClass
     * @param <T>
     * @return
     */
    public List<List<Object>> projectToTable(List<Project> projects, List<TableCell> tableCells, Class<R> rClass) {
        List<List<Object>> data = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(projects)) {
            tableRows = BeanCopierUtil.copyPropertiesOfList(projects, rClass);
            for (int i = 0; i < projects.size(); i++) {
                List<Object> columnValues = new ArrayList<>();
                Project project = projects.get(i);
                Class<? extends Project> aClass = project.getClass();
                try {
                    for (final TableCell tableCell : tableCells) {
                        String valueField = tableCell.getValueField();
                        if (StringUtils.isNotBlank(valueField)) {
                            Method method = aClass.getMethod("get" + valueField, new Class[0]);
                            Object invoke = method.invoke(project, new Object[]{});
                            if (invoke != null) {
                                if (invoke instanceof Date) {
                                    Date date = (Date) invoke;
                                    invoke = DateUtil.date2String(date, null);
                                }
                            }
                            columnValues.add(invoke);
                        } else {
                            columnValues.add(StringUtils.isNotBlank(tableCell.getShowName()) ? tableCell.getShowName() : tableCell.getName());
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                data.add(columnValues);
            }
        }
        return data;
    }

    public List<TableCell> getColumnNames() {
        return columnNames;
    }

    public void setColumnNames(final List<TableCell> columnNames) {
        this.columnNames = columnNames;
    }

    /**
     * @param customCellRenderers
     */
    public void setCellRenderer(final List<? extends AbstractCustomCellRenderer> customCellRenderers) {
        if (CollectionUtils.isNotEmpty(customCellRenderers)) {
            for (final AbstractCustomCellRenderer customCellRenderer : customCellRenderers) {
                this.getColumn(customCellRenderer.getCellName())
                        .setCellRenderer(customCellRenderer);
            }
        }
    }

    /**
     * @param customCellEditors
     */
    public void setCellEditor(final List<? extends AbstractCustomCellEditor> customCellEditors) {
        if (CollectionUtils.isNotEmpty(customCellEditors)) {
            for (final AbstractCustomCellEditor customCellEditor : customCellEditors) {
                customCellEditor.setBindPanel(bindPanel);
                this.getColumn(customCellEditor.getCellName())
                        .setCellEditor(customCellEditor);
            }
        }
    }

    public List<R> getTableRows() {
        return tableRows;
    }

    public void setTableRows(final List<R> tableRows) {
        this.tableRows = tableRows;
    }

    public List<? extends AbstractCustomCellRenderer> getCustomCellRenderers() {
        return customCellRenderers;
    }

    public void setCustomCellRenderers(final List<? extends AbstractCustomCellRenderer> customCellRenderers) {
        setCellRenderer(customCellRenderers);
        this.customCellRenderers = customCellRenderers;
    }

    public List<? extends AbstractCustomCellEditor> getCustomCellEditors() {
        return customCellEditors;
    }

    public void setCustomCellEditors(final List<? extends AbstractCustomCellEditor> customCellEditors) {
        setCellEditor(customCellEditors);
        this.customCellEditors = customCellEditors;
    }

    /**
     * @param minWidth
     * @param minHeight
     */


    public void updateCell() {
        setCellRenderer(customCellRenderers);
        setCellEditor(customCellEditors);
        for (int i = 0; i < columnNames.size(); i++) {
            TableCell tableCell = columnNames.get(i);
            if (tableCell.isHide()) {
                this.removeColumn(this.getColumnModel().getColumn(i));
            }
        }
    }

    /**
     *
     */
    public void fitTableByColumnNames() {
        if (CollectionUtils.isNotEmpty(columnNames)) {
            final TableColumnModel columnModel = this.getColumnModel();
            Integer maxHeight = null;
            for (int column = 0, index = 0; column < columnNames.size(); column++) {
                if (columnNames.get(column).isHide()) {
                    continue;
                }
                TableCell tableCell = columnNames.get(column);
                Integer width = tableCell.getMinWith();
                if (width != null) {
                    columnModel.getColumn(index).setPreferredWidth(width);
                }
                index++;
                if (tableCell.getMinHeight() != null) {
                    if (maxHeight != null) {
                        maxHeight = Math.max(tableCell.getMinHeight(), maxHeight);
                    } else {
                        maxHeight = tableCell.getMinHeight();
                    }
                }
            }
            if (maxHeight != null) {
                this.setRowHeight(maxHeight);
            }
        }
    }

    public void fitTableColumns() {
        JTable myTable = this;
        JTableHeader header = myTable.getTableHeader();
        int rowCount = myTable.getRowCount();

        Enumeration columns = myTable.getColumnModel().getColumns();
        while (columns.hasMoreElements()) {
            TableColumn column = (TableColumn) columns.nextElement();
            int col = header.getColumnModel().getColumnIndex(column.getIdentifier());
            int width = (int) header.getDefaultRenderer()
                    .getTableCellRendererComponent(myTable, column.getIdentifier(), false, false,
                            -1, col).getPreferredSize().getWidth();
            for (int row = 0; row < rowCount; row++) {
                int preferedWidth = (int) myTable.getCellRenderer(row, col)
                        .getTableCellRendererComponent(myTable, myTable.getValueAt(row, col),
                                false, false, row, col).getPreferredSize().getWidth();
                width = Math.max(width, preferedWidth);
            }
            header.setResizingColumn(column);
            column.setWidth(width + myTable.getIntercellSpacing().width);
        }
    }

    public RightPanel getBindPanel() {
        return bindPanel;
    }

    public void setBindPanel(final RightPanel bindPanel) {
        this.bindPanel = bindPanel;
    }

    public void resizeSize() {
        int minHeight = 20;
        final TableColumnModel columnModel = this.getColumnModel();
        for (int column = 0; column < this.getColumnCount(); column++) {
            int width = 20;
            for (int row = 0; row < this.getRowCount(); row++) {
                TableCellRenderer renderer = this.getCellRenderer(row, column);
                Component comp = this.prepareRenderer(renderer, row, column);
                width = Math.max(comp.getPreferredSize().width + 1, width);
                int h = comp.getPreferredSize().height;
                String format = String.format("行：%s,列：%s，高：%s", row, column, h);
                System.out.println(format);
                minHeight = Math.max(h, minHeight);
            }
            if (width > 400) {
                width = 400;
            }
            columnModel.getColumn(column).setPreferredWidth(width);
        }
        this.setRowHeight(minHeight);
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(final List<Project> projects) {
        this.projects = projects;
    }
}
