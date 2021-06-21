package com.bhp.opusb.workflow;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractDocActionHandler<D, E extends WorkflowDoc> implements IDocActionHandler<D> {

    private static Logger log = LoggerFactory.getLogger(AbstractDocActionHandler.class);

    protected abstract E fromId(Long id, String tableName);

    protected abstract E toEntity(D dto);

    protected abstract D toDto(E entity);

    protected abstract E save(E entity);

    @Override
    public List<String> getValidActions(Long id, String tableName) {
        List<String> actions = new ArrayList<>();
        //sementara tidak di cek dulu
        //E e = fromId(id, tableName);
        actions.add("CMP");
        actions.add("APV");
        actions.add("TRM");
        actions.add("RJC");
        return actions;
    }

    @Override
    public D voidIt(D dto) {
        log.info("voidIt(dto="+ dto +")");
        E e = toEntity(dto);
        e.setProcessed(true);
        e.setProcessing(false);
        e.setDocumentStatus("CNL");
        e = save(e);
        D d = toDto(e);
        return d;
    }

    @Override
    public D prepareIt(D dto) {
        log.info("prepareIt(dto="+ dto +")");
        return dto;
    }

    @Override
    public D approveIt(D dto) {
        log.info("approveIt(dto="+ dto +")");
        E e = toEntity(dto);
        e.setApproved(true);
        e.setDocumentStatus("APV");
        e.setDateApprove(LocalDate.now());
        e = save(e);
        D d = toDto(e);
        return d;
    }

    @Override
    public D rejectIt(D dto) {
        log.info("rejectIt(dto="+ dto +")");
        E e = toEntity(dto);
        e.setApproved(false);
        e.setDocumentStatus("RJC");
        e.setDateReject(LocalDate.now());
        e = save(e);
        D d = toDto(e);
        return d;
    }

    @Override
    public D completeIt(D dto) {
        log.info("completeIt(dto="+ dto +")");
        E e = toEntity(dto);
        e.setApproved(true);
        e.setProcessed(true);
        e.setProcessing(false);
        e.setDocumentStatus("CMP");
        e = save(e);
        D d = toDto(e);
        return d;
    }

    @Override
    public D invalidateIt(D dto) {
        log.info("invalidateIt(dto="+ dto +")");
        E e = toEntity(dto);
        e.setApproved(false);
        e.setProcessed(false);
        e.setProcessing(false);
        e.setDocumentStatus("DRF");
        e = save(e);
        D d = toDto(e);
        return d;
    }

    @Override
    public D reActivateIt(D dto) {
        log.info("reActivateIt(dto="+ dto +")");
        E e = toEntity(dto);
        e.setApproved(false);
        e.setProcessed(false);
        e.setProcessing(false);
        e.setDocumentStatus("DRF");
        e = save(e);
        D d = toDto(e);
        return d;
    }

    @Override
    public D closeIt(D dto) {
        log.info("closeIt(dto="+ dto +")");
        E e = toEntity(dto);
        e.setProcessed(true);
        e.setProcessing(false);
        e.setDocumentStatus("CLS");
        e = save(e);
        D d = toDto(e);
        return d;
    }
}