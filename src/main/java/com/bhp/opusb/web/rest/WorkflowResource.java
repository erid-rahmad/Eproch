package com.bhp.opusb.web.rest;

import java.util.List;

import com.bhp.opusb.workflow.FlowableWorkflowService;
import com.bhp.opusb.workflow.WorkflowApproval;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing Workflow.
 */
@RestController
@RequestMapping("/api")
public class WorkflowResource {

    private final Logger log = LoggerFactory.getLogger(WorkflowResource.class);

    private final FlowableWorkflowService workflowService;

    public WorkflowResource(FlowableWorkflowService workflowService) {
        this.workflowService = workflowService;
    }

//  CRUD APIs
    @GetMapping("/workflows")
    public ResponseEntity<Object> getAllWorkflows() {
        log.debug("REST request to get workflows");
        return ResponseEntity.ok(workflowService.getAllWorkflows());
    }

    @GetMapping("/workflows/count")
    public ResponseEntity<Long> countAllWorkflows() {
        log.debug("REST request to get workflow count");
        return ResponseEntity.ok(workflowService.countAllWorkflows());
    }

    @DeleteMapping("/workflows/{id}")
    public ResponseEntity<Object> removeWorkflow(@PathVariable String id) {
        log.debug("REST request to remove workflow id {}", id);
        workflowService.deleteWorkflow(id);
        return ResponseEntity.noContent().build();
    }

//  Workflow APIs
    @PutMapping("/workflows/start")
    public ResponseEntity<Object> start(@RequestBody String json) {
        log.debug("REST request to start workflow " + json);
        try {
            JsonNode jsonNode = new ObjectMapper().readTree(json);
            String tableName = jsonNode.get("tableName").asText();
            Long id = jsonNode.get("id").asLong();
            Object result = workflowService.startWorkflow(tableName, id);
            return ResponseEntity.ok(result);
        } catch (JsonProcessingException e) {
            log.error("Failed to process json " + json, e);
        }
        return ResponseEntity.status(500).build();
    }

    @PutMapping("/workflows/stop")
    public ResponseEntity<Void> stop(@RequestBody String json) {
        log.debug("REST request to start workflow " + json);
        try {
            JsonNode jsonNode = new ObjectMapper().readTree(json);
            String tableName = jsonNode.get("tableName").asText();
            Long id = jsonNode.get("id").asLong();
            String message = jsonNode.get("message").asText();
            workflowService.stopWorkflow(tableName, id, message);
            return ResponseEntity.noContent().build();
        } catch (JsonProcessingException e) {
            log.error("Failed to process json " + json, e);
        }
        return ResponseEntity.status(500).build();
    }

    @GetMapping("/workflows/approvals")
    public ResponseEntity<List<WorkflowApproval>> getApprovals() {
        log.debug("REST request to get approvals");
        List<WorkflowApproval> result = workflowService.getApprovals();
        return ResponseEntity.ok(result);
    }

    @PutMapping("/workflows/actions")
    public ResponseEntity<List<String>> getActions(@RequestBody String json) {
        log.debug("REST request to get valid actions " + json);
        try {
            JsonNode jsonNode = new ObjectMapper().readTree(json);
            String tableName = jsonNode.get("tableName").asText();
            Long id = jsonNode.get("id").asLong();
            List<String> result = workflowService.getValidActions(tableName, id);
            return ResponseEntity.ok(result);
        } catch (JsonProcessingException e) {
            log.error("Failed to process json " + json, e);
        }
        return ResponseEntity.status(500).build();
    }

    @PutMapping("/workflows/approve")
    public ResponseEntity<Void> approve(@RequestBody String json) {
        log.debug("REST request to approve doc " + json);
        try {
            JsonNode jsonNode = new ObjectMapper().readTree(json);
            String approvalId = jsonNode.get("approvalId").asText();
            workflowService.approve(approvalId, true);
            return ResponseEntity.noContent().build();
        } catch (JsonProcessingException e) {
            log.error("Failed to process json " + json, e);
        }
        return ResponseEntity.status(500).build();
    }

    @PutMapping("/workflows/reject")
    public ResponseEntity<Void> reject(@RequestBody String json) {
        log.debug("REST request to approve doc " + json);
        try {
            JsonNode jsonNode = new ObjectMapper().readTree(json);
            String approvalId = jsonNode.get("approvalId").asText();
            workflowService.approve(approvalId, false);
            return ResponseEntity.noContent().build();
        } catch (JsonProcessingException e) {
            log.error("Failed to process json " + json, e);
        }
        return ResponseEntity.status(500).build();
    }
}