/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.itidez.plugins.potionprotect.region.builtin;

/**
 *
 * @author tjs238
 */
import java.util.HashMap;
import me.itidez.plugins.potionprotect.Potionprotect;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerListener
  implements Listener
{
  Potionprotect plugin;

  public PlayerListener(Potionprotect plugin)
  {
    this.plugin = plugin;
  }
  @EventHandler
  public void onPlayerInteract(PlayerInteractEvent event) {
    Player p = event.getPlayer();
    Block b = event.getClickedBlock();
    if (b == null) return;
    Region r = null;
    Material itemInHand = p.getItemInHand().getType();

    if (p.getItemInHand().getTypeId() == Potionprotect.adminWandID) {
      if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
        if (p.hasPermission("Potionprotect.magicwand")) {
          Potionprotect.secondLocationSelections.put(p, b.getLocation());
          p.sendMessage(ChatColor.AQUA + "Set the second magic wand location to (" + ChatColor.GOLD + b.getLocation().getBlockX() + ChatColor.AQUA + ", " + ChatColor.GOLD + b.getLocation().getBlockY() + ChatColor.AQUA + ", " + ChatColor.GOLD + b.getLocation().getBlockZ() + ChatColor.AQUA + ").");
          event.setCancelled(true);
          return;
        }
      }
      else if ((event.getAction().equals(Action.LEFT_CLICK_BLOCK)) && 
        (p.hasPermission("Potionprotect.magicwand"))) {
        Potionprotect.firstLocationSelections.put(p, b.getLocation());
        p.sendMessage(ChatColor.AQUA + "Set the first magic wand location to (" + ChatColor.GOLD + b.getLocation().getBlockX() + ChatColor.AQUA + ", " + ChatColor.GOLD + b.getLocation().getBlockY() + ChatColor.AQUA + ", " + ChatColor.GOLD + b.getLocation().getBlockZ() + ChatColor.AQUA + ").");
        event.setCancelled(true);
        return;
      }
    }

    if (p.getItemInHand().getTypeId() == Potionprotect.infoWandID) {
      if (event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
        r = Potionprotect.rm.getRegion(p.getLocation());
      }
      else if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
        r = Potionprotect.rm.getRegion(b.getLocation());
      }
      if (p.hasPermission("Potionprotect.infowand")) {
        if (r == null) {
          p.sendMessage(ChatColor.RED + "There is no region at that block's location!");
        } else {
          p.sendMessage(ChatColor.AQUA + "--------------- [" + ChatColor.GOLD + r.getName() + ChatColor.AQUA + "] ---------------");
          p.sendMessage(r.info());
          p.sendMessage(r.getFlagInfo());
        }
        event.setCancelled(true);
        return;
      }

    }

    if (b.getType().equals(Material.CHEST)) {
      r = Potionprotect.rm.getRegion(b.getLocation());
      if (r == null) return;
      if (!r.canChest(p)) {
        if (!Potionprotect.ph.hasPerm(p, "Potionprotect.bypass")) {
          p.sendMessage(ChatColor.RED + "You can't open this chest!");
          event.setCancelled(true);
        } else {
          p.sendMessage(ChatColor.YELLOW + "Opened locked chest in " + r.getCreator() + "'s region.");
        }
      }

    }
    else if (b.getType().equals(Material.DISPENSER)) {
      r = Potionprotect.rm.getRegion(b.getLocation());
      if (r == null) return;
      if (!r.canChest(p)) {
        if (!Potionprotect.ph.hasPerm(p, "Potionprotect.bypass")) {
          p.sendMessage(ChatColor.RED + "You can't open this dispenser!");
          event.setCancelled(true);
        } else {
          p.sendMessage(ChatColor.YELLOW + "Opened locked dispenser in " + r.getCreator() + "'s region.");
        }
      }

    }
    else if ((b.getType().equals(Material.FURNACE)) || (b.getType().equals(Material.BURNING_FURNACE))) {
      r = Potionprotect.rm.getRegion(b.getLocation());
      if (r == null) return;
      if (!r.canChest(p)) {
        if (!Potionprotect.ph.hasPerm(p, "Potionprotect.bypass")) {
          p.sendMessage(ChatColor.RED + "You can't open this furnace!");
          event.setCancelled(true);
        } else {
          p.sendMessage(ChatColor.YELLOW + "Opened locked furnace in " + r.getCreator() + "'s region.");
        }
      }

    }
    else if (b.getType().equals(Material.LEVER)) {
      r = Potionprotect.rm.getRegion(b.getLocation());
      if (r == null) return;
      if (!r.canLever(p)) {
        if (!Potionprotect.ph.hasPerm(p, "Potionprotect.bypass")) {
          p.sendMessage(ChatColor.RED + "You can't toggle this lever!");
          event.setCancelled(true);
        } else {
          p.sendMessage(ChatColor.YELLOW + "Toggled locked lever in " + r.getCreator() + "'s region.");
        }
      }

    }
    else if (b.getType().equals(Material.STONE_BUTTON)) {
      r = Potionprotect.rm.getRegion(b.getLocation());
      if (r == null) return;
      if (!r.canButton(p)) {
        if (!Potionprotect.ph.hasPerm(p, "Potionprotect.bypass")) {
          p.sendMessage(ChatColor.RED + "You can't activate this button!");
          event.setCancelled(true);
        } else {
          p.sendMessage(ChatColor.YELLOW + "Activated locked button in " + r.getCreator() + "'s region.");
        }
      }

    }
    else if (b.getType().equals(Material.WOODEN_DOOR)) {
      r = Potionprotect.rm.getRegion(b.getLocation());
      if (r == null) return;
      if (!r.canDoor(p)) {
        if (!Potionprotect.ph.hasPerm(p, "Potionprotect.bypass")) {
          p.sendMessage(ChatColor.RED + "You can't open this door!");
          event.setCancelled(true);
        } else {
          p.sendMessage(ChatColor.YELLOW + "Opened locked door in " + r.getCreator() + "'s region.");
        }
      }
    }

    if (((itemInHand.equals(Material.FLINT_AND_STEEL)) || (itemInHand.equals(Material.WATER_BUCKET)) || (itemInHand.equals(Material.LAVA_BUCKET)) || (itemInHand.equals(Material.PAINTING)) || (itemInHand.equals(Material.ITEM_FRAME))) && 
      (!Potionprotect.rm.canBuild(p, b, b.getWorld()))) {
      p.sendMessage(ChatColor.RED + "You can't use that here!");
      event.setCancelled(true);
    }
  }

  @EventHandler
  public void onPlayerInteract(PlayerInteractEntityEvent event) {
    Entity e = event.getRightClicked();
    Player p = event.getPlayer();
    if ((e instanceof ItemFrame)) {
      Region r = Potionprotect.rm.getRegion(e.getLocation());
      if ((r != null) && (!r.canBuild(p))) {
        p.sendMessage(ChatColor.RED + "You can't edit that item frame here!");
        event.setCancelled(true);
      }
    }
  }
}
