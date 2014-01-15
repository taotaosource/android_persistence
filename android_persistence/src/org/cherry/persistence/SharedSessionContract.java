package org.cherry.persistence;

import java.io.Serializable;


/**
 * Contract methods shared between {@link Session} and {@link StatelessSession}
 * 
  */
public interface SharedSessionContract extends Serializable {
	 /**
     * Begins a transaction in EXCLUSIVE mode.
     * <p>
     * Transactions can be nested.
     * When the outer transaction is ended all of
     * the work done in that transaction and all of the nested transactions will be committed or
     * rolled back. The changes will be rolled back if any transaction is ended without being
     * marked as clean (by calling setTransactionSuccessful). Otherwise they will be committed.
     * </p>
     * <p>Here is the standard idiom for transactions:
     *
     */
	public void beginTransaction();
	 /**
     * Marks the current transaction as successful. Do not do any more database work between
     * calling this and calling endTransaction. Do as little non-database work as possible in that
     * situation too. If any errors are encountered between this and endTransaction the transaction
     * will still be committed.
     *
     * @throws IllegalStateException if the current thread is not in a transaction or the
     * transaction is already marked as successful.
     */
    public void setTransactionSuccessful();
    
    /**
     * End a transaction. See beginTransaction for notes about how to use this and when transactions
     * are committed and rolled back.
     */
    public void endTransaction();
    
    /**
     * Returns true if the current thread has a transaction pending.
     *
     * @return True if the current thread is in a transaction.
     */
	public boolean inTransaction();
	
	/**
	 * Create {@link Criteria} instance for the given class (entity or subclasses/implementors)
	 *
	 * @param persistentClass The class, which is an entity, or has entity subclasses/implementors
	 *
	 * @return The criteria instance for manipulation and execution
	 */
	public  Criteria createCriteria(Class<?> persistentClass);
	
}
