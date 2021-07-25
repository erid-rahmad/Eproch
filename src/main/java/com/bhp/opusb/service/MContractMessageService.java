package com.bhp.opusb.service;

import com.bhp.opusb.domain.AdUser;
import com.bhp.opusb.domain.MContract;
import com.bhp.opusb.domain.MContractMessage;
import com.bhp.opusb.repository.AdUserRepository;
import com.bhp.opusb.repository.MContractMessageRepository;
import com.bhp.opusb.repository.MContractRepository;
import com.bhp.opusb.service.dto.MContractMessageDTO;
import com.bhp.opusb.service.mapper.MContractMessageMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link MContractMessage}.
 */
@Service
@Transactional
public class MContractMessageService {

    private final Logger log = LoggerFactory.getLogger(MContractMessageService.class);

    private final MContractMessageRepository mContractMessageRepository;
    private final MContractRepository mContractRepository;

    private final MContractMessageMapper mContractMessageMapper;
    
    private final AdUserRepository adUserRepository;
    private final MailService mailService;

    public MContractMessageService(MContractMessageRepository mContractMessageRepository, MContractRepository mContractRepository,
        MContractMessageMapper mContractMessageMapper, MailService mailService, AdUserRepository adUserRepository) {
        this.mContractMessageRepository = mContractMessageRepository;
        this.mContractMessageMapper = mContractMessageMapper;
        this.mContractRepository = mContractRepository;
        this.adUserRepository = adUserRepository;
        this.mailService = mailService;
    }

    /**
     * Save a mContractMessage.
     *
     * @param mContractMessageDTO the entity to save.
     * @return the persisted entity.
     */
    public MContractMessageDTO save(MContractMessageDTO mContractMessageDTO) {
        log.debug("Request to save MContractMessage : {}", mContractMessageDTO);
        MContractMessage mContractMessage = mContractMessageMapper.toEntity(mContractMessageDTO);
        mContractMessage = mContractMessageRepository.save(mContractMessage);

        MContract contract = mContractRepository.findById(mContractMessageDTO.getContractId()).get();

        if((mContractMessageDTO.getPublishToEmail()==null?false:mContractMessageDTO.getPublishToEmail())){
            if(StringUtils.hasLength(mContractMessageDTO.getBuyerText())) {
                String emailBody = "The buyer has posted something on Contract " +
                contract.getName() + "'s Message Board.<br/>"
                +"The following is their response: <br/><br/>"
                +mContractMessageDTO.getBuyerText();

                List<AdUser> pics = adUserRepository.findBycVendor(contract.getVendor());
                if(pics.size()==0) log.debug("No pics found for vendor {}", contract.getVendor());
                for(AdUser pic: pics){
                    log.debug("Sending Buyer contract notification to {}", pic.getUser().getEmail());
                    mailService.sendEmail(pic.getUser().getEmail(), "New post on Contract " +
                    contract.getName() + "'s Message Board", emailBody, false, false);
                }
            } else if (StringUtils.hasLength(mContractMessageDTO.getVendorText())){
                // ... reply to who?
                // TODO: send to corresponding buyer; pic or sth... or nothing at all?
            }
        }

        return mContractMessageMapper.toDto(mContractMessage);
    }

    /**
     * Get all the mContractMessages.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MContractMessageDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MContractMessages");
        return mContractMessageRepository.findAll(pageable)
            .map(mContractMessageMapper::toDto);
    }

    /**
     * Get one mContractMessage by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MContractMessageDTO> findOne(Long id) {
        log.debug("Request to get MContractMessage : {}", id);
        return mContractMessageRepository.findById(id)
            .map(mContractMessageMapper::toDto);
    }

    /**
     * Delete the mContractMessage by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MContractMessage : {}", id);
        mContractMessageRepository.deleteById(id);
    }
}
