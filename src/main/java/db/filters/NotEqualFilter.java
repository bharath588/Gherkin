package db.filters;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.sql.RowSet;
import javax.sql.rowset.FilteredRowSet;
import javax.sql.rowset.Predicate;

import com.sun.rowset.FilteredRowSetImpl;

@SuppressWarnings("restriction")
public class NotEqualFilter implements Predicate,Cloneable{
	private Object filterOnValue;
	public NotEqualFilter(Object value, int colNumber) {
		
		this.filterOnValue = value;
		this.colNumber = colNumber;
	}

	public NotEqualFilter(Object value, String colName) {
		
		this.filterOnValue = value;
		this.fieldValue = colName;
	}

	private String fieldValue = null;
	private int colNumber = -1;
	
	/**
	 * This method is typically called a 
	 * FilteredRowSet object internal methods (not public) that control the RowSet object's cursor moving from row to the next. 
	 * In addition, if this internal method moves the cursor onto a row that has been deleted, the internal method will continue
	 *  to over the cursor until a valid row is found.
	 */
	
	@Override
	public boolean evaluate(RowSet rs) {

		FilteredRowSet frs = (FilteredRowSetImpl)rs;
		boolean evaluation = false;

		try {
			Object columnValue = null;
			String colValue="";
			if (this.colNumber > 0) {
				
				columnValue = frs.getObject(this.colNumber);
				colValue = String.valueOf(columnValue);
			} else if (this.fieldValue != null) {
				columnValue = frs.getObject(this.fieldValue);
				colValue = String.valueOf(columnValue);
			} else {
				return false;
			}

			if(columnValue == null && this.filterOnValue == null) {
			evaluation = false;
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
				evaluation = false;
			}else{
				evaluation = true;
			}
			}
			
			if(columnValue instanceof Timestamp || columnValue instanceof Date)
			{
				String sqlDate = null;
				String formatted = null;
				if(columnValue == null && this.filterOnValue == null) {
					evaluation = false;
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
					evaluation = false;
				}else{
					evaluation = true;
				}
				}
			}
		} catch (SQLException e) {
			return false;
		} catch (NullPointerException npe) {
			return false;
		}
		return evaluation;
	}

	@Override
	public boolean evaluate(Object value, int column) throws SQLException {
		boolean evaluation = true;

		if (this.colNumber == column) {
			if(value instanceof Integer){
			int columnValue = ((Integer) value).intValue();
			if ((columnValue == (Integer)this.filterOnValue)) {
				evaluation = true;
			} else {
				evaluation = false;
			}
			}
			
			if(value instanceof String){
				String columnValue = ((String) value);
				if (columnValue.equals((String)this.filterOnValue)) 
						{
					evaluation = true;
				} else {
					evaluation = false;
				}
		}
		}
		return evaluation;
	}
	
	@Override
	public boolean evaluate(Object value, String columnName)
			throws SQLException {
		boolean evaluation = true;
		if (columnName.equalsIgnoreCase(this.fieldValue)) {
			if(value instanceof Integer){
			int columnValue = ((Integer) value).intValue();
			if ((columnValue == (Integer)this.filterOnValue)){
				evaluation = true;
			} else {
				evaluation = false;	
			}
			}
			if(value instanceof String){
				String columnValue = ((String) value);
				if (columnValue.equals((String)this.filterOnValue)) 
						{
					evaluation = true;
				} else {
					evaluation = false;
				}
		}
		}
		return evaluation;
	}

}
