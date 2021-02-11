package com.bhp.opusb.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

import com.bhp.opusb.config.ApplicationProperties;
import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.CAttachment;
import com.bhp.opusb.repository.CAttachmentRepository;
import com.bhp.opusb.service.dto.CAttachmentDTO;
import com.bhp.opusb.service.mapper.CAttachmentMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * Service Implementation for managing {@link CAttachment}.
 */
@Service
@Transactional
public class CAttachmentService {

    private final Logger log = LoggerFactory.getLogger(CAttachmentService.class);

    private final CAttachmentRepository cAttachmentRepository;

    private final CAttachmentMapper cAttachmentMapper;

    private final Path uploadPath;

    private final ApplicationProperties.Attachment attachment;

    public CAttachmentService(CAttachmentRepository cAttachmentRepository, CAttachmentMapper cAttachmentMapper, ApplicationProperties applicationProperties) {
        this.cAttachmentRepository = cAttachmentRepository;
        this.cAttachmentMapper = cAttachmentMapper;

        attachment = applicationProperties.getAttachment();
        uploadPath = Paths.get(attachment.getUploadDir()).toAbsolutePath().normalize();

        try {
            Files.createDirectory(uploadPath);
        } catch(FileAlreadyExistsException e) {
            log.info("Upload path is already exist. Not creating.");
        } catch (IOException e) {
            log.warn("Error creating directory. {}", e.getLocalizedMessage());
        }
    }

    public CAttachmentDTO storeFile(MultipartFile file) {
        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        try {
            File tmpFile = File.createTempFile("bhp", fileExtension, null);
            String fileName = tmpFile.getName();
            tmpFile.delete();

            Path targetLocation = this.uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            ADOrganization organization = new ADOrganization();
            CAttachment cAttachment = new CAttachment();

            organization.setId(1L);
            cAttachment.active(true)
                .adOrganization(organization)
                .fileName(fileName)
                .documentType(file.getContentType())
                .mimeType(file.getContentType());

            cAttachmentRepository.save(cAttachment);
            return cAttachmentMapper.toDto(cAttachment);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Resource loadFileAsResource(Long id, String fileName) throws FileNotFoundException {
        log.debug("Request to load file. id:{}, name: {}", fileName, id);
        Optional<CAttachment> attachment = cAttachmentRepository.findById(id);

        if (attachment.isPresent()) {
            CAttachment record = attachment.get();
            if (! record.getFileName().equals(fileName)) {
                throw new FileNotFoundException("File not found. id: " + id + ", name: " + fileName);
            }
        }
        try {
            Path filePath = this.uploadPath.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            } else {
                throw new FileNotFoundException("File not found " + fileName);
            }
        } catch (MalformedURLException ex) {
            throw new FileNotFoundException("File not found " + fileName);
        }
    }

    /**
     * Save a cAttachment.
     *
     * @param cAttachmentDTO the entity to save.
     * @return the persisted entity.
     */
    public CAttachmentDTO save(CAttachmentDTO cAttachmentDTO) {
        log.debug("Request to save CAttachment : {}", cAttachmentDTO);
        CAttachment cAttachment = cAttachmentMapper.toEntity(cAttachmentDTO);
        cAttachment = cAttachmentRepository.save(cAttachment);
        return cAttachmentMapper.toDto(cAttachment);
    }

    /**
     * Get all the cAttachments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CAttachmentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CAttachments");
        return cAttachmentRepository.findAll(pageable)
            .map(cAttachmentMapper::toDto);
    }

    /**
     * Get one cAttachment by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CAttachmentDTO> findOne(Long id) {
        log.debug("Request to get CAttachment : {}", id);
        return cAttachmentRepository.findById(id)
            .map(cAttachmentMapper::toDto);
    }

    /**
     * Delete the cAttachment by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CAttachment : {}", id);
        cAttachmentRepository.deleteById(id);
    }
}
