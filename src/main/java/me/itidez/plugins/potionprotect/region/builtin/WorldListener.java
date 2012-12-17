/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.itidez.plugins.potionprotect.region.builtin;

/**
 *
 * @author tjs238
 */
import me.itidez.plugins.potionprotect.Potionprotect;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.event.world.WorldUnloadEvent;

public class WorldListener
  implements Listener
{
  Potionprotect plugin;

  public WorldListener(Potionprotect plugin)
  {
    this.plugin = plugin;
  }
  @EventHandler(priority=EventPriority.NORMAL)
  public void onWorldLoad(WorldLoadEvent e) {
    World w = e.getWorld();
    try {
      Potionprotect.rm.load(w);
      Potionprotect.debug("World loaded: " + w.getName());
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  @EventHandler(priority=EventPriority.NORMAL)
  public void onWorldUnload(WorldUnloadEvent e) {
    World w = e.getWorld();
    try {
      Potionprotect.rm.unload(w);
      Potionprotect.debug("World unloaded: " + w.getName());
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}
