package forms.panel;

import layout.VerticalFlowLayout;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

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

    private BufferedImage image;

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

    @Override

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image != null) {
            g.drawImage(image, 0, 0, this);
        }
    }
}
