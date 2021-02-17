package com.bhp.opusb.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.bhp.opusb.repository.GenericDocumentRepository;

public class DocumentUtil {

  public static final String STATUS_APPROVE = "APV";
  public static final String STATUS_DRAFT = "DRF";
  public static final String STATUS_REOPEN = "ROP";
  public static final String STATUS_REJECT = "RJC";
  public static final String STATUS_SUBMIT = "SMT";
  public static final String STATUS_VOID = "CNL";

  private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern("yyMM");

  private DocumentUtil() {}

  public static boolean isNew(String documentStatus) {
    return documentStatus == null;
  }

  public static boolean isApprove(String documentStatus) {
    return STATUS_APPROVE.equals(documentStatus);
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

  public static String buildRunningNumber(GenericDocumentRepository<?, ?> repository) {
    LocalDate now = LocalDate.now();
    LocalDate start = now.withDayOfMonth(1);
    LocalDate end = now.withDayOfMonth(now.lengthOfMonth());

    long numOfRecords = repository.countByDateTrxBetween(start, end);
    String prefix = now.format(DATETIME_FORMATTER);

    return prefix + (String.format("%04d", numOfRecords));
  }
}
