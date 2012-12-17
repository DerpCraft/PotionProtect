/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.itidez.plugins.potionprotect.region.builtin;

/**
 *
 * @author tjs238
 */
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import me.itidez.plugins.potionprotect.Potionprotect;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class RegionManager
{
  Map<World, WorldRegionManager> regionManagers = new HashMap();

  public void loadAll() throws Exception {
    Iterator worlds = Bukkit.getWorlds().iterator();
    while (worlds.hasNext()) {
      World w = (World)worlds.next();

      if (this.regionManagers.containsKey(w))
        continue;
      WorldRegionManager mgr;
      mgr = new WorldFlatFileRegionManager(w);
      mgr.load();
      this.regionManagers.put(w, mgr);
    }
  }

  public void load(World w) throws Exception {
    if (this.regionManagers.containsKey(w))
      return;
    WorldRegionManager mgr;
    mgr = new WorldFlatFileRegionManager(w);
    mgr.load();
    this.regionManagers.put(w, mgr);
  }

  public void unload(World w) {
    if (!this.regionManagers.containsKey(w)) {
      return;
    }
    WorldRegionManager mgr = (WorldRegionManager)this.regionManagers.get(w);
    mgr.save();
    this.regionManagers.remove(w);
  }

  public void saveAll() {
    Iterator rms = this.regionManagers.values().iterator();
    while (rms.hasNext())
      ((WorldRegionManager)rms.next()).save();
  }

  public void save(World w)
  {
    ((WorldRegionManager)this.regionManagers.get(w)).save();
  }

  public Region getRegion(String name, World w) {
    return ((WorldRegionManager)this.regionManagers.get(w)).getRegion(name);
  }

  public int getTotalRegionSize(String name) {
    int ret = 0;
    Iterator rms = this.regionManagers.values().iterator();
    while (rms.hasNext()) {
      ret += ((WorldRegionManager)rms.next()).getTotalRegionSize(name);
    }
    return ret;
  }

  public Set<Region> getWorldRegions(Player player, World w) {
    return ((WorldRegionManager)this.regionManagers.get(w)).getRegions(player);
  }

  public Set<Region> getRegions(String player) {
    Set ret = new HashSet();
    Iterator rms = this.regionManagers.values().iterator();
    while (rms.hasNext()) {
      ret.addAll(((WorldRegionManager)rms.next()).getRegions(player));
    }
    return ret;
  }

  public Set<Region> getRegions(Player player) {
    return getRegions(player.getName());
  }

  public Set<Region> getRegionsNear(Player player, int i, World w) {
    return ((WorldRegionManager)this.regionManagers.get(w)).getRegionsNear(player, i);
  }

  public Set<Region> getRegions(String string, World w) {
    return ((WorldRegionManager)this.regionManagers.get(w)).getRegions(string);
  }

  public Region getRegion(Player player, World w) {
    return ((WorldRegionManager)this.regionManagers.get(w)).getRegion(player);
  }

  public void add(Region region, World w) {
    ((WorldRegionManager)this.regionManagers.get(w)).add(region);
  }

  public void remove(Region reg) {
    Iterator rms = this.regionManagers.values().iterator();
    while (rms.hasNext())
      ((WorldRegionManager)rms.next()).remove(reg);
  }

  public boolean canBuild(Player p, Block b, World w)
  {
    return ((WorldRegionManager)this.regionManagers.get(w)).canBuild(p, b);
  }

  public boolean isSurroundingRegion(Region rect, World w) {
    return ((WorldRegionManager)this.regionManagers.get(w)).isSurroundingRegion(rect);
  }

  public boolean regionExists(Block block, World w) {
    return ((WorldRegionManager)this.regionManagers.get(w)).regionExists(block);
  }

  public boolean regionExists(int x, int z, World w) {
    return ((WorldRegionManager)this.regionManagers.get(w)).regionExists(x, z);
  }

  public Region getRegion(Location location) {
    return ((WorldRegionManager)this.regionManagers.get(location.getWorld())).getRegion(location);
  }

  public Set<Region> getPossibleIntersectingRegions(Region r, World w)
  {
    return ((WorldRegionManager)this.regionManagers.get(w)).getPossibleIntersectingRegions(r);
  }

  public void rename(Region rect, String name, World world) {
    WorldRegionManager rm = (WorldRegionManager)this.regionManagers.get(world);
    if (!rm.regionExists(rect)) {
      return;
    }
    rm.setRegionName(rect, name);
  }

  public void setFlag(Region rect, int flag, boolean value, World world) {
    WorldRegionManager rm = (WorldRegionManager)this.regionManagers.get(world);
    if (!rm.regionExists(rect)) {
      return;
    }
    rm.setFlagValue(rect, flag, value);
  }
}
