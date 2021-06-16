package com.bhp.opusb.workflow;

import java.util.List;

public interface IDocActionHandler<D> {

    public boolean acceptTableName(String tableName);

    public List<String> getValidActions(Long id, String tableName);

    public D voidIt(D dto);

    public D prepareIt(D dto);

    public D approveIt(D dto);

    public D rejectIt(D dto);

    public D completeIt(D dto);

    public D invalidateIt(D dto);

    public D reActivateIt(D dto);

    public D closeIt(D dto);
}