package com.bhp.opusb.service;

import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.bhp.opusb.domain.enumeration.ADColumnType;
import com.bhp.opusb.repository.ImportExportRepository;
import com.bhp.opusb.service.dto.ImportCsvColumn;
import com.bhp.opusb.service.dto.ImportParameterDTO;
import com.bhp.opusb.service.dto.ImportRecordWrapper;
import com.bhp.opusb.web.websocket.NotificationService;
import com.bhp.opusb.web.websocket.dto.NotificationDTO;
import com.bhp.opusb.web.websocket.dto.ProcessStatus;
import com.bhp.opusb.web.websocket.dto.ProcessType;
import com.univocity.parsers.common.ParsingContext;
import com.univocity.parsers.common.processor.RowProcessor;
import com.univocity.parsers.csv.CsvFormat;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;

import org.joda.time.Instant;
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
        CsvParserSettings settings = new CsvParserSettings();
        CsvFormat format = settings.getFormat();
        
        log.debug("Import CSV parameter: {}", parameter);
        RowProcessor processor = new DynCsvRowProcessor(parameter.getTableName(), parameter.getFieldsMap());
        
        settings.setHeaderExtractionEnabled(true);
        settings.setNumberOfRecordsToRead(parameter.getMaxRows());
        settings.setProcessor(processor);
        format.setLineSeparator(parameter.getLineSeparator());
        format.setDelimiter(parameter.getDelimiter());

        CsvParser parser = new CsvParser(settings);
        parser.parse(input);
    }

    private class DynCsvRowProcessor implements RowProcessor {

        private final String tableName;
        private final Map<String, ImportCsvColumn> fieldsMap;
        private final ImportRecordWrapper recordWrapper = new ImportRecordWrapper();

        public DynCsvRowProcessor(String tableName, Map<String, ImportCsvColumn> fieldsMap) {
            this.tableName = tableName;
            this.fieldsMap = fieldsMap;
        }
        
        @Override
        public void processStarted(ParsingContext context) {
            log.debug("Processing CSV...");

            if (log.isDebugEnabled()) {
                log.debug("Headers: {}", Stream.of(context.headers()).collect(Collectors.joining(", ")));
            }
        }

        @Override
        public void rowProcessed(String[] row, ParsingContext context) {
            final Map<String, Object> record = new HashMap<>();
            
            for (int i = 0; i < row.length; ++i) {
                final ImportCsvColumn columnMeta = fieldsMap.get(String.valueOf(i));

                // Skip un-mapped fields.
                if (columnMeta == null) {
                    continue;
                }

                String rawValue = row[i];
                Object formattedValue = formatValue(rawValue, columnMeta.getType());
                record.put(columnMeta.getTargetName(), formattedValue);
            }
            recordWrapper.addRecord(record);
        }

        @Override
        public void processEnded(ParsingContext context) {
            ProcessStatus status = ProcessStatus.SUCCESS;
            String message = "Data " + tableName + " has been imported";

            try {
                importExportRepository.importData(tableName, recordWrapper.getRecords());
            } catch (DataAccessException e) {
                message = "Failed importing " + tableName + " data. " + e.getLocalizedMessage();
                status = ProcessStatus.ERROR;
            }

            NotificationDTO statusPayload = new NotificationDTO(status, message, ProcessType.IMPORT);
            notificationService.notify(statusPayload);
        }

        /**
         * Format the raw value into an object based on the specified field type.
         * @param rawValue
         * @param fieldType JHipster's type of fields. https://www.jhipster.tech/jdl/entities-fields#field-types-and-validations
         * @return
         */
        private Object formatValue(String rawValue, ADColumnType fieldType) {
            Object result = rawValue;

            if (ADColumnType.INTEGER.equals(fieldType)) {
                try {
                    result = Integer.parseInt(rawValue);
                } catch (NumberFormatException e) {
                    log.warn("Failed to parse text to Integer. text: {}", rawValue);
                }
            } else if (ADColumnType.LONG.equals(fieldType)) {
                try {
                    result = Long.parseLong(rawValue);
                } catch (NumberFormatException e) {
                    log.warn("Failed to parse text to Long. text: {}", rawValue);
                }
            } else if (ADColumnType.FLOAT.equals(fieldType)) {
                try {
                    result = Float.parseFloat(rawValue);
                } catch (NumberFormatException e) {
                    log.warn("Failed to parse text to Float. text: {}", rawValue);
                }
            } else if (ADColumnType.DOUBLE.equals(fieldType)) {
                try {
                    result = Double.parseDouble(rawValue);
                } catch (NumberFormatException e) {
                    log.warn("Failed to parse text to Double. text: {}", rawValue);
                }
            } else if (ADColumnType.BIG_DECIMAL.equals(fieldType)) {
                result = new BigDecimal(rawValue);
            } else if (ADColumnType.BOOLEAN.equals(fieldType)) {
                result = rawValue != null && (rawValue.equals("1") || rawValue.equalsIgnoreCase("Y")
                        || rawValue.equalsIgnoreCase("YES") || rawValue.equalsIgnoreCase("TRUE"));
            } else if (ADColumnType.LOCAL_DATE.equals(fieldType)) {
                // e.g. yyyy-MM-dd
                result = LocalDate.parse(rawValue, DateTimeFormatter.ISO_LOCAL_DATE);
            } else if (ADColumnType.ZONED_DATE_TIME.equals(fieldType)) {
                // e.g. yyyy-MM-ddTHH:mm:ss+08:00
                result = ZonedDateTime.parse(rawValue, DateTimeFormatter.ISO_DATE_TIME);
            } else if (ADColumnType.INSTANT.equals(fieldType)) {
                // e.g. yyyy-MM-ddTHH:mm:ssZ
                result = Instant.parse(rawValue);
            } else if (ADColumnType.DURATION.equals(fieldType)) {
                // ISO-8601 period formats PnYnMnD and PnW
                result = Period.parse(rawValue);
            }

            return result;
        }
    }
}
