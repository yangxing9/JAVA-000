package yx.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.stereotype.Component;

/**
 * @author yangxing
 * @version 1.0
 * @date 2021/1/25 0025 16:49
 */
@Component
public class ContextClosedEventListener implements ApplicationListener<ContextClosedEvent> {

    @Override
    public void onApplicationEvent(ContextClosedEvent contextClosedEvent) {
        System.out.println(contextClosedEvent);
        System.out.println("=============contextClosedEvent............................");
    }
}
