package org.cherry.persistence.engine.spi;

/**
 * 
 */
public final class QueryParameters {

	private RowSelection rowSelection;
	private Object[] positionalParameterValues;

	public QueryParameters() {
	}

	public QueryParameters(RowSelection rowSelection, Object[] positionalParameterValues) {
		super();
		this.rowSelection = rowSelection;
		this.positionalParameterValues = positionalParameterValues;
	}

	public Object[] getPositionalParameterValues() {
		return positionalParameterValues;
	}

	public void setPositionalParameterValues(Object[] positionalParameterValues) {
		this.positionalParameterValues = positionalParameterValues;
	}

	public RowSelection getRowSelection() {
		return rowSelection;
	}

	public void setRowSelection(RowSelection rowSelection) {
		this.rowSelection = rowSelection;
	}

}
