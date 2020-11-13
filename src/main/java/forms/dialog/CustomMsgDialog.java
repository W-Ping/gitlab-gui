package forms.dialog;

import forms.dialog.event.AbstractCustomMsgDialogEvent;
import forms.dialog.event.EventEnum;
import forms.panel.CommonJPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @author liu_wp
 * @date 2020/11/10
 * @see
 */
public class CustomMsgDialog extends JDialog implements WindowListener {

    private boolean isAutoClose;
    private Timer timer;
    private int reqTime;
    private JLabel reqTimeTxt;
    private String message;
    private boolean isRequest;
    public final static Map<EventEnum, AbstractCustomMsgDialogEvent> events = new ConcurrentHashMap<>();

    public CustomMsgDialog(Component parentComponent, String message, String title) {
        this(parentComponent, message, title, true, true);
    }

    public CustomMsgDialog(Component parentComponent, String message, String title, boolean isAutoClose) {
        this(parentComponent, message, title, true, isAutoClose);
    }

    /**
     * @param parentComponent
     * @param title
     * @param modal
     */
    public CustomMsgDialog(Component parentComponent, String message, String title, boolean modal, boolean isAutoClose, AbstractCustomMsgDialogEvent... eventList) {
        super((Frame) SwingUtilities.windowForComponent(parentComponent), title, modal);
        this.setSize(200, 80);
        this.setResizable(false);
        this.message = message;
        this.isAutoClose = isAutoClose;
        if (!isAutoClose) {
            setModalityType(ModalityType.MODELESS);
        }
        this.setLocationRelativeTo(parentComponent);
        this.setUndecorated(true);
        this.addWindowListener(this);
        this.register(eventList);
        JLabel label = new JLabel(message, JLabel.CENTER);
        CommonJPanel panel = new CommonJPanel(new BorderLayout());
        panel.add(label, BorderLayout.SOUTH);
        panel.setBackground(label.getBackground());
        CommonJPanel panel2 = new CommonJPanel(new BorderLayout());
        panel2.add(reqTimeTxt = new JLabel("", JLabel.CENTER), BorderLayout.NORTH);
        panel2.setBackground(label.getBackground());
        Box box = Box.createVerticalBox();
        box.add(panel);
        box.add(panel2);
        this.setContentPane(box);
        this.setVisible(true);

    }

    /**
     * @param eventEnum
     * @param e
     * @param <E>
     */
    public void register(AbstractCustomMsgDialogEvent... eventList) {
        if (eventList != null) {
            for (final AbstractCustomMsgDialogEvent event : eventList) {
                CustomMsgDialog customMsgDialog = event.getCustomMsgDialog();
                if (customMsgDialog == null) {
                    event.setCustomMsgDialog(this);
                }
                events.put(event.getEventEnum(), event);
            }
        }
    }

    public void autoClose() {
        CustomMsgDialog dialog = this;
        timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dialog.close();
            }
        });
        timer.start();
    }

    public void close() {
//        setModal(true);
//        getOwner().setEnabled(true);
        CustomMsgDialog.this.setVisible(false);
        CustomMsgDialog.this.dispose();
    }

    @Override
    public void windowOpened(final WindowEvent e) {
        System.out.println("LoginDialogWindowListener.windowOpened");
        timer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                reqTimeTxt.setText(showReqTime((++reqTime)));
                if (!isRequest) {
                    isRequest = true;
                    CompletableFuture.runAsync(() -> {
                        try {
                            TimeUnit.SECONDS.sleep(1);
                        } catch (InterruptedException interruptedException) {
                            interruptedException.printStackTrace();
                        }
                        AbstractCustomMsgDialogEvent abstractCustomMsgDialogEvent = events.get(EventEnum.windowOpened);
                        if (abstractCustomMsgDialogEvent != null) {
                            abstractCustomMsgDialogEvent.process();
                            if (isAutoClose) {
                                close();
                            }
                        }
                    });
                }
            }
        });
        timer.start();
//        if (isAutoClose) {
//            autoClose();
//        }
    }

    @Override
    public void windowClosing(final WindowEvent e) {
        System.out.println("LoginDialogWindowListener.windowClosing");
    }

    @Override
    public void windowClosed(final WindowEvent e) {
        System.out.println("LoginDialogWindowListener.windowClosed");
        if (timer != null) {
            System.out.println("timer is stopping");
            timer.stop();
        }
    }

    @Override
    public void windowIconified(final WindowEvent e) {
        System.out.println("LoginDialogWindowListener.windowIconified");
    }

    @Override
    public void windowDeiconified(final WindowEvent e) {
        System.out.println("LoginDialogWindowListener.windowDeiconified");
    }

    @Override
    public void windowActivated(final WindowEvent e) {
        System.out.println("LoginDialogWindowListener.windowActivated");
    }

    @Override
    public void windowDeactivated(final WindowEvent e) {
        System.out.println("LoginDialogWindowListener.windowDeactivated");
    }

    private String showReqTime(int reqTime) {
        return reqTime + " 秒";
    }
}
