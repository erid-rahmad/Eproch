package com.bhp.opusb.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletResponse;

import com.bhp.opusb.domain.ADTab;
import com.bhp.opusb.domain.ADWindow;
import com.bhp.opusb.repository.ADTabRepository;
import com.bhp.opusb.repository.ADWindowRepository;
import com.bhp.opusb.service.dto.ADWindowDTO;
import com.bhp.opusb.service.dto.ExportParameterDTO;
import com.bhp.opusb.service.mapper.ADWindowMapper;
import com.bhp.opusb.web.rest.errors.BadRequestAlertException;

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

    private static final Set<String> EXCLUDED_COLUMN_NAMES = Stream.of(
        "id", "uid", "created_by", "created_date", "last_modified_by", "last_modified_date", "tableName", "active"
    ).collect(Collectors.toSet());

    private final Logger log = LoggerFactory.getLogger(ADWindowService.class);

    private final ADTabService adTabService;
    private final ImportExportService importExportService;

    private final ADWindowRepository aDWindowRepository;
    private final ADTabRepository adTabRepository;

    private final ADWindowMapper aDWindowMapper;

    public ADWindowService(ADTabService adTabService, ImportExportService importExportService,
            ADWindowRepository aDWindowRepository, ADTabRepository adTabRepository, ADWindowMapper aDWindowMapper) {
        this.adTabService = adTabService;
        this.importExportService = importExportService;
        this.aDWindowRepository = aDWindowRepository;
        this.adTabRepository = adTabRepository;
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
        return aDWindowRepository.findAll(pageable).map(aDWindowMapper::toDto);
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
        return aDWindowRepository.findById(id).map(aDWindowMapper::toDto);
    }

    public String getWindowName(Long windowId) {
        return aDWindowRepository.findFirstById(windowId).getName();
    }

    /**
     * Export a window tabs to a CSV file.
     * 
     * @param parameter
     * @param response
     * @throws IOException
     */
    @Transactional(readOnly = true)
    public byte[] export(ExportParameterDTO parameter, HttpServletResponse response) throws IOException {
        log.debug("Request to export ADWindow : {}", parameter.getWindowId());
        ADTab mainTab = adTabRepository.findFirstByAdWindow_idAndParentTabIsNull(parameter.getWindowId())
            .orElseThrow(() -> new BadRequestAlertException("Invalid id", "aDWindow", "idnull"));

        String[] csvHeaderNames = getCsvHeaderNames(mainTab, parameter);
        return importExportService.exportCsv(parameter, mainTab.getAdTable().getName(), csvHeaderNames);
    }

    private String[] getCsvHeaderNames(ADTab mainTab, ExportParameterDTO parameter) {
        Map<String, Object> tabTree = new LinkedHashMap<>();
        adTabService.buildTab(tabTree, mainTab, null);

        List<String> headerNames = buildCsvHeaderNames(tabTree, null, null);

        if (parameter.isCurrentRowOnly() && ! parameter.getIncludedSubTabs().isEmpty()) {
            adTabRepository.findByIdInOrderByTabSequence(parameter.getIncludedSubTabs())
                .forEach(tab -> {
                    final Map<String, Object> tree = new LinkedHashMap<>();
                    adTabService.buildTab(tree, tab, null);
                    headerNames.addAll(buildCsvHeaderNames(tree, null, tab.getAdTable().getName()));
                });
        }

        return headerNames.toArray(new String[headerNames.size()]);
    }

    private List<String> buildCsvHeaderNames(Map<String, Object> tree, String prefix, String listName) {
        List<String> headerNames = new ArrayList<>();

        tree.entrySet().stream()
            .filter(entry -> ! entry.getKey().endsWith("_id") && ! EXCLUDED_COLUMN_NAMES.contains(entry.getKey()))
            .forEach(entry -> {
                String key = entry.getKey();
                Object value = entry.getValue();

                if (key.contains("@")) {
                    buildCsvHeaderNames((Map<String, Object>) value, key, listName)
                        .forEach(headerNames::add);
                } else {
                    StringBuilder name = new StringBuilder();

                    if (listName != null) {
                        name.append(listName).append(">");
                    }

                    if (prefix == null) {
                        name.append(key);
                    } else {
                        name.append(prefix + "." + key);
                    }

                    headerNames.add(name.toString());
                }
            });

        return headerNames;
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
            .ifPresent(tab -> adTabService.mapColumns(tabTree, tab, null));

        return tabTree;
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
