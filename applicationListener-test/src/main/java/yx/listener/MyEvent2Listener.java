package yx.listener;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import yx.event.MyEvent2;

/**
 * @author yangxing
 * @version 1.0
 * @date 2021/1/25 0025 17:45
 */
@Component
public class MyEvent2Listener {

    @EventListener(classes={MyEvent2.class})
    public void listen(MyEvent2 event){
        System.out.println("MyEvent2Listener。。监听到的事件：" + event);
    }

}
