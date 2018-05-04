package aag.DRMBatch;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

import lib.DB;
import lib.Reporter;
import lib.Stock;

import org.apache.commons.io.FileUtils;
import org.joda.time.Days;
import org.joda.time.LocalDate;

import shell.utils.ShellUtils;
import aag.aag_lib.General;

import com.aventstack.extentreports.Status;

import core.framework.Globals;

public class DRMUtils {

	ResultSet queryResultset;

	public void runDrmBatch() throws IOException {
		Reporter.logEvent(Status.INFO, "Run DRM Batch", "Run DRM Batch", false);
		FileUtils.cleanDirectory(new File(Globals.GC_REMOTE_OUTPUT_PATH));
		ShellUtils.executeShellCommand("cd ~");
		ShellUtils.executeShellCommand("bash");
		General.threadSleepInvocation(5000);
		ShellUtils.executeShellCommand(Globals.GC_SELECT_ENVIRONMENT);
		General.threadSleepInvocation(5000);
		ShellUtils.executeShellCommand(0);
		General.threadSleepInvocation(5000);
		ShellUtils
				.runAndWriteShellCommandsToFile(Globals.GC_SELECT_FOLDER_FOR_RUNNING_JOB);
		ShellUtils.runAndWriteShellCommandsToFile("./DRMSweep.ksh");
		General.threadSleepInvocation(5000);
		Reporter.logEvent(Status.PASS, "Run DRM Batch Job",
				"DRM Batch Job is Completed", false);
	}

	// In gaservice
	public boolean checkIfPlanHasDRM(String gaId) {
		queryResultset = DB.executeQuery(Globals.dbNameAlias,
				Stock.getTestQuery("chekcForDRMinGaServiceQuery")[1], gaId);
		if (DB.getRecordSetCount(queryResultset) > 0) {
			return true;
		} else {
			return false;
		}
	}

	// In partservice
	public boolean checkInPlanServiceforDRM(String gaId) {
		queryResultset = DB.executeQuery(Globals.dbNameAlias,
				Stock.getTestQuery("checkForDrmInPartServiceQuery")[1], gaId);

		if (DB.getRecordSetCount(queryResultset) > 0) {
			return true;
		} else {
			return false;
		}
	}

	public String getPpt(String gaId) throws Exception {
		String indId = null;
		queryResultset = DB.executeQuery(Globals.dbNameAlias,
				Stock.getTestQuery("getDrmPpt")[1], gaId);
		System.out.println("");

		while (queryResultset.next()) {
			indId = queryResultset.getString("IND_ID");
			break;
		}

		return indId;
	}

	public void updatePartServiceRecords(String indId) throws Exception {
		DB.executeUpdate(Globals.dbNameAlias,
				Stock.getTestQuery("updatePartService")[1], indId);
	}

	// In gaservice
	public boolean checkIfPlanOffersMA(String gaId) {
		queryResultset = DB.executeQuery(Globals.dbNameAlias,
				Stock.getTestQuery("checkForMAinGaService")[1], gaId);

		if (DB.getRecordSetCount(queryResultset) > 0) {
			return true;
		} else {
			return false;
		}
	}

	public boolean checkForGDIORuleSetup(String gaId) {
		queryResultset = DB.executeQuery(Globals.dbNameAlias,
				Stock.getTestQuery("checkForGDIORuleSetup")[1], gaId);

		if (DB.getRecordSetCount(queryResultset) > 0) {
			return true;
		} else {
			return false;
		}
	}

	// Ppt level setup

	public boolean checkForDistribution(String pptId) throws SQLException {
		queryResultset = DB.executeQuery(Globals.dbNameAlias,
				Stock.getTestQuery("")[1], pptId);
		boolean hasBalance = false;
		long balAmt = 0;
		while (queryResultset.next()) {
			balAmt = Long.parseLong(queryResultset.getString("AMOUNT"));
			break;
		}

		if (balAmt > 0) {
			hasBalance = true;
		} else {
			hasBalance = false;
		}
		return hasBalance;
	}

	public void verifyStatusCodePostDrm(String pptId) throws SQLException {
		queryResultset = DB.executeQuery(Globals.dbNameAlias,
				Stock.getTestQuery("getpartServiceRecords")[1], pptId);
		String statusCode = null;
		String statusReasonCode = null;
		String effdate = null;
		while (queryResultset.next()) {
			statusReasonCode = queryResultset.getString("status_reason_code");
			statusCode = queryResultset.getString("STATUS_CODE");
			effdate = queryResultset.getString("EFFDATE");
			break;
		}
		if (statusReasonCode.equalsIgnoreCase("NOTIFICATION PROCESS STARTED")) {
			Reporter.logEvent(Status.PASS,
					"Validate the status reason code post drm batch",
					statusReasonCode, false);
		} else {
			Reporter.logEvent(Status.FAIL,
					"Validate the status reason code post drm batch",
					statusReasonCode, false);
		}
		if (statusCode.equalsIgnoreCase("NS")) {
			Reporter.logEvent(Status.PASS,
					"Validate the status code post drm batch", statusCode,
					false);
		} else {
			Reporter.logEvent(Status.FAIL,
					"Validate the status code post drm batch", statusCode,
					false);
		}
		if (!effdate
				.equalsIgnoreCase(new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss")
						.format(new java.util.Date(System.currentTimeMillis())))) {
			Reporter.logEvent(Status.PASS,
					"Validate the effdate post drm batch", effdate, false);
		} else {
			Reporter.logEvent(Status.FAIL,
					"Validate the effdate post drm batch", effdate, false);
		}
	}

	public void checkForBasicDrmPlanSetup(String gaId) {

		if (checkIfPlanHasDRM(gaId)) {
			Reporter.logEvent(Status.PASS,
					"Check if Plans must have DRM turned on in ga_service",
					"Plans has DRM turned on in ga_service", false);
		} else {
			Reporter.logEvent(Status.FAIL,
					"Check if Plans must have DRM turned on in ga_service",
					"Plan does not have DRM turned on in ga_service", false);
		}

		if (checkInPlanServiceforDRM(gaId)) {
			Reporter.logEvent(Status.PASS,
					"Check if Plans must have DRM turned on in part_service",
					"Plans has DRM turned on in part_service", false);
		} else {
			Reporter.logEvent(Status.FAIL,
					"Check if Plans must have DRM turned on in part_service",
					"Plan does not have DRM turned on in part_service", false);
		}

		if (checkIfPlanOffersMA(gaId)) {
			Reporter.logEvent(
					Status.PASS,
					"Check if Plans must have Managed accounts turned on in ga_service",
					"Plans has MA turned on in ga_service", false);
		} else {
			Reporter.logEvent(Status.FAIL,
					"Check if Plans must have MA turned on in ga_service",
					"Plan does not have MA turned on in ga_service", false);
		}

		if (checkForGDIORuleSetup(gaId)) {
			Reporter.logEvent(Status.PASS,
					"Check if Plans have GDIO rule setup",
					"Plan has GDIO rule setup", false);
		} else {
			Reporter.logEvent(Status.FAIL,
					"Check if Plans have GDIO rule setup",
					"Plan doesnot have GDIO rule setup", false);
		}
	}

	public void getPptBirthdate(String ppt) throws SQLException {

		queryResultset = DB.executeQuery(Globals.dbNameAlias,
				Stock.getTestQuery("getPPtBirthdate")[1], ppt);
		String birthdate = null;
		while (queryResultset.next()) {
			birthdate = queryResultset.getString("BIRTH_DATE");
		}

		Reporter.logEvent(Status.PASS, "Check for participants birthdate",
				birthdate, false);
	}

	public void getPptTransitionDate(String ppt) throws SQLException {
		queryResultset = DB.executeQuery(Globals.dbNameAlias,
				Stock.getTestQuery("getpartServiceRecords")[1], ppt);
		String transDate = null;
		while (queryResultset.next()) {
			transDate = queryResultset.getString("EFFDATE");
		}

		Reporter.logEvent(Status.PASS, "Check for participants transitiondate",
				transDate, false);
	}

	public void checkForTypeCode(String pptId) throws Exception {
		queryResultset = DB.executeQuery(Globals.dbNameAlias,
				Stock.getTestQuery("getpartServiceRecords")[1], pptId);
		String typecode = null;

		while (queryResultset.next()) {
			typecode = queryResultset.getString("TYPE_CODE");
			break;
		}
		if (typecode == null || !typecode.equalsIgnoreCase("DEFAULT")) {
			Reporter.logEvent(Status.PASS, "check for participants type code",
					typecode, false);
		} else {
			Reporter.logEvent(Status.INFO, "check for participants type code",
					"The type code is default for the ppt hence updating",
					false);
			try {
				DB.executeUpdate(Globals.dbNameAlias,
						Stock.getTestQuery("")[1], pptId);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void checkForQDRO(String pptId) throws Exception {
		queryResultset = DB.executeQuery(Globals.dbNameAlias,
				Stock.getTestQuery("checkQDRO")[1], pptId);
		String typecode = null;

		while (queryResultset.next()) {
			typecode = queryResultset.getString("ownership_ind");
			break;
		}
		if (!typecode.equalsIgnoreCase("T")) {
			Reporter.logEvent(Status.PASS, "check for participants QDRO ",
					"The qdro code is :" + typecode, false);
		} else {
			Reporter.logEvent(Status.INFO,
					"check for participant does not have qdro beneficiary acc",
					"The ppt contains qdro acc hence updating the same", false);
			try {
				DB.executeUpdate(Globals.dbNameAlias,
						Stock.getTestQuery("")[1], pptId);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void verifyEnrollmentChannel(String pptId) throws SQLException {
		queryResultset = DB.executeQuery(Globals.dbNameAlias,
				Stock.getTestQuery("getpartServiceRecords")[1], pptId);
		String enrollChannel = null;

		while (queryResultset.next()) {
			enrollChannel = queryResultset.getString("ext_service_acct_nbr");
			break;
		}
		if (enrollChannel.equalsIgnoreCase("DYNAMICRETMGR")) {
			Reporter.logEvent(Status.PASS,
					"check for participants enrollment channel post drm batch",
					enrollChannel, false);
		} else {
			Reporter.logEvent(Status.FAIL,
					"check for participants enrollment channel post drm batch",
					enrollChannel, false);

		}
	}

	public void verifyIsisFeedBatch(String pptId) throws SQLException {
		queryResultset = DB.executeQuery(Globals.dbNameAlias,
				Stock.getTestQuery("getIsisFeedBatchTodorecord")[1], pptId);
		String statusCode = null;
		String batch_number = null;

		while (queryResultset.next()) {
			Reporter.logEvent(Status.PASS,
					"Check if record has created in isis feed batch todo",
					"Record has been created post drm batch", false);
			statusCode = queryResultset.getString("status_code");
			batch_number = queryResultset.getString("batch_number");

			Reporter.logEvent(Status.PASS, "Details from isis feed batch todo",
					"Status code " + statusCode + "and batch number "
							+ batch_number, false);
		}

		if (DB.getRecordSetCount(queryResultset) == 0) {
			Reporter.logEvent(
					Status.FAIL,
					"Check if record has created in isis feed batch todo",
					"Record has not been created in isis feed batch to do post drm batch",
					false);
		}

	}

	public String getPptForFEPlan(String gaId) throws SQLException {
		String pptId = null;
		queryResultset = DB.executeQuery(Globals.dbNameAlias,
				Stock.getTestQuery("getPartUnderaPlan")[1], gaId);

		while (queryResultset.next()) {
			pptId = queryResultset.getString("IND_ID");
			break;
		}
		return pptId;
	}

	public boolean checkIfPlanOffersMorningStar(String gaId) {
		boolean planOffersMStar = false;

		queryResultset = DB.executeQuery(Globals.dbNameAlias,
				Stock.getTestQuery("checkIfPlanOffersMorningStar")[1], gaId);

		if (DB.getRecordSetCount(queryResultset) > 0) {
			planOffersMStar = true;
		}
		return planOffersMStar;
	}

	public String getPptForMorningstarPlan(String gaId) throws SQLException {
		String pptId = null;
		queryResultset = DB.executeQuery(Globals.dbNameAlias,
				Stock.getTestQuery("getPartUnderaPlan")[1], gaId);

		while (queryResultset.next()) {
			pptId = queryResultset.getString("IND_ID");
			break;
		}
		return pptId;
	}

	public void setUpPptforFEMA(String pptId) throws Exception {
		// setup in emplotment for 16b and expat
		DB.executeUpdate(Globals.dbNameAlias,
				Stock.getTestQuery("updateExpatAnd16b")[1], pptId);
		DB.executeUpdate(Globals.dbNameAlias,
				Stock.getTestQuery("updateEnrollmentStatus")[1], pptId);
		DB.executeUpdate(Globals.dbNameAlias,
				Stock.getTestQuery("updateDefaultBirthdateIndicator")[1], pptId);
		DB.executeUpdate(Globals.dbNameAlias,
				Stock.getTestQuery("updateSdaCode")[1], pptId);

	}

	public void setupPptforMorningstar(String pptId) throws Exception {
		DB.executeUpdate(Globals.dbNameAlias,
				Stock.getTestQuery("updateDefaultBirthdateIndicator")[1], pptId);
		DB.executeUpdate(Globals.dbNameAlias,
				Stock.getTestQuery("updateEnrollmentStatus")[1], pptId);
		updateIndividualBirthdate(pptId);
	}

	public void checkForBasicDrmPlanSetupMstar(String gaId) {
		if (checkIfPlanHasDRM(gaId)) {
			Reporter.logEvent(Status.PASS,
					"Check if Plans must have DRM turned on in ga_service",
					"Plans has DRM turned on in ga_service", false);
		} else {
			Reporter.logEvent(Status.FAIL,
					"Check if Plans must have DRM turned on in ga_service",
					"Plan does not have DRM turned on in ga_service", false);
		}

		if (checkInPlanServiceforDRM(gaId)) {
			Reporter.logEvent(Status.PASS,
					"Check if Plans must have DRM turned on in part_service",
					"Plans has DRM turned on in part_service", false);
		} else {
			Reporter.logEvent(Status.FAIL,
					"Check if Plans must have DRM turned on in part_service",
					"Plan does not have DRM turned on in part_service", false);
		}

		if (checkIfPlanOffersMStar(gaId)) {
			Reporter.logEvent(
					Status.PASS,
					"Check if Plans must have Managed accounts turned on in ga_service",
					"Plans has MA turned on in ga_service", false);
		} else {
			Reporter.logEvent(Status.FAIL,
					"Check if Plans must have MA turned on in ga_service",
					"Plan does not have MA turned on in ga_service", false);
		}

		if (checkForGDIORuleSetup(gaId)) {
			Reporter.logEvent(Status.PASS,
					"Check if Plans have GDIO rule setup",
					"Plan has GDIO rule setup", false);
		} else {
			Reporter.logEvent(Status.FAIL,
					"Check if Plans have GDIO rule setup",
					"Plan doesnot have GDIO rule setup", false);
		}

	}

	private void updateIndividualBirthdate(String indId) throws Exception
	{
		DB.executeUpdate(Globals.dbNameAlias, Stock.getTestQuery("updateIndividualBirthdate")[1], indId);
	}
	private boolean checkIfPlanOffersMStar(String gaId) {
		queryResultset = DB.executeQuery(Globals.dbNameAlias,
				Stock.getTestQuery("checkForMAStarinGaService")[1], gaId);

		if (DB.getRecordSetCount(queryResultset) > 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Check for the record in transaction table
	 * @return
	 */
	public void checkIfTransactionTableRecordisCreated() {
		queryResultset = DB.executeQuery(Globals.dbNameAlias,
				Stock.getTestQuery("verifyTransactionTable")[1]);

		if (DB.getRecordSetCount(queryResultset) > 0) {
			Reporter.logEvent(Status.PASS, "Verify if the transaction record is created after DRM batch", "Record is present in transaction table", false);
		} else {
			Reporter.logEvent(Status.FAIL, "Verify if the transaction record is created after DRM batch", "Record is not present in transaction table", false);
		}
	}

	
	public void checkIfStepTypeRecord() {
		queryResultset = DB.executeQuery(Globals.dbNameAlias,
				Stock.getTestQuery("verifyStepTypeRecord")[1]);

		if (DB.getRecordSetCount(queryResultset) > 0) {
			Reporter.logEvent(Status.PASS, "Verify  step_type record is present on the system with following parameters: cod = 'MA_ DRM', evty_code = 'MANAGEACCT', descr = 'MANAGED ACCOUNTS DRM TRANSITION NOTIFICATION", "Record is present in the step_type table", false);
		} else {
			Reporter.logEvent(Status.FAIL, "Verify  step_type record is present on the system with following parameters: cod = 'MA_ DRM', evty_code = 'MANAGEACCT', descr = 'MANAGED ACCOUNTS DRM TRANSITION NOTIFICATION", "Record is not present in the step_type table", false);
		}
	}

	public String getDocumentTableRecords(String indId, String gaId)
			throws SQLException {
		String docId = null;
		queryResultset = DB.executeQuery(Globals.dbNameAlias,
				Stock.getTestQuery("getDocumentTableRecords")[1], indId, gaId);

		if (DB.getRecordSetCount(queryResultset) > 0) {
			Reporter.logEvent(Status.PASS,
					"Verify if record is created in document table",
					"Record has been created in document table" + "for : "
							+ indId + gaId, false);
		} else {
			Reporter.logEvent(Status.FAIL,
					"Verify if record is created in document table",
					"Record has not created in document table" + "for : "
							+ indId + gaId, false);
		}

		String mailingName1 = "";
		String mailingName2 = "";
		String mailingName3 = "";
		String cityName = "";
		String country = "";
		String zipCode = "";
		while (queryResultset.next()) {
			docId = queryResultset.getString("ID");
			String dmty_code = queryResultset.getString("DMTY_CODE");
			Reporter.logEvent(Status.INFO, "ID", docId, false);
			Reporter.logEvent(Status.INFO, "DMTY CODE", dmty_code, false);
			break;
		}

		queryResultset = DB.executeQuery(Globals.dbNameAlias,
				Stock.getTestQuery("getIndividualDetails")[1], indId);
		while (queryResultset.next()) {
			String mailingName1IndTable = queryResultset
					.getString("MAILING_NAME_1");
			String mailingName2IndTable = queryResultset
					.getString("MAILING_NAME_2");
			String mailingName3IndTable = queryResultset
					.getString("MAILING_NAME_3");

			if (mailingName1.equalsIgnoreCase(mailingName1IndTable)) {
				Reporter.logEvent(Status.PASS,
						"Verify if the first mailing address is same",
						"Mailing adreess are similar"
								+ "From individual table : "
								+ mailingName1IndTable + "from document table "
								+ mailingName1, false);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify if the first mailing address is same",
						"Mailing adreess are not similar"
								+ "From individual table : "
								+ mailingName1IndTable + "from document table "
								+ mailingName1, false);
			}
			
			if (mailingName2.equalsIgnoreCase(mailingName2IndTable)) {
				Reporter.logEvent(Status.PASS,
						"Verify if the first mailing address is same",
						"Mailing adreess are similar"
								+ "From individual table : "
								+ mailingName2IndTable + "from document table "
								+ mailingName2, false);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify if the first mailing address is same",
						"Mailing adreess are not similar"
								+ "From individual table : "
								+ mailingName2IndTable + "from document table "
								+ mailingName2, false);
			}
			
			if (mailingName1.equalsIgnoreCase(mailingName1IndTable)) {
				Reporter.logEvent(Status.PASS,
						"Verify if the first mailing address is same",
						"Mailing adreess are similar"
								+ "From individual table : "
								+ mailingName3IndTable + "from document table "
								+ mailingName3, false);
			} else {
				Reporter.logEvent(Status.FAIL,
						"Verify if the first mailing address is same",
						"Mailing adreess are not similar"
								+ "From individual table : "
								+ mailingName3IndTable + "from document table "
								+ mailingName3, false);
			}
			
			
		}
		queryResultset = DB.executeQuery(Globals.dbNameAlias, Stock.getTestQuery("getIndividualAddressDetails")[1], indId);
		
		while(queryResultset.next())
		{
		String cityNameAddress = queryResultset.getString("CITY");
		String countryNameAddress = queryResultset.getString("COUNTRY");
		String zipCodeAddress = queryResultset.getString("ZIP_CODE");
		

		if (cityNameAddress.equalsIgnoreCase(cityName)) {
			Reporter.logEvent(Status.PASS,
					"Verify if the first mailing address is same",
					"Mailing adreess are similar"
							+ "From address table : "
							+ cityNameAddress + "from document table "
							+ cityName, false);
		} else {
			Reporter.logEvent(Status.FAIL,
					"Verify if the first mailing address is same",
					"Mailing adreess are not similar"
							+ "From address table : "
							+ cityNameAddress + "from document table "
							+ cityName, false);
		}
		
		if (countryNameAddress.equalsIgnoreCase(country)) {
			Reporter.logEvent(Status.PASS,
					"Verify if the first mailing address is same",
					"Mailing adreess are similar"
							+ "From address table : "
							+ countryNameAddress + "from document table "
							+ country, false);
		} else {
			Reporter.logEvent(Status.FAIL,
					"Verify if the first mailing address is same",
					"Mailing adreess are not similar"
							+ "From address table : "
							+ countryNameAddress + "from document table "
							+ country, false);
		}
		
		if (zipCodeAddress.equalsIgnoreCase(zipCode)) {
			Reporter.logEvent(Status.PASS,
					"Verify if the first mailing address is same",
					"Mailing adreess are similar"
							+ "From address table : "
							+ zipCodeAddress + "from document table "
							+ zipCode, false);
		} else {
			Reporter.logEvent(Status.FAIL,
					"Verify if the first mailing address is same",
					"Mailing adreess are not similar"
							+ "From address table : "
							+ zipCodeAddress + "from document table "
							+ zipCode, false);
		}
		}
		return docId;
		
	}
	
	
	public void getDocRecvrDestTableDetails(String docId) throws Exception
	{
	queryResultset = DB.executeQuery(Globals.dbNameAlias, Stock.getTestQuery("getDetailsOfDocRecvr")[1], docId);
	
	while(queryResultset.next())
	{
		String sjtyCode = queryResultset.getString("SJTY_CODE");
		String userLogonId = queryResultset.getString("USER_LOGON_ID");
		String docRevrType =  queryResultset.getString("DOC_RECVR_TYPE");
		String retToUserId = queryResultset.getString("RETURN_TO_USER_IND");
		String printStatusCode = queryResultset.getString("PRINT_STATUS_CODE");
		String priorityPrintInd = queryResultset.getString("PRIORITY_PRINT_IND");
		String masterDocId = queryResultset.getString("MASTER_DOC_ID");
		
		if(sjtyCode.equalsIgnoreCase("IND"))
		{
			Reporter.logEvent(Status.PASS, "Verify if sjtyCode is displayed as expected", "sjty Code is as expected", false);
		}else{
			Reporter.logEvent(Status.FAIL, "Verify if sjtyCode is displayed as expected", "sjty Code is not as expected", false);
		}
		
		if(userLogonId.equalsIgnoreCase(Stock.getConfigParam(Globals.GC_COLNAME_USERID)))
		{
			Reporter.logEvent(Status.PASS, "Verify if userLogonId is displayed as expected", "userLogonId is as expected", false);
		}else{
			Reporter.logEvent(Status.FAIL, "Verify if userLogonId is displayed as expected", "userLogonId is not as expected", false);
		}
		
		if(docRevrType.equalsIgnoreCase("P"))
		{
			Reporter.logEvent(Status.PASS, "Verify if docRevrType is displayed as expected", "docRevrType is as expected", false);
		}else{
			Reporter.logEvent(Status.FAIL, "Verify if docRevrType is displayed as expected", "docRevrType is not as expected", false);
		}
		
		if(retToUserId.equalsIgnoreCase("N"))
		{
			Reporter.logEvent(Status.PASS, "Verify if retToUserId is displayed as expected", "retToUserId is as expected", false);
		}else{
			Reporter.logEvent(Status.FAIL, "Verify if retToUserId is displayed as expected", "retToUserId is not as expected", false);
		}
		
		if(printStatusCode.equalsIgnoreCase("W"))
		{
			Reporter.logEvent(Status.PASS, "Verify if printStatusCode is displayed as expected", "printStatusCode is as expected", false);
		}else{
			Reporter.logEvent(Status.FAIL, "Verify if printStatusCode is displayed as expected", "printStatusCode is not as expected", false);
		}
		
		if(priorityPrintInd.equalsIgnoreCase("N"))
		{
			Reporter.logEvent(Status.PASS, "Verify if priorityPrintInd is displayed as expected", "priorityPrintInd is as expected", false);
		}else{
			Reporter.logEvent(Status.FAIL, "Verify if priorityPrintInd is displayed as expected", "priorityPrintInd is not as expected", false);
		}
		
		if(masterDocId.equalsIgnoreCase("SINGLE"))
		{
			Reporter.logEvent(Status.PASS, "Verify if masterDocId is displayed as expected", "masterDocId is as expected", false);
		}else{
			Reporter.logEvent(Status.FAIL, "Verify if masterDocId is displayed as expected", "masterDocId is not as expected", false);
		}
		
	}
	
	}
	public void verifyStatusCodePostDrm2(String pptId) throws SQLException {
		queryResultset = DB.executeQuery(Globals.dbNameAlias,
				Stock.getTestQuery("getpartServiceRecords")[1], pptId);
		String statusCode = null;
		String statusReasonCode = null;
		String effdate = null;
		while (queryResultset.next()) {
			statusReasonCode = queryResultset.getString("status_reason_code");
			statusCode = queryResultset.getString("STATUS_CODE");
			effdate = queryResultset.getString("EFFDATE");
			break;
		}
		if (statusReasonCode.equalsIgnoreCase("IN TRANSITION")) {
			Reporter.logEvent(Status.PASS,
					"Validate the status reason code post drm batch",
					statusReasonCode, false);
		} else {
			Reporter.logEvent(Status.FAIL,
					"Validate the status reason code post drm batch",
					statusReasonCode, false);
		}
		if (statusCode.equalsIgnoreCase("IT")) {
			Reporter.logEvent(Status.PASS,
					"Validate the status code post drm batch", statusCode,
					false);
		} else {
			Reporter.logEvent(Status.FAIL,
					"Validate the status code post drm batch", statusCode,
					false);
		}
		java.util.Date dt = new java.util.Date();
		if (effdate
				.equalsIgnoreCase(new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss")
						.format(LocalDateTime.from(dt.toInstant()).plusDays(30)))) {
			Reporter.logEvent(Status.PASS,
					"Validate the effdate post drm batch", effdate, false);
		} else {
			Reporter.logEvent(Status.FAIL,
					"Validate the effdate post drm batch", effdate, false);
		}
	}
	
	
	public void ceckIfNewStepRecordIsCreated()
	{
		
	}
	
	public void verifyIsisFeedBatchTodoPostDrm2(String pptId) throws SQLException
	{
		queryResultset = DB.executeQuery(Globals.dbNameAlias,
				Stock.getTestQuery("getIsisFeedBatchTodorecord")[1], pptId);
		String statusCode = null;
		

		while (queryResultset.next()) {
			Reporter.logEvent(Status.PASS,
					"Check if record has created in isis feed batch todo",
					"Record has been created post drm batch", false);
			statusCode = queryResultset.getString("status_code");
			

			Reporter.logEvent(Status.PASS, "Details from isis feed batch todo",
					"Status code " + statusCode , false);
			
			if(statusCode.equalsIgnoreCase("COMPLETE"))
			{
				Reporter.logEvent(Status.PASS, "Verify if status code changed to complete", "Status code is changed to complete", false);
			}else{
				Reporter.logEvent(Status.FAIL, "Verify if status code changed to complete", "Status code is not changed to complete", false);
			}
		}
	}
	
	public void verifyEventCreated(String indId)
	{
		queryResultset = DB.executeQuery(Globals.dbNameAlias, Stock.getTestQuery("getEventTableRecords")[1], indId);
		
		
		if(DB.getRecordSetCount(queryResultset) > 0)
		{
			Reporter.logEvent(Status.PASS, "Verify if event is created for the indId", "An event is created for the pptId", false);
		}else{
			Reporter.logEvent(Status.FAIL, "Verify if event is created for the indId", "An event is not created for the pptId", false);
		}
	}
	
	public void changeStatusToIT(String indId) throws Exception
	{
		DB.executeUpdate(Globals.dbNameAlias,  Stock.getTestQuery("updatePartServiceStatusToIT")[1], indId);
	}
	
	
	public void verifyStatusCodePostDrm3(String pptId) throws SQLException {
		Reporter.logEvent(Status.INFO, "Validate part service table records", "Part service records", false);
		queryResultset = DB.executeQuery(Globals.dbNameAlias,
				Stock.getTestQuery("getpartServiceRecords")[1], pptId);
		String statusCode = null;
		String statusReasonCode = null;
		int count = 0;
		while (queryResultset.next()) {
			statusReasonCode = queryResultset.getString("status_reason_code");
			statusCode = queryResultset.getString("STATUS_CODE");
			
			count ++ ;
			if(count == 1){
		
		if (statusReasonCode.equalsIgnoreCase("ENROLLED")) {
			Reporter.logEvent(Status.PASS,
					"Validate the status reason code post drm batch",
					statusReasonCode, false);
		} else {
			Reporter.logEvent(Status.FAIL,
					"Validate the status reason code post drm batch",
					statusReasonCode, false);
		}
		if (statusCode.equalsIgnoreCase("A")) {
			Reporter.logEvent(Status.PASS,
					"Validate the status code post drm batch", statusCode,
					false);
		} else {
			Reporter.logEvent(Status.FAIL,
					"Validate the status code post drm batch", statusCode,
					false);
		}
		
	}else if(count == 2)
	{
		if (statusReasonCode.equalsIgnoreCase("TRANSITIONED")) {
			Reporter.logEvent(Status.PASS,
					"Validate the status reason code post drm batch",
					statusReasonCode, false);
		} else {
			Reporter.logEvent(Status.FAIL,
					"Validate the status reason code post drm batch",
					statusReasonCode, false);
		}
		if (statusCode.equalsIgnoreCase("T")) {
			Reporter.logEvent(Status.PASS,
					"Validate the status code post drm batch", statusCode,
					false);
		} else {
			Reporter.logEvent(Status.FAIL,
					"Validate the status code post drm batch", statusCode,
					false);
		}
		}
		
		}
	}
	
	
	public void runDrmTransitionBatch() throws IOException {
		Reporter.logEvent(Status.INFO, "Run DRMTransition Batch", "Run DRMTransition Batch", false);
		FileUtils.cleanDirectory(new File(Globals.GC_REMOTE_OUTPUT_PATH));
		ShellUtils.executeShellCommand("cd ~");
		ShellUtils.executeShellCommand("bash");
		General.threadSleepInvocation(5000);
		ShellUtils.executeShellCommand(Globals.GC_SELECT_ENVIRONMENT);
		General.threadSleepInvocation(5000);
		ShellUtils.executeShellCommand(0);
		General.threadSleepInvocation(5000);
		ShellUtils
				.runAndWriteShellCommandsToFile(Globals.GC_SELECT_FOLDER_FOR_RUNNING_JOB);
		ShellUtils.runAndWriteShellCommandsToFile("./DRM_Transition_Notice_Request.ksh");
		General.threadSleepInvocation(5000);
		Reporter.logEvent(Status.PASS, "Run DRM Transition Batch Job",
				"DRM Batch Job is Completed", false);
	}
	
	
	public void verifyIsisFeedBatchFor60DayRecord(String gaId,String indId,String expectedStatus) throws SQLException
	{
		queryResultset = DB.executeQuery(Globals.dbNameAlias, Stock.getTestQuery("verify60DayRecord")[1], gaId,indId,expectedStatus);
		String statusCode = "";
		if(DB.getRecordSetCount(queryResultset) > 0)
		{
			Reporter.logEvent(Status.PASS, "Verify creation of 60 day DRM transition notice isis_feed_batch_todo record","60 day DRM transition notice isis_feed_batch_todo record has been created" , false);
		}else{
			Reporter.logEvent(Status.FAIL, "Verify creation of 60 day DRM transition notice isis_feed_batch_todo record","60 day DRM transition notice isis_feed_batch_todo record has not created" , false);
		}
		
		while(queryResultset.next())
		{
			statusCode = queryResultset.getString("status_code");
			break;
		}
		if(statusCode.equalsIgnoreCase(expectedStatus))
		{
			Reporter.logEvent(Status.PASS, "Verify the status code for 60 day record", "The status code for 60 day record is as expected" + "Expected : "+ expectedStatus +"Actual : " + statusCode, false);
		}else{
			Reporter.logEvent(Status.FAIL, "Verify the status code for 60 day record", "The status code for 60 day record is not updated as expected" + "Expected : "+ expectedStatus +"Actual : " + statusCode, false);	
		}
	}
	
	public void verifyIsisFeedBatchFor30DayRecord(String gaId,String indId,String expectedStatus) throws SQLException
	{
		queryResultset = DB.executeQuery(Globals.dbNameAlias, Stock.getTestQuery("verify30DayRecord")[1], gaId,indId,expectedStatus);
		String statusCode="";
		if(DB.getRecordSetCount(queryResultset) > 0)
		{
			Reporter.logEvent(Status.PASS, "Verify creation of 30 day DRM transition notice isis_feed_batch_todo record","30 day DRM transition notice isis_feed_batch_todo record has been created" , false);
		}else{
			Reporter.logEvent(Status.FAIL, "Verify creation of 30 day DRM transition notice isis_feed_batch_todo record","30 day DRM transition notice isis_feed_batch_todo record has not created" , false);
		}	
		
		while(queryResultset.next())
		{
			statusCode = queryResultset.getString("status_code");
			break;
		}
		if(statusCode.equalsIgnoreCase(expectedStatus))
		{
			Reporter.logEvent(Status.PASS, "Verify the status code for 30 day record", "The status code for 30 day record is as expected" + "Expected : "+ expectedStatus +"Actual : " + statusCode, false);
		}else{
			Reporter.logEvent(Status.FAIL, "Verify the status code for 30 day record", "The status code for 30 day record is not updated as expected" + "Expected : "+ expectedStatus +"Actual : " + statusCode, false);	
		}
	}
	
	public void verifyIsisFeedBatchFor90DayRecord(String gaId,String indId,String expectedStatus) throws SQLException
	{
		queryResultset = DB.executeQuery(Globals.dbNameAlias, Stock.getTestQuery("verify90DayRecord")[1], gaId,indId,expectedStatus);
		String statusCode="";
		if(DB.getRecordSetCount(queryResultset) > 0)
		{
			Reporter.logEvent(Status.PASS, "Verify creation of 90 day DRM transition notice isis_feed_batch_todo record","90 day DRM transition notice isis_feed_batch_todo record has been created" , false);
		}else{
			Reporter.logEvent(Status.FAIL, "Verify creation of 90 day DRM transition notice isis_feed_batch_todo record","90 day DRM transition notice isis_feed_batch_todo record has not created" , false);
		}	
		
		while(queryResultset.next())
		{
			statusCode = queryResultset.getString("status_code");
			break;
		}
		if(statusCode.equalsIgnoreCase(expectedStatus))
		{
			Reporter.logEvent(Status.PASS, "Verify the status code for 90 day record", "The status code for 90 day record is as expected" + "Expected : "+ expectedStatus +"Actual : " + statusCode, false);
		}else{
			Reporter.logEvent(Status.FAIL, "Verify the status code for 90 day record", "The status code for 90 day record is not updated as expected" + "Expected : "+ expectedStatus +"Actual : " + statusCode, false);	
		}
	}
	
	public void verifyEffDateAfterDrmTransition(String indId,int expectedDayDiff) throws SQLException
	{
		queryResultset = DB.executeQuery(Globals.dbNameAlias,
				Stock.getTestQuery("getpartServiceRecords")[1], indId);
		
		Date transDate = null;
		Date currentDate = Calendar.getInstance().getTime();
		while(queryResultset.next())
		{
			transDate = queryResultset.getDate("effdate");
			break;
		}
		int dayDiff = Days.daysBetween(new LocalDate(currentDate), new LocalDate(transDate)).getDays();
		
		
		if(dayDiff == expectedDayDiff)
		{
			Reporter.logEvent(Status.PASS, "Verify if the trasition date is correctly updated in part service table", "Transition date is updated as expected " + "Expected diff : " + expectedDayDiff +"Actual Diff : "+ dayDiff, false);
		}else{
			Reporter.logEvent(Status.FAIL, "Verify if the trasition date is correctly updated in part service table", "Transition date is not updated as expected " + "Expected diff : " + expectedDayDiff +"Actual Diff : "+ dayDiff, false);
		}
	}
	
	public void verifyStatusCodePostDrmTransition(String pptId) throws SQLException {
		Reporter.logEvent(Status.INFO, "Validate part service table records", "Part service records", false);
		queryResultset = DB.executeQuery(Globals.dbNameAlias,
				Stock.getTestQuery("getpartServiceRecords")[1], pptId);
		
		String statusCode = null;
		while(queryResultset.next())
		{
			statusCode = queryResultset.getString("");
			break;
		}
		
		if(statusCode.equalsIgnoreCase("IT"))
		{
			Reporter.logEvent(Status.PASS, "Verify if the status in part service has changed to IT post drm transition", "The status has been changed to IT successfully", false);
		}else{
			Reporter.logEvent(Status.FAIL, "Verify if the status in part service has changed to IT post drm transition", "The status has not been changed to IT successfully", false);
		}
	}
	
	public void deleteDuplicateDBRecords(String[] query,String indId) throws Exception
	{
		DB.executeUpdate(Globals.dbNameAlias, query[1], indId,indId);
	}
	
	public void updateEffdateForIsisFeedBatchRecord(String indId,String noOfDays) throws Exception
	{
		DB.executeUpdate(Globals.dbNameAlias, Stock.getTestQuery("updateEffdateIsisFeedBatch")[1], indId,noOfDays);
	}
	
	public void updateEffdateForPartserviceRecord(String indId) throws Exception
	{
		DB.executeUpdate(Globals.dbNameAlias, Stock.getTestQuery("updatepartServiceEffdate")[1], indId);
	}
	
	
	public void getPartFromMultiplePlan()
	{
		
	}

	public void updatePptBirthDate(String indId) throws Exception {
	DB.executeUpdate(Globals.dbNameAlias, Stock.getTestQuery("updatePptBdate")[1], indId);
	}
	
	
}
