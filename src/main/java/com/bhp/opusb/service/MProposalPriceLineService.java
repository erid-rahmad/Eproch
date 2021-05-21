package com.bhp.opusb.service;

import com.bhp.opusb.domain.MProposalPriceLine;
import com.bhp.opusb.repository.MProposalPriceLineRepository;
import com.bhp.opusb.service.dto.MProposalPriceDTO;
import com.bhp.opusb.service.dto.MProposalPriceLineDTO;
import com.bhp.opusb.service.mapper.MProposalPriceLineMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link MProposalPriceLine}.
 */
@Service
@Transactional
public class MProposalPriceLineService {

    private final Logger log = LoggerFactory.getLogger(MProposalPriceLineService.class);

    private final MProposalPriceLineRepository mProposalPriceLineRepository;

    private final MProposalPriceLineMapper mProposalPriceLineMapper;

    public MProposalPriceLineService(MProposalPriceLineRepository mProposalPriceLineRepository, MProposalPriceLineMapper mProposalPriceLineMapper) {
        this.mProposalPriceLineRepository = mProposalPriceLineRepository;
        this.mProposalPriceLineMapper = mProposalPriceLineMapper;
    }

    /**
     * Save a mProposalPriceLine.
     *
     * @param mProposalPriceLineDTO the entity to save.
     * @return the persisted entity.
     */
    public MProposalPriceLineDTO save(MProposalPriceLineDTO mProposalPriceLineDTO) {
        log.debug("Request to save MProposalPriceLine : {}", mProposalPriceLineDTO);
        MProposalPriceLine mProposalPriceLine = mProposalPriceLineMapper.toEntity(mProposalPriceLineDTO);
        mProposalPriceLine = mProposalPriceLineRepository.save(mProposalPriceLine);
        return mProposalPriceLineMapper.toDto(mProposalPriceLine);
    }

    /**
     * Save the mProposalPriceLines.
     *
     * @param mProposalPriceLineDTOs the entities to save.
     * @return the persisted entities.
     */
    public List<MProposalPriceLineDTO> saveAll(List<MProposalPriceLineDTO> mProposalPriceLineDTOs, MProposalPriceDTO mProposalPriceDTO) {
        log.debug("Request to save MProposalPriceLines. count : {}", mProposalPriceLineDTOs.size());
        mProposalPriceLineDTOs.forEach(line -> line.setProposalPriceId(mProposalPriceDTO.getId()));

        List<MProposalPriceLine> mProposalPriceLines = mProposalPriceLineMapper.toEntity(mProposalPriceLineDTOs);
        mProposalPriceLines = mProposalPriceLineRepository.saveAll(mProposalPriceLines);
        return mProposalPriceLineMapper.toDto(mProposalPriceLines);
    }

    /**
     * Get all the mProposalPriceLines.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MProposalPriceLineDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MProposalPriceLines");
        return mProposalPriceLineRepository.findAll(pageable)
            .map(mProposalPriceLineMapper::toDto);
    }

    /**
     * Get one mProposalPriceLine by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MProposalPriceLineDTO> findOne(Long id) {
        log.debug("Request to get MProposalPriceLine : {}", id);
        return mProposalPriceLineRepository.findById(id)
            .map(mProposalPriceLineMapper::toDto);
    }

    /**
     * Delete the mProposalPriceLine by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MProposalPriceLine : {}", id);
        mProposalPriceLineRepository.deleteById(id);
    }
}
