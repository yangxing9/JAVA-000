package yx.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author yangxing
 * @version 1.0
 * @date 2021/1/25 0025 17:44
 */
public class MyEvent2 extends ApplicationEvent {

    private String eventName;

    public MyEvent2(Object source,String name) {
        super(source);
        this.eventName = name;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }
}
