/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.itidez.plugins.potionprotect;

import java.lang.reflect.Field;
import net.minecraft.server.EntityPotion;
import org.bukkit.craftbukkit.entity.CraftEntity;
import org.bukkit.craftbukkit.inventory.CraftItemStack;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author tjs238
 */
public class Potion {
  public EntityPotion basePotion;
  public ItemStack potion;
  public int id;
  public boolean isPotion;
  
  public Potion(ProjectileHitEvent e) {
    this.basePotion = getPotionFromEvent(e);
    this.potion = getPotionFromEntity(basePotion);
    if(potion != null) {
      this.isPotion = true;
      this.id = potion.getTypeId();
    } else {
      this.isPotion = false;
      this.id = 0;
    }
  }
  
  public Potion(EntityPotion epotion) {
    this.basePotion = epotion;
    this.potion = getPotionFromEntity(epotion);
    if(potion != null) {
     this.isPotion = true;
     this.id = potion.getTypeId();
    } else {
     this.isPotion = false;
     this.id = 0;
    }
  }
  
  public boolean isPotion() {
    return isPotion;
  }
  
  public EntityPotion getBasePotion() {
    if(isPotion)
      return basePotion;
    else
      return null;
  }
  
  public ItemStack getPotion() {
    if(isPotion)
      return potion;
    else
      return null;
  }
  
  public int getId() {
    if(isPotion)
      return id;
    else
      return 0;
  }
  
  private EntityPotion getPotionFromEvent(ProjectileHitEvent e) {
    EntityPotion ption = (EntityPotion)((CraftEntity)e.getEntity()).getHandle();
    return ption;
  }
  
  private ItemStack getPotionFromEntity(EntityPotion ent) {
    ItemStack epotion = null;
    try {
      Field d = EntityPotion.class.getDeclaredField("c");
      epotion = new CraftItemStack((net.minecraft.server.ItemStack) d.get(ent));
    }
    catch (NoSuchFieldException e1) {
      e1.printStackTrace();
    }
    catch (SecurityException e1) {
      e1.printStackTrace();
    }
    catch (IllegalArgumentException e1) {
      e1.printStackTrace();
    }
    catch (IllegalAccessException e1) {
      e1.printStackTrace();
    }
    return epotion;
  }
}
