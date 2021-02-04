package yx.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import yx.event.MyEvent;

/**
 * @author yangxing
 * @version 1.0
 * @date 2021/1/25 0025 17:32
 */
@Component
public class MyEventListener implements ApplicationListener<MyEvent> {

    @Override
    public void onApplicationEvent(MyEvent myEvent) {
        System.out.println("===========自定义事件==========");
        System.out.println(myEvent);
    }
}
