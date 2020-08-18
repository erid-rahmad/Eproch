package com.bhp.opusb.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Opus Web App.
 * <p>
 * Properties are configured in the {@code application.yml} file.
 * See {@link io.github.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

  private final Attachment attachment = new Attachment();

  public Attachment getAttachment() {
    return attachment;
  }

  public static final class Attachment {
    private String uploadDir = "/opt/data/attachment";

    public String getUploadDir() {
      return uploadDir;
    }

    public void setUploadDir(String uploadDir) {
      this.uploadDir = uploadDir;
    }
  }
}
