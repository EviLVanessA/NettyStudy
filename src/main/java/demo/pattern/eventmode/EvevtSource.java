package demo.pattern.eventmode;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jianghui
 * @date 2021-02-24 17:04
 */
public class EvevtSource {
    private List<EventListener> listeners = new ArrayList<>();
    public void register(EventListener listener){
        listeners.add(listener);
    }
    public void publishEvent(Event event){
        for (EventListener eventListener : listeners){
            eventListener.processEvent(event);
        }
    }
}
