package com.bhp.opusb.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.bhp.opusb.repository.GenericDocumentRepository;

public class DocumentUtil {

  public static final String STATUS_APPROVE = "APV";
  public static final String STATUS_ACCEPT = "ACC";
  public static final String STATUS_DECLINE = "DCL";
  public static final String STATUS_DRAFT = "DRF";
  public static final String STATUS_REOPEN = "ROP";
  public static final String STATUS_REJECT = "RJC";
  public static final String STATUS_SUBMIT = "SMT";
  public static final String STATUS_VOID = "CNL";
  public static final String STATUS_PUBLISH = "PUB";
  public static final String STATUS_TERMINATE = "TRM";

  private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyMM");

  private DocumentUtil() {}

  public static boolean isNew(String documentStatus) {
    return documentStatus == null;
  }

  public static boolean isApprove(String documentStatus) {
    return STATUS_APPROVE.equals(documentStatus);
  }

  public static boolean isAccept(String documentStatus) {
    return STATUS_ACCEPT.equals(documentStatus);
  }

  public static boolean isDecline(String documentStatus) {
    return STATUS_DECLINE.equals(documentStatus);
  }

  public static boolean isDraft(String documentStatus) {
    return STATUS_DRAFT.equals(documentStatus);
  }

  public static boolean isReject(String documentStatus) {
    return STATUS_REJECT.equals(documentStatus);
  }

  public static boolean isSubmit(String documentStatus) {
    return STATUS_SUBMIT.equals(documentStatus);
  }

  public static boolean isVoid(String documentStatus) {
    return STATUS_VOID.equals(documentStatus);
  }

  public static boolean isReopen(String documentStatus) {
    return STATUS_REOPEN.equals(documentStatus);
  }

  public static boolean isPublish(String documentStatus) {
    return STATUS_PUBLISH.equals(documentStatus);
  }

  public static boolean isTerminate(String documentStatus) {
    return STATUS_TERMINATE.equals(documentStatus);
  }

  public static String buildRunningNumber(LocalDate dateTrx, GenericDocumentRepository<?, ?> repository) {
    LocalDate start = dateTrx.withDayOfMonth(1);
    LocalDate end = dateTrx.withDayOfMonth(dateTrx.lengthOfMonth());
    Long lastNumber = repository.getMaxDocumentNoWithinDates(start, end);

    if (lastNumber != null) {
      return String.valueOf(lastNumber + 1);
    }

    String prefix = dateTrx.format(DATETIME_FORMATTER);

    return prefix + (String.format("%04d", 1));
  }

  public static String buildDocumentNumber(String prefix, GenericDocumentRepository<?, ?> repository) {
    final int documentSequence = repository.getNextDocumentSequence();
    final String datePrefix = LocalDate.now().format(DATETIME_FORMATTER);
    final String runningNumber = datePrefix + (String.format("%04d", documentSequence));

    if (prefix == null) {
      return runningNumber;
    }

    return prefix + runningNumber;
  }
}
