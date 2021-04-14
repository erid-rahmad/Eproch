package com.bhp.opusb.service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import com.bhp.opusb.config.ApplicationProperties;
import com.bhp.opusb.config.ApplicationProperties.Document;
import com.bhp.opusb.domain.AdUser;
import com.bhp.opusb.domain.MBidding;
import com.bhp.opusb.domain.MVendorSuggestion;
import com.bhp.opusb.domain.User;
import com.bhp.opusb.domain.enumeration.MBiddingProcess;
import com.bhp.opusb.repository.AdUserRepository;
import com.bhp.opusb.repository.CDocumentTypeRepository;
import com.bhp.opusb.repository.MBiddingLineRepository;
import com.bhp.opusb.repository.MBiddingRepository;
import com.bhp.opusb.repository.MProjectInformationRepository;
import com.bhp.opusb.repository.MVendorSuggestionRepository;
import com.bhp.opusb.service.dto.MBiddingDTO;
import com.bhp.opusb.service.dto.MBiddingFormDTO;
import com.bhp.opusb.service.dto.MBiddingLineDTO;
import com.bhp.opusb.service.dto.MBiddingScheduleDTO;
import com.bhp.opusb.service.dto.MDocumentScheduleDTO;
import com.bhp.opusb.service.dto.MProjectInformationDTO;
import com.bhp.opusb.service.dto.MVendorInvitationDTO;
import com.bhp.opusb.service.dto.MVendorSuggestionDTO;
import com.bhp.opusb.service.mapper.MBiddingFormMapper;
import com.bhp.opusb.service.mapper.MBiddingMapper;
import com.bhp.opusb.util.DocumentUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link MBidding}.
 */
@Service
@Transactional
public class MBiddingService {

    private final Logger log = LoggerFactory.getLogger(MBiddingService.class);

    private final MBiddingRepository mBiddingRepository;
    private final CDocumentTypeRepository cDocumentTypeRepository;

    private final MBiddingLineService mBiddingLineService;
    private final MProjectInformationService mProjectInformationService;
    private final MBiddingScheduleService mBiddingScheduleService;
    private final MDocumentScheduleService mDocumentScheduleService;
    private final MVendorInvitationService mVendorInvitationService;
    private final MVendorSuggestionService mVendorSuggestionService;

    private final MBiddingMapper mBiddingMapper;
    private final MBiddingFormMapper mBiddingFormMapper;

    private final Document document;

    @Autowired
    private MailService mailService;

    @Autowired
    private MBiddingLineRepository mBiddingLineRepository;

    @Autowired
    private MProjectInformationRepository mProjectInformationRepository;

    @Autowired
    private MVendorSuggestionRepository mVendorSuggestionRepository;

    @Autowired
    private AdUserRepository adUserRepository;

    public MBiddingService(ApplicationProperties applicationProperties, CDocumentTypeRepository cDocumentTypeRepository,
            MBiddingRepository mBiddingRepository, MBiddingLineService mBiddingLineService,
            MProjectInformationService mProjectInformationService, MBiddingScheduleService mBiddingScheduleService,
            MDocumentScheduleService mDocumentScheduleService, MVendorInvitationService mVendorInvitationService,
            MVendorSuggestionService mVendorSuggestionService, MBiddingMapper mBiddingMapper,
            MBiddingFormMapper mBiddingFormMapper) {
        this.cDocumentTypeRepository = cDocumentTypeRepository;
        this.mBiddingRepository = mBiddingRepository;
        this.mBiddingLineService = mBiddingLineService;
        this.mProjectInformationService = mProjectInformationService;
        this.mBiddingScheduleService = mBiddingScheduleService;
        this.mDocumentScheduleService = mDocumentScheduleService;
        this.mVendorInvitationService = mVendorInvitationService;
        this.mVendorSuggestionService = mVendorSuggestionService;
        this.mBiddingMapper = mBiddingMapper;
        this.mBiddingFormMapper = mBiddingFormMapper;

        document = applicationProperties.getDocuments().get("bidding");
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
            String documentno = document.getDocumentNumberPrefix() + number;
            mBiddingDTO.setDocumentNo(documentno);
        }

        if (Boolean.TRUE.equals(mBiddingDTO.isApproved())){
            log.info("send email to vendor");
            List<MVendorSuggestion> mVendorSuggestion = mVendorSuggestionRepository.findbyheaderid(mBiddingDTO.getId());
            log.info("this list vendor {}",mVendorSuggestion);
            mailService.sendWinnerEmail("sitech@gmail.com");
            mailService.sendBiddingInvatationEmail("sitech@gmail.com",mBiddingDTO);
            mailService.sendTerminateEmail("sitech@gmail.com");


            for (MVendorSuggestion mVendorSuggestion1 : mVendorSuggestion){

                log.info("this vendor id {}",mVendorSuggestion1.getVendor().getId().toString());

//                List<AdUser> adUsers =adUserRepository.findBycVendorIdAndActiveTrue(mVendorSuggestion1.getVendor().getId());
                List<AdUser> adUsers =adUserRepository.findBycVendorId(41001l);
                log.info("this add user {}",adUsers);

                for (AdUser adUser : adUsers){

                    log.info("this email {} count {}",adUser.getUser().getEmail());
//                    mailService.sendEmail(adUser.getUser().getEmail(),"testing","testing",false,false);
                    mailService.sendEmail(adUser.getUser().getEmail(),"Bidding Invitation","<html><body>" +
                        "<p>Kepada Bapak/Ibu Pimpinan </p>" +
                        mVendorSuggestion1.getVendor().getName()+""+
                        "<p>Hal: Undangan Bidding</p>"+
                        "<p>Dengan hormat</p>" +
                        "<p>Sehubung dengan bidding sesuai judul di atas,kami mengundang Ibu/Bapak untuk mengikuti bidding tersebut" +
                        ". Silahkan Bapak/Ibu melakukan login di "+" login.com "+" untuk mendaftar pada bidding tersebut" +
                        ". Demikian penyampaian ini kami dengan senang hati menerima bila ada yang hendak di komunikasikan silahkan sampaikan ke email eproc.berca.co.id </p>" +
                        "<p>Hormat Kami</p>" +
                        "<p>Berca.co.id</p>" +
                        "</body></html>",false,true);
                }
            }
            log.info("this vendor sugestion {}",mVendorSuggestion.toString());
        }

//        mBiddingLineRepository.saveAll(mBiddingDTO.getBiddingLineList());
//        mProjectInformationRepository.saveAll(mBiddingDTO.getProjectInformationList());

        MBidding mBidding = mBiddingMapper.toEntity(mBiddingDTO);
        mBidding = mBiddingRepository.save(mBidding);
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
        MBiddingProcess step = mBiddingDTO.getStep();

        if (MBiddingProcess.INFO.equals(step)) {
            final List<MBiddingLineDTO> lines = mBiddingDTO.getBiddingLines();
            final List<MBiddingLineDTO> removedLines = mBiddingDTO.getRemovedBiddingLines();
            final List<MProjectInformationDTO> projectInformations = mBiddingDTO.getProjectInformations();
            final List<MProjectInformationDTO> removedProjectInformations = mBiddingDTO.getRemovedProjectInformations();

            if (mBidding.getDocumentNo() == null) {
               mBidding.setDocumentNo(DocumentUtil.buildDocumentNumber(document.getDocumentNumberPrefix(), mBiddingRepository));
            }

            // Set the default Purchase Order document type.
            if (mBidding.getDocumentType() == null) {
                cDocumentTypeRepository.findFirstByName(document.getDocumentType())
                    .ifPresent(mBidding::setDocumentType);
            }

            mBidding = mBiddingRepository.save(mBidding);
            mBiddingDTO = mBiddingFormMapper.toDto(mBidding);
            saveInformation(mBiddingDTO, lines, projectInformations,
                    removedLines, removedProjectInformations);

            // Initialize bidding schedules as it depends on the Event Type value.
            mBiddingScheduleService.initBiddingSchedules(mBidding);

        } else if (MBiddingProcess.SCHEDULE.equals(step)) {
            final List<MBiddingScheduleDTO> biddingSchedules = mBiddingDTO.getBiddingSchedules();
            final List<MDocumentScheduleDTO> documentSchedules = mBiddingDTO.getDocumentSchedules();
            final List<MDocumentScheduleDTO> removedDocumentSchedules = mBiddingDTO.getRemovedDocumentSchedules();
            saveSchedule(mBiddingDTO, biddingSchedules, documentSchedules, removedDocumentSchedules);
        } else if (MBiddingProcess.SELECTION.equals(step)) {
            final List<MVendorInvitationDTO> vendorInvitations = mBiddingDTO.getVendorInvitations();
            final List<MVendorInvitationDTO> removedVendorInvitations = mBiddingDTO.getRemovedVendorInvitations();
            final List<MVendorSuggestionDTO> vendorSuggestions = mBiddingDTO.getVendorSuggestions();
            final List<MVendorSuggestionDTO> removedVendorSuggestions = mBiddingDTO.getRemovedVendorSuggestions();
            saveInvitation(mBiddingDTO, vendorInvitations, vendorSuggestions, removedVendorInvitations, removedVendorSuggestions);
        } else if (MBiddingProcess.SCORING.equals(step)) {

        }

        return mBiddingDTO;
    }

    /**
     * Save bidding header, lines, and project informations in the first step.
     * @param mBiddingDTO
     * @param biddingLines
     * @param projectInformations
     * @param removedBiddingLines
     * @param removedProjectInformations
     * @return
     */
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
     * Save bidding schedule and document schedule in the second step.
     * @param mBiddingDTO
     * @param biddingSchedules
     * @param documentSchedules
     * @param removedDocumentSchedules
     * @return
     */
    private MBiddingDTO saveSchedule(MBiddingDTO mBiddingDTO, List<MBiddingScheduleDTO> biddingSchedules,
            List<MDocumentScheduleDTO> documentSchedules, List<MDocumentScheduleDTO> removedDocumentSchedules) {

        mBiddingScheduleService.saveAll(biddingSchedules);
        mDocumentScheduleService.saveAll(documentSchedules, mBiddingDTO);
        mDocumentScheduleService.deleteAll(removedDocumentSchedules);
        removedDocumentSchedules.clear();
        return mBiddingDTO;
    }

    /**
     * Save vendor invitation and suggestions in the third step.
     * @param mBiddingDTO
     * @param vendorInvitations
     * @param vendorSuggestions
     * @param removedVendorInvitations
     * @param removedVendorSuggestions
     * @return
     */
    private MBiddingDTO saveInvitation(MBiddingDTO mBiddingDTO, List<MVendorInvitationDTO> vendorInvitations,
            List<MVendorSuggestionDTO> vendorSuggestions, List<MVendorInvitationDTO> removedVendorInvitations,
            List<MVendorSuggestionDTO> removedVendorSuggestions) {

        mVendorInvitationService.saveAll(vendorInvitations, mBiddingDTO);
        mVendorInvitationService.deleteAll(removedVendorInvitations);
        removedVendorInvitations.clear();
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
         mBiddingDTO.get().setBiddingLineList(mBiddingLineService.findbyheader(mBiddingDTO.get().getId()));
        mBiddingDTO.get().setProjectInformationList(mProjectInformationService.findByBindId(mBiddingDTO.get().getId()));
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
