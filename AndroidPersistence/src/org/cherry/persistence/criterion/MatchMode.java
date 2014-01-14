package org.cherry.persistence.criterion;

/**
 * Represents an strategy for matching strings using "like".
 *
  * @see Example#enableLike(MatchMode)
 */
public enum MatchMode {

	/**
	 * Match the entire string to the pattern
	 */
	EXACT {
		public String toMatchString(String pattern) {
			return pattern;
		}
	},

	/**
	 * Match the start of the string to the pattern
	 */
	START {
		public String toMatchString(String pattern) {
			return pattern + '%';
		}
	},

	/**
	 * Match the end of the string to the pattern
	 */
	END {
		public String toMatchString(String pattern) {
			return '%' + pattern;
		}
	},

	/**
	 * Match the pattern anywhere in the string
	 */
	ANYWHERE {
		public String toMatchString(String pattern) {
			return '%' + pattern + '%';
		}
	};

	/**
	 * convert the pattern, by appending/prepending "%"
	 */
	public abstract String toMatchString(String pattern);

}





