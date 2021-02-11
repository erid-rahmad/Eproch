package com.bhp.opusb.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.bhp.opusb.domain.ADColumn;
import com.bhp.opusb.domain.ADTab;
import com.bhp.opusb.domain.ADTable;
import com.bhp.opusb.domain.ADWindow;
import com.bhp.opusb.repository.ADTabRepository;
import com.bhp.opusb.repository.ADTableRepository;
import com.bhp.opusb.repository.ADWindowRepository;
import com.bhp.opusb.service.dto.ADWindowDTO;
import com.bhp.opusb.service.mapper.ADWindowMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ADWindow}.
 */
@Service
@Transactional
public class ADWindowService {

    private final Logger log = LoggerFactory.getLogger(ADWindowService.class);

    private final ADWindowRepository aDWindowRepository;
    private final ADTabRepository adTabRepository;
    private final ADTableRepository adTableRepository;

    private final ADWindowMapper aDWindowMapper;

    public ADWindowService(ADWindowRepository aDWindowRepository, ADTabRepository adTabRepository,
            ADTableRepository adTableRepository, ADWindowMapper aDWindowMapper) {
        this.aDWindowRepository = aDWindowRepository;
        this.adTabRepository = adTabRepository;
        this.adTableRepository = adTableRepository;
        this.aDWindowMapper = aDWindowMapper;
    }

    /**
     * Save a aDWindow.
     *
     * @param aDWindowDTO the entity to save.
     * @return the persisted entity.
     */
    public ADWindowDTO save(ADWindowDTO aDWindowDTO) {
        log.debug("Request to save ADWindow : {}", aDWindowDTO);
        ADWindow aDWindow = aDWindowMapper.toEntity(aDWindowDTO);
        aDWindow = aDWindowRepository.save(aDWindow);
        return aDWindowMapper.toDto(aDWindow);
    }

    /**
     * Get all the aDWindows.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ADWindowDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ADWindows");
        return aDWindowRepository.findAll(pageable)
            .map(aDWindowMapper::toDto);
    }

    /**
     * Get one aDWindow by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ADWindowDTO> findOne(Long id) {
        log.debug("Request to get ADWindow : {}", id);
        return aDWindowRepository.findById(id)
            .map(aDWindowMapper::toDto);
    }

    /**
     * Get the deeply nested tab tree of a window.
     * @param id the id of the window.
     * @return the tree structure of the tab.
     */
    @Transactional(readOnly = true)
    public Map<String, Object> getTabTree(Long windowId) {
        Map<String, Object> tabTree = new LinkedHashMap<>();
        adTabRepository.findFirstByAdWindow_idAndParentTabIsNull(windowId)
            .ifPresent(tab -> mapColumns(tabTree, tab, null));

        return tabTree;
    }

    private void mapColumns(Map<String, Object> tabTree, ADTab tab, ADTab parentTab) {
        ADTable table = tab.getAdTable();

        tabTree.put("tableName", table.getName());
        for (ADColumn column : table.getADColumns()) {
            if (Boolean.TRUE.equals(column.isForeignKey())) {
                adTableRepository.findFirstByName(column.getImportedTable())
                    .ifPresent(linkedTable -> {
                        if (parentTab == null || ! parentTab.getAdTable().equals(linkedTable)) {
                            tabTree.put(column.getSqlName() + "@" + linkedTable.getName(), buildLinkedTab(linkedTable));
                        }
                    });
            } else {
                tabTree.put(column.getSqlName(), column.getType());
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
     * Delete the aDWindow by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ADWindow : {}", id);
        aDWindowRepository.deleteById(id);
    }
}
