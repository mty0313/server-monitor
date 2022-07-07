package top.mty.listener;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {

  @EventHandler
  public void onPlayerJoinEvent(PlayerJoinEvent event) {
    // 清除原有上线提示
    event.setJoinMessage(null);
    if ("taotiantian".equals(event.getPlayer().getName())) {
      Bukkit.broadcastMessage(event.getPlayer().getName() + " §d上线了");
    } else {
      Bukkit.broadcastMessage(event.getPlayer().getName() + " §b上线了");
    }

  }

  @EventHandler
  public void onPlayerQuitEvent(PlayerQuitEvent event) {
    // 清除原有下线提示
    event.setQuitMessage(null);
    Bukkit.broadcastMessage(event.getPlayer().getName() + " §4下线了");
  }

}
