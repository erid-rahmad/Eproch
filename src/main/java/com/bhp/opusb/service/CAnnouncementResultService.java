package com.bhp.opusb.service;

import com.bhp.opusb.domain.CAnnouncementResult;
import com.bhp.opusb.repository.CAnnouncementResultRepository;
import com.bhp.opusb.service.dto.CAnnouncementResultDTO;
import com.bhp.opusb.service.mapper.CAnnouncementResultMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CAnnouncementResult}.
 */
@Service
@Transactional
public class CAnnouncementResultService {

    private final Logger log = LoggerFactory.getLogger(CAnnouncementResultService.class);

    private final CAnnouncementResultRepository cAnnouncementResultRepository;

    private final CAnnouncementResultMapper cAnnouncementResultMapper;

    public CAnnouncementResultService(CAnnouncementResultRepository cAnnouncementResultRepository, CAnnouncementResultMapper cAnnouncementResultMapper) {
        this.cAnnouncementResultRepository = cAnnouncementResultRepository;
        this.cAnnouncementResultMapper = cAnnouncementResultMapper;
    }

    /**
     * Save a cAnnouncementResult.
     *
     * @param cAnnouncementResultDTO the entity to save.
     * @return the persisted entity.
     */
    public CAnnouncementResultDTO save(CAnnouncementResultDTO cAnnouncementResultDTO) {
        log.debug("Request to save CAnnouncementResult : {}", cAnnouncementResultDTO);
        CAnnouncementResult cAnnouncementResult = cAnnouncementResultMapper.toEntity(cAnnouncementResultDTO);
        cAnnouncementResult = cAnnouncementResultRepository.save(cAnnouncementResult);
        return cAnnouncementResultMapper.toDto(cAnnouncementResult);
    }

    /**
     * Get all the cAnnouncementResults.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CAnnouncementResultDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CAnnouncementResults");
        return cAnnouncementResultRepository.findAll(pageable)
            .map(cAnnouncementResultMapper::toDto);
    }

    /**
     * Get one cAnnouncementResult by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CAnnouncementResultDTO> findOne(Long id) {
        log.debug("Request to get CAnnouncementResult : {}", id);
        return cAnnouncementResultRepository.findById(id)
            .map(cAnnouncementResultMapper::toDto);
    }

    /**
     * Delete the cAnnouncementResult by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CAnnouncementResult : {}", id);
        cAnnouncementResultRepository.deleteById(id);
    }
}
