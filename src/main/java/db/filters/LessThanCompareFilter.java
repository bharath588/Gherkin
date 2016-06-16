package db.filters;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.sql.RowSet;
import javax.sql.rowset.Predicate;

public class LessThanCompareFilter implements Predicate {
	private Object value;
	private String colName = null;
	private int colNumber = -1;

	public LessThanCompareFilter(Object value, int colNumber) {

		this.value = value;
		this.colNumber = colNumber;
	}

	public LessThanCompareFilter(Object value, String colName) {

		this.value = value;
		this.colName = colName;
	}
	@Override
	public boolean evaluate(RowSet rs) {
		boolean evaluation = false;
		
		if (rs == null)
			return false;

		try {
			String columnValue = null;
			Object value = null;

			if (this.colNumber < 0) {
				value = rs.getObject(this.colNumber);
				columnValue = String.valueOf(value);
			} else if (this.colName != null) {
				value = rs.getObject(this.colName);
				columnValue = String.valueOf(value);
			} else {
				return false;
			}


			if (!value.equals(null)) {		
					if (value instanceof Timestamp || value instanceof Date) {
						SimpleDateFormat format = new SimpleDateFormat(
								"yyyy-MM-dd");
						String formatted = format.format(this.value);
						String sqlDate = format.format(value);
						if (format.parse(sqlDate).compareTo(
								format.parse(formatted)) < 0) {
							evaluation = true;
						} else {
							evaluation = false;
						}
					}else{
						if (((columnValue).compareTo((String) this.value)) < 0) {
							evaluation = true;
						} else {
							evaluation = false;
						}
					}
				}
		} catch (SQLException e) {
			return false;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
				String formatedDate = format.format(this.value);
				String sqlDate = format.format(value);
				try {
					if ((format.parse(sqlDate).compareTo(
							format.parse(formatedDate)) < 0)) {
						evaluation = true;
					}
				} catch (ParseException e) {
					evaluation = false;
				}
			}else{

				if (((columnValue).compareTo((String) (this.value)) < 0))
				{
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
				String formatedDate = format.format(this.value);
				String sqlDate = format.format(value);
				try {
					if ((format.parse(sqlDate).compareTo(
							format.parse(formatedDate)) < 0)) {
						evaluation = true;
					}
				} catch (ParseException e) {
					evaluation = false;
				}
			}else{

				if (((columnValue).compareTo((String) (this.value)) < 0))
				{
					evaluation = true;
				}
			}
		}
		return evaluation;
	}

}
