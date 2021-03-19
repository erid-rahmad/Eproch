package com.bhp.opusb.service;

import com.bhp.opusb.domain.MProjectInformation;
import com.bhp.opusb.repository.MProjectInformationRepository;
import com.bhp.opusb.service.dto.MBiddingDTO;
import com.bhp.opusb.service.dto.MProjectInformationDTO;
import com.bhp.opusb.service.mapper.MProjectInformationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link MProjectInformation}.
 */
@Service
@Transactional
public class MProjectInformationService {

    private final Logger log = LoggerFactory.getLogger(MProjectInformationService.class);

    private final MProjectInformationRepository mProjectInformationRepository;

    private final MProjectInformationMapper mProjectInformationMapper;

    public MProjectInformationService(MProjectInformationRepository mProjectInformationRepository, MProjectInformationMapper mProjectInformationMapper) {
        this.mProjectInformationRepository = mProjectInformationRepository;
        this.mProjectInformationMapper = mProjectInformationMapper;
    }

    /**
     * Save a mProjectInformation.
     *
     * @param mProjectInformationDTO the entity to save.
     * @return the persisted entity.
     */
    public MProjectInformationDTO save(MProjectInformationDTO mProjectInformationDTO) {
        log.debug("Request to save MProjectInformation : {}", mProjectInformationDTO);
        MProjectInformation mProjectInformation = mProjectInformationMapper.toEntity(mProjectInformationDTO);
        mProjectInformation = mProjectInformationRepository.save(mProjectInformation);
        return mProjectInformationMapper.toDto(mProjectInformation);
    }

    /**
     * Save a list of mProjectInformations.
     *
     * @param mProjectInformationDTOs the entities to save.
     * @return the persisted entities.
     */
    public List<MProjectInformationDTO> saveAll(List<MProjectInformationDTO> mProjectInformationDTOs, MBiddingDTO mBiddingDTO) {
        log.debug("Request to save MProjectInformation : {}", mProjectInformationDTOs);

        mProjectInformationDTOs.forEach(dto -> {
            dto.setBiddingId(mBiddingDTO.getId());
            dto.setAdOrganizationId(mBiddingDTO.getAdOrganizationId());
        });

        List<MProjectInformation> mProjectInformation = mProjectInformationMapper.toEntity(mProjectInformationDTOs);
        mProjectInformation = mProjectInformationRepository.saveAll(mProjectInformation);
        return mProjectInformationMapper.toDto(mProjectInformation);
    }

    /**
     * Get all the mProjectInformations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MProjectInformationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MProjectInformations");
        return mProjectInformationRepository.findAll(pageable)
            .map(mProjectInformationMapper::toDto);
    }

    @Transactional(readOnly = true)
    public List<MProjectInformation> findByBindId(Long id) {
        return mProjectInformationRepository.findByBindId(id);
    }

    /**
     * Get one mProjectInformation by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MProjectInformationDTO> findOne(Long id) {
        log.debug("Request to get MProjectInformation : {}", id);
        return mProjectInformationRepository.findById(id)
            .map(mProjectInformationMapper::toDto);
    }

    /**
     * Get all mProjectInformations with the specific biddingId.
     * @param biddingId
     * @return the list of entities.
     */
    /* public List<MProjectInformationDTO> findByBiddingId(Long biddingId) {
        log.debug("Request to get MBiddingLines of a specific bidding");
        return mProjectInformationRepository.findByBidding_Id(biddingId).stream()
            .map(mProjectInformationMapper::toDto).collect(Collectors.toList());
    } */

    /**
     * Delete the mProjectInformation by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MProjectInformation : {}", id);
        mProjectInformationRepository.deleteById(id);
    }

    /**
     * Delete the list of mProjectInformations.
     *
     * @param id the entities.
     */
    public void deleteAll(List<MProjectInformationDTO> mProjectInformationDTOs) {
        log.debug("Request to delete MProjectInformations : {}", mProjectInformationDTOs);
        List<MProjectInformation> entities = mProjectInformationMapper.toEntity(mProjectInformationDTOs);
        mProjectInformationRepository.deleteAll(entities);
    }
}
