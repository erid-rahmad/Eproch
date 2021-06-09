package com.bhp.opusb.service;

import com.bhp.opusb.domain.MBidNegoPrice;
import com.bhp.opusb.domain.MBidNegoPriceLine;
import com.bhp.opusb.repository.MBidNegoPriceLineRepository;
import com.bhp.opusb.repository.MBidNegoPriceRepository;
import com.bhp.opusb.service.dto.MBidNegoPriceDTO;
import com.bhp.opusb.service.dto.MBidNegoPriceLineDTO;
import com.bhp.opusb.service.mapper.MBidNegoPriceLineMapper;
import com.bhp.opusb.service.mapper.MBidNegoPriceMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link MBidNegoPrice}.
 */
@Service
@Transactional
public class MBidNegoPriceService {

    private final Logger log = LoggerFactory.getLogger(MBidNegoPriceService.class);

    private final MBidNegoPriceRepository mBidNegoPriceRepository;
    private final MBidNegoPriceLineRepository mBidNegoPriceLineRepository;

    private final MBidNegoPriceMapper mBidNegoPriceMapper;
    private final MBidNegoPriceLineMapper mBidNegoPriceLineMapper;

    public MBidNegoPriceService(MBidNegoPriceRepository mBidNegoPriceRepository, MBidNegoPriceMapper mBidNegoPriceMapper,
    MBidNegoPriceLineRepository mBidNegoPriceLineRepository, MBidNegoPriceLineMapper mBidNegoPriceLineMapper) {
        this.mBidNegoPriceRepository = mBidNegoPriceRepository;
        this.mBidNegoPriceMapper = mBidNegoPriceMapper;
        this.mBidNegoPriceLineRepository = mBidNegoPriceLineRepository;
        this.mBidNegoPriceLineMapper = mBidNegoPriceLineMapper;
    }

    /**
     * Save a mBidNegoPrice.
     *
     * @param mBidNegoPriceDTO the entity to save.
     * @return the persisted entity.
     */
    public MBidNegoPriceDTO save(MBidNegoPriceDTO mBidNegoPriceDTO) {
        log.debug("Request to save MBidNegoPrice : {}", mBidNegoPriceDTO);
        MBidNegoPrice mBidNegoPrice = mBidNegoPriceMapper.toEntity(mBidNegoPriceDTO);
        mBidNegoPrice = mBidNegoPriceRepository.save(mBidNegoPrice);
        
        if(mBidNegoPriceDTO.getLine()!=null) {
            List<MBidNegoPriceLine> line = new ArrayList<>();
            for(MBidNegoPriceLineDTO dto: mBidNegoPriceDTO.getLine()){
                line.add(mBidNegoPriceLineMapper.toEntity(dto));
            }
            mBidNegoPriceLineRepository.saveAll(line);
        }
        return mBidNegoPriceMapper.toDto(mBidNegoPrice);
    }

    /**
     * Get all the mBidNegoPrices.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MBidNegoPriceDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MBidNegoPrices");
        return mBidNegoPriceRepository.findAll(pageable)
            .map(mBidNegoPriceMapper::toDto);
    }

    /**
     * Get one mBidNegoPrice by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MBidNegoPriceDTO> findOne(Long id) {
        log.debug("Request to get MBidNegoPrice : {}", id);
        return mBidNegoPriceRepository.findById(id)
            .map(mBidNegoPriceMapper::toDto);
    }

    /**
     * Delete the mBidNegoPrice by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MBidNegoPrice : {}", id);
        mBidNegoPriceRepository.deleteById(id);
    }
}
