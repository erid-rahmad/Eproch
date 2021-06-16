package com.bhp.opusb.workflow;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.flowable.engine.delegate.DelegateExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class DocProcessorService {

    private static Logger log = LoggerFactory.getLogger(DocProcessorService.class);

    private ApplicationContext appContext;

    public DocProcessorService(ApplicationContext appContext) {
        this.appContext = appContext;
    }

    private List<IDocActionHandler> getDocActionHandlers(String tableName) {
        Map<String, IDocActionHandler> handlers = appContext.getBeansOfType(IDocActionHandler.class);
        handlers.keySet().stream().forEach(k->System.out.println(k));
        if(handlers.keySet().size()==0) System.out.println("No handlers found...");
        return handlers.values().stream()
            .filter(h -> h.acceptTableName(tableName))
            .collect(Collectors.toList());
    }

    public List<String> getValidActions(String tableName, Long id) {
        List<String> actions = new ArrayList<>();
        for (IDocActionHandler h : getDocActionHandlers(tableName)) {
            //if(h.acceptTableName(tableName)) { (sudah dicek apakah handler bisa dipakai untuk table bersangkutan)
                actions = h.getValidActions(id, tableName);
                break;
            //}
        }
        return actions;
    }

    public void log(DelegateExecution execution) {
        log.info(execution.getVariables().toString());
    }

    public void setApproved(DelegateExecution execution, Boolean approved) {
        log.info("setApproved("+ approved+") " + execution);
        execution.setVariable(WorkflowVariables.APPROVED, approved);
    }

    public void voidIt(DelegateExecution execution) {
        log.info("voidIt() " + execution);
        WorkflowVariables v = new WorkflowVariables(execution);
        Object dto = v.getDto();
        for (IDocActionHandler h : getDocActionHandlers(v.getTableName())) {
            dto = h.voidIt(dto);
        }
        execution.setVariable(WorkflowVariables.DOC, dto);
    }

    public void prepareIt(DelegateExecution execution) {
        log.info("prepareIt() " + execution);
        WorkflowVariables v = new WorkflowVariables(execution);
        Object dto = v.getDto();
        for (IDocActionHandler h : getDocActionHandlers(v.getTableName())) {
            dto = h.prepareIt(dto);
        }
        execution.setVariable(WorkflowVariables.DOC, dto);
    }

    public void approveIt(DelegateExecution execution) {
        log.info("approveIt() " + execution);
        WorkflowVariables v = new WorkflowVariables(execution);
        Object dto = v.getDto();
        for (IDocActionHandler h : getDocActionHandlers(v.getTableName())) {
            dto = h.approveIt(dto);
        }
        execution.setVariable(WorkflowVariables.DOC, dto);
    }

    public void rejectIt(DelegateExecution execution) {
        log.info("rejectIt() " + execution);
        WorkflowVariables v = new WorkflowVariables(execution);
        Object dto = v.getDto();
        for (IDocActionHandler h : getDocActionHandlers(v.getTableName())) {
            dto = h.rejectIt(dto);
        }
        execution.setVariable(WorkflowVariables.DOC, dto);
    }

    public void completeIt(DelegateExecution execution) {
        log.info("completeIt() " + execution);
        WorkflowVariables v = new WorkflowVariables(execution);
        Object dto = v.getDto();
        for (IDocActionHandler h : getDocActionHandlers(v.getTableName())) {
            dto = h.completeIt(dto);
        }
        execution.setVariable(WorkflowVariables.DOC, dto);
    }

    public void invalidateIt(DelegateExecution execution) {
        log.info("invalidateIt() " + execution);
        WorkflowVariables v = new WorkflowVariables(execution);
        Object dto = v.getDto();
        for (IDocActionHandler h : getDocActionHandlers(v.getTableName())) {
            dto = h.invalidateIt(dto);
        }
        execution.setVariable(WorkflowVariables.DOC, dto);
    }

    public void reActivateIt(DelegateExecution execution) {
        log.info("reActivateIt() " + execution);
        WorkflowVariables v = new WorkflowVariables(execution);
        Object dto = v.getDto();
        for (IDocActionHandler h : getDocActionHandlers(v.getTableName())) {
            dto = h.reActivateIt(dto);
        }
        execution.setVariable(WorkflowVariables.DOC, dto);
    }

    public void closeIt(DelegateExecution execution) {
        log.info("closeIt() " + execution);
        WorkflowVariables v = new WorkflowVariables(execution);
        Object dto = v.getDto();
        for (IDocActionHandler h : getDocActionHandlers(v.getTableName())) {
            dto = h.closeIt(dto);
        }
        execution.setVariable(WorkflowVariables.DOC, dto);
    }
}