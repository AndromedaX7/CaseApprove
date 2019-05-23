package com.zhhl.android.policemovecareful.bean;

/**
 * Created by miao on 2018/9/11.
 */
public class CaseQueryBean {
    private String caseType;
    private String petitioner;
    private String time;
    private long timeMilloms;
    private String caseName;

    public String getCaseType() {
        return caseType;
    }

    public void setCaseType(String caseType) {
        this.caseType = caseType;
    }

    public String getPetitioner() {
        return petitioner;
    }

    public void setPetitioner(String petitioner) {
        this.petitioner = petitioner;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public long getTimeMilloms() {
        return timeMilloms;
    }

    public void setTimeMilloms(long timeMilloms) {
        this.timeMilloms = timeMilloms;
    }

    public CaseQueryBean() {

    }

    public CaseQueryBean(String caseName, String caseType, String petitioner, String time, long timeMilloms) {
        this.caseName = caseName;
        this.caseType = caseType;
        this.petitioner = petitioner;
        this.time = time;
        this.timeMilloms = timeMilloms;
    }

    public void setCaseName(String caseName) {
        this.caseName = caseName;
    }

    public String getCaseName() {
        return caseName;
    }
}
