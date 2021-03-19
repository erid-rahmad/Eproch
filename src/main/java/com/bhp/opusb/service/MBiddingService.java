package com.bhp.opusb.service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import com.bhp.opusb.domain.ADOrganization;
import com.bhp.opusb.domain.AdUser;
import com.bhp.opusb.domain.MBidding;
import com.bhp.opusb.domain.MBiddingLine;
import com.bhp.opusb.domain.MProjectInformation;
import com.bhp.opusb.domain.MVendorSuggestion;
import com.bhp.opusb.domain.enumeration.MBiddingProcess;
import com.bhp.opusb.repository.AdUserRepository;
import com.bhp.opusb.repository.MBiddingLineRepository;
import com.bhp.opusb.repository.MBiddingRepository;
import com.bhp.opusb.repository.MProjectInformationRepository;
import com.bhp.opusb.repository.MVendorSuggestionRepository;
import com.bhp.opusb.service.dto.MBiddingDTO;
import com.bhp.opusb.service.dto.MBiddingFormDTO;
import com.bhp.opusb.service.dto.MBiddingLineDTO;
import com.bhp.opusb.service.dto.MProjectInformationDTO;
import com.bhp.opusb.service.mapper.MBiddingFormMapper;
import com.bhp.opusb.service.mapper.MBiddingMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.config.JHipsterProperties;

/**
 * Service Implementation for managing {@link MBidding}.
 */
@Service
@Transactional
public class MBiddingService {

    private final Logger log = LoggerFactory.getLogger(MBiddingService.class);

    private final MBiddingRepository mBiddingRepository;
    private final MBiddingLineService mBiddingLineService;
    private final MProjectInformationService mProjectInformationService;
    private final ADOrganizationService adOrganizationService;

    private final MBiddingMapper mBiddingMapper;
    private final MBiddingFormMapper mBiddingFormMapper;

    private final JavaMailSender javaMailSender;

    private final JHipsterProperties jHipsterProperties;

    @Autowired
    MailService mailService;


    @Autowired
    MBiddingLineRepository mBiddingLineRepository;

    @Autowired
    MProjectInformationRepository mProjectInformationRepository;

    @Autowired
    MVendorSuggestionRepository mVendorSuggestionRepository;

    @Autowired
    AdUserRepository adUserRepository;

    public MBiddingService(MBiddingRepository mBiddingRepository, MBiddingLineService mBiddingLineService,
            MProjectInformationService mProjectInformationService, ADOrganizationService adOrganizationService,
            MBiddingMapper mBiddingMapper, MBiddingFormMapper mBiddingFormMapper,
            JavaMailSender javaMailSender, JHipsterProperties jHipsterProperties) {
        this.mBiddingRepository = mBiddingRepository;
        this.mBiddingLineService = mBiddingLineService;
        this.mProjectInformationService = mProjectInformationService;
        this.adOrganizationService = adOrganizationService;
        this.mBiddingMapper = mBiddingMapper;
        this.mBiddingFormMapper = mBiddingFormMapper;
        this.javaMailSender = javaMailSender;
        this.jHipsterProperties = jHipsterProperties;
    }
    /**
     * Save a mBidding.
     *
     * @param mBiddingDTO the entity to save.
     * @return the persisted entity.
     */
    public MBiddingDTO save(MBiddingDTO mBiddingDTO) {
        log.debug("Request to save MBidding : {}", mBiddingDTO);

        if (mBiddingDTO.getDocumentNo()==null) {
            Random rnd = new Random();
            int number = rnd.nextInt(999999);
            String documentno = "BD-" + number;
            mBiddingDTO.setDocumentNo(documentno);
        }

        if (Boolean.TRUE.equals(mBiddingDTO.isApproved())){
            log.info("send email to vendor");
//            mailService.sendEmail("erid.rahmad@gmail.com","testing","testing",false,false);
//            List<MVendorSuggestion> mVendorSuggestion = mVendorSuggestionRepository.findbyheaderid(664801);
            List<MVendorSuggestion> mVendorSuggestion = mVendorSuggestionRepository.findbyheaderid(mBiddingDTO.getId());

            for (MVendorSuggestion mVendorSuggestion1 : mVendorSuggestion){
                log.info("this vendor id {}",mVendorSuggestion1.getVendor().getId().toString());

                List<AdUser> adUsers =adUserRepository.findBycVendorIdAndActiveTrue(mVendorSuggestion1.getVendor().getId());
                log.info("this add user {}",adUsers);

                for (AdUser adUser : adUsers){
                    log.info("this email {} count {}",adUser.getUser().getEmail());
                    mailService.sendEmail(adUser.getUser().getEmail(),"testing","testing",false,false);
                }
            }
            log.info("this vendor sugestion {}",mVendorSuggestion.toString());
        }

//        mBiddingLineRepository.saveAll(mBiddingDTO.getBiddingLineList());
//        mProjectInformationRepository.saveAll(mBiddingDTO.getProjectInformationList());

        MBidding mBidding = mBiddingMapper.toEntity(mBiddingDTO);
        mBidding = mBiddingRepository.save(mBidding);
        /* log.info("this mbidding {}",MapperJSONUtil.prettyLog(mBidding));

        try {
            for (MBiddingLine mBiddingLine : mBiddingDTO.getBiddingLineList()){
                log.info("this line {}",MapperJSONUtil.prettyLog(mBiddingLine));
                mBiddingLine.setBidding(mBidding);
                mBiddingLineRepository.save(mBiddingLine);
            }
        }catch (Exception e){}
        try {
            for (MProjectInformation mProjectInformation : mBiddingDTO.getProjectInformationList() ){
                mProjectInformation.setBidding(mBidding);
                mProjectInformationRepository.save(mProjectInformation);
            }
        }catch (Exception e){} */
        return mBiddingMapper.toDto(mBidding);
    }

    /**
     * Save a mBidding form.
     *
     * @param mBiddingDTO the entity to save.
     * @return the persisted entity.
     */
    public MBiddingDTO saveForm(MBiddingFormDTO mBiddingDTO) {
        log.debug("Request to save MBidding : {}", mBiddingDTO);
        MBidding mBidding = mBiddingMapper.toEntity(mBiddingDTO);

        // TODO Set organization to be same as the user's organization.
        ADOrganization org = adOrganizationService.getDefaultOrganization();

        if (mBiddingDTO.getStep().equals(MBiddingProcess.INFO)) {
            mBidding.setAdOrganization(org);
            mBidding = mBiddingRepository.save(mBidding);
            mBiddingDTO = mBiddingFormMapper.toDto(mBidding);
            saveInformation(mBiddingDTO, mBiddingDTO.getBiddingLines(), mBiddingDTO.getProjectInformations(),
                    mBiddingDTO.getRemovedBiddingLines(), mBiddingDTO.getRemovedProjectInformations());
        }

        return mBiddingDTO;
    }

    private MBiddingDTO saveInformation(MBiddingDTO mBiddingDTO, List<MBiddingLineDTO> biddingLines,
            List<MProjectInformationDTO> projectInformations, List<MBiddingLineDTO> removedBiddingLines,
            List<MProjectInformationDTO> removedProjectInformations) {

        mBiddingLineService.saveAll(biddingLines, mBiddingDTO);
        mBiddingLineService.deleteAll(removedBiddingLines);
        mProjectInformationService.saveAll(projectInformations, mBiddingDTO);
        mProjectInformationService.deleteAll(removedProjectInformations);

        removedBiddingLines.clear();
        removedProjectInformations.clear();
        return mBiddingDTO;
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
        /* mBiddingDTO.get().setBiddingLineList(mBiddingLineService.findbyheader(mBiddingDTO.get().getId()));
        mBiddingDTO.get().setProjectInformationList(mProjectInformationService.findByBindId(mBiddingDTO.get().getId())); */
        return mBiddingDTO;
    }

    /**
     * Get one mBidding form by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MBiddingFormDTO> findOneForm(Long id, MBiddingProcess step) {
        log.debug("Request to get MBidding (FormDTO) : {}", id);
        Optional<MBiddingFormDTO> record = mBiddingRepository.findById(id).map(mBiddingFormMapper::toDto);
            
        record.ifPresent(dto -> {
            dto.setStep(step);

            if (step.equals(MBiddingProcess.INFO)) {
                List<MBiddingLineDTO> biddingLines = mBiddingLineService.findByBiddingId(dto.getId());
                // List<MProjectInformationDTO> projectInformations = mProjectInformationService.findByBindId(dto.getId());
                dto.setBiddingLines(biddingLines);
                // dto.setProjectInformations(projectInformations);
            }
        });

        return record;
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
