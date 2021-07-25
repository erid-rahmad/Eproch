package com.bhp.opusb.service;

import com.bhp.opusb.domain.AdUser;
import com.bhp.opusb.domain.MBidNegoPrice;
import com.bhp.opusb.domain.MBidNegoPriceLine;
import com.bhp.opusb.domain.MBiddingNegotiation;
import com.bhp.opusb.domain.MBiddingNegotiationChat;
import com.bhp.opusb.domain.MBiddingNegotiationLine;
import com.bhp.opusb.domain.MProposalPrice;
import com.bhp.opusb.domain.MProposalPriceLine;
import com.bhp.opusb.repository.AdUserRepository;
import com.bhp.opusb.repository.MBidNegoPriceLineRepository;
import com.bhp.opusb.repository.MBidNegoPriceRepository;
import com.bhp.opusb.repository.MBiddingNegotiationChatRepository;
import com.bhp.opusb.repository.MBiddingNegotiationLineRepository;
import com.bhp.opusb.repository.MBiddingNegotiationRepository;
import com.bhp.opusb.repository.MProposalPriceLineRepository;
import com.bhp.opusb.repository.MProposalPriceRepository;
import com.bhp.opusb.service.dto.MBiddingNegotiationChatDTO;
import com.bhp.opusb.service.mapper.MBiddingNegotiationChatMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link MBiddingNegotiationChat}.
 */
@Service
@Transactional
public class MBiddingNegotiationChatService {

    private final Logger log = LoggerFactory.getLogger(MBiddingNegotiationChatService.class);

    private final MBiddingNegotiationChatRepository mBiddingNegotiationChatRepository;
    private final MBiddingNegotiationLineRepository mBiddingNegotiationLineRepository;
    private final MBiddingNegotiationRepository mBiddingNegotiationRepository;

    private final MBidNegoPriceRepository mBidNegoPriceRepository;
    private final MBidNegoPriceLineRepository mBidNegoPriceLineRepository;
    private final MProposalPriceRepository mProposalPriceRepository;
    private final MProposalPriceLineRepository mProposalPriceLineRepository;

    private final MBiddingNegotiationChatMapper mBiddingNegotiationChatMapper;

    private final AdUserRepository adUserRepository;
    private final MailService mailService;

    public MBiddingNegotiationChatService(MBiddingNegotiationChatRepository mBiddingNegotiationChatRepository,
    MBiddingNegotiationChatMapper mBiddingNegotiationChatMapper,
    MBiddingNegotiationLineRepository mBiddingNegotiationLineRepository,
    MBiddingNegotiationRepository mBiddingNegotiationRepository,
    MBidNegoPriceRepository mBidNegoPriceRepository, MBidNegoPriceLineRepository mBidNegoPriceLineRepository,
    MProposalPriceRepository mProposalPriceRepository, MProposalPriceLineRepository mProposalPriceLineRepository,
    MailService mailService, AdUserRepository adUserRepository) {
        this.mBiddingNegotiationChatRepository = mBiddingNegotiationChatRepository;
        this.mBiddingNegotiationChatMapper = mBiddingNegotiationChatMapper;
        this.mBiddingNegotiationLineRepository = mBiddingNegotiationLineRepository;
        this.mBiddingNegotiationRepository = mBiddingNegotiationRepository;

        this.mBidNegoPriceRepository = mBidNegoPriceRepository;
        this.mBidNegoPriceLineRepository = mBidNegoPriceLineRepository;
        this.mProposalPriceRepository = mProposalPriceRepository;
        this.mProposalPriceLineRepository = mProposalPriceLineRepository;

        this.adUserRepository = adUserRepository;
        this.mailService = mailService;
    }

    /**
     * Save a mBiddingNegotiationChat.
     *
     * @param mBiddingNegotiationChatDTO the entity to save.
     * @return the persisted entity.
     */
    public MBiddingNegotiationChatDTO save(MBiddingNegotiationChatDTO mBiddingNegotiationChatDTO) {
        log.debug("Request to save MBiddingNegotiationChat : {}", mBiddingNegotiationChatDTO);

        MBiddingNegotiationChat mBiddingNegotiationChat = mBiddingNegotiationChatMapper.toEntity(mBiddingNegotiationChatDTO);
        mBiddingNegotiationChat = mBiddingNegotiationChatRepository.save(mBiddingNegotiationChat);

        MBiddingNegotiationLine mbnl = mBiddingNegotiationLineRepository.findById(mBiddingNegotiationChatDTO.getNegotiationLineId()).get();
        MBiddingNegotiation mbn = mBiddingNegotiationRepository.findById(mbnl.getNegotiation().getId()).get();
        if("not started".contentEquals(mbnl.getNegotiationStatus())){
            mbnl.setNegotiationStatus("in progress");
            mBiddingNegotiationLineRepository.save(mbnl);

            /*
            mbn.setBiddingStatus("P");
            mBiddingNegotiationRepository.save(mbn);
            */
            List<MProposalPrice> pps = mProposalPriceRepository.findByBiddingSubmission(mbn.getBiddingEvalResult().getBiddingSubmission());
            if(pps.size()>0) {
                MProposalPrice pp = pps.get(0);
                MBidNegoPrice bnp = new MBidNegoPrice();
                bnp.setNegotiationPrice(pp.getProposedPrice());
                bnp.setBidding(pp.getBiddingSubmission().getBidding());
                bnp.setPriceProposal(pp);
                bnp.setNegotiationLine(mbnl);
                bnp.setActive(true);

                bnp = mBidNegoPriceRepository.save(bnp);

                List<MProposalPriceLine> ppls = mProposalPriceLineRepository.findByProposalPrice(pp);
                List<MBidNegoPriceLine> bnpls = new ArrayList<>();
                for(MProposalPriceLine ppl: ppls){
                    MBidNegoPriceLine bnpl = new MBidNegoPriceLine();
                    bnpl.setActive(true);
                    bnpl.setBidNegoPrice(bnp);
                    bnpl.setBiddingLine(ppl.getBiddingLine());
                    bnpl.setPriceNegotiation(ppl.getProposedPrice());
                    bnpl.setTotalNegotiationPrice(ppl.getTotalPriceSubmission());
                    bnpl.setNegotiationPercentage(new BigDecimal("0"));
                    bnpl.setProposalLine(ppl);
                    bnpls.add(bnpl);
                }

                mBidNegoPriceLineRepository.saveAll(bnpls);
            }
        }

        if((mBiddingNegotiationChatDTO.getPublishToEmail()==null?false:mBiddingNegotiationChatDTO.getPublishToEmail())){
            if(StringUtils.hasLength(mBiddingNegotiationChatDTO.getBuyerText())) {
                String emailBody = "The buyer has responded to the negotiation of bidding " +
                mbn.getBiddingEvalResult().getBiddingSubmission().getBidding().getName() + ".<br/>"
                +"The following is their response: <br/><br/>"
                +mBiddingNegotiationChatDTO.getBuyerText();

                List<AdUser> pics = adUserRepository.findBycVendor(mBiddingNegotiationChat.getVendor());
                if(pics.size()==0) log.debug("No pics found for vendor {}", mBiddingNegotiationChat.getVendor());
                for(AdUser pic: pics){
                    log.debug("Sending Buyer contract notification to {}", pic.getUser().getEmail());
                    mailService.sendEmail(pic.getUser().getEmail(), "Buyer Responded to Negotiation for Bidding " +
                    mbn.getBiddingEvalResult().getBiddingSubmission().getBidding().getName(), emailBody, false, false);
                }
            } else if (StringUtils.hasLength(mBiddingNegotiationChatDTO.getVendorText())){
                // ... reply to who?
                // TODO: send to corresponding buyer; pic or sth... or nothing at all?
            }
        }

        return mBiddingNegotiationChatMapper.toDto(mBiddingNegotiationChat);
    }

    /**
     * Get all the mBiddingNegotiationChats.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MBiddingNegotiationChatDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MBiddingNegotiationChats");
        return mBiddingNegotiationChatRepository.findAll(pageable)
            .map(mBiddingNegotiationChatMapper::toDto);
    }

    /**
     * Get one mBiddingNegotiationChat by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MBiddingNegotiationChatDTO> findOne(Long id) {
        log.debug("Request to get MBiddingNegotiationChat : {}", id);
        return mBiddingNegotiationChatRepository.findById(id)
            .map(mBiddingNegotiationChatMapper::toDto);
    }

    /**
     * Delete the mBiddingNegotiationChat by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MBiddingNegotiationChat : {}", id);
        mBiddingNegotiationChatRepository.deleteById(id);
    }
}
