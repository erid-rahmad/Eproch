package com.bhp.opusb.workflow;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.bhp.opusb.domain.Authority;
import com.bhp.opusb.service.UserService;
import com.bhp.opusb.workflow.exceptions.InvalidDocActionException;

import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FlowableWorkflowService {

    private static Logger log = LoggerFactory.getLogger(FlowableWorkflowService.class);

    private ApplicationContext appContext;
    private final UserService userService;
    private final RuntimeService runtimeService;
    private final TaskService taskService;
    private final DocProcessorService docProcessorService;

    private final String WORKFLOW_DOC_PROCESS = "docBiddingProcess";

    FlowableWorkflowService(ApplicationContext appContext, UserService userService, RuntimeService runtimeService,
            TaskService taskService, DocProcessorService docProcessorService) {
        this.appContext = appContext;
        this.userService = userService;
        this.runtimeService = runtimeService;
        this.taskService = taskService;
        this.docProcessorService = docProcessorService;
    }

    public Object startWorkflow(String tableName, Long id) {
        return startWorkflow(WORKFLOW_DOC_PROCESS, tableName, id);
    }

    public Object startWorkflow(String workflowName, String tableName, Long id) {
        log.info("startWorkflow(workflowName=" + workflowName + ", tableName=" + tableName + ", id=" + id + ")");
        
        // a document can only have one active workflow
        long count = getPendingWorkflowCount(tableName, id);
        if (count > 0) {
            throw new RuntimeException("Pending workflow already exists");
        }

        Object bean = appContext.getBean(getBeanName(tableName, "Service"));
        try {
            Method method = bean.getClass().getMethod("findOne", Long.class);
            Optional<Object> dto = (Optional<Object>) method.invoke(bean, id);

            // validate doc action to be executed
            if (!isValidAction(tableName, id, dto.get())) {
                throw new InvalidDocActionException();
            }

            // start the doc workflow process
            WorkflowVariables v = new WorkflowVariables(tableName, id, dto.get());
            ProcessInstance pi = runtimeService.startProcessInstanceByKey(workflowName, v.createMap());
            return pi.getProcessVariables().getOrDefault(WorkflowVariables.DOC, "");
        } catch (InvalidDocActionException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Unable to start workflow " + workflowName, e);
        }
    }

    public void stopWorkflow(String tableName, Long id, String message) {
        log.info("stopWorkflow(message=" + message + ", tableName=" + tableName + ", id=" + id + ")");

        List<Task> taskList = taskService.createTaskQuery().includeProcessVariables()
            .active()
            .processVariableValueEquals(WorkflowVariables.TABLE_NAME, tableName)
            .processVariableValueEquals(WorkflowVariables.ID, id)
            .list();

        // delete process instances first
        List<String> instanceIdList = taskList.stream()
            .map(Task::getProcessInstanceId)
            .collect(Collectors.toList());
        instanceIdList.stream().forEach(i -> runtimeService.deleteProcessInstance(i, message));

        // delete tasks
        List<String> taskIdList = taskList.stream()
            .map(Task::getId)
            .collect(Collectors.toList());
        taskService.deleteTasks(taskIdList, message);
    }

    public List<String> getValidActions(String tableName, Long id) {
        log.info("getValidActions(tableName=" + tableName + ", id=" + id + ")");
        List<String> das = docProcessorService.getValidActions(tableName, id);
        for(String da: das) System.out.println(da);
        return das;
    }

    public List<WorkflowApproval> getApprovals() {
        log.info("getApprovals()");

        // get the roles of current user
        List<String> authorities = userService.getUserWithAuthorities().get()
            .getAuthorities().stream()
            .map(Authority::getName).collect(Collectors.toList());

        // find approval tasks based on user roles    
        List<Task> taskList = taskService.createTaskQuery().includeProcessVariables()
                .taskCandidateGroupIn(authorities)
                .list();
        List<WorkflowApproval> approvalList = new ArrayList<>();
        for (Task task : taskList) {
            WorkflowVariables v = new WorkflowVariables(task.getProcessVariables());
            WorkflowApproval approval = new WorkflowApproval(task.getId(), v.getTableName(), v.getId(), v.getDto());
            approval.setName(task.getName());
            approvalList.add(approval);
        }
        return approvalList;
    }

    public void approve(String approvalId, Boolean approved) {
        log.info("approve(approvalId=" + approvalId + ", approved="+approved+")");
        
        String user = userService.getUserWithAuthorities().get().getLogin();
        Task task = taskService.createTaskQuery().includeProcessVariables().taskId(approvalId).singleResult();
        if (task == null) {
            throw new RuntimeException("Approval id not found: " + approvalId);
        } else {
            taskService.claim(task.getId(), user);
            Map<String, Object> variables = task.getProcessVariables();
            variables.put(WorkflowVariables.APPROVED, approved);
            taskService.complete(task.getId(), variables);
        }
    }

    private String getBeanName(String tableName, String suffix) {
        final String separator = "_";
        String titleCase = Arrays.stream(tableName.split(separator)).map(
                word -> word.isEmpty() ? word : Character.toTitleCase(word.charAt(0)) + word.substring(1).toLowerCase())
                .collect(Collectors.joining(separator)).replaceAll(separator, "");

        String serviceName = Character.toLowerCase(titleCase.charAt(0)) + titleCase.substring(1) + suffix;
        return serviceName;
    }

    private long getPendingWorkflowCount(String tableName, Long id) {
        return taskService.createTaskQuery().includeProcessVariables()
            .active()
            .processVariableValueEquals(WorkflowVariables.TABLE_NAME, tableName)
            .processVariableValueEquals(WorkflowVariables.ID, id)
            .count();
    }

    private boolean isValidAction(String tableName, Long id, Object dto) throws Exception {
        Method method = dto.getClass().getMethod("getDocumentAction");
        String action = (String) method.invoke(dto);
        boolean valid = getValidActions(tableName, id).contains(action);
        System.out.println("Action is "+(valid?"valid":("invalid. DocAction in question being "+action)));
        return valid;
    }
}