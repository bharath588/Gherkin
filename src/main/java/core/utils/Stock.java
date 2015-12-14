package core.utils;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import core.framework.Globals;
import core.utils.Log.Level;
import core.utils.ThrowException.TYPE;

public class Stock {
	public static Map<String, String> globalParam = new LinkedHashMap<String, String>();
	private static int dataProviderIterations;
	public static Map<String, String> globalTestdata;

	public static Object[][] setDataProvider(LinkedHashMap<Integer, Map<String, String>> dataObj) {
		// Converting Map to Object[][] to handle @DataProvider
		Object[][] tdObject = null;
		try{
			if (dataObj != null) {
				tdObject = new Object[dataObj.size()][2];
				int count = 0;
				for (Map.Entry<Integer, Map<String, String>> entry : dataObj.entrySet()) {
					tdObject[count][0] = entry.getKey();
					tdObject[count][1] = entry.getValue();
					count++;
				}
				dataProviderIterations = tdObject.length;
				return tdObject;
			}
		}catch(Exception e){
			ThrowException.Report(TYPE.EXCEPTION, "Exception occurred while setting DataProvider : " + e.getMessage());
		}	
		return new Object[][] { {} }; // As null cannot be returned in DataProvider
	}
	
	public static int getIterations(){
		return dataProviderIterations;
	}

	public static LinkedHashMap<Integer, Map<String, String>> getTestData(String tcAbsPath, String tcName)
			throws Exception {
		Log.Report(Level.INFO, Globals.GC_LOG_INITTC_MSG + tcAbsPath + "." + tcName + Globals.GC_LOG_INITTC_MSG);

		// Getting Application name and Module name so that the
		// correct excel is picked up
		LinkedHashMap<Integer, Map<String, String>> td = null;
		Map<String, String> mapData = null;
		String appName = tcAbsPath.split("\\.")[1];
		String modName = tcAbsPath.split("\\.")[3];
		boolean ifTCFound = false;
		Log.Report(Level.DEBUG, "Preparing test data for Test Case : " + tcName);

		try {
			XL_ReadWrite XL = new XL_ReadWrite(Globals.GC_TESTDATALOC + Globals.GC_TESTDATAPREFIX + appName + ".xls");
			int tcColNo = XL.getColNum(modName, 0, Globals.GC_TESTDATATCCOLNAME);
			int itrColNo = XL.getColNum(modName, 0, Globals.GC_ITRCCOLNAME);
			int itcPointer = 0;

			// Finding the TC in given TestData sheet
			for (; itcPointer <= XL.getRowCount(modName); itcPointer++) {
				if (XL.getCellData(modName, itcPointer, tcColNo).trim().equalsIgnoreCase(tcName)) {
					Log.Report(Level.DEBUG, tcName + " found in " + modName + " sheet for row : " + itcPointer);
					ifTCFound = true;
					break;
				}
			}

			if (ifTCFound) { // Loop to TD index
				String strLoopPattern = XL.getCellData(modName, itcPointer, itrColNo).trim();
				int counter = 0;
				// Run ALL Test Data iteration
				if (strLoopPattern.equals(Globals.GC_EMPTY)
						|| strLoopPattern.equalsIgnoreCase(Globals.GC_VAL_RUNALLITR)) {
					for (int iLoop = itcPointer; iLoop <= XL.getRowCount(modName); iLoop++) {
						if (XL.getCellData(modName, iLoop + 2, itrColNo - 1).trim().equals(tcName)) {
							counter = counter + 1;
						} else if (XL.getCellData(modName, iLoop + 2, itrColNo - 1).trim().equals(Globals.GC_EMPTY)) {
							break;
						}
					}
					if (counter > 1) {
						strLoopPattern = ("1>" + String.valueOf(counter));
					} else {
						strLoopPattern = "1";
					}
				}

				td = new LinkedHashMap<Integer, Map<String, String>>();
				Map<String, String> testDataItr = getLoopIndex(strLoopPattern);

				for (String itrNo : testDataItr.keySet()) {
					mapData = new HashMap<String, String>();
					// Loop to point to TC iterations
					for (int iSelIndx = itcPointer + 2; iSelIndx <= XL.getRowCount(modName); iSelIndx++) {
						if (itrNo.equals(XL.getCellData(modName, iSelIndx, itrColNo))) {
							// Loop through the column to generate Key,Value
							// pair TD map
							for (int iColCnt = itrColNo + 1; iColCnt <= XL.getColCount(iSelIndx - 1,
									modName); iColCnt++) {
								if (!XL.getCellData(modName, itcPointer + 1, iColCnt).trim().equals(Globals.GC_EMPTY)) {
									mapData.put(XL.getCellData(modName, itcPointer + 1, iColCnt).trim(),
											XL.getCellData(modName, iSelIndx, iColCnt).trim());
									Log.Report(Level.DEBUG,
											"test data mapped for index " + itrNo + " with key "
													+ XL.getCellData(modName, itcPointer + 1, iColCnt) + " and value "
													+ XL.getCellData(modName, iSelIndx, iColCnt));
								}
							}
							mapData.remove("");
							td.put(Integer.valueOf(itrNo), mapData);
							break;
						}
					}
				}
				XL.clearXL();
			} else {
				Log.Report(Level.DEBUG, tcName + " not found in test data : " + modName + " sheet");
			}
			return td;
		} catch (Exception e) {
			ThrowException.Report(TYPE.EXCEPTION, "Exception occurred while preparing test data : " + e.getMessage());
		}
		return null;
	}

	public static Map<String, String> getLoopIndex(String indexString) throws Exception {
		String GET_INDEX_PATTERN = "[0-9,>temp]+";
		String[] arrFirstSplit = null;
		Pattern pattern = null;
		Matcher matcher = null;
		Map<String, String> mapIndex = new LinkedHashMap<String, String>();
		Log.Report(Level.INFO, "computing pattern : " + indexString + " to get test data index");
		if (!indexString.contains(",")) {
			indexString = indexString + ",temp";
		}
		try {
			pattern = Pattern.compile(GET_INDEX_PATTERN);
			matcher = pattern.matcher(indexString);
			if (!matcher.matches()) {
				ThrowException.Report(TYPE.ILLEGALSTATE, "Pattern mismatch found for getLoopIndex() -"
						+ " Only allowed characters: '0-9,>' and no consecutive repetions of operator is allowed (>>)");
			}
			if (indexString.contains(",")) {
				arrFirstSplit = indexString.split(",");
			} else { // to do else if
				arrFirstSplit = (indexString).split(">");
			}

			if (arrFirstSplit.length > 1) {
				String[] arrayOfString1;
				int j = (arrayOfString1 = arrFirstSplit).length;
				for (int i = 0; i < j; i++) {
					String str = arrayOfString1[i];
					if (str.split(">").length - 1 != 0) {
						int varA = Integer.parseInt(str.split(">")[1]);
						int varB = Integer.parseInt(str.split(">")[0]);
						if (varA - varB > 0) {
							for (int iLoop = 0; iLoop <= varA - varB; iLoop++) {
								mapIndex.put(Integer.toString(varB + iLoop), "");
							}
						}
					} else {
						mapIndex.put(str, "");
					}
				}
			}
			mapIndex.remove("");
			mapIndex.remove("temp");
			return mapIndex;
		} catch (Exception e) {
			ThrowException.Report(TYPE.EXCEPTION, "Exception at getLoopIndex() : " + e.getMessage());
		}
		return null;
	}

	public static void getParam(String configPath) throws Exception {
		String key = Globals.GC_EMPTY;
		String val = Globals.GC_EMPTY;
		XL_ReadWrite xlRW = null;
		if (globalParam.isEmpty()) {
			try {
				xlRW = new XL_ReadWrite(configPath);
				Log.Report(Level.INFO, "reading " + configPath + " file");
				for (int iConfLoop = 0; iConfLoop < xlRW.getRowCount(Globals.GC_CONFIGFILEANDSHEETNAME)
						- 1; iConfLoop++) {
					key = xlRW.getCellData(Globals.GC_CONFIGFILEANDSHEETNAME, iConfLoop + 1, Globals.GC_COLNAME_CONFIG)
							.trim();
					val = xlRW.getCellData(Globals.GC_CONFIGFILEANDSHEETNAME, iConfLoop + 1, Globals.GC_COLNAME_VALUE)
							.trim();
					globalParam.put(key, val);
					Log.Report(Level.DEBUG, "setting @globalParam with key :" + key + " -- value :" + val);
				}
				globalParam.remove(Globals.GC_EMPTY);
				xlRW.clearXL();
				xlRW = null;
			} catch (Exception e) {
				ThrowException.Report(TYPE.EXCEPTION, "Unable to read Config :" + e.getMessage());
			}
		}

	}
	
	public static String[] getTestQuery(String queryName) throws Exception
	{
		String[] queryData;
		XL_ReadWrite XL = new XL_ReadWrite(Globals.GC_TESTDATALOC + Globals.GC_TESTDATAPREFIX + Stock.globalParam.get("AUT") + ".xls");
		int queryColNo = XL.getColNum("query", 0,"QueryName");
		int queryPointer = 0;

		for (; queryPointer < XL.getRowCount("query"); queryPointer++) {
			if(XL.getCellData("query",queryPointer, queryColNo).equalsIgnoreCase(queryName)){
				break;
			}
		}
		queryData = new String[]{XL.getCellData("query",queryPointer, queryColNo+1)
				,XL.getCellData("query",queryPointer, queryColNo+2)};		
		return queryData;
	}

}
