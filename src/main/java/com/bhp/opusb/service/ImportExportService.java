package com.bhp.opusb.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.bhp.opusb.repository.ImportExportRepository;
import com.bhp.opusb.service.dto.ImportCsvColumn;
import com.bhp.opusb.service.dto.ImportParameterDTO;
import com.bhp.opusb.util.AdColumnUtil;
import com.bhp.opusb.web.websocket.NotificationService;
import com.bhp.opusb.web.websocket.dto.NotificationDTO;
import com.bhp.opusb.web.websocket.dto.ProcessStatus;
import com.bhp.opusb.web.websocket.dto.ProcessType;
import com.univocity.parsers.common.ParsingContext;
import com.univocity.parsers.common.processor.RowProcessor;
import com.univocity.parsers.csv.CsvFormat;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
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
        log.debug("Request to import CSV. parameter: {}", parameter);
        CsvParserSettings settings = new CsvParserSettings();
        CsvFormat format = settings.getFormat();
        RowProcessor processor = new BatchedRowProcessor(parameter);

        settings.setHeaderExtractionEnabled(true);
        settings.setNumberOfRecordsToRead(parameter.getMaxRows());
        settings.setProcessor(processor);
        format.setLineSeparator(parameter.getLineSeparator());
        format.setDelimiter(parameter.getDelimiter());

        CsvParser parser = new CsvParser(settings);
        parser.parse(input);
    }

    private class BatchedRowProcessor implements RowProcessor {

        private final List<String[]> rows = new ArrayList<>();
        private final int rowsPerBatch;
        private final ImportParameterDTO parameter;
        private int batchCount;
        private long rowCount;
        private String errorMessage;

        public BatchedRowProcessor(ImportParameterDTO parameter) {
            this.parameter = parameter;
            rowsPerBatch = parameter.getBatchSize();
        }

        @Override
        public void processStarted(ParsingContext context) {
            rows.clear();
            batchCount = 0;
        }

        @Override
        public void rowProcessed(String[] row, ParsingContext context) {
            if (errorMessage == null) {
                rows.add(row);
                ++rowCount;

                if (++batchCount >= rowsPerBatch) {
                    processBatch();
                    rows.clear();
                    batchCount = 0;
                }
            } else {
                context.stop();
            }
        }
        
        public void processBatch() {
            List<Map<String, Object>> records = rows.stream().map(row -> {
                Map<String, Object> record = new HashMap<>(row.length);
                for (int colIdx = 0; colIdx < row.length; ++colIdx) {
                    final ImportCsvColumn columnMeta = parameter.getFieldsMap().get(String.valueOf(colIdx));

                    // Skip un-mapped fields.
                    if (columnMeta == null) {
                        continue;
                    }

                    Object formattedValue = AdColumnUtil.formatValue(row[colIdx], columnMeta.getType());
                    record.put(columnMeta.getTargetName(), formattedValue);
                }
                return record;
            }).collect(Collectors.toList());

            try {
                importExportRepository.importData(parameter.getTableName(), records, parameter.isInsertOnly());
            } catch (DataAccessException e) {
                log.error("Error importing the data. {}", e.getLocalizedMessage());
                errorMessage = "There was error when importing the data";
            }
        }

        @Override
        public void processEnded(ParsingContext context) {
            // Process the remaining batch.
            if (batchCount > 0) {
                processBatch();
            }

            final boolean noError = errorMessage == null;
            ProcessStatus status = noError ? ProcessStatus.SUCCESS : ProcessStatus.ERROR;
            String message = noError ? "Imported " + rowCount + " row(s) into " + parameter.getTableName() : errorMessage;
            NotificationDTO statusPayload = new NotificationDTO(status, message, ProcessType.IMPORT);

            notificationService.notify(statusPayload);
        }
    }
}
