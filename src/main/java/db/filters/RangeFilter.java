package db.filters;


import java.util.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.sql.RowSet;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.Predicate;

public class RangeFilter implements Predicate {
	private Object highValue;
	private Object lowValue;
	private String colName = null;
	private int colNumber = -1;

	public RangeFilter(Object lowValue, Object highValue, String colName) {
		this.highValue = highValue;
		this.lowValue = lowValue;
		this.colName = colName;
	}

	public RangeFilter(Object lowValue, Object highValue, int colNumber) {
		this.highValue = highValue;
		this.lowValue = lowValue;
		this.colNumber = colNumber;
	}

	@Override
	public boolean evaluate(RowSet rs) {
		CachedRowSet frs = (CachedRowSet) rs;
		boolean evaluation = false;
		String value=null;
		try {
			Object columnValue = null;

			if (this.colNumber > 0) {
				columnValue = frs.getObject(this.colNumber);
				value = String.valueOf(columnValue);
			} else if (this.colName != null) {
				columnValue = frs.getObject(this.colName);
				value = String.valueOf(columnValue);
			} else {
				return false;
			}
			if (!columnValue.equals(null)) {	
				if (columnValue instanceof Timestamp
						|| columnValue instanceof Date) {
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					String formatedLowDate = format.format(this.lowValue);
					String formatedHighDate = format.format(this.highValue);
					String sqlDate = format.format(columnValue);
					if ((format.parse(sqlDate).compareTo(
							format.parse(formatedLowDate)) >= 0)
							&& (format.parse(sqlDate).compareTo(
									format.parse(formatedHighDate)) <= 0)) {
						evaluation = true;
					}
				}else{
					if (((value)
							.compareTo((String) (this.lowValue)) >= 0)
							&& (value)
									.compareTo((String) (this.highValue)) <= 0) {
						evaluation = true;
					}
				}
			}
		} catch (SQLException e) {
			return false;
		} catch (NullPointerException npe) {
			return false;
		} catch (ParseException e) {
			return false;
		}
		return evaluation;
	}

	@Override
	public boolean evaluate(Object value, int column) throws SQLException {
		boolean evaluation = false;
		String columnValue = String.valueOf(value);
		if (column == this.colNumber) {	
			if (value instanceof Timestamp || value instanceof Date) {
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				String formatedLowDate = format.format(this.lowValue);
				String formatedHighDate = format.format(this.highValue);
				String sqlDate = format.format(value);
				try {
					if ((format.parse(sqlDate).compareTo(
							format.parse(formatedLowDate)) >= 0)
							&& (format.parse(sqlDate).compareTo(
									format.parse(formatedHighDate)) <= 0)) {
						evaluation = true;
					}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				if (((columnValue).compareTo((String) (this.lowValue)) >= 0)
						&& (columnValue)
								.compareTo((String) (this.highValue)) <= 0) {
					evaluation = true;
				}
			}
		}
		return evaluation;
	}

	@Override
	public boolean evaluate(Object value, String columnName)
			throws SQLException {
		boolean evaluation = false;
		String columnValue = String.valueOf(value);
		if (colName.equalsIgnoreCase(this.colName)) {
			if (value instanceof Timestamp || value instanceof Date) {
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				String formatedLowDate = format.format(this.lowValue);
				String formatedHighDate = format.format(this.highValue);
				String sqlDate = format.format(value);
				try {
					if ((format.parse(sqlDate).compareTo(
							format.parse(formatedLowDate)) >= 0)
							&& (format.parse(sqlDate).compareTo(
									format.parse(formatedHighDate)) <= 0)) {
						evaluation = true;
					}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				if (((columnValue).compareTo((String) (this.lowValue)) >= 0)
						&& (columnValue)
								.compareTo((String) (this.highValue)) <= 0) {
					evaluation = true;
				}
			}
		}
		return evaluation;
	}

}
