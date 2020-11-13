package forms.panel;

import javax.swing.*;
import java.awt.*;

/**
 * @author liu_wp
 * @date 2020/11/3
 * @see
 */
public class RightPanel extends BasePanel {
    private CreateBranchPanel createBranchPanel;
    private DeleteBranchPanel deleteBranchPanel;
    private JFrame frame;


    public RightPanel(JFrame frame) {
        setLayout(new FlowLayout(FlowLayout.CENTER));
        this.setPreferredSize(dimension = new Dimension(300, 600));
        this.frame = frame;
        this.createBranchPanel = new CreateBranchPanel(this);
        this.deleteBranchPanel = new DeleteBranchPanel(this);
        add(createBranchPanel, 0);
        add(deleteBranchPanel, 1);
        setVisible(false);
    }


    public CreateBranchPanel getCreateBranchPanel() {
        return createBranchPanel;
    }

    public void setCreateBranchPanel(final CreateBranchPanel createBranchPanel) {
        this.createBranchPanel = createBranchPanel;
    }

    public DeleteBranchPanel getDeleteBranchPanel() {
        return deleteBranchPanel;
    }

    public void setDeleteBranchPanel(final DeleteBranchPanel deleteBranchPanel) {
        this.deleteBranchPanel = deleteBranchPanel;
    }

    public JFrame getFrame() {
        return frame;
    }

    public void setFrame(final JFrame frame) {
        this.frame = frame;
    }

    public void hideRightPanel() {
        boolean isClose = true;
        int sumComponent = this.getComponents().length;
        for (int i = 0; i < sumComponent; i++) {
            Component component = this.getComponent(i);
            if (component.isVisible()) {
                isClose = false;
            }
        }
        if (this.isVisible() && isClose) {
            JFrame frame = this.getFrame();
            Dimension dimension = this.getDimension();
            int width = frame.getWidth();
            int sumWidth = width - (int) dimension.getWidth();
            frame.setSize(new Dimension(sumWidth, frame.getHeight()));
            this.setVisible(false);
        }
    }
}
