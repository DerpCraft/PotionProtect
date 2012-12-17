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
import net.milkbowl.vault.chat.Chat;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public class PermissionHandler {
	//Important permission stuff: {
	final Chat permission;
	//}
	public PermissionHandler() throws Exception {
		RegisteredServiceProvider<Chat> provider = Bukkit.getServer().getServicesManager().getRegistration(Chat.class);
		if(provider == null) {
			Potionprotect.log("Vault not found, player limits will be set to the default.");
			permission = null;
			return;
		}
		permission = provider.getProvider();
	}

	public boolean hasPerm(Player p, String perm){
		return p.hasPermission(perm);
	}

	public boolean hasPerm(String pl, String perm){
		Player p = Bukkit.getServer().getPlayerExact(pl);
		if(p == null) {
			return false;
		}
		return p.hasPermission(perm);
	}

	public boolean hasRegionPerm(Player p, String s, Region poly){
		String adminperm = "potionprotect.admin." + s;
		String userperm = "potionprotect.own." + s;
		if (poly == null){
			return (hasPerm(p, adminperm)||hasPerm(p, userperm));
		}else{
			return hasPerm(p, adminperm)||(hasPerm(p, userperm)&&poly.isOwner(p));
		}
	}

	public boolean hasHelpPerm(Player p, String s){
		String adminperm = "potionprotect.admin." + s;
		String userperm = "potionprotect.own." + s;
		return (hasPerm(p, adminperm) || hasPerm(p, userperm));
	}

	public int getPlayerLimit(Player p){
		if(permission == null) return Potionprotect.limitAmount;
		return permission.getPlayerInfoInteger(p, "maxregionsize", Potionprotect.limitAmount);
	}
}
