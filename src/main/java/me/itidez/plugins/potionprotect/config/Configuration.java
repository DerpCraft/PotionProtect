/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.itidez.plugins.potionprotect.config;

import java.util.ArrayList;
import java.util.List;
import me.itidez.plugins.potionprotect.Config;
import me.itidez.plugins.potionprotect.Potionprotect;

/**
 *
 * @author tjs238
 */
public class Configuration extends Config{
    public boolean PotionProtect_debug = false;
    public int PotionProtect_SmallRegionSize = 20;
    public int PotionProtect_MediumRegionSize = 30;
    public int PotionProtect_LargeRegionSize = 50;
    
    public Configuration(Potionprotect plugin) {
        this.setFile(plugin);
    }
    
}
