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
import org.bukkit.Location;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Hanging;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;

public class EntityListener
  implements Listener
{
  Potionprotect plugin;
  static final String noPvPMsg = ChatColor.RED + "You can't PvP in this region!";
  static final String noPassiveHurtMsg = ChatColor.RED + "You can't hurt passive entities in this region!";

  public EntityListener(Potionprotect plugin)
  {
    this.plugin = plugin;
  }

  @EventHandler
  public void onEntityTarget(EntityTargetEvent e)
  {
    Entity target = e.getTarget();
    if (target == null) return;
    Region r = Potionprotect.rm.getRegion(target.getLocation());
    if ((r != null) && 
      (!r.canMobs()))
      e.setCancelled(true);
  }

  @EventHandler
  public void onCreatureSpawn(CreatureSpawnEvent event)
  {
    Entity e = event.getEntity();
    if (e == null) return;
    if ((e instanceof Monster)) {
      Region r = Potionprotect.rm.getRegion(e.getLocation());
      if ((r != null) && 
        (!r.canMobs()) && 
        (event.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.NATURAL)))
        event.setCancelled(true);
    }
  }

  @EventHandler
  public void onEntityDamage(EntityDamageEvent event)
  {
    if (event.isCancelled()) return;
    if ((event instanceof EntityDamageByEntityEvent)) {
      EntityDamageByEntityEvent de = (EntityDamageByEntityEvent)event;
      Entity e1 = de.getEntity();
      Entity e2 = de.getDamager();
      if (e2 == null) {
        return;
      }
      if ((e2 instanceof Arrow)) {
        Arrow a = (Arrow)e2;
        e2 = a.getShooter();
        a = null;
        if (e2 == null) {
          return;
        }
      }
      Region r1 = Potionprotect.rm.getRegion(e1.getLocation());
      Region r2 = Potionprotect.rm.getRegion(e2.getLocation());
      if ((e1 instanceof Player))
      {
        if ((e2 instanceof Player)) {
          Player p2 = (Player)e2;
          if (r1 != null) {
            if (r2 != null) {
              if ((!r1.canPVP(p2)) || (!r2.canPVP(p2))) {
                event.setCancelled(true);
                p2.sendMessage(noPvPMsg);
              }
            }
            else if (!r1.canPVP(p2)) {
              event.setCancelled(true);
              p2.sendMessage(noPvPMsg);
            }

          }
          else if ((r2 != null) && 
            (!r2.canPVP(p2))) {
            event.setCancelled(true);
            p2.sendMessage(noPvPMsg);
          }

        }

      }
      else if (((e1 instanceof Animals)) || ((e1 instanceof Villager))) {
        Region r = Potionprotect.rm.getRegion(e1.getLocation());
        if (r != null)
          if ((e2 instanceof Player)) {
            Player p = (Player)e2;
            if (!r.canHurtPassives(p)) {
              event.setCancelled(true);
              p.sendMessage(noPassiveHurtMsg);
            }
          }
          else if (!r.getFlag(6)) {
            event.setCancelled(true);
          }
      }
    }
  }

  @EventHandler
  public void onHangingDamaged(HangingBreakByEntityEvent event)
  {
    Entity remover = event.getRemover();
    if ((remover instanceof Player)) {
      Player player = (Player)remover;
      Location loc = event.getEntity().getLocation();
      Region r = Potionprotect.rm.getRegion(loc);
      if ((r != null) && (!r.canBuild(player))) {
        player.sendMessage(ChatColor.RED + "You can't build here!");
        event.setCancelled(true);
      }
    }
  }
}