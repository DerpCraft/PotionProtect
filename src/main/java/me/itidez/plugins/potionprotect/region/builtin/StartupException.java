/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.itidez.plugins.potionprotect.region.builtin;

/**
 *
 * @author tjs238
 */
public class StartupException extends RuntimeException
{
  private static final long serialVersionUID = 8630115432743132912L;

  public StartupException(Throwable t)
  {
    super(t);
  }

  public StartupException(String message) {
    super(message);
  }

  public StartupException(String message, Throwable t) {
    super(message, t);
  }
}
