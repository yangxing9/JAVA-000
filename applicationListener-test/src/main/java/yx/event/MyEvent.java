package yx.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author yangxing
 * @version 1.0
 * @date 2021/1/25 0025 16:47
 */
public class MyEvent extends ApplicationEvent {

    private String name;

    public MyEvent(Object source, String name) {
        super(source);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
