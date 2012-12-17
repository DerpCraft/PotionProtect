/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.itidez.plugins.potionprotect.region.builtin;

/**
 *
 * @author tjs238
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import me.itidez.plugins.potionprotect.Potionprotect;
import org.bukkit.Server;

public class Util
{
  public static Potionprotect plugin;

  public static void init(Potionprotect plugins)
  {
    plugin = plugins;
  }

  public static boolean isFileEmpty(String s)
  {
    File f = new File(s);
    if (!f.isFile()) {
      return true;
    }
    try
    {
      FileInputStream fis = new FileInputStream(s);
      int b = fis.read();
      if (b != -1)
        return false;
    }
    catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return true;
  }

  public static String formatName(String name)
  {
    String s = name.substring(1).toLowerCase();
    String fs = name.substring(0, 1).toUpperCase();
    String ret = fs + s;
    ret = ret.replace("_", " ");
    return ret;
  }

  public static Server getServer() {
    return plugin.getServer();
  }

  public static int[] toIntArray(List<Integer> list) {
    int[] ret = new int[list.size()];
    int i = 0;
    for (Integer e : list)
      ret[(i++)] = e.intValue();
    return ret;
  }
}
