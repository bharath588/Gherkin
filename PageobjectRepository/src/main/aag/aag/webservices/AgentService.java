package aag.webservices;

import java.util.List;

public class AgentService {

	String username;
	String password;
	String accuCode;
	String accuAccessTypeCode;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAccuCode() {
		return accuCode;
	}
	public void setAccuCode(String accuCode) {
		this.accuCode = accuCode;
	}
	public String getAccuAccessTypeCode() {
		return accuAccessTypeCode;
	}
	public void setAccuAccessTypeCode(String accuAccessTypeCode) {
		this.accuAccessTypeCode = accuAccessTypeCode;
	}
	
	String id_type;
	String ind_id;
	List<String> gaIdList;
	String application;
	String database;
	String signUpReason;
	String enrollmentChannel;
	String advisoryServiceProvider;
	
	public String getId_type() {
		return id_type;
	}
	public void setId_type(String id_type) {
		this.id_type = id_type;
	}
	public String getInd_id() {
		return ind_id;
	}
	public void setInd_id(String ind_id) {
		this.ind_id = ind_id;
	}
	public List<String> getGaIdList() {
		return gaIdList;
	}
	public void setGaIdList(List<String> gaIdList) {
		this.gaIdList = gaIdList;
	}
	public String getApplication() {
		return application;
	}
	public void setApplication(String application) {
		this.application = application;
	}
	public String getDatabase() {
		return database;
	}
	public void setDatabase(String database) {
		this.database = database;
	}
	public String getSignUpReason() {
		return signUpReason;
	}
	public void setSignUpReason(String signUpReason) {
		this.signUpReason = signUpReason;
	}
	public String getEnrollmentChannel() {
		return enrollmentChannel;
	}
	public void setEnrollmentChannel(String enrollmentChannel) {
		this.enrollmentChannel = enrollmentChannel;
	}
	public String getAdvisoryServiceProvider() {
		return advisoryServiceProvider;
	}
	public void setAdvisoryServiceProvider(String advisoryServiceProvider) {
		this.advisoryServiceProvider = advisoryServiceProvider;
	}
	
	String rkPlanOwnerId;
	String rkUserId;
	String callBackURL;
	String callBackToken;
	String fullSessionTicket;

	public String getRkPlanOwnerId() {
		return rkPlanOwnerId;
	}
	public void setRkPlanOwnerId(String rkPlanOwnerId) {
		this.rkPlanOwnerId = rkPlanOwnerId;
	}
	public String getRkUserId() {
		return rkUserId;
	}
	public void setRkUserId(String rkUserId) {
		this.rkUserId = rkUserId;
	}
	public String getCallBackURL() {
		return callBackURL;
	}
	public void setCallBackURL(String callBackURL) {
		this.callBackURL = callBackURL;
	}
	public String getCallBackToken() {
		return callBackToken;
	}
	public void setCallBackToken(String callBackToken) {
		this.callBackToken = callBackToken;
	}
	public String getFullSessionTicket() {
		return fullSessionTicket;
	}
	public void setFullSessionTicket(String fullSessionTicket) {
		this.fullSessionTicket = fullSessionTicket;
	}

	//MAX Unenrollment
	String idType;
	String id;
	String unenrollmentReason;
	String sponsorId;
	
	public String getIdType() {
		return idType;
	}
	public void setIdType(String idType) {
		this.idType = idType;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUnenrollmentReason() {
		return unenrollmentReason;
	}
	public void setUnenrollmentReason(String unenrollmentReason) {
		this.unenrollmentReason = unenrollmentReason;
	}
	public String getSponsorId() {
		return sponsorId;
	}
	public void setSponsorId(String sponsorId) {
		this.sponsorId = sponsorId;
	}
	
	//MAX Authentication
	String token;
	String indId;
	String gaId;
	String userId;

	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getIndId() {
		return indId;
	}
	public void setIndId(String indId) {
		this.indId = indId;
	}
	public String getGaId() {
		return gaId;
	}
	public void setGaId(String gaId) {
		this.gaId = gaId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
}
