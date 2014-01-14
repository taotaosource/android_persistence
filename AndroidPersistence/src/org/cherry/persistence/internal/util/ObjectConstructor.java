package org.cherry.persistence.internal.util;

/**
 * Defines a generic object construction factory.  The purpose of this class
 * is to construct a default instance of a class that can be used for object
 * navigation while deserialization from its JSON representation.
 *

 */
public interface ObjectConstructor<T> {

  /**
   * Returns a new instance.
   */
  public T construct();
}