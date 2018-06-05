package lib;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.aventstack.extentreports.Status;


public class ReportEntity {
    Status logStatus;
    String step;
    String details;
    static ReportEntity objReportEntity;
    public static Map<String,List<ReportEntity>> reporterMap = new LinkedHashMap<>();
    public static final String GC_STATUS = "STATUS";
    public static final String GC_STEP = "STEP";
    public static final String GC_DETAILS = "DETAILS";
    public static List<ReportEntity> stepsList;
    
    public Status getLogStatus() {
                    return logStatus;
    }
    public void setLogStatus(Status logStatus) {
                    this.logStatus = logStatus;
    }
    public String getStep() {
                    return step;
    }
    public void setStep(String step) {
                    this.step = step;
    }
    public String getDetails() {
                    return details;
    }
    public void setDetails(String details) {
                    this.details = details;
    }
    public static void initializeReporter()
    {
                    stepsList = new LinkedList<>();
    }
    
    public static void buildReporterMap(Status logStatus,String step,String details)
    {
    objReportEntity = new ReportEntity();
    objReportEntity.setLogStatus(logStatus);
    objReportEntity.setStep(step);
    objReportEntity.setDetails(details);
    stepsList.add(objReportEntity);
    }
    
    
    
    public static void finalizeReporter(String tcName)
    {
                    
                    reporterMap.put(tcName, stepsList);
    }
    

}
