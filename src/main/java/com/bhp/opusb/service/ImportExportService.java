package com.bhp.opusb.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bhp.opusb.repository.ImportExportRepository;
import com.bhp.opusb.service.dto.ImportCsvColumn;
import com.bhp.opusb.service.dto.ImportParameterDTO;
import com.bhp.opusb.util.AdColumnUtil;
import com.bhp.opusb.web.websocket.NotificationService;
import com.bhp.opusb.web.websocket.dto.NotificationDTO;
import com.bhp.opusb.web.websocket.dto.ProcessStatus;
import com.bhp.opusb.web.websocket.dto.ProcessType;
import com.univocity.parsers.common.ParsingContext;
import com.univocity.parsers.common.processor.BatchedColumnProcessor;
import com.univocity.parsers.common.processor.RowProcessor;
import com.univocity.parsers.csv.CsvFormat;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ImportExportService {
    private final Logger log = LoggerFactory.getLogger(ImportExportService.class);

    private final ImportExportRepository importExportRepository;
    private final NotificationService notificationService;

    public ImportExportService(ImportExportRepository importExportRepository, NotificationService notificationService) {
        this.importExportRepository = importExportRepository;
        this.notificationService = notificationService;
    }

    @Async("delegatingSecurityContextAsyncTaskExecutor")
    public void importCsv(InputStream input, ImportParameterDTO parameter) {
        CsvParserSettings settings = new CsvParserSettings();
        CsvFormat format = settings.getFormat();

        log.debug("Import CSV parameter: {}", parameter);
        RowProcessor processor = new BatchedColumnProcessor(parameter.getBatchSize()) {

            @Override
            public void batchProcessed(int rowsInThisBatch) {
                log.debug("batchProcessed. rowCount: {}", rowsInThisBatch);
                List<List<String>> columnValues = getColumnValuesAsList();
                List<Map<String, Object>> records = new ArrayList<>(rowsInThisBatch);
                int rowCount = columnValues.get(0).size();

                for (int rowIdx = 0; rowIdx < rowCount; ++rowIdx) {
                    Map<String, Object> record = new HashMap<>();
                    for (int colIdx = 0; colIdx < columnValues.size(); ++colIdx) {
                        final ImportCsvColumn columnMeta = parameter.getFieldsMap().get(String.valueOf(colIdx));

                        // Skip un-mapped fields.
                        if (columnMeta == null) {
                            continue;
                        }

                        String rawValue = columnValues.get(colIdx).get(rowIdx);
                        Object formattedValue = AdColumnUtil.formatValue(rawValue, columnMeta.getType());
                        record.put(columnMeta.getTargetName(), formattedValue);
                    }
                    records.add(record);
                }
                
                importExportRepository.importData(parameter.getTableName(), records, parameter.isInsertOnly());
            }

            @Override
            public void processEnded(ParsingContext context) {
                ProcessStatus status = ProcessStatus.SUCCESS;
                String message = "Data " + parameter.getTableName() + " has been imported";
                NotificationDTO statusPayload = new NotificationDTO(status, message, ProcessType.IMPORT);
                notificationService.notify(statusPayload);
            }
            
        };

        settings.setHeaderExtractionEnabled(true);
        settings.setNumberOfRecordsToRead(parameter.getMaxRows());
        settings.setProcessor(processor);
        format.setLineSeparator(parameter.getLineSeparator());
        format.setDelimiter(parameter.getDelimiter());

        CsvParser parser = new CsvParser(settings);
        parser.parse(input);
    }
}
