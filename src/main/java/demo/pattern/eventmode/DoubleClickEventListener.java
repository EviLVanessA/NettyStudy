package demo.pattern.eventmode;

/**
 * @author jianghui
 * @date 2021-02-24 17:02
 */
public class DoubleClickEventListener implements EventListener {
    @Override
    public void processEvent(Event event) {
        if ("DoubleClickListener".equals(event.getType())){
            System.out.println("双击事件触发");
        }
    }
}
