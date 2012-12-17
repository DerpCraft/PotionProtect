package me.itidez.plugins.potionprotect;

/*
 *
 * Author: iTidez
 * Website: http://derpcraft.co/
 * Bugtracker: http://bug.derpcraft.co/
 * Jenkins: http://ci.derpcraft.co/
 * Licence: All Rights Reserved
 *
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
      this.id = null;
    }
  }
  
  public Potion(EntityPotion epotion) {
    this.basePotion = epotion;
    this.potion = getPotionFromEntity(epoiton);
    if(potion != null) {
     this.isPotion = true;
     this.id = potion.getTypeId();
    } else {
     this.isPotion = false;
     this.id = null;
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
      return potion
    else
      return null;
  }
  
  public int getId() {
    if(isPotion)
      return id
    else
      return null;
  }
  
  private EntityPotion getPotionFromEvent(ProjectileHitEvent e) {
    EntityPotion ption = (EntityPotion)((CraftEntity)e.getEntity()).getHandle();
    return ption;
  }
  
  private ItemStack getPotionFromEntity(EntityPotion ent) {
    try {
      Field d = EntityPotion.class.getDeclaredField("c");
      this.potion = new CraftItemStack((net.minecraft.server.ItemStack) d.get(ent));
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
  }
}
