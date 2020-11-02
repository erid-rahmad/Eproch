package com.bhp.opusb.service;

import com.bhp.opusb.domain.ADOrganizationInfo;
import com.bhp.opusb.repository.ADOrganizationInfoRepository;
import com.bhp.opusb.service.dto.ADOrganizationInfoDTO;
import com.bhp.opusb.service.mapper.ADOrganizationInfoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ADOrganizationInfo}.
 */
@Service
@Transactional
public class ADOrganizationInfoService {

    private final Logger log = LoggerFactory.getLogger(ADOrganizationInfoService.class);

    private final ADOrganizationInfoRepository aDOrganizationInfoRepository;

    private final ADOrganizationInfoMapper aDOrganizationInfoMapper;

    public ADOrganizationInfoService(ADOrganizationInfoRepository aDOrganizationInfoRepository, ADOrganizationInfoMapper aDOrganizationInfoMapper) {
        this.aDOrganizationInfoRepository = aDOrganizationInfoRepository;
        this.aDOrganizationInfoMapper = aDOrganizationInfoMapper;
    }

    /**
     * Save a aDOrganizationInfo.
     *
     * @param aDOrganizationInfoDTO the entity to save.
     * @return the persisted entity.
     */
    public ADOrganizationInfoDTO save(ADOrganizationInfoDTO aDOrganizationInfoDTO) {
        log.debug("Request to save ADOrganizationInfo : {}", aDOrganizationInfoDTO);
        ADOrganizationInfo aDOrganizationInfo = aDOrganizationInfoMapper.toEntity(aDOrganizationInfoDTO);
        aDOrganizationInfo = aDOrganizationInfoRepository.save(aDOrganizationInfo);
        return aDOrganizationInfoMapper.toDto(aDOrganizationInfo);
    }

    /**
     * Get all the aDOrganizationInfos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ADOrganizationInfoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ADOrganizationInfos");
        return aDOrganizationInfoRepository.findAll(pageable)
            .map(aDOrganizationInfoMapper::toDto);
    }

    /**
     * Get one aDOrganizationInfo by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ADOrganizationInfoDTO> findOne(Long id) {
        log.debug("Request to get ADOrganizationInfo : {}", id);
        return aDOrganizationInfoRepository.findById(id)
            .map(aDOrganizationInfoMapper::toDto);
    }

    /**
     * Delete the aDOrganizationInfo by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ADOrganizationInfo : {}", id);
        aDOrganizationInfoRepository.deleteById(id);
    }
}
