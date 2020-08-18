package com.bhp.opusb.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.CVendor;
import com.bhp.opusb.domain.CVendorBankAcct;
import com.bhp.opusb.repository.CVendorBankAcctRepository;
import com.bhp.opusb.service.dto.CVendorBankAcctDTO;
import com.bhp.opusb.service.mapper.CAttachmentMapper;
import com.bhp.opusb.service.mapper.CVendorBankAcctMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link CVendorBankAcct}.
 */
@Service
@Transactional
public class CVendorBankAcctService {

    private final Logger log = LoggerFactory.getLogger(CVendorBankAcctService.class);

    private final CVendorBankAcctRepository cVendorBankAcctRepository;

    private final CVendorBankAcctMapper cVendorBankAcctMapper;
    private final CAttachmentMapper cAttachmentMapper;

    public CVendorBankAcctService(CVendorBankAcctRepository cVendorBankAcctRepository,
        CVendorBankAcctMapper cVendorBankAcctMapper,
        CAttachmentMapper cAttachmentMapper
    ) {
        this.cVendorBankAcctRepository = cVendorBankAcctRepository;
        this.cVendorBankAcctMapper = cVendorBankAcctMapper;
        this.cAttachmentMapper = cAttachmentMapper;
    }

    /**
     * Save a cVendorBankAcct.
     *
     * @param cVendorBankAcctDTO the entity to save.
     * @return the persisted entity.
     */
    public CVendorBankAcctDTO save(CVendorBankAcctDTO cVendorBankAcctDTO) {
        log.debug("Request to save CVendorBankAcct : {}", cVendorBankAcctDTO);
        CVendorBankAcct cVendorBankAcct = cVendorBankAcctMapper.toEntity(cVendorBankAcctDTO);
        cVendorBankAcct = cVendorBankAcctRepository.save(cVendorBankAcct);
        return cVendorBankAcctMapper.toDto(cVendorBankAcct);
    }

    public List<CVendorBankAcctDTO> saveAll(List<CVendorBankAcctDTO> cVendorBankAcctDTOs, CVendor vendor, ADOrganization organization) {
        List<CVendorBankAcct> bankAccounts = cVendorBankAcctDTOs
            .stream()
            .map(account -> cVendorBankAcctMapper.toEntity(account)
                .active(true)
                .adOrganization(organization)
                .file(cAttachmentMapper.fromId(account.getFileId()))
                .vendor(vendor)
            )
            .collect(Collectors.toList());

        return cVendorBankAcctRepository.saveAll(bankAccounts)
            .stream()
            .map(cVendorBankAcctMapper::toDto)
            .collect(Collectors.toList());
    }

    /**
     * Get all the cVendorBankAccts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CVendorBankAcctDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CVendorBankAccts");
        return cVendorBankAcctRepository.findAll(pageable)
            .map(cVendorBankAcctMapper::toDto);
    }

    /**
     * Get one cVendorBankAcct by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CVendorBankAcctDTO> findOne(Long id) {
        log.debug("Request to get CVendorBankAcct : {}", id);
        return cVendorBankAcctRepository.findById(id)
            .map(cVendorBankAcctMapper::toDto);
    }

    /**
     * Delete the cVendorBankAcct by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CVendorBankAcct : {}", id);
        cVendorBankAcctRepository.deleteById(id);
    }
}
