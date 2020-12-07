package com.bhp.opusb.service;

import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.CTaxInvoice;
import com.bhp.opusb.repository.CTaxInvoiceRepository;
import com.bhp.opusb.service.dto.CTaxInvoiceDTO;
import com.bhp.opusb.service.mapper.CTaxInvoiceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CTaxInvoice}.
 */
@Service
@Transactional
public class CTaxInvoiceService {

    private final Logger log = LoggerFactory.getLogger(CTaxInvoiceService.class);

    private final ADOrganization organization;
    private final CTaxInvoiceRepository cTaxInvoiceRepository;

    private final CTaxInvoiceMapper cTaxInvoiceMapper;

    public CTaxInvoiceService(CTaxInvoiceRepository cTaxInvoiceRepository, CTaxInvoiceMapper cTaxInvoiceMapper, ADOrganizationService adOrganizationService) {
        this.cTaxInvoiceRepository = cTaxInvoiceRepository;
        this.cTaxInvoiceMapper = cTaxInvoiceMapper;

        organization = adOrganizationService.getDefaultOrganization();
    }

    /**
     * Save a cTaxInvoice.
     *
     * @param cTaxInvoiceDTO the entity to save.
     * @return the persisted entity.
     */
    public CTaxInvoiceDTO save(CTaxInvoiceDTO cTaxInvoiceDTO) {
        log.debug("Request to save CTaxInvoice : {}", cTaxInvoiceDTO);
        CTaxInvoice cTaxInvoice = cTaxInvoiceMapper.toEntity(cTaxInvoiceDTO);
        cTaxInvoice.active(true)
            .adOrganization(organization);

        cTaxInvoice = cTaxInvoiceRepository.save(cTaxInvoice);
        return cTaxInvoiceMapper.toDto(cTaxInvoice);
    }

    /**
     * Get all the cTaxInvoices.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CTaxInvoiceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CTaxInvoices");
        return cTaxInvoiceRepository.findAll(pageable)
            .map(cTaxInvoiceMapper::toDto);
    }

    /**
     * Get one cTaxInvoice by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CTaxInvoiceDTO> findOne(Long id) {
        log.debug("Request to get CTaxInvoice : {}", id);
        return cTaxInvoiceRepository.findById(id)
            .map(cTaxInvoiceMapper::toDto);
    }

    /**
     * Delete the cTaxInvoice by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CTaxInvoice : {}", id);
        cTaxInvoiceRepository.deleteById(id);
    }
}
