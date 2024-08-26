package org.exploration.flowable.first.process;

import lombok.extern.slf4j.Slf4j;
import org.flowable.common.engine.api.Engine;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.flowable.engine.impl.util.ProcessInstanceHelper;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.runtime.ProcessInstance;

import java.util.HashMap;
import java.util.Map;


@Slf4j
public class BootstrapEngine {
    public static void main(String[] args) {

        String processFileAtClassPath = "holiday-request.bpmn.xml";
        String processDefinitionKey = "holidayRequest";

        FirstBPMNProcess firstBPMNProcess = new FirstBPMNProcess();
        ProcessEngine processEngine = firstBPMNProcess.initProcessEngine();
        Deployment deployment = firstBPMNProcess.deployBPMNProcess(processEngine, processFileAtClassPath);
        firstBPMNProcess.verifyDeployment(processEngine, deployment);

        String employee = "kermit";
        String nrOfHolidays = "3";
        String description = "I need a holiday!";
        Map<String, Object> processVariable = new HashMap<>();
        processVariable.put("employee", employee);
        processVariable.put("nrOfHolidays", nrOfHolidays);
        processVariable.put("description", description);
        ProcessInstance processInstance =
                firstBPMNProcess.startProcessInstance(processEngine, processDefinitionKey, processVariable);

        while (processInstance.isEnded()) {
            while (processInstance.isSuspended()) {
                log.info("Process is suspended");
            }
        }
    }


}