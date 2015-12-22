package lib;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddressList;

import core.framework.Globals;




/*  ****************************************************************************************************************************************
	CLASS:				XL_ReadWrite	
	DESCRIPTION:	    XL_ReadWrite contains multiple methods which helps to interact with Excel.
	                    Methods listed in this class are meant to work with .xlsx files (XSSF - POI api used)
	FUNCTION LIST :     Non Static functions.
	                    Needs to be called using an object of the class XL_ReadWrite                 
	REVISION HISTORY: 
	****************************************************************************************************************************************
	Author : Souvik     Date : 01-10-2013       
	****************************************************************************************************************************************
*/	
public class XL_ReadWrite { 
	private String path;
	private FileInputStream fin = null;
	public FileOutputStream fout = null;
	private HSSFWorkbook workbook = null;
	private HSSFSheet sheet = null;
	private HSSFRow row = null;
	public HSSFCell cell = null;
	
/* ------------------------------------------------------------------------------------------------------------------------------------------------------------
   	CONSTRUCTOR:		XL_ReadWrite(String path)	
    DESCRIPTION:	    Default Constructor for Class XL_ReadWrite
    PARAMETERS: 		String path - (Excel Path)
    RETURNS:		    VOID
    EXAMPLE:	        new XL_ReadWrite(path)
    REVISION HISTORY: 
    ------------------------------------------------------------------------------------------------------------------------------------------------------------
    Author : Souvik     Date : 01-10-2013       
    ------------------------------------------------------------------------------------------------------------------------------------------------------------
*/	public XL_ReadWrite(String path) throws Exception{
	  this.path = path;
		try{ 
			fin = new FileInputStream(path);
			workbook = new HSSFWorkbook(fin);
			sheet = workbook.getSheetAt(0);
			fin.close();
	    } catch(FileNotFoundException e){
			throw new Exception("Exception occurred while finding the file : " + path + " while XLS initialize .Exception details : "+e.getMessage());
	    } catch(IOException e){
	    	throw new Exception("I/O interrupted exception occurred while XLS initialize .Exception details : " + e.getMessage());
	    } catch(Exception e){
	    	throw new Exception("Exception occurred while XLS initialize .Exception details : "+ e.getMessage());
	  }
	}
/*  ------------------------------------------------------------------------------------------------------------------------------------------------------------
   	FUNCTION:			getRowCount(String sheetName)	
    DESCRIPTION:	    gets the total used row count in a sheet 
    PARAMETERS: 		String sheetName
    RETURNS:		    int Row number
    EXAMPLE:	        getRowCount(String sheetName)	
    REVISION HISTORY: 
    ------------------------------------------------------------------------------------------------------------------------------------------------------------
    Author : Souvik     Date : 01-10-2013       
    ------------------------------------------------------------------------------------------------------------------------------------------------------------
*/  public int getRowCount(String sheetName) throws Exception{
		int number = 0;
		try{ 
			int index = workbook.getSheetIndex(sheetName);
			if(index < 0)
			   return 0;
			else{
			   sheet = workbook.getSheetAt(index);
			   number = sheet.getLastRowNum()+1;
			   return number;
			}
	    } catch(Exception e){
	    	throw new Exception("Exception occurred while getting row count for sheetname :" + sheetName+" . Exception details : "+e.getMessage());
	    }
	}
/*  ------------------------------------------------------------------------------------------------------------------------------------------------------------
	FUNCTION:			getColCount(int RowNum,String sheetName)	
	DESCRIPTION:	    gets the total used column count in a row in a sheet 
	PARAMETERS: 		int RowNum,String sheetName
	RETURNS:			integer Column number
	EXAMPLE:	        getColCount(int RowNum,String sheetName)	
	REVISION HISTORY: 
	------------------------------------------------------------------------------------------------------------------------------------------------------------
	Author : Souvik     Date : 01-10-2013       
	------------------------------------------------------------------------------------------------------------------------------------------------------------
*/  public int getColCount(int RowNum,String sheetName) throws Exception{
	 	int iCol = 0;   
	 	try{
			int index = workbook.getSheetIndex(sheetName);
		    sheet = workbook.getSheetAt(index);
			row = sheet.getRow(RowNum);
			iCol = row.getLastCellNum();
			return iCol;
	    } catch(Exception e){		   
		    throw new Exception("Exception occurred while getting col count for row number : " + RowNum + " in sheet name : " +sheetName+" .Exception details : "+e.getMessage());
	    }
	}
/*  ------------------------------------------------------------------------------------------------------------------------------------------------------------
	FUNCTION:			getCellData(String sheetName,int rowNum,String colName)	
	DESCRIPTION:	    gets the cell data from a specific sheet,specific row and specific column name 
	PARAMETERS: 		String sheetName,int rowNum,String colName
	RETURNS:			String Cell Data
	EXAMPLE:	        getCellData(String sheetName,int rowNum,String colName)		
	REVISION HISTORY: 
	------------------------------------------------------------------------------------------------------------------------------------------------------------
	Author : Souvik     Date : 02-10-2013       
	------------------------------------------------------------------------------------------------------------------------------------------------------------
*/  public String getCellData(String sheetName,int rowNum,String colName) throws Exception{
	    try{
		  int index = workbook.getSheetIndex(sheetName);
		  if (index < 0) return Globals.GC_DATASHEET_ERR;
		  if (rowNum < 0) return Globals.GC_EMPTY;
		  int colNum = -1; 
		  sheet = workbook.getSheetAt(index);
		  row = sheet.getRow(0);
		  colNum = getColNum(sheetName,rowNum,colName);
				   
		  if(colNum == -1) return Globals.GC_EMPTY;
		  row = sheet.getRow(rowNum);
		  if(row == null) return Globals.GC_EMPTY;
		  cell = row.getCell(colNum);
		  if(cell == null) return Globals.GC_EMPTY;
		   
		    switch (cell.getCellType()) {
				case Cell.CELL_TYPE_STRING:
					   return cell.getRichStringCellValue().getString();
				case Cell.CELL_TYPE_NUMERIC:   
					   if (DateUtil.isCellDateFormatted(cell)) {						 
						   String date = new SimpleDateFormat("MM/dd/yyyy").format(cell.getDateCellValue());
						   return date;						  
					   } else {   						   
						   double d = cell.getNumericCellValue();
						   if(d == Math.floor(d)){
							 cell.setCellType(Cell.CELL_TYPE_STRING);
							 return cell.getRichStringCellValue().getString(); 
						   }else{
							 String num = new Double(d).toString();
							 return num;
						   }
					   }
				case Cell.CELL_TYPE_BLANK:
					   return Globals.GC_EMPTY;
				case Cell.CELL_TYPE_BOOLEAN:
					   return String.valueOf(cell.getBooleanCellValue());
				default:
					   return "Default";
		    }		    
	    } catch(Exception e){
	    	 throw new Exception("Exception occurred while getting cell data from Row: " + rowNum + "and Column Name : " + colName+". Exception details : "+e.getMessage());
	    }
	}
/*  ------------------------------------------------------------------------------------------------------------------------------------------------------------
	FUNCTION:			getCellData(String sheetName,int rowNum,int colNum)	 OVERLOADED
	DESCRIPTION:	    gets the cell data from a specific sheet,specific row and specific col number
	PARAMETERS: 		String sheetName,int rowNum,int colNum
	RETURNS:			String Cell Data
	EXAMPLE:	        getCellData(String sheetName,int rowNum,int colNum)	
	REVISION HISTORY: 
	------------------------------------------------------------------------------------------------------------------------------------------------------------
	Author : Souvik     Date : 02-10-2013       
	------------------------------------------------------------------------------------------------------------------------------------------------------------
*/  public String getCellData(String sheetName,int rowNum,int colNum) throws Exception{
	    try{
		   int index = workbook.getSheetIndex(sheetName);
		   if (index < 0) return Globals.GC_DATASHEET_ERR;
		   if(rowNum < 0) return Globals.GC_EMPTY;
		   sheet = workbook.getSheetAt(index);
		   row = sheet.getRow(0);

		   if(colNum == -1) return Globals.GC_EMPTY;
		   row = sheet.getRow(rowNum);
		   if(row == null) return Globals.GC_EMPTY;
		   cell = row.getCell(colNum);
		   if(cell == null) return Globals.GC_EMPTY;
		   cell.setCellType(Cell.CELL_TYPE_STRING);
		
		    switch (cell.getCellType()) {
				case Cell.CELL_TYPE_STRING:
					   return cell.getRichStringCellValue().getString();
				case Cell.CELL_TYPE_NUMERIC:   
					   if (DateUtil.isCellDateFormatted(cell)) {						
						   String date = new SimpleDateFormat("MM/DD/yyyy").format(cell.getDateCellValue());
						   return date;						
					   } else {   						 
						   double d = cell.getNumericCellValue();
						   if(d == Math.floor(d)){
							 cell.setCellType(Cell.CELL_TYPE_STRING);
							 return cell.getRichStringCellValue().getString(); 
						   }else{
							 String num = new Double(d).toString();
							 return num;
						   }						   						   
					   }
				case Cell.CELL_TYPE_BLANK:
					   return Globals.GC_EMPTY;
				case Cell.CELL_TYPE_BOOLEAN:
					   return String.valueOf(cell.getBooleanCellValue());
				default:
					   return "Default";
	        }
   	    } catch(Exception e){
   	    	throw new Exception("Exception occurred while getting cell data from Row: " + rowNum + "and Column number : " + colNum+". Exception details : "+e.getMessage());
	    }
	}
/*  ------------------------------------------------------------------------------------------------------------------------------------------------------------
	FUNCTION:			getColNum(String sheetName,int rowNum,String colName)
	DESCRIPTION:	    gets the column number from a specific sheet and specific column name 
	PARAMETERS: 		String sheetName,int rowNum,String colName
	RETURNS:			int Column number
	EXAMPLE:	        getColNum(String sheetName,int rowNum,String colName)	
	REVISION HISTORY: 
    ------------------------------------------------------------------------------------------------------------------------------------------------------------
	Author : Souvik     Date : 02-10-2013       
    ------------------------------------------------------------------------------------------------------------------------------------------------------------
*/  public int getColNum(String sheetName,int rowNum,String colName) throws Exception{
		try{
		   int index = workbook.getSheetIndex(sheetName);
		   if (index < 0) return -1;
		   if (rowNum < 0) return -1;
		   int colNum = -1; 
		   sheet = workbook.getSheetAt(index);
		   row = sheet.getRow(0);
		   for(int iCol = 0;iCol<=row.getLastCellNum()-1;iCol++){
			   if(row.getCell(iCol).getStringCellValue().trim().equalsIgnoreCase(colName.trim())){
				colNum = iCol;break;     
			   }
		     } 
		   	   return colNum;
	    } catch(Exception e){
	    	throw new Exception("Exception occurred while getting Column number for Column name : " + colName+". Exception details : "+e.getMessage());
		}
    }

/*  ------------------------------------------------------------------------------------------------------------------------------------------------------------
	FUNCTION:			setCellData(String sheetName,String colName,int rowNum,String Data)
	DESCRIPTION:	    sets a cell value on a specific sheet,specific column name in a specific row
	PARAMETERS: 		String sheetName,String colName,int rowNum,String Data
	RETURNS:			null
	EXAMPLE:	        setCellData(String sheetName,String colName,int rowNum,String Data)
	COMMENTS:           To save the changes made in Excel, use workbook and fout of XL_ReadWrite class
	                    (e.g. XL.workbook.write(XL.fout);XL.fout.close();)	
	REVISION HISTORY: 
    ------------------------------------------------------------------------------------------------------------------------------------------------------------
	Author : Souvik     Date : 02-10-2013       
    ------------------------------------------------------------------------------------------------------------------------------------------------------------
*/  public void setCellData(String sheetName,String colName,int rowNum,String Data) throws Exception{
	    try{
		   fout = new FileOutputStream(path);	
		   HSSFCellStyle style = workbook.createCellStyle();
		   int index = workbook.getSheetIndex(sheetName);
		   int colNum = -1; 
		   sheet = workbook.getSheetAt(index);
		   row = sheet.getRow(0);
		   for(int iCol = 0;iCol<=row.getLastCellNum()-1;iCol++){
			   if(row.getCell(iCol).getStringCellValue().trim().equalsIgnoreCase(colName.trim())){
				colNum = iCol;break;     
			   }
		   }
		   row = sheet.getRow(rowNum);
		   if(row == null) row = sheet.createRow(rowNum);
		  
		   cell = row.createCell(colNum);	   
		   setStyle(cell,rowNum);
		   cell.setCellType(Cell.CELL_TYPE_STRING);
		   style.setAlignment(CellStyle.ALIGN_CENTER);
		   cell.setCellStyle(style);
		   cell.setCellValue(Data);
		   
	    } catch(FileNotFoundException e){
	    	throw new Exception("File No Found Exception occurred while setting cell data . Exception details : "+e.getMessage());  
		} catch(Exception e){
			throw new Exception("Exception occurred while setting data for Column: " + colName + " and Row: "+ rowNum+". Exception details : "+e.getMessage());  		
	    }
    }
/*  ------------------------------------------------------------------------------------------------------------------------------------------------------------
	FUNCTION:			setCellData(String sheetName,int rowNum,int colNum,String Data)  OVERLOADED
	DESCRIPTION:	    sets a cell value on a specific sheet,specific column column in a specific row
	PARAMETERS: 		String sheetName,int rowNum,int colNum,String Data
	RETURNS:			null
	EXAMPLE:	        setCellData(String sheetName,int rowNum,int colNum,String Data)	
	COMMENTS:           To save the changes made in Excel, use workbook and fout of XL_ReadWrite class
	                    (e.g. XL.workbook.write(XL.fout);XL.fout.close();)	
	REVISION HISTORY: 
	------------------------------------------------------------------------------------------------------------------------------------------------------------
	Author : Souvik     Date : 03-10-2013       
	------------------------------------------------------------------------------------------------------------------------------------------------------------
*/	public void setCellData(String sheetName,int rowNum,int colNum,String Data) throws Exception{
	    try{
		   fout = new FileOutputStream(path);	
		   HSSFCellStyle style = workbook.createCellStyle();
		   int index = workbook.getSheetIndex(sheetName);
		   
		   sheet = workbook.getSheetAt(index);
		   row = sheet.getRow(rowNum);
		   if(row == null) row = sheet.createRow(rowNum);
		    
		   cell = row.createCell(colNum);   
		   setStyle(cell,rowNum);
		   cell.setCellType(Cell.CELL_TYPE_STRING);
		   style.setAlignment(CellStyle.ALIGN_CENTER);
		   cell.setCellStyle(style);
		   cell.setCellValue(Data);		   
		} catch(Exception e){		 
		   throw new Exception("Exception occurred while setting data for Column: " + colNum + " and Row: "+ rowNum+". Exception details : "+e.getMessage());  			  
	    }
    }
/*  ------------------------------------------------------------------------------------------------------------------------------------------------------------
	FUNCTION:			addSheet(String sheetName)
	DESCRIPTION:	    Adds a sheet to a particular file
	PARAMETERS: 		String sheetName
	RETURNS:			null
	EXAMPLE:	        addSheet(String sheetName)	
	REVISION HISTORY: 
	------------------------------------------------------------------------------------------------------------------------------------------------------------
	Author : Souvik     Date : 03-10-2013       
	------------------------------------------------------------------------------------------------------------------------------------------------------------
*/	public void addSheet(String sheetName) throws Exception{
	    File file = new File(path);
		if (!file.exists()) {
	        try {
	            workbook = (HSSFWorkbook)WorkbookFactory.create(file);
	            sheet = workbook.createSheet(sheetName);	            
	        }catch (InvalidFormatException e) {
	        	throw new Exception("InvalidFormatException occurred while adding sheet : " + sheetName + ". Exception details : "+e.getMessage());	    
	        }catch(IOException e){
	        	throw new Exception("IOException occurred while adding sheet : " + sheetName + ". Exception details : "+e.getMessage());
	        }
	    }
	    else{
	    	throw new FileNotFoundException("Error : " + path + " not found");
	    }
	}
/*  ------------------------------------------------------------------------------------------------------------------------------------------------------------
	FUNCTION:			deleteSheet(String sheetName)
	DESCRIPTION:	    Deletes a sheet from a particular file
	PARAMETERS: 		String sheetName
	RETURNS:			boolean
	EXAMPLE:	        deleteSheet(String sheetName)	
	REVISION HISTORY: 
	------------------------------------------------------------------------------------------------------------------------------------------------------------
	Author : Souvik     Date : 03-10-2013       
	------------------------------------------------------------------------------------------------------------------------------------------------------------
*/	public void deleteSheet(String sheetName) throws Exception{
		try {
			int index = workbook.getSheetIndex(sheetName);			
			workbook.removeSheetAt(index);			
		}catch (Exception e){
			throw new Exception("Exception occurred while deleting sheet : " + sheetName+". Exception details : "+e.getMessage());
		}
	}
/*  ------------------------------------------------------------------------------------------------------------------------------------------------------------
	FUNCTION:			isSheetExist(String sheetName)
	DESCRIPTION:	    Confirms if the given sheet exist in a particular file
	PARAMETERS: 		String sheetName
	RETURNS:			null
	EXAMPLE:	        isSheetExist(String sheetName)	
	REVISION HISTORY: 
	------------------------------------------------------------------------------------------------------------------------------------------------------------
	Author : Souvik     Date : 03-10-2013       
	------------------------------------------------------------------------------------------------------------------------------------------------------------
*/	public boolean isSheetExist(String sheetName) throws Exception{
		try {
			int index = workbook.getSheetIndex(sheetName);
			if(index < 0) return Globals.GC_FALSE;
			return Globals.GC_TRUE;
		}catch (Exception e) {
			throw new Exception("Exception occurred checking if sheet exist : " + sheetName+". Exception details : "+e.getMessage());
		}
	}
/*  ------------------------------------------------------------------------------------------------------------------------------------------------------------
	FUNCTION:			addColumn(String sheetName,String colName)
	DESCRIPTION:	    Adds a column with a colName to a sheet  
	PARAMETERS: 		String sheetName,String colName
	RETURNS:			boolean
	EXAMPLE:	        addColumn(String sheetName,String colName)	
	REVISION HISTORY: 
	------------------------------------------------------------------------------------------------------------------------------------------------------------
	Author : Souvik     Date : 03-10-2013       
	------------------------------------------------------------------------------------------------------------------------------------------------------------
*/	public void addColumn(String sheetName,String colName) throws Exception{
		try {
			int index = workbook.getSheetIndex(sheetName);			
			int colNum = -1; 
			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(0);
			colNum = row.getLastCellNum()-1;
			setCellData(sheetName,0,colNum+1,colName);				
		}catch (Exception e) {
			throw new Exception("Exception occurred while adding column : " + colName + " for sheet name : "+ sheetName+". Exception details : "+e.getMessage());
		}
	}
/*  ------------------------------------------------------------------------------------------------------------------------------------------------------------
	FUNCTION:			setStyle(Cell cell,int rowNum)
	DESCRIPTION:	    Sets a Cell style  
	PARAMETERS: 		Cell cell,int rowNum
	RETURNS:			Nothing
	EXAMPLE:	        setStyle(Cell cell,int rowNum)	
	REVISION HISTORY: 
	------------------------------------------------------------------------------------------------------------------------------------------------------------
	Author : Souvik     Date : 03-10-2013       
	------------------------------------------------------------------------------------------------------------------------------------------------------------
*/	public void setStyle(Cell cell,int rowNum) throws Exception{
			try{	
			   HSSFCellStyle style = workbook.createCellStyle();
			   HSSFFont font = workbook.createFont();
			   if(rowNum > 0){
				  style.setFillForegroundColor(HSSFColor.WHITE.index);   
			   }else{
				   style.setFillForegroundColor(HSSFColor.YELLOW.index);
				   font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
				   style.setFont(font);
			   }			   
			   style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
			   style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			   style.setBorderRight(HSSFCellStyle.BORDER_THIN);
			   style.setBorderTop(HSSFCellStyle.BORDER_THIN);
			   style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			   style.setFillBackgroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
			   cell.setCellStyle(style);
			}catch(Exception e) {
				throw new Exception("Exception occurred while setting cell style ");
			}
		}
/*------------------------------------------------------------------------------------------------------------------------------------------------------------
FUNCTION:			setCellData(String sheetName,int rowNum,int colNum,String Data)  OVERLOADED
DESCRIPTION:	    sets a cell value on a specific sheet,specific column column in a specific row
PARAMETERS: 		String sheetName,int rowNum,int colNum,String Data
RETURNS:			boolean
EXAMPLE:	        setCellData(String sheetName,int rowNum,int colNum,String Data)	
COMMENTS:           To save the changes made in Excel, use workbook and fout of XL_ReadWrite class
                    (e.g. XL.workbook.write(XL.fout);XL.fout.close();)	
REVISION HISTORY: 
------------------------------------------------------------------------------------------------------------------------------------------------------------
Author : Souvik     Date : 03-10-2013       
------------------------------------------------------------------------------------------------------------------------------------------------------------
*/	public void setCellData(String sheetName,int rowNum,int colNum,String Data,String Colour) throws Exception{
    try{
       //fout = null;
       fout = new FileOutputStream(path);	
	   int index = workbook.getSheetIndex(sheetName);
	   
	   sheet = workbook.getSheetAt(index);
	   row = sheet.getRow(rowNum);
	   if(row == null) row = sheet.createRow(rowNum);
	   
	   cell = row.createCell(colNum);   
	   setStyle(cell,Colour);
	   cell.setCellType(Cell.CELL_TYPE_STRING);
	   cell.setCellValue(Data);  
	} catch(Exception e){
		 throw new Exception("Exception occurred while setting data for Column: " + colNum + " and Row : "+ rowNum + " and Sheet name: "+ sheetName+". Exception details : "+e.getMessage());
    }
}
/*  ------------------------------------------------------------------------------------------------------------------------------------------------------------
FUNCTION:			setStyle(Cell cell,int rowNum)
DESCRIPTION:	    Sets a Cell style  
PARAMETERS: 		Cell cell,int rowNum
RETURNS:			Nothing
EXAMPLE:	        setStyle(Cell cell,int rowNum)	
REVISION HISTORY: 
------------------------------------------------------------------------------------------------------------------------------------------------------------
Author : Souvik     Date : 03-10-2013       
------------------------------------------------------------------------------------------------------------------------------------------------------------
*/	public void setStyle(Cell cell,String Colour) throws Exception{
		try{	
		   HSSFCellStyle style = workbook.createCellStyle();
		   HSSFFont font = workbook.createFont();
		   if(Colour.equalsIgnoreCase("White")){
			  style.setFillForegroundColor(HSSFColor.WHITE.index);   
		   }else if(Colour.equalsIgnoreCase("Yellow")){
			   style.setFillForegroundColor(HSSFColor.YELLOW.index);
			   font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			   style.setFont(font);
		   }else if(Colour.equalsIgnoreCase("Grey")){
			   style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
			   font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			   style.setFont(font);
		   }else if(Colour.equalsIgnoreCase("Gold")){
			   style.setFillForegroundColor(HSSFColor.GOLD.index);
			   font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			   style.setFont(font);
		   }else if(Colour.equalsIgnoreCase("Blue")){
			   style.setFillForegroundColor(HSSFColor.LIGHT_CORNFLOWER_BLUE.index);
			   font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			   style.setFont(font);
		   }else if(Colour.equalsIgnoreCase("Green")){
			   style.setFillForegroundColor(HSSFColor.BRIGHT_GREEN.index);
			   font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			   style.setFont(font); 
		   }else if(Colour.equalsIgnoreCase("Red")){
			   style.setFillForegroundColor(HSSFColor.RED.index);
			   font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
			   style.setFont(font);
		   }
		   style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		   style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		   style.setBorderRight(HSSFCellStyle.BORDER_THIN);
		   style.setBorderTop(HSSFCellStyle.BORDER_THIN);
		   style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		   style.setFillBackgroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
		   cell.setCellStyle(style);
		}catch(Exception e) {
			throw new Exception("Exception occurred while setting cell style. Exception details : "+e.getMessage());
		}
	}
/*  ------------------------------------------------------------------------------------------------------------------------------------------------------------
FUNCTION:			setDataValidation(String sheetName,int rowNum,String colName,ArrayList<String> dataItems)
DESCRIPTION:	    
PARAMETERS: 		
RETURNS:			Nothing
EXAMPLE:	        	
REVISION HISTORY: 
------------------------------------------------------------------------------------------------------------------------------------------------------------
Author : Souvik     Date : 03-10-2013       
------------------------------------------------------------------------------------------------------------------------------------------------------------
*/	public void setDataValidation(String sheetName,int rowNum,String colName,ArrayList<String> dataItems) throws Exception{
		try{
		   HSSFSheet hiddenSheet;Name namedCell;	
		   if(workbook.getSheetIndex("hidden")>0){
			   hiddenSheet = workbook.getSheetAt(workbook.getSheetIndex("hidden"));
		   }else{
			   hiddenSheet = workbook.createSheet("hidden");  
		   }
		
		   for (int ihiddenRow=0;ihiddenRow<=dataItems.size()-1;ihiddenRow++) {
			   String name = dataItems.get(ihiddenRow);
			   HSSFRow row = hiddenSheet.createRow(ihiddenRow);
			   HSSFCell cell = row.createCell(0);
			   cell.setCellValue(name);
			 }
		   
		   if(workbook.getNameIndex("hidden")>=0){
			   namedCell = workbook.getName("hidden");   
		   }else{
			   namedCell = workbook.createName();		   
			   namedCell.setNameName("hidden");
		   }
		   		   
		   namedCell.setRefersToFormula("hidden!$A$1:$A$" + dataItems.size());
			
		   DataValidation dataValidation = null;  
		   DVConstraint constraint = null;
			 
		   fout = new FileOutputStream(path);	
		   int index = workbook.getSheetIndex(sheetName);
		   int colNum = -1; 
		   sheet = workbook.getSheetAt(index);
		   row = sheet.getRow(0);
		   for(int iCol = 0;iCol<=row.getLastCellNum()-1;iCol++){
			   if(row.getCell(iCol).getStringCellValue().trim().equalsIgnoreCase(colName.trim())){
				colNum = iCol;break;     
			   }
		   }
		   
		   row = sheet.getRow(rowNum);
		   if(row == null) row = sheet.createRow(rowNum);
		  
		   cell = row.createCell(colNum);	 
		   setStyle(cell,rowNum);
		   
		   constraint = DVConstraint.createFormulaListConstraint("hidden");
		   CellRangeAddressList itemList = new CellRangeAddressList(rowNum,rowNum,colNum,colNum);
		   dataValidation = new HSSFDataValidation(itemList, constraint);
		  
		   sheet.addValidationData(dataValidation);
		   workbook.setSheetHidden(workbook.getSheetIndex(hiddenSheet), true);
		   
		}catch(Exception e) {
			throw new Exception("Exception occurred while setting data validation. Exception details :" +e.getMessage());
		}
	}


	public void clearXL() throws Exception{	
		try{
			this.workbook.close();					
		}catch(Exception e){
			throw new Exception("Exception occurred while closing workbook. Exception details :" +e.getMessage());
		}
			
	}

	public void saveXL() throws Exception{		
		try{
			fout = new FileOutputStream(path);	
			this.workbook.write(this.fout);
			this.fout.close();
		}catch(Exception e){
			throw new Exception("Exception occurred while saving excel. Exception details :" +e.getMessage());
		}	
	}
	
	public void setFormulae(String sheetName,int rowNum,int colNo,String xlFormulae) throws Exception{
		try{			
			int index = workbook.getSheetIndex(sheetName);		   		 
			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(rowNum);		
			HSSFCell cell = row.createCell(colNo);
			cell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
			cell.setCellFormula(xlFormulae);
			workbook.getCreationHelper()
			.createFormulaEvaluator()
			.evaluateAll();					
		}catch(Exception e){
			throw new Exception("Exception occurred while setting formulae :" +e.getMessage());
		}		 
	}

}

