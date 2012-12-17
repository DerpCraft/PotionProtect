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
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;

public class BlockListener
  implements Listener
{
  Potionprotect plugin;

  public BlockListener(Potionprotect plugin)
  {
    this.plugin = plugin;
  }
  @EventHandler(priority=EventPriority.NORMAL)
  public void onSignChange(SignChangeEvent e) {
    Block b = e.getBlock();
    Player p = e.getPlayer();
    if ((e.isCancelled()) || (b == null)) {
      setErrorSign(e, p, "The block you placed was null!");
      return;
    }
    String[] lines = e.getLines();
    String line = lines[0].toLowerCase();
    if ((!line.equals("[rp]")) && (!line.equals("[p]")) && (!line.equals("[protect]"))) {
      return;
    }
    if (lines.length != 4) {
      setErrorSign(e, p, "The number of lines on your sign is wrong!");
      return;
    }
    if (!Potionprotect.ph.hasPerm(p, "Potionprotect.create")) {
      setErrorSign(e, p, "You don't have permission to make regions!");
      return;
    }
    RegionBuilder rb = new EncompassRegionBuilder(e);
    if (rb.ready()) {
      Region r = rb.build();
      p.getWorld().getBlockAt(r.getCenterX(), 70, r.getCenterZ()).setTypeId(1);
      e.setLine(0, ChatColor.GREEN + "[RP]: Done.");
      p.sendMessage(ChatColor.AQUA + "Created a region with name: " + ChatColor.GOLD + r.getName() + ChatColor.AQUA + ", with you as owner.");
      Potionprotect.rm.add(r, p.getWorld());
    }
  }

  void setErrorSign(SignChangeEvent e, Player p, String error) {
    e.setLine(0, ChatColor.RED + "[RP]: Error");
    p.sendMessage(ChatColor.RED + "[RP] ERROR:" + error);
  }
  @EventHandler(priority=EventPriority.NORMAL)
  public void onBlockPlace(BlockPlaceEvent e) {
    try {
      Block b = e.getBlock();
      Player p = e.getPlayer();
      if (!Potionprotect.rm.canBuild(p, b, p.getWorld())) {
        p.sendMessage(ChatColor.RED + "You can't build here!");
        e.setCancelled(true);
      }
    } catch (Throwable t) {
      t.printStackTrace();
    }
  }

  @EventHandler(priority=EventPriority.NORMAL)
  public void onBlockBreak(BlockBreakEvent e) {
    Player p = e.getPlayer();
    Block b = e.getBlock();
    if (!Potionprotect.rm.canBuild(p, b, p.getWorld())) {
      p.sendMessage(ChatColor.RED + "You can't build here!");
      e.setCancelled(true);
    }
  }
}
