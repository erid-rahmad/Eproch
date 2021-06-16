package com.bhp.opusb.workflow.beans;

import java.time.LocalDate;

import com.bhp.opusb.domain.MBidding;
import com.bhp.opusb.repository.MBiddingRepository;
import com.bhp.opusb.service.dto.MBiddingDTO;
import com.bhp.opusb.service.mapper.MBiddingMapper;
import com.bhp.opusb.workflow.AbstractDocActionHandler;

import org.springframework.stereotype.Component;

@Component
public class BiddingDocHandler extends AbstractDocActionHandler<MBiddingDTO, MBidding> {
    private MBiddingRepository repo;
    private MBiddingMapper mapper;

    public BiddingDocHandler(MBiddingRepository repo, MBiddingMapper mapper){
        this.repo = repo;
        this.mapper = mapper;
    }

    @Override
    public boolean acceptTableName(String tableName) {
        return "m_bidding".equals(tableName);
    }

    @Override
    protected MBidding fromId(Long id, String tableName) {
        return repo.findById(id).get();
    }

    @Override
    protected MBidding toEntity(MBiddingDTO dto) {
        return mapper.toEntity(dto);
    }

    @Override
    protected MBiddingDTO toDto(MBidding entity) {
        return mapper.toDto(entity);
    }

    @Override
    protected MBidding save(MBidding entity) {
        return repo.save(entity);
    }

    @Override
    public MBiddingDTO approveIt(MBiddingDTO dto){
        dto.setDateApprove(LocalDate.now());
        save(toEntity(dto));
        return dto;
    }

    @Override
    public MBiddingDTO rejectIt(MBiddingDTO dto){
        dto.setDateReject(LocalDate.now());
        save(toEntity(dto));
        return dto;
    }
}
