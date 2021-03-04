package com.bhp.opusb.service;

import com.bhp.opusb.domain.MBidding;
import com.bhp.opusb.domain.MBiddingLine;
import com.bhp.opusb.repository.MBiddingLineRepository;
import com.bhp.opusb.repository.MBiddingRepository;
import com.bhp.opusb.repository.MProjectInformationRepository;
import com.bhp.opusb.service.dto.MBiddingDTO;
import com.bhp.opusb.service.mapper.MBiddingMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Random;

/**
 * Service Implementation for managing {@link MBidding}.
 */
@Service
@Transactional
public class MBiddingService {

    private final Logger log = LoggerFactory.getLogger(MBiddingService.class);

    private final MBiddingRepository mBiddingRepository;

    private final MBiddingMapper mBiddingMapper;

    @Autowired
    MBiddingLineService mBiddingLineService;

    @Autowired
    MProjectInformationService mProjectInformationService;

    @Autowired
    MBiddingLineRepository mBiddingLineRepository;

    @Autowired
    MProjectInformationRepository mProjectInformationRepository;

    public MBiddingService(MBiddingRepository mBiddingRepository, MBiddingMapper mBiddingMapper) {
        this.mBiddingRepository = mBiddingRepository;
        this.mBiddingMapper = mBiddingMapper;
    }

    /**
     * Save a mBidding.
     *
     * @param mBiddingDTO the entity to save.
     * @return the persisted entity.
     */
    public MBiddingDTO save(MBiddingDTO mBiddingDTO) {
        log.debug("Request to save MBidding : {}", mBiddingDTO);

        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        String documentno = "BD-"+number;
        mBiddingDTO.setBiddingNo(documentno);

        mBiddingLineRepository.saveAll(mBiddingDTO.getBiddingLineList());
        mProjectInformationRepository.saveAll(mBiddingDTO.getProjectInformationList());

        MBidding mBidding = mBiddingMapper.toEntity(mBiddingDTO);
        mBidding = mBiddingRepository.save(mBidding);
        return mBiddingMapper.toDto(mBidding);
    }

    /**
     * Get all the mBiddings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MBiddingDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MBiddings");
        return mBiddingRepository.findAll(pageable)
            .map(mBiddingMapper::toDto);
    }



    /**
     * Get one mBidding by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MBiddingDTO> findOne(Long id) {
        Optional<MBiddingDTO> mBiddingDTO = mBiddingRepository.findById(id)
            .map(mBiddingMapper::toDto);
        mBiddingDTO.get().setBiddingLineList(mBiddingLineService.findbyheader(mBiddingDTO.get().getId()));
        mBiddingDTO.get().setProjectInformationList(mProjectInformationService.findByBindId(mBiddingDTO.get().getId()));
        return mBiddingDTO;
    }

    /**
     * Delete the mBidding by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MBidding : {}", id);
        mBiddingRepository.deleteById(id);
    }
}
