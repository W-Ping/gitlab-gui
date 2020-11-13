package forms.panel;

import layout.VerticalFlowLayout;

import javax.swing.*;
import java.awt.*;

/**
 * @author liu_wp
 * @date 2020/11/3
 * @see
 */
public class CommonJPanel extends JPanel {
    public CommonJPanel() {
        setLayout(new FlowLayout(FlowLayout.LEFT));
    }

    public CommonJPanel(LayoutManager mgr) {
        setLayout(mgr);
    }

    public CommonJPanel(LayoutManager layoutManager, JComponent... jComponent) {
        super(layoutManager);
        for (int i = 0; i < jComponent.length; i++) {
            add(jComponent[i], i);
        }
    }

    public CommonJPanel(JComponent... jComponent) {
        for (int i = 0; i < jComponent.length; i++) {
            add(jComponent[i], i);
        }
    }

    public CommonJPanel(VerticalFlowLayout verticalFlowLayout, JComponent... jComponent) {
        super(verticalFlowLayout);
        for (int i = 0; i < jComponent.length; i++) {
            add(jComponent[i], i);
        }
    }

    public CommonJPanel(int align, JComponent... jComponent) {
        for (int i = 0; i < jComponent.length; i++) {
            add(jComponent[i], i);
        }
        this.setLayout(new FlowLayout(align));
    }
}
