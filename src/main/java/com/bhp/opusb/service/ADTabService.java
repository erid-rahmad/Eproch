package com.bhp.opusb.service;

import com.bhp.opusb.domain.ADColumn;
import com.bhp.opusb.domain.ADTab;
import com.bhp.opusb.domain.ADTable;
import com.bhp.opusb.repository.ADTabRepository;
import com.bhp.opusb.repository.ADTableRepository;
import com.bhp.opusb.service.dto.ADTabDTO;
import com.bhp.opusb.service.mapper.ADTabMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link ADTab}.
 */
@Service
@Transactional
public class ADTabService {

    private final Logger log = LoggerFactory.getLogger(ADTabService.class);

    private final ADTabRepository aDTabRepository;
    private final ADTableRepository adTableRepository;
    private final ADTabMapper aDTabMapper;

    public ADTabService(ADTabRepository aDTabRepository, ADTableRepository adTableRepository, ADTabMapper aDTabMapper) {
        this.aDTabRepository = aDTabRepository;
        this.adTableRepository = adTableRepository;
        this.aDTabMapper = aDTabMapper;
    }

    /**
     * Save a aDTab.
     *
     * @param aDTabDTO the entity to save.
     * @return the persisted entity.
     */
    public ADTabDTO save(ADTabDTO aDTabDTO) {
        log.debug("Request to save ADTab : {}", aDTabDTO);
        ADTab aDTab = aDTabMapper.toEntity(aDTabDTO);
        aDTab = aDTabRepository.save(aDTab);
        return aDTabMapper.toDto(aDTab);
    }

    /**
     * Get all the aDTabs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ADTabDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ADTabs");
        return aDTabRepository.findAll(pageable)
            .map(aDTabMapper::toDto);
    }

    /**
     * Get one aDTab by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ADTabDTO> findOne(Long id) {
        log.debug("Request to get ADTab : {}", id);
        return aDTabRepository.findById(id)
            .map(aDTabMapper::toDto);
    }

    /**
     * Get the deeply nested tab tree of a window.
     * @param id the id of the window.
     * @return the tree structure of the tab.
     */
    @Transactional(readOnly = true)
    public Map<String, Object> getTabTree(Long tabId) {
        Map<String, Object> tabTree = new LinkedHashMap<>();
        aDTabRepository.findById(tabId).ifPresent(tab -> mapColumns(tabTree, tab, null));

        return tabTree;
    }

    private void mapColumns(Map<String, Object> tabTree, ADTab tab, ADTab parentTab) {
        ADTable table = tab.getAdTable();

        tabTree.put("tableName", table.getName());
        for (ADColumn column : table.getADColumns()) {
            tabTree.put(column.getSqlName(), column.getType());

            if (Boolean.TRUE.equals(column.isForeignKey())) {
                adTableRepository.findFirstByName(column.getImportedTable())
                    .ifPresent(linkedTable -> {
                        if (parentTab == null || ! parentTab.getAdTable().equals(linkedTable)) {
                            tabTree.put(column.getSqlName() + "@" + linkedTable.getName(), buildLinkedTab(linkedTable));
                        }
                    });
            }
        }

        if (!tab.getADTabs().isEmpty()) {
            tabTree.put("children", buildTabChildren(tab));
        }
    }

    private Map<String, Object> buildLinkedTab(ADTable linkedTable) {
        Map<String, Object> tab = new LinkedHashMap<>();
        for (ADColumn column : linkedTable.getADColumns()) {
            tab.put(column.getSqlName(), column.getType());
        }
        return tab;
    }

    private List<Map<String, Object>> buildTabChildren(ADTab parentTab) {
        return parentTab.getADTabs().stream().map(tab -> {
            Map<String, Object> tabTree = new LinkedHashMap<>();
            mapColumns(tabTree, tab, parentTab);
            return tabTree;
        }).collect(Collectors.toList());
    }

    /**
     * Delete the aDTab by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ADTab : {}", id);
        aDTabRepository.deleteById(id);
    }
}
