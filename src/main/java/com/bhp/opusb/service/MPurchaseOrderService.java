package com.bhp.opusb.service;

import com.bhp.opusb.domain.MPurchaseOrder;
import com.bhp.opusb.domain.MPurchaseOrderLine;
import com.bhp.opusb.repository.MPurchaseOrderRepository;
import com.bhp.opusb.service.dto.MPurchaseOrderDTO;
import com.bhp.opusb.service.dto.MPurchaseOrderLineDTO;
import com.bhp.opusb.service.mapper.MPurchaseOrderMapper;
import com.bhp.opusb.util.MapperJSONUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

import javax.sql.DataSource;

/**
 * Service Implementation for managing {@link MPurchaseOrder}.
 */
@Service
@Transactional
public class MPurchaseOrderService {

    private final Logger log = LoggerFactory.getLogger(MPurchaseOrderService.class);
    private final DataSource dataSource;
    private final MPurchaseOrderRepository mPurchaseOrderRepository;

    private final MPurchaseOrderMapper mPurchaseOrderMapper;

    public MPurchaseOrderService(MPurchaseOrderRepository mPurchaseOrderRepository, MPurchaseOrderMapper mPurchaseOrderMapper, DataSource dataSource) {
        this.mPurchaseOrderRepository = mPurchaseOrderRepository;
        this.mPurchaseOrderMapper = mPurchaseOrderMapper;
        this.dataSource = dataSource;
    }

    @Autowired
    MPurchaseOrderLineService mPurchaseOrderLineService;

    /**
     * Save a mPurchaseOrder.
     *
     * @param mPurchaseOrderDTO the entity to save.
     * @return the persisted entity.
     */
    public MPurchaseOrderDTO save(MPurchaseOrderDTO mPurchaseOrderDTO) {
        log.info("this json");
        log.info(MapperJSONUtil.prettyLog(mPurchaseOrderDTO));
        log.debug("Request to save MPurchaseOrder 22: {}", mPurchaseOrderDTO);

        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        String documentno = "PO-"+number;
        mPurchaseOrderDTO.setDocumentNo(documentno);
        MPurchaseOrder mPurchaseOrder = mPurchaseOrderMapper.toEntity(mPurchaseOrderDTO);
        mPurchaseOrder = mPurchaseOrderRepository.save(mPurchaseOrder);
        for (MPurchaseOrderLine mPurchaseOrderLine : mPurchaseOrderDTO.getmPurchaseOrderLineList()){
            mPurchaseOrderLine.setPurchaseOrder(mPurchaseOrder);
            mPurchaseOrderLineService.savetoPO(mPurchaseOrderLine);
        }
        return mPurchaseOrderMapper.toDto(mPurchaseOrder);
    }

    /**
     * Get all the mPurchaseOrders.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<MPurchaseOrderDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MPurchaseOrders 11");
        Page<MPurchaseOrderDTO> page;
        page = mPurchaseOrderRepository.findAll(pageable)
            .map(mPurchaseOrderMapper::toDto);
        log.debug("this page {}",MapperJSONUtil.prettyLog(page));
        return null;
    }

    /**
     * Get one mPurchaseOrder by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<MPurchaseOrderDTO> findOne(Long id) {

        log.debug("Request to get MPurchaseOrder : {}", id);
        Optional<MPurchaseOrderDTO> costumdto;
        costumdto = mPurchaseOrderRepository.findById(id)
            .map(mPurchaseOrderMapper::toDto);
        costumdto.equals(mPurchaseOrderMapper);
        try {costumdto.get().setmPurchaseOrderLineList( mPurchaseOrderLineService.
                mPOLineList(costumdto.get().getId()));
        }catch (Exception e){

        }
        return costumdto;
    }
    /**
     * Delete the mPurchaseOrder by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete MPurchaseOrder : {}", id);
        mPurchaseOrderRepository.deleteById(id);
    }

    public JasperPrint exportPurchaseOrder(Long poNo) throws IOException, SQLException, JRException {

        File file = null;

        file = ResourceUtils.getFile("classpath:templates/report/purchase-order.jrxml");

        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("poNo", poNo);
        JasperPrint print = JasperFillManager.fillReport(jasperReport, parameters, dataSource.getConnection());
        return print;

    }
}
