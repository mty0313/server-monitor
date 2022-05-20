package top.mty.executor;

import org.bukkit.event.Event;
import org.bukkit.event.EventException;
import org.bukkit.event.Listener;
import org.bukkit.plugin.EventExecutor;

public class PlayerJoinExecutor implements EventExecutor {
    @Override
    public void execute(Listener listener, Event event) throws EventException {
        System.out.println("listener = " + listener);
        System.out.println("event = " + event);
    }
}
