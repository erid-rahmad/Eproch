package com.bhp.opusb.web.rest.errors;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

public class UserNotAuthorizedException extends AbstractThrowableProblem {

    private static final long serialVersionUID = 1L;

    public UserNotAuthorizedException() {
        super(ErrorConstants.USER_NOT_AUTHORIZED_TYPE, "Not authorized", Status.FORBIDDEN);
    }
}
