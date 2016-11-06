package sfa.view;

import java.io.Serializable;

import oracle.adf.controller.TaskFlowId;

public class DynamicRegion implements Serializable {
    @SuppressWarnings("compatibility:1724855728318833805")
    private static final long serialVersionUID = -1969927688142037685L;
    private String contactTaskFlowId = "/WEB-INF/ContactFlow.xml#ContactFlow";
    private String accountTaskFlowID = "/WEB-INF/AccountFlow.xml#AccountFlow";
    private String currentTF = "contact";

    public DynamicRegion() {
    }

    public TaskFlowId getDynamicTaskFlowId() {
        if(this.currentTF.equalsIgnoreCase("contact"))
            return TaskFlowId.parse(contactTaskFlowId);
        else if(this.currentTF.equalsIgnoreCase("account"))
            return TaskFlowId.parse(accountTaskFlowID);
        else
           return null;
        
    }

    public void setDynamicTaskFlowId(String contactTaskFlowId,String accountTaskFlowID ) {
        this.contactTaskFlowId = contactTaskFlowId;
        this.accountTaskFlowID = accountTaskFlowID;
        
    }

    public String contactFlow() {
        setDynamicTaskFlowId("/WEB-INF/ContactFlow.xml#ContactFlow","/WEB-INF/AccounttFlow.xml#AccounttFlow");
        return null;
    }

    public void setCurrentTF(String currentTF) {
        this.currentTF = currentTF;
    }

    public String getCurrentTF() {
        return currentTF;
    }
}
