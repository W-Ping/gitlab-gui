package actions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author liu_wp
 * @date 2020/11/3
 * @see
 */
public abstract class AbstractActionListener<C extends Component, T extends Component> implements ActionListener {
    protected static final Logger log = LoggerFactory.getLogger(AbstractActionListener.class);
    protected C condition;
    protected T resComponent;

    public AbstractActionListener(C condition, T resComponent) {
        this.resComponent = resComponent;
        this.condition = condition;
    }

    @Override
    public void actionPerformed(final ActionEvent event) {
        Object result = action(event, condition);
        bindObject(resComponent, result);
    }

    protected abstract Object action(final ActionEvent event, final C condition);

    protected abstract void bindObject(final T resComponent, final Object result);
}
