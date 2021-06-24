package com.bhp.opusb.service.trigger.process;

import java.io.IOException;
import java.util.Map;

import com.bhp.opusb.domain.CAttachment;
import com.bhp.opusb.repository.CAttachmentRepository;
import com.bhp.opusb.service.CAttachmentService;
import com.bhp.opusb.service.dto.ProcessResult;
import com.bhp.opusb.service.dto.TriggerResult;
import com.bhp.opusb.service.trigger.ProcessTrigger;

import org.flowable.engine.RepositoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UploadWorkflow implements ProcessTrigger {

  private final Logger log = LoggerFactory.getLogger(UploadWorkflow.class);

  private final CAttachmentService cAttachmentService;
  private final CAttachmentRepository cAttachmentRepository;
  private final RepositoryService repositoryService;

  UploadWorkflow(CAttachmentService cAttachmentService, CAttachmentRepository cAttachmentRepository,
    RepositoryService repositoryService) {
    this.cAttachmentService = cAttachmentService;
    this.cAttachmentRepository = cAttachmentRepository;
    this.repositoryService = repositoryService;
  }

  @Override
  public TriggerResult run(Map<String, Object> params) {
    CAttachment dto = cAttachmentRepository.findById(Integer.toUnsignedLong((Integer)params.get("fileId"))).get();
    
    try {
      Resource resource = cAttachmentService.loadFileAsResource(Integer.toUnsignedLong((Integer)params.get("fileId")), dto.getFileName());
      repositoryService.createDeployment().name("system").addInputStream(dto.getFileName(), resource.getInputStream()).deploy();
      
    } catch (IOException e) {
      e.printStackTrace();
    }

    return new ProcessResult();
  }
}
