package org.exploration.flowable.first.process;

import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.ProcessEngine;
import org.flowable.engine.ProcessEngineConfiguration;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.repository.ProcessDefinitionQuery;
import org.flowable.engine.runtime.ProcessInstance;

import java.util.Map;

@Slf4j
public class FirstBPMNProcess {

    public ProcessEngine initProcessEngine() {
        ProcessEngineConfiguration cfg = new StandaloneProcessEngineConfiguration()
                .setJdbcUrl("jdbc:h2:mem:flowable;DB_CLOSE_DELAY=-1")
                .setJdbcUsername("sa")
                .setJdbcPassword("")
                .setJdbcDriver("org.h2.Driver")
                .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);

        ProcessEngine processEngine = cfg.buildProcessEngine();
        return processEngine;
    }

    public Deployment deployBPMNProcess(ProcessEngine processEngine, String classPathFileName) {
        //deploy a process definition
        RepositoryService repositoryService = processEngine.getRepositoryService();
        Deployment deployment = repositoryService.createDeployment()
                .addClasspathResource(classPathFileName)
                .deploy();
        return deployment;
    }

    public void verifyDeployment(ProcessEngine processEngine, Deployment deployment) {
        ProcessDefinitionQuery processDefinitionQuery =
                processEngine.getRepositoryService().createProcessDefinitionQuery();

        ProcessDefinition processDefinition = processDefinitionQuery.deploymentId(deployment.getId()).singleResult();
        if (processDefinition != null) {
            log.info("Found process definition : " + processDefinition.getName());
        } else {
           // throw invalid deployment exception

        }
    }

    public ProcessInstance startProcessInstance(ProcessEngine processEngine, String processDefinitionKey, Map<String, Object> processInputVariables) {
        RuntimeService runtimeService = processEngine.getRuntimeService();
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey(processDefinitionKey, processInputVariables);
        return processInstance;
    }
}
