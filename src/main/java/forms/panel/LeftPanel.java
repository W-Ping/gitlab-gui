package forms.panel;

import actions.QueryProjectAction;
import forms.table.CustomTable;
import forms.table.TableCell;
import forms.table.button.CustomButtonCellCellEditor;
import forms.table.button.CustomButtonRenderer;
import forms.table.button.TableCustomButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author liu_wp
 * @date 2020/11/3
 * @see
 */
public class LeftPanel extends BasePanel {
    private RightPanel rightPanel;
    private JFrame frame;

    public LeftPanel(JFrame jFrame, RightPanel rightPanel) {
        setLayout(new FlowLayout(FlowLayout.CENTER));
        this.setSize(dimension = new Dimension(620, 500));
        this.rightPanel = rightPanel;
        this.frame = jFrame;
        CustomButtonRenderer customButtonRenderer = new CustomButtonRenderer("操作", getTableCustomButtons());
        CustomButtonCellCellEditor customButtonCellEditor = new CustomButtonCellCellEditor("操作", getTableCustomButtons());
        CommonJPanel tablePanel = createTablePanel(setHeaderTableCell(), null, Arrays.asList(customButtonRenderer), Arrays.asList(customButtonCellEditor), rightPanel, dimension);
        CommonJPanel txtPanel = createJTextFieldPanel("项目名称：", "");
        CommonJPanel jButtonPanel = createJButtonPanel(queryBranchJButton(txtPanel, tablePanel));
        Box horizontalBox = createHorizontalBox(txtPanel, jButtonPanel);
        Box verticalBox = createVerticalBox(horizontalBox, tablePanel);
        add(verticalBox, 0);
        setVisible(true);
    }

    private List<TableCustomButton> getTableCustomButtons() {
        List<TableCustomButton> list = new ArrayList<>();
        TableCustomButton btn1 = new TableCustomButton("createHotfix", "创建分支", rightPanel);
        TableCustomButton btn2 = new TableCustomButton("deleteHotfix", "删除分支", rightPanel);
        list.add(btn1);
        list.add(btn2);
        return list;
    }

    /**
     * @return
     */
    private List<TableCell> setHeaderTableCell() {
        List<TableCell> tableCells = new ArrayList<>();
//        TableCell h1 = new TableCell("ID", "Id", 10);
        TableCell h2 = new TableCell("项目名称", "Name", 20);
        TableCell h3 = new TableCell("说明", "Description", 20, true);
//        TableCell h4 = new TableCell("最新更时间", "LastActivityAt", 20);
        TableCell h5 = new TableCell("代码地址", "HttpUrlToRepo", 30);
        TableCell h6 = new TableCell("操作", "Id", 60, 30);
//        tableCells.add(h1);
        tableCells.add(h2);
        tableCells.add(h3);
//        tableCells.add(h4);
        tableCells.add(h5);
        tableCells.add(h6);
        return tableCells;

    }

    /**
     * @param txtPanel
     * @param tablePanel
     * @return
     */
    public JButton[] queryBranchJButton(CommonJPanel txtPanel, CommonJPanel tablePanel) {
        JTextField jTextField = (JTextField) txtPanel.getComponent(txtPanel.getComponents().length - 1);
        CustomTable customTable = getCommonTable(tablePanel);
        JButton queryProjectBt = new JButton("搜 索");
        queryProjectBt.setPreferredSize(new Dimension(80, 30));
        tablePanel.getComponent(0);
        QueryProjectAction queryProjectAction = new QueryProjectAction(jTextField, customTable, this);
        queryProjectBt.addActionListener(queryProjectAction);
        queryProjectBt.registerKeyboardAction(queryProjectAction, KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), JComponent.WHEN_IN_FOCUSED_WINDOW);
        return Stream.of(queryProjectBt).toArray(size -> new JButton[size]);
    }

    public RightPanel getRightPanel() {
        return rightPanel;
    }

    public void setRightPanel(final RightPanel rightPanel) {
        this.rightPanel = rightPanel;
    }

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(final JFrame frame) {
        this.frame = frame;
    }


}
