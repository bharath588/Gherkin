package db.filters;


import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.sql.RowSet;
import javax.sql.rowset.FilteredRowSet;
import javax.sql.rowset.Predicate;
//import javax.sql.rowset.RowSetProvider;

//import com.sun.rowset.FilteredRowSetImpl;

public class EqualsFilter implements Predicate,Cloneable{
	private Object filterOnValue;//filterOnValue
	private String fieldName = null;//fieldName
	private int colNumber = -1;
	public EqualsFilter(Object value, int colNumber) {
		this.filterOnValue = value;
		this.colNumber = colNumber;
	}

	public EqualsFilter(Object value, String colName) {
		
		this.filterOnValue = value;
		this.fieldName = colName;
	}

	/**
	 * This method is typically called a 
	 * FilteredRowSet object internal methods (not public) that control the RowSet object's cursor moving from row to the next. 
	 * In addition, if this internal method moves the cursor onto a row that has been deleted, the internal method will continue
	 *  to over the cursor until a valid row is found.
	 */

	@Override
	public boolean evaluate(RowSet rs) {
		FilteredRowSet frs =(FilteredRowSet) rs;
	
		boolean evaluation = false;

		try {
			Object columnValue = null;
			String colValue="";
			if (this.colNumber > 0) {
				
				columnValue = frs.getObject(this.colNumber);
				colValue = String.valueOf(columnValue);
			} else if (this.fieldName != null) {
				columnValue = frs.getObject(this.fieldName);
				colValue = String.valueOf(columnValue);
			} else {
				return false;
			}

			if(columnValue == null && this.filterOnValue == null) {
			evaluation = true;
			}
			else if(columnValue != null && this.filterOnValue == null)
			{
				evaluation = false;
			}
			else if(columnValue == null && this.filterOnValue != null)
			{
				evaluation = false;
			}
			else{
				
			if ((colValue.equals(this.filterOnValue))) {
				evaluation = true;
			}else{
				evaluation = false;
			}
			}
			
			if(columnValue instanceof Timestamp || columnValue instanceof Date)
			{
				String sqlDate = null;
				String formatted = null;
				if(columnValue == null && this.filterOnValue == null) {
					evaluation = true;
					}
					else if(columnValue != null && this.filterOnValue == null)
					{
						evaluation = false;
					}
					else if(columnValue == null && this.filterOnValue != null)
					{
						evaluation = false;
					}
					else{
				SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
				formatted = format1.format(this.filterOnValue);
				sqlDate = format1.format(columnValue);
				if (sqlDate.equals(formatted)) {
					evaluation = true;
				}else{
					evaluation = false;
				}
				}
			}
		} catch (SQLException e) {
			// JDBCTutorialUtilities.printSQLException(e);
			return false;
		} catch (NullPointerException npe) {
			return false;
		}
		return evaluation;
	}

	/**
	 * This method is called by a FilteredRowSet object to check whether the value lies between the filtering criterion (or criteria if multiple constraints exist) set using the setFilter() method. 
	 * The FilteredRowSet object will use this method internally while inserting new rows to a FilteredRowSet instance.
	 */
	@Override
	public boolean evaluate(Object value, int column) throws SQLException {
		boolean evaluation = true;

		if (this.colNumber == column) {
			String columnValue = String.valueOf(value);
				if (columnValue.equals((String)this.filterOnValue)) 
						{
					evaluation = true;
				} else {
					evaluation = false;
				}
		}
		return evaluation;
	}
	
	

	/**
	 * <p>
	 * This method is called by a FilteredRowSet object to check whether the value lies between the filtering criterion (or criteria if multiple constraints exist) set using the setFilter() method. 
	 * The FilteredRowSet object will use this method internally while inserting new rows to a FilteredRowSet instance.
	 * </p>
	 * @param value,columnName
	 */
	@Override
	public boolean evaluate(Object value, String columnName)
			throws SQLException {
		boolean evaluation = true;
		if (columnName.equalsIgnoreCase(this.fieldName)) {
			String columnValue = String.valueOf(value);
				if (columnValue.equals((String)this.filterOnValue)) 
						{
					evaluation = true;
				} else {
					evaluation = false;
				}
		}
		return evaluation;
	}
	
	
	

}
