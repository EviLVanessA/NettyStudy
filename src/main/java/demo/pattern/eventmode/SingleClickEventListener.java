package demo.pattern.eventmode;

/**
 * @author jianghui
 * @date 2021-02-24 17:01
 */
public class SingleClickEventListener implements EventListener{
    @Override
    public void processEvent(Event event) {
        if ("singleClick".equals(event.getType())){
            System.out.println("触发单机事件");
        }
    }
}
