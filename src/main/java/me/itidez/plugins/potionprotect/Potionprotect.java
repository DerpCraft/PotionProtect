package me.itidez.plugins.potionprotect;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import java.io.File;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import me.itidez.plugins.potionprotect.config.Configuration;
import me.itidez.plugins.potionprotect.region.builtin.*;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Potionprotect extends JavaPlugin{
    public Server server = Bukkit.getServer();
    public PluginManager pm;
    private static Logger log;
    private PluginDescriptionFile description;
    private static String prefix;
    public Permission permission;
    public static Potionprotect potionprotect;
    public static Configuration config;
    public static WorldEditPlugin worldedit;
    public static WorldGuardPlugin worldguard;
    public static boolean isUsingWorldGuard;
    public BlockListener bListener;
    public PlayerListener pListener;
    public EntityListener eListener;
    public WorldListener wListener;
    public static PermissionHandler ph;
    public static RegionManager rm;
    public static final String lineSeparator = System.getProperty("line.separator");
    public static final HashMap<Player, Location> firstLocationSelections = new HashMap();
    public static final HashMap<Player, Location> secondLocationSelections = new HashMap();
    
    public static final String pathMain = "plugins" + File.separator + "PotionProtect" + File.separator;
    public static final String pathData = pathMain + File.separator + "data" + File.separator;
    
    public static FILE_TYPE fileType = FILE_TYPE.oosgz;
    public static boolean removeBlocks = false;
    public static int limitAmount = 400;
    public static int blockID = 55;
    public static int maxScan = 600;
    public static int heightStart = 50;
    public static boolean backup = true;
    public static int adminWandID = Material.FEATHER.getId();
    public static int infoWandID = Material.STRING.getId();
    public static String version;
    
    @Override
    public void onDisable() {
        rm.saveAll();
    }

    @Override
    public void onEnable() {
        log = Logger.getLogger("Minecraft");
        description = getDescription();
        version = description.getVersion();
        prefix = "["+description.getName()+"] ";
        log("Starting up");
        if(setupWorldEdit()) {
            log("Loading WorldEdit");
        }
        if(setupWorldGuard()) {
            log("Found WorldGuard - Using WorldGuard for region management!");
            isUsingWorldGuard = true;
        } else {
            log("WorldGuard not found! Using built in region management!");
            isUsingWorldGuard = false;
            try {
                setupBuiltInRegion();
            } catch (Exception ex) {
                Logger.getLogger(Potionprotect.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        potionprotect = this;
        config = new Configuration(this);
        config.load();
    }
    
    public static void log(String message){
        log.log(Level.INFO, "{0}{1}", new Object[]{prefix, message});
    }
    
    public static void debug(String message) {
        boolean debug = config.PotionProtect_debug;
        if (debug)
        log.log(Level.INFO, "{0}{1}", new Object[] {"[DEV]",prefix, message});
    } 
    
    private boolean setupWorldEdit() {
        worldedit = (WorldEditPlugin)getServer().getPluginManager().getPlugin("WorldEdit");
        return (worldedit != null);
    }
    
    private boolean setupWorldGuard() {
        worldguard = (WorldGuardPlugin)getServer().getPluginManager().getPlugin("WorldGuard");
        return (worldguard != null);
    }
    
    private void setupBuiltInRegion() throws Exception{
        this.pm = getServer().getPluginManager();
        this.bListener = new BlockListener(this);
        this.pListener = new PlayerListener(this);
        this.eListener = new EntityListener(this);
        this.wListener = new WorldListener(this);
        rm = new RegionManager();
        Util.init(this);
        rm.loadAll();
        this.pm.registerEvents(this.bListener, this);
        this.pm.registerEvents(this.pListener, this);
        this.pm.registerEvents(this.eListener, this);
        this.pm.registerEvents(this.wListener, this);
        ph = new PermissionHandler();
    }
    
    public static enum FILE_TYPE {
                yml,
		ymlgz,
		oos,
		oosgz,
		mysql
    }
}

