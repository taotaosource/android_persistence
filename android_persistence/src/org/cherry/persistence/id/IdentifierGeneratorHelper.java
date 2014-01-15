package org.cherry.persistence.id;

import java.io.Serializable;

/**
 * Factory and helper methods for {@link IdentifierGenerator} framework.
 *
 * 

 */
public final class IdentifierGeneratorHelper {
	/**
	 * Marker object returned from {@link IdentifierGenerator#generate} to indicate that the entity's identifier will
	 * be generated as part of the datbase insertion.
	 */
	public static final Serializable POST_INSERT_INDICATOR = new Serializable() {
		 
		private static final long serialVersionUID = -8579047005957504820L;

		@Override
		public String toString() {
			return "POST_INSERT_INDICATOR";
		}
	};
}