package com.bhp.opusb.service;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import com.bhp.opusb.domain.User;
import com.bhp.opusb.service.dto.MBiddingDTO;
import com.bhp.opusb.service.dto.MVerificationDTO;
import com.bhp.opusb.service.dto.MVerificationLineDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.MailParseException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import io.github.jhipster.config.JHipsterProperties;

/**
 * Service for sending emails.
 * <p>
 * We use the {@link Async} annotation to send emails asynchronously.
 */
@Service
public class MailService {

    private final Logger log = LoggerFactory.getLogger(MailService.class);

    private static final String USER = "user";
    private static final String CURRENT_LOGIN = "current_login";
    private static final String VERIFICATION = "verification";
    private static final String VERIFICATION_LINE = "verification_line";

    private static final String BASE_URL = "baseUrl";




    private final JHipsterProperties jHipsterProperties;

    private final JavaMailSender javaMailSender;

    private final MessageSource messageSource;

    private final SpringTemplateEngine templateEngine;



    public MailService(JHipsterProperties jHipsterProperties, JavaMailSender javaMailSender,
            MessageSource messageSource, SpringTemplateEngine templateEngine) {

        this.jHipsterProperties = jHipsterProperties;
        this.javaMailSender = javaMailSender;
        this.messageSource = messageSource;
        this.templateEngine = templateEngine;
    }



    @Async
    public void sendEmail(String to, String subject, String content, boolean isMultipart, boolean isHtml) {
        log.debug("Send email[multipart '{}' and html '{}'] to '{}' with subject '{}' and content={}",
            isMultipart, isHtml, to, subject, content);


        // Prepare message using a Spring helper
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, isMultipart, StandardCharsets.UTF_8.name());
            message.setTo(to);
            message.setFrom(jHipsterProperties.getMail().getFrom());
            message.setSubject(subject);
            message.setText(content, isHtml);

            javaMailSender.send(mimeMessage);
            log.debug("Sent email to User '{}'", to);

        } catch (MailException | MessagingException e) {
            log.warn("Email could not be sent to user '{}'", to, e);
        }
    }

    @Async
    public void sendMailWithAttachment(String to, String subject, String content, boolean isMultipart, boolean isHtml,
            String fileToAttach) {
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("admin@yahoo.com");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            if (fileToAttach != null) {
                FileSystemResource file = new FileSystemResource(fileToAttach);
                helper.addAttachment(file.getFilename(), file);
            }
        } catch (MessagingException e) {
            throw new MailParseException(e);
        }
        javaMailSender.send(message);
    }

    @Async
    public void sendEmailFromTemplate(User user, String templateName, String titleKey) {
        if (user.getEmail() == null) {
            log.warn("Email doesn't exist for user '{}'", user.getLogin());
            return;
        }
        Locale locale = Locale.forLanguageTag(user.getLangKey());
        Context context = new Context(locale);
        context.setVariable(USER, user);
        context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());
        String content = templateEngine.process(templateName, context);
        String subject = messageSource.getMessage(titleKey, null, locale);
        sendEmail(user.getEmail(), subject, content, false, true);
    }

    @Async
    public void sendEmailFromTemplate(User user, String templateName, String titleKey, Map<String, Object> contextVariables) {
        if (user.getEmail() == null) {
            log.warn("Email doesn't exist for user '{}'", user.getLogin());
            return;
        }
        Locale locale = Locale.forLanguageTag(user.getLangKey());
        final Context context = new Context(locale);
        context.setVariable(USER, user);
        context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());

        contextVariables.entrySet().forEach(entry -> {
            context.setVariable(entry.getKey(), entry.getValue());
        });

        String content = templateEngine.process(templateName, context);
        String subject = messageSource.getMessage(titleKey, null, locale);
        sendEmail(user.getEmail(), subject, content, false, true);
    }

    @Async
    public void sendEmailFromTemplate(String email, String templateName, String titleKey) {
        Locale locale = Locale.forLanguageTag("en");
        Context context = new Context(locale);
        String content = templateEngine.process(templateName, context);
        String subject = titleKey;
        sendEmail(email, subject, content, false, true);
    }

    @Async
    public void sendEmailFromTemplate(String email, String templateName, String titleKey,MBiddingDTO mBiddingDTO) {
        Locale locale = Locale.forLanguageTag("en");
        Context context = new Context(locale);
        context.setVariable("BIDING_DATA", mBiddingDTO);
        String content = templateEngine.process(templateName, context);
        String subject = titleKey;
        sendEmail(email, subject, content, false, true);
    }

    @Async
    public void sendEmailFromTemplate(User user, String currentLogin, MVerificationDTO mVerification, List<MVerificationLineDTO> mVerificationLines,
        String templateName, String titleKey) {

        if (user.getEmail() == null) {
            log.warn("Email doesn't exist for user '{}'", user.getLogin());
            return;
        }
        Locale locale = Locale.forLanguageTag(user.getLangKey());
        Context context = new Context(locale);
        context.setVariable(USER, user);
        context.setVariable(CURRENT_LOGIN, currentLogin);
        context.setVariable(VERIFICATION, mVerification);
        context.setVariable(VERIFICATION_LINE, mVerificationLines);
        context.setVariable(BASE_URL, jHipsterProperties.getMail().getBaseUrl());
        String content = templateEngine.process(templateName, context);
        String subject = messageSource.getMessage(titleKey, null, locale);
        sendEmail(user.getEmail(), subject, content, false, true);
    }

    @Async
    public void sendWinnerEmail(String email) {
        log.debug("Sending activation email to '{}'", email);
        sendEmailFromTemplate(email, "mail/winnerEmail", "Pengumuman Pemenang Lelang");
    }

    @Async
    public void sendBiddingInvatationEmail(String email, MBiddingDTO mBiddingDTO) {
        log.debug("Sending bidding invitation email to '{}'", email);
        sendEmailFromTemplate(email, "mail/biddingInvitationEmail", "Undangan Lelang", mBiddingDTO);
    }

    @Async
    public void sendTerminateEmail(String email) {
        log.debug("Sending bidding termination email to '{}'", email);
        sendEmailFromTemplate(email, "mail/biddingTerminateEmail", "Pembatalan Lelang");
    }

    @Async
    public void sendActivationEmail(User user) {
        log.debug("Sending activation email to '{}'", user.getEmail());
        sendEmailFromTemplate(user, "mail/activationEmail", "email.activation.title");
    }

    @Async
    public void sendCreationEmail(User user) {
        log.debug("Sending creation email to '{}'", user.getEmail());
        sendEmailFromTemplate(user, "mail/creationEmail", "email.activation.title");
    }

    @Async
    public void sendPasswordResetMail(User user) {
        log.debug("Sending password reset email to '{}'", user.getEmail());
        sendEmailFromTemplate(user, "mail/passwordResetEmail", "email.reset.title");
    }

    @Async
    public void sendNotifRejectVerification(User user, String currentLogin, MVerificationDTO mVerification, List<MVerificationLineDTO> mVerificationLines) {
        log.debug("Sending notif reject verification email to '{}'", user.getEmail());
        sendEmailFromTemplate(user, currentLogin, mVerification, mVerificationLines, "mail/notifRejectVerification", "email.verification.title");
    }

    @Async
    public void sendPaidInvoiceEmail(User user, MVerificationDTO mVerification, List<MVerificationLineDTO> mVerificationLines) {
        log.debug("Sending payment status (Paid) email notification to '{}'", user.getEmail());
        sendEmailFromTemplate(user, mVerification.getLastModifiedBy(), mVerification, mVerificationLines, "mail/invoicePaidEmail", "email.verification.title");
    }

    public void sendInvitationCodeEmail(User user, Map<String, Object> contextVariables) {
        log.debug("Sending invitation code email to '{}'", user.getEmail());
        sendEmailFromTemplate(user, "mail/invitationCodeEmail", "email.invitationCode.title", contextVariables);
    }


}
