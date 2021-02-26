package demo.pattern.eventmode;

/**
 * @author jianghui
 * @date 2021-02-24 17:07
 */
public class EventModeDemo {
    public static void main(String[] args) {
        EvevtSource evevtSource = new EvevtSource();
        SingleClickEventListener singleClickEventListener = new SingleClickEventListener();
        DoubleClickEventListener doubleClickEventListener = new DoubleClickEventListener();
        Event event = new Event();
        event.setType("DoubleClickListener");
        evevtSource.register(singleClickEventListener);
        evevtSource.register(doubleClickEventListener);
        evevtSource.publishEvent(event);
    }
}
