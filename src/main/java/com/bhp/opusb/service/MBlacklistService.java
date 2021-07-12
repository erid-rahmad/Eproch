package com.bhp.opusb.service;

import com.bhp.opusb.domain.MBlacklist;
import com.bhp.opusb.domain.MBlacklistLine;
import com.bhp.opusb.repository.MBlacklistRepository;
import com.bhp.opusb.repository.MBlacklistLineRepository;
import com.bhp.opusb.service.dto.MBlacklistDTO;
import com.bhp.opusb.service.mapper.MBlacklistMapper;
import com.bhp.opusb.service.mapper.MBlacklistLineMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

/**
 * Service Implementation for managing {@link MBlacklist}.
 */
@Service
@Transactional
public class MBlacklistService {

    private final Logger log = LoggerFactory.getLogger(MBlacklistService.class);

    private final MBlacklistRepository mBlacklistRepository;
    private final MBlacklistLineRepository mBlacklistLineRepository;

    private final MBlacklistMapper mBlacklistMapper;
    private final MBlacklistLineMapper mBlacklistLineMapper;

    public MBlacklistService(MBlacklistRepository mBlacklistRepository, MBlacklistMapper mBlacklistMapper,
        MBlacklistLineRepository mBlacklistLineRepository, MBlacklistLineMapper mBlacklistLineMapper) {
        this.mBlacklistRepository = mBlacklistRepository;
        this.mBlacklistMapper = mBlacklistMapper;
        this.mBlacklistLineRepository = mBlacklistLineRepository;
        this.mBlacklistLineMapper = mBlacklistLineMapper;
    }

    /**
     * Save a mBlacklist.
     *
     * @param mBlacklistDTO the entity to save.
     * @return the persisted entity.
     */
    public MBlacklistDTO save(MBlacklistDTO mBlacklistDTO) {
        log.debug("Request to save MBlacklist : {}", mBlacklistDTO);

        if(mBlacklistDTO.getLines()==null) mBlacklistDTO.setLines(new ArrayList<>());
        if(mBlacklistDTO.getUsers()==null) mBlacklistDTO.setUsers(new ArrayList<>());
        if(mBlacklistDTO.getShareholders()==null) mBlacklistDTO.setShareholders(new ArrayList<>());

        mBlacklistDTO.getLines().addAll(mBlacklistDTO.getUsers());
        mBlacklistDTO.getLines().addAll(mBlacklistDTO.getShareholders());

        List<MBlacklistLine> lines = mBlacklistLineMapper.toEntity(mBlacklistDTO.getLines());

        MBlacklist mBlacklist = mBlacklistMapper.toEntity(mBlacklistDTO);
        mBlacklist = mBlacklistRepository.save(mBlacklist);

        for(MBlacklistLine x: lines){
            if(x.getId() == null){
                x.setAdOrganization(mBlacklist.getAdOrganization());
                x.setBlacklist(mBlacklist);
            }
        }

        mBlacklistLineRepository.saveAll(lines);
        if(mBlacklistDTO.getDeleteLineIds()!=null && mBlacklistDTO.getDeleteLineIds().size()>0){
            mBlacklistLineRepository.deleteAll(mBlacklistLineRepository.findAllById(mBlacklistDTO.getDeleteLineIds()));
        }

        return mBlacklistMapper.toDto(mBlacklist);
    }

    /**
     * Get all the mBlacklists.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MBlacklistDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MBlacklists");
        return mBlacklistRepository.findAll(pageable)
            .map(mBlacklistMapper::toDto);
    }

    /**
     * Get one mBlacklist by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MBlacklistDTO> findOne(Long id) {
        log.debug("Request to get MBlacklist : {}", id);
        return mBlacklistRepository.findById(id)
            .map(mBlacklistMapper::toDto);
    }

    /**
     * Delete the mBlacklist by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MBlacklist : {}", id);
        mBlacklistRepository.deleteById(id);
    }
}
