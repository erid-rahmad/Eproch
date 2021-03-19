package com.bhp.opusb.service;

import com.bhp.opusb.domain.MBiddingSubmission;

import com.bhp.opusb.domain.MBiddingSubmissionLine;
import com.bhp.opusb.repository.MBiddingSubmissionLineRepository;
import com.bhp.opusb.repository.MBiddingSubmissionRepository;
import com.bhp.opusb.repository.MSubmissionSubItemRepository;
import com.bhp.opusb.service.dto.MBiddingSubmissionDTO;
import com.bhp.opusb.service.mapper.MBiddingSubmissionMapper;
import com.bhp.opusb.util.MapperJSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
import java.util.Set;


/**
 * Service Implementation for managing {@link MBiddingSubmission}.
 */
@Service
@Transactional
public class MBiddingSubmissionService {

    private final Logger log = LoggerFactory.getLogger(MBiddingSubmissionService.class);

    private final MBiddingSubmissionRepository mBiddingSubmissionRepository;

    private final MBiddingSubmissionMapper mBiddingSubmissionMapper;

    @Autowired
    MBiddingSubmissionLineRepository mBiddingSubmissionLineRepository;

    @Autowired
    MSubmissionSubItemRepository mSubmissionSubItemRepository;


    public MBiddingSubmissionService(MBiddingSubmissionRepository mBiddingSubmissionRepository, MBiddingSubmissionMapper mBiddingSubmissionMapper) {
        this.mBiddingSubmissionRepository = mBiddingSubmissionRepository;
        this.mBiddingSubmissionMapper = mBiddingSubmissionMapper;
    }

    /**
     * Save a mBiddingSubmission.
     *
     * @param mBiddingSubmissionDTO the entity to save.
     * @return the persisted entity.
     */
    public MBiddingSubmissionDTO save(MBiddingSubmissionDTO mBiddingSubmissionDTO) {
        log.debug("Request to save MBiddingSubmission : {}", MapperJSONUtil.prettyLog(mBiddingSubmissionDTO));
        MBiddingSubmission mBiddingSubmission = mBiddingSubmissionMapper.toEntity(mBiddingSubmissionDTO);
        mBiddingSubmission = mBiddingSubmissionRepository.save(mBiddingSubmission);

        try {
            for (MBiddingSubmissionLine mBiddingSubmissionLine1 : mBiddingSubmissionDTO.getmBiddingSubmissionLineList()){
                mBiddingSubmissionLine1.setMBiddingSubmission(mBiddingSubmission);
                mBiddingSubmissionLineRepository.save(mBiddingSubmissionLine1);
            }

        }catch (Exception e){}


        return mBiddingSubmissionMapper.toDto(mBiddingSubmission);
    }

    /**
     * Get all the mBiddingSubmissions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MBiddingSubmissionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MBiddingSubmissions");
        return mBiddingSubmissionRepository.findAll(pageable)
            .map(mBiddingSubmissionMapper::toDto);
    }

    @Transactional(readOnly = true)
    public Optional<MBiddingSubmission> findAllnested(Long id) {
        log.debug("Request to get all MBiddingSubmissions");
        Optional<MBiddingSubmission> mBiddingSubmission = mBiddingSubmissionRepository.findById(id);
        log.info("this mBiddingSubmission ");
//        MBiddingSubmissionLine mBiddingSubmissionLine = null;
//        mBiddingSubmission.get().addMBiddingSubmissionLine(mBiddingSubmissionLine);
//        Set<MBiddingSubmissionLine> mBiddingSubmissionLines = mBiddingSubmissionLineRepository.findbyheaders(1l);
//        for(MBiddingSubmissionLine mBiddingSubmissionLine :mBiddingSubmissionLines) {
//            try {
//                mBiddingSubmission.get().addMBiddingSubmissionLine(mBiddingSubmissionLine);
//            }catch (Exception e){}
//
//        }
        return mBiddingSubmission;
    }


    /**
     * Get one mBiddingSubmission by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MBiddingSubmissionDTO> findOne(Long id) {
        log.debug("Request to get MBiddingSubmission nested : {}", id);
        Optional<MBiddingSubmissionDTO> mBiddingSubmissionDTO =mBiddingSubmissionRepository.findById(id)
            .map(mBiddingSubmissionMapper::toDto);
        List<MBiddingSubmissionLine> mBiddingSubmissionLines = mBiddingSubmissionLineRepository.findbyheader(mBiddingSubmissionDTO.get().getId());
        mBiddingSubmissionDTO.get().setmBiddingSubmissionLineList(mBiddingSubmissionLines);



        return mBiddingSubmissionDTO;
    }

    /**
     * Delete the mBiddingSubmission by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MBiddingSubmission : {}", id);
        mBiddingSubmissionRepository.deleteById(id);
    }
}
