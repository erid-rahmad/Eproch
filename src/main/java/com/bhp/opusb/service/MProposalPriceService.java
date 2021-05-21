package com.bhp.opusb.service;

import com.bhp.opusb.domain.MProposalPrice;
import com.bhp.opusb.repository.MProposalPriceRepository;
import com.bhp.opusb.service.dto.MProposalPriceDTO;
import com.bhp.opusb.service.dto.MProposalPriceLineDTO;
import com.bhp.opusb.service.dto.MProposalPriceVM;
import com.bhp.opusb.service.mapper.MProposalPriceMapper;
import com.bhp.opusb.service.mapper.MProposalPriceVMMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link MProposalPrice}.
 */
@Service
@Transactional
public class MProposalPriceService {

    private final Logger log = LoggerFactory.getLogger(MProposalPriceService.class);

    private final MProposalPriceLineService mProposalPriceLineService;

    private final MProposalPriceRepository mProposalPriceRepository;

    private final MProposalPriceMapper mProposalPriceMapper;
    private final MProposalPriceVMMapper mProposalPriceVMMapper;

    public MProposalPriceService(MProposalPriceLineService mProposalPriceLineService,
            MProposalPriceRepository mProposalPriceRepository, MProposalPriceMapper mProposalPriceMapper,
            MProposalPriceVMMapper mProposalPriceVMMapper) {
        this.mProposalPriceLineService = mProposalPriceLineService;
        this.mProposalPriceRepository = mProposalPriceRepository;
        this.mProposalPriceMapper = mProposalPriceMapper;
        this.mProposalPriceVMMapper = mProposalPriceVMMapper;
    }

    /**
     * Save a mProposalPrice.
     *
     * @param mProposalPriceDTO the entity to save.
     * @return the persisted entity.
     */
    public MProposalPriceDTO save(MProposalPriceDTO mProposalPriceDTO) {
        log.debug("Request to save MProposalPrice : {}", mProposalPriceDTO);
        MProposalPrice mProposalPrice = mProposalPriceMapper.toEntity(mProposalPriceDTO);
        mProposalPrice = mProposalPriceRepository.save(mProposalPrice);
        return mProposalPriceMapper.toDto(mProposalPrice);
    }

    /**
     * Save a mProposalPriceVM, as well as its lines.
     *
     * @param mProposalPriceVM the entity to save.
     * @return the persisted entity.
     */
    public MProposalPriceDTO saveForm(MProposalPriceVM mProposalPriceVM) {
        log.debug("Request to save MProposalPrice form : {}", mProposalPriceVM);
        MProposalPrice mProposalPrice = mProposalPriceMapper.toEntity(mProposalPriceVM);
        mProposalPrice = mProposalPriceRepository.save(mProposalPrice);

        MProposalPriceVM result = mProposalPriceVMMapper.toDto(mProposalPrice);
        List<MProposalPriceLineDTO> lines = mProposalPriceLineService.saveAll(mProposalPriceVM.getProposalPriceLines(), result);

        result.setProposalPriceLines(lines);
        return result;
    }

    /**
     * Get all the mProposalPrices.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MProposalPriceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MProposalPrices");
        return mProposalPriceRepository.findAll(pageable)
            .map(mProposalPriceMapper::toDto);
    }

    /**
     * Get one mProposalPrice by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MProposalPriceDTO> findOne(Long id) {
        log.debug("Request to get MProposalPrice : {}", id);
        return mProposalPriceRepository.findById(id)
            .map(mProposalPriceMapper::toDto);
    }

    /**
     * Delete the mProposalPrice by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MProposalPrice : {}", id);
        mProposalPriceRepository.deleteById(id);
    }
}
