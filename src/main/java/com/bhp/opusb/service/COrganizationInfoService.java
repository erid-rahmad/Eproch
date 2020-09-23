package com.bhp.opusb.service;

import com.bhp.opusb.domain.COrganizationInfo;
import com.bhp.opusb.repository.COrganizationInfoRepository;
import com.bhp.opusb.service.dto.COrganizationInfoDTO;
import com.bhp.opusb.service.mapper.COrganizationInfoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link COrganizationInfo}.
 */
@Service
@Transactional
public class COrganizationInfoService {

    private final Logger log = LoggerFactory.getLogger(COrganizationInfoService.class);

    private final COrganizationInfoRepository cOrganizationInfoRepository;

    private final COrganizationInfoMapper cOrganizationInfoMapper;

    public COrganizationInfoService(COrganizationInfoRepository cOrganizationInfoRepository, COrganizationInfoMapper cOrganizationInfoMapper) {
        this.cOrganizationInfoRepository = cOrganizationInfoRepository;
        this.cOrganizationInfoMapper = cOrganizationInfoMapper;
    }

    /**
     * Save a cOrganizationInfo.
     *
     * @param cOrganizationInfoDTO the entity to save.
     * @return the persisted entity.
     */
    public COrganizationInfoDTO save(COrganizationInfoDTO cOrganizationInfoDTO) {
        log.debug("Request to save COrganizationInfo : {}", cOrganizationInfoDTO);
        COrganizationInfo cOrganizationInfo = cOrganizationInfoMapper.toEntity(cOrganizationInfoDTO);
        cOrganizationInfo = cOrganizationInfoRepository.save(cOrganizationInfo);
        return cOrganizationInfoMapper.toDto(cOrganizationInfo);
    }

    /**
     * Get all the cOrganizationInfos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<COrganizationInfoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all COrganizationInfos");
        return cOrganizationInfoRepository.findAll(pageable)
            .map(cOrganizationInfoMapper::toDto);
    }

    /**
     * Get one cOrganizationInfo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<COrganizationInfoDTO> findOne(Long id) {
        log.debug("Request to get COrganizationInfo : {}", id);
        return cOrganizationInfoRepository.findById(id)
            .map(cOrganizationInfoMapper::toDto);
    }

    /**
     * Delete the cOrganizationInfo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete COrganizationInfo : {}", id);
        cOrganizationInfoRepository.deleteById(id);
    }
}
