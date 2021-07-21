package com.bhp.opusb.service;

import com.bhp.opusb.domain.MBiddingEvalResult;
import com.bhp.opusb.repository.MBiddingEvalResultRepository;
import com.bhp.opusb.repository.MBiddingRepository;
import com.bhp.opusb.repository.MBiddingScheduleRepository;
import com.bhp.opusb.service.dto.MBiddingDTO;
import com.bhp.opusb.service.dto.MBiddingEvalResultDTO;
import com.bhp.opusb.service.dto.MBiddingScheduleDTO;
import com.bhp.opusb.service.mapper.MBiddingEvalResultMapper;
import com.bhp.opusb.service.mapper.MBiddingMapper;
import com.bhp.opusb.service.mapper.MBiddingScheduleMapper;
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
 * Service Implementation for managing {@link MBiddingEvalResult}.
 */
@Service
@Transactional
public class MBiddingEvalResultService {

    private final Logger log = LoggerFactory.getLogger(MBiddingEvalResultService.class);

    private final MBiddingEvalResultRepository mBiddingEvalResultRepository;

    private final MBiddingEvalResultMapper mBiddingEvalResultMapper;
    private final MBiddingRepository mBiddingRepository;
    private final MBiddingMapper mBiddingMapper;
    private final MBiddingScheduleRepository mBiddingScheduleRepository;
    private final MBiddingScheduleMapper mBiddingScheduleMapper;


    public MBiddingEvalResultService(MBiddingEvalResultRepository mBiddingEvalResultRepository, MBiddingEvalResultMapper mBiddingEvalResultMapper, MBiddingRepository mBiddingRepository, MBiddingMapper mBiddingMapper, MBiddingScheduleRepository mBiddingScheduleRepository, MBiddingScheduleMapper mBiddingScheduleMapper) {
        this.mBiddingEvalResultRepository = mBiddingEvalResultRepository;
        this.mBiddingEvalResultMapper = mBiddingEvalResultMapper;
        this.mBiddingRepository = mBiddingRepository;
        this.mBiddingMapper = mBiddingMapper;
        this.mBiddingScheduleRepository = mBiddingScheduleRepository;
        this.mBiddingScheduleMapper = mBiddingScheduleMapper;
    }

    /**
     * Save a mBiddingEvalResult.
     *
     * @param mBiddingEvalResultDTO the entity to save.
     * @return the persisted entity.
     */
    public MBiddingEvalResultDTO save(MBiddingEvalResultDTO mBiddingEvalResultDTO) {
        log.debug("Request to save MBiddingEvalResult : {}", mBiddingEvalResultDTO);
        MBiddingEvalResult mBiddingEvalResult = mBiddingEvalResultMapper.toEntity(mBiddingEvalResultDTO);
        mBiddingEvalResult = mBiddingEvalResultRepository.save(mBiddingEvalResult);
        return mBiddingEvalResultMapper.toDto(mBiddingEvalResult);
    }

    public  List<MBiddingDTO> getGrid(String menu){
        List<MBiddingDTO> mBiddingDTOS = mBiddingMapper.toDto(mBiddingRepository.findAll());
        List<MBiddingDTO> mBiddingDTOS_=new ArrayList<>();
        mBiddingDTOS.forEach(mBiddingDTO -> {
            List<MBiddingScheduleDTO> mBiddingScheduleDTOS = mBiddingScheduleMapper.
                toDto(mBiddingScheduleRepository.findByBiddingId(mBiddingDTO.getId()));
            log.info("this mBiddingScheduleDTOS {}",mBiddingScheduleDTOS);
            int x=0;
            for (MBiddingScheduleDTO mBiddingScheduleDTO:mBiddingScheduleDTOS) {
                try {
                    log.info("mBiddingScheduleDTO.getFormType() {}",mBiddingScheduleDTO.getFormType());
                    if (mBiddingScheduleDTO.getFormType().contains("S1") && mBiddingScheduleDTO.getActualStartDate()!=null){
                        x=x+1;
                    }
                    if (mBiddingScheduleDTO.getFormType().contains("S2") && mBiddingScheduleDTO.getActualStartDate()!=null){
                        x=x+3;
                    }
                    if (mBiddingScheduleDTO.getFormType().contains("S3") && mBiddingScheduleDTO.getActualStartDate()!=null){
                        x=x+5;
                    }
                    log.info("mBiddingScheduleDTO.getFormType() total {}",x);
                }catch (Exception e){}
            }

            if (menu.contains("EVALUASI")) {
                if (x == 2) {
                    mBiddingDTO.setFormType("S1");
                    mBiddingDTOS_.add(mBiddingDTO);
                }  else if (x == 6) {
                    mBiddingDTO.setFormType("S2");
                    mBiddingDTOS_.add(mBiddingDTO);
                }else if (x == 11) {
                    mBiddingDTO.setFormType("S2");
                    mBiddingDTOS_.add(mBiddingDTO);
                } else if (x == 16) {
                    mBiddingDTO.setFormType("S3");
                    mBiddingDTOS_.add(mBiddingDTO);
                } else {
                }
            }else if(menu.contains("SUBMISSION")){
                if (x == 1) {
                    mBiddingDTO.setFormType("S1");
                    mBiddingDTOS_.add(mBiddingDTO);
                } else if (x == 2) {
                    mBiddingDTO.setFormType("S1");
                    mBiddingDTOS_.add(mBiddingDTO);
                }else if (x == 3) {
                    mBiddingDTO.setFormType("S2");
                    mBiddingDTOS_.add(mBiddingDTO);
                }else if (x == 6) {
                    mBiddingDTO.setFormType("S2");
                    mBiddingDTOS_.add(mBiddingDTO);
                } else if (x == 11) {
                    mBiddingDTO.setFormType("S3");
                    mBiddingDTOS_.add(mBiddingDTO);
                } else if (x == 16) {
                    mBiddingDTO.setFormType("S3");
                    mBiddingDTOS_.add(mBiddingDTO);
                }  else { }
            }
        });
        return mBiddingDTOS_;
    }

    /**
     * Get all the mBiddingEvalResults.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MBiddingEvalResultDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MBiddingEvalResults");
        return mBiddingEvalResultRepository.findAll(pageable)
            .map(mBiddingEvalResultMapper::toDto);
    }

    /**
     * Get one mBiddingEvalResult by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MBiddingEvalResultDTO> findOne(Long id) {
        log.debug("Request to get MBiddingEvalResult : {}", id);
        return mBiddingEvalResultRepository.findById(id)
            .map(mBiddingEvalResultMapper::toDto);
    }

    /**
     * Delete the mBiddingEvalResult by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MBiddingEvalResult : {}", id);
        mBiddingEvalResultRepository.deleteById(id);
    }
}
