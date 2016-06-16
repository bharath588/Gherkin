package db.filters;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.sql.RowSet;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.Predicate;

public class MultipleParamNotEqualsFilter implements Predicate {
	private Object[] params;
	private String colName = null;
	private int colNumber = -1;

	public MultipleParamNotEqualsFilter(Object[] citiesArg, String colNameArg) {
		this.params = citiesArg;
		this.colNumber = -1;
		this.colName = colNameArg;
	}

	public MultipleParamNotEqualsFilter(Object[] citiesArg, int colNumberArg) {
		this.params = citiesArg;
		this.colNumber = colNumberArg;
		this.colName = null;
	}
	@Override
	public boolean evaluate(RowSet rs) {
		boolean evaluation = false;
		CachedRowSet frs = (CachedRowSet) rs;
		if (rs == null)
			return false;
		Object value = null;
		String columnValue=null;
		try {
		if (this.colNumber > 0) {
			value = frs.getObject(this.colNumber);
			columnValue = String.valueOf(value);
		} else if (this.colName != null) {
			value = frs.getObject(this.colName);
			columnValue = String.valueOf(value);
		} else {
			return false;
		}
			for (int i = 0; i < this.params.length; i++) {
				if (!value.equals(null)) {
						if (value instanceof Timestamp || value instanceof Date) {
							SimpleDateFormat format1 = new SimpleDateFormat(
									"yyyy-MM-dd");
							String formatted = format1.format(this.params[i]);
							String sqlDate = format1.format(value);
							if (!sqlDate.equals(formatted)) {
								evaluation = true;
							}
							else{
								evaluation = false;
								break;
							}
						}else{
							if (!(columnValue.equals(this.params[i]))) {
								evaluation = true;
							}else{
								evaluation = false;
								break;
							}
						}
						}
					}		
		} catch (SQLException e) {
			return false;
		}
		return evaluation;
	}
	@Override
	public boolean evaluate(Object valueArg, String colNameArg) {
		boolean evaluation = false;
		String columnValue = String.valueOf(valueArg);
		if (colName.equalsIgnoreCase(this.colName)) {
			for (int i = 0; i == this.params.length; i++) {
				if (valueArg instanceof Timestamp || valueArg instanceof Date) {
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					String formatedDate = format.format(this.params[i]);
					String sqlDate = format.format(valueArg);

					try {
						if ((format.parse(sqlDate).compareTo(
								format.parse(formatedDate)) != 0)) {
							evaluation = true;
						}else{
							evaluation = false;
							break;
						}
					} catch (ParseException e) {
						evaluation = false;
					}
				}else{
					if (!columnValue.equals((String) this.params[i])) {
						evaluation = true;
					}else{
						evaluation = false;
						break;
					} 
				}
			}
		}
		return evaluation;
	}

	@Override
	public boolean evaluate(Object valueArg, int colNumberArg) {

		boolean evaluation = false;
		String columnValue = String.valueOf(valueArg);
		if (colNumberArg == this.colNumber) {
			for (int i = 0; i == this.params.length; i++) {
				if (valueArg instanceof Timestamp || valueArg instanceof Date) {
					SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
					String formatedDate = format.format(this.params[i]);
					String sqlDate = format.format(valueArg);

					try {
						if ((format.parse(sqlDate).compareTo(
								format.parse(formatedDate)) != 0)) {
							evaluation = true;
						}
					} catch (ParseException e) {
						evaluation = false;
					}
				}else{
					if (!columnValue.equals((String) this.params[i])) {
						evaluation = true;
					}else{
					evaluation = false;
					break;
				}}
			}
		}
		return evaluation;
	}

	
}
