package com.bhp.opusb.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.bhp.opusb.domain.MBiddingLine;
import com.bhp.opusb.repository.MBiddingLineRepository;
import com.bhp.opusb.service.dto.MBiddingDTO;
import com.bhp.opusb.service.dto.MBiddingLineDTO;
import com.bhp.opusb.service.dto.MBiddingSubItemDTO;
import com.bhp.opusb.service.mapper.MBiddingLineMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MBiddingLine}.
 */
@Service
@Transactional
public class MBiddingLineService {

    private final Logger log = LoggerFactory.getLogger(MBiddingLineService.class);

    private final MBiddingLineRepository mBiddingLineRepository;
    private final MBiddingSubItemService mBiddingSubItemService;

    private final MBiddingLineMapper mBiddingLineMapper;

    public MBiddingLineService(MBiddingLineRepository mBiddingLineRepository,
            MBiddingSubItemService mBiddingSubItemService, MBiddingLineMapper mBiddingLineMapper) {
        this.mBiddingLineRepository = mBiddingLineRepository;
        this.mBiddingSubItemService = mBiddingSubItemService;
        this.mBiddingLineMapper = mBiddingLineMapper;
    }

    /**
     * Save a mBiddingLine.
     *
     * @param mBiddingLineDTO the entity to save.
     * @return the persisted entity.
     */
    public MBiddingLineDTO save(MBiddingLineDTO mBiddingLineDTO) {
        log.debug("Request to save MBiddingLine : {}", mBiddingLineDTO);
        MBiddingLine mBiddingLine = mBiddingLineMapper.toEntity(mBiddingLineDTO);
        mBiddingLine = mBiddingLineRepository.save(mBiddingLine);
        return mBiddingLineMapper.toDto(mBiddingLine);
    }


    /**
     * Save a list of mBiddingLines.
     *
     * @param mBiddingLineDTOs the entities to save.
     * @return the persisted entities.
     */
    public List<MBiddingLineDTO> saveAll(List<MBiddingLineDTO> mBiddingLineDTOs, MBiddingDTO mBiddingDTO) {
        log.debug("Request to save MBiddingLine list : {}", mBiddingLineDTOs);
        final List<MBiddingSubItemDTO> mBiddingSubItemDTOs = new ArrayList<>(mBiddingLineDTOs.size());

        mBiddingLineDTOs.forEach(line -> {
            line.setBiddingId(mBiddingDTO.getId());
            line.setAdOrganizationId(mBiddingDTO.getAdOrganizationId());

            final MBiddingSubItemDTO subItemDTO = line.getSubItem();

            if (subItemDTO != null) {
                final MBiddingSubItemDTO savedSubItem = mBiddingSubItemService.save(subItemDTO);
                line.setSubItemId(savedSubItem.getId());
            }
        });

        // Batch save sub-items.
        mBiddingSubItemService.saveAll(mBiddingSubItemDTOs);

        List<MBiddingLine> mBiddingLines = mBiddingLineMapper.toEntity(mBiddingLineDTOs);
        mBiddingLines = mBiddingLineRepository.saveAll(mBiddingLines);
        return mBiddingLineMapper.toDto(mBiddingLines);
    }

    /**
     * Get all the mBiddingLines.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MBiddingLineDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MBiddingLines");
        return mBiddingLineRepository.findAll(pageable)
            .map(mBiddingLineMapper::toDto);
    }
    @Transactional(readOnly = true)
    public List<MBiddingLine> findbyheader(long id) {
        return mBiddingLineRepository.findbyheader(id);
    }

    /**
     * Get one mBiddingLine by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MBiddingLineDTO> findOne(Long id) {
        log.debug("Request to get MBiddingLine : {}", id);
        return mBiddingLineRepository.findById(id)
            .map(mBiddingLineMapper::toDto);
    }

    /**
     * Get all mBiddingLines with the specific biddingId.
     * @param biddingId
     * @return the list of entities.
     */
    public List<MBiddingLineDTO> findByBiddingId(Long biddingId) {
        log.debug("Request to get MBiddingLines of a specific bidding");
        return mBiddingLineRepository.findbyheader(biddingId).stream()
            .map(mBiddingLineMapper::toDto).collect(Collectors.toList());
    }

    /**
     * Delete the mBiddingLine by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MBiddingLine : {}", id);
        mBiddingLineRepository.deleteById(id);
    }

    /**
     * Delete the list of mBiddingLines.
     *
     * @param id the entities.
     */
    public void deleteAll(List<MBiddingLineDTO> mBiddingLineDTOs) {
        log.debug("Request to delete MBiddingLines : {}", mBiddingLineDTOs);
        List<MBiddingLine> entities = mBiddingLineMapper.toEntity(mBiddingLineDTOs);
        mBiddingLineRepository.deleteAll(entities);
    }
}
