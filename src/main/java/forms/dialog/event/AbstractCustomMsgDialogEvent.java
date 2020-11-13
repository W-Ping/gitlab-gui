package forms.dialog.event;

import forms.dialog.CustomMsgDialog;
import pojo.ResponseResult;

/**
 * @author liu_wp
 * @date 2020/11/10
 * @see
 */
public abstract class AbstractCustomMsgDialogEvent {
    protected CustomMsgDialog customMsgDialog;
    protected EventEnum eventEnum;
    protected ResponseResult responseResult;

    /**
     * @param eventEnum
     */
    public AbstractCustomMsgDialogEvent(EventEnum eventEnum) {
        this.eventEnum = eventEnum;
    }

    public AbstractCustomMsgDialogEvent(EventEnum eventEnum, CustomMsgDialog customMsgDialog) {
        this.customMsgDialog = customMsgDialog;
        this.eventEnum = eventEnum;
    }

    public void process() {
        responseResult = handle();
    }

    public abstract ResponseResult handle();

    public ResponseResult getHandleResult() {
        return responseResult;
    }

    public CustomMsgDialog getCustomMsgDialog() {
        return customMsgDialog;
    }

    public void setCustomMsgDialog(final CustomMsgDialog customMsgDialog) {
        this.customMsgDialog = customMsgDialog;
    }

    public EventEnum getEventEnum() {
        return eventEnum;
    }

    public void setEventEnum(final EventEnum eventEnum) {
        this.eventEnum = eventEnum;
    }
}
