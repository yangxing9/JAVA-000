package yx.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.RequestHandledEvent;

/**
 * @author yangxing
 * @version 1.0
 * @date 2021/1/25 0025 16:49
 */
@Component
public class RequestHandledEventListener implements ApplicationListener<RequestHandledEvent> {

    @Override
    public void onApplicationEvent(RequestHandledEvent requestHandledEvent) {
        System.out.println(requestHandledEvent);
        System.out.println("=============requestHandledEvent............................");
    }
}
