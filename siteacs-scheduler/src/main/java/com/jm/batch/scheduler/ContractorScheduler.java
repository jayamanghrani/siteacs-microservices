package com.jm.batch.scheduler;

import com.jm.batch.service.EmailService;
import com.jm.batch.service.ExcelParserService;
import com.jm.batch.util.ContractorValidationUtil;
import com.jm.common.entity.Contractor;
import com.jm.common.entity.JobRunLog;
import com.jm.common.repository.ContractorRepository;
import com.jm.common.repository.JobRunLogRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class ContractorScheduler {

    private final ContractorRepository contractorRepository;
    private final JobRunLogRepository jobRunLogRepository;
    private final ExcelParserService excelParserService;
    private final EmailService emailService;


    @Value("${file.upload-dir}")
    private String uploadDir;

    private static final Logger LOG = LoggerFactory.getLogger(ContractorScheduler.class);

//    @Scheduled(cron = "${scheduler.contractor-load.cron}")
    @Scheduled(fixedDelay = 26000)
    public void loadContractors() {
        LOG.info("ContractorScheduler job started");

        JobRunLog jobLog = new JobRunLog();
        jobLog.setJobName("loadContractor");
        jobLog.setStartedAt(LocalDateTime.now());

        int newRecordsTotal = 0, updated = 0, newfailedRecords = 0;

        List<Contractor> batch = new ArrayList<>();
        List<Contractor> newContractorsForEmail = new ArrayList<>();
        int batchSize = 50;

        try {
            String latestFilePath = getLatestFile(uploadDir);
            LOG.info("Processing file: {}", latestFilePath);
            List<Contractor> contractors = excelParserService.parse(latestFilePath);
            LOG.info("Total rows read from Excel: {}", contractors.size());

            Set<String> seenIds = new HashSet<>();

            for (Contractor c : contractors) {
                try {

                    // check is duplicate record -
                    if (!seenIds.add(c.getCnum())) {
                        LOG.warn("Duplicate ID skipped: {}", c.getCnum());
                        continue;
                    }

                    ContractorValidationUtil.validate(c);

                    boolean isNew = !contractorRepository.existsById(c.getCnum());
                    batch.add(c);

                    if (isNew) {
                        newRecordsTotal++;                        // Counter badhaya - "kitne NAYE insert hue"
                        newContractorsForEmail.add(c);      // Is contractor ko, EMAIL-basket mein bhi daal diya
                    } else {
                        updated++;                          // Counter badhaya - "kitne PURANE update hue"
                        // Email-basket mein NAHI daala, kyunki purane ko dobara email nahi bhejna
                    }

                    if (batch.size() == batchSize) {
                        try{
                            // ho skata h 50 record me se sirf 1 record me problem ho
                            // pura sab kuch fail hone se acha h , ek ek krke kre
                        saveBatchRecordInDB(batch);
                        }
                        catch (Exception e) {
                            LOG.error("Batch save failed, Now saving one one record", e);
                            // Fallback: ek-ek row save karo, taaki sirf galat wala fail ho
                            for (Contractor cr : batch) {
                                try {
                                    saveContractor(cr);   // single-row @Transactional method
                                } catch (Exception ex) {
                                    newfailedRecords++;
                                    LOG.error("Row failed for cnum: {}", c.getCnum(), ex);
                                }
                            }
                        }
                        batch.clear();
                    }

                } catch (Exception e) {
                    newfailedRecords++;
                    LOG.error("Failed to process contractor id: {}", c.getCnum(), e);
                }
            }

            if (!batch.isEmpty()) {
                saveBatchRecordInDB(batch);
            }

            // Sab save hone ke baad, emails bhejo
            for (Contractor c : newContractorsForEmail) {
                emailService.sendWelcomeEmail(c.getPersonalEmailId(), c.getFirstName());   // <-- YAHAN
            }

            jobLog.setStatus("SUCCESS");
            LOG.info("ContractorScheduler job completed. Inserted: {}, Updated: {}, Failed: {}",
                    newRecordsTotal, updated, newfailedRecords);

        } catch (Exception e) {
            jobLog.setStatus("FAILED");
            LOG.error("ContractorScheduler job failed completely", e);
        } finally {
            jobLog.setInserted(newRecordsTotal);
            jobLog.setUpdated(updated);
            jobLog.setFailed(newfailedRecords);
            jobLog.setFinishedAt(LocalDateTime.now());
            jobRunLogRepository.save(jobLog);
        }
    }

    private String getLatestFile(String dirPath) {
        java.io.File dir = new java.io.File(dirPath);
        java.io.File[] files = dir.listFiles((d, name) -> name.endsWith(".xlsx"));

        if (files == null || files.length == 0) {
            throw new RuntimeException("No Excel file found in directory: " + dirPath);
        }

        java.io.File latestFile = files[0];
        for (java.io.File f : files) {
            if (f.lastModified() > latestFile.lastModified()) {
                latestFile = f;
            }
        }
        return latestFile.getAbsolutePath();
    }

    //Nahi — @Transactional ko Entity-class pe nahi laga sakते. Chaliye samझते hain kyu.
    //@Transactional ek "behavior/action" pe lagता hai (method),
    // na ki ek "data-structure" (Entity) pe. Entity khud kуछ "execute" nahi karti —
    // usमें koई method-call/business-logic hoती hi nahi hai jispe transaction-boundary lagाई jaए.
    @Transactional
    public void saveContractor(Contractor c) {
        contractorRepository.save(c);
    }

    @Transactional
    public void saveBatchRecordInDB(List<Contractor> batch) {
        contractorRepository.saveAll(batch);
    }



    @Scheduled(cron = "0 0 3 * * *")   // roज raat 3 baje
    public void deactivateExpiredContractors() {
        List<Contractor> expired = contractorRepository.findByEndDateBeforeAndStatus(LocalDate.now(), "ACTIVE");
        for (Contractor c : expired) {
            c.setStatus("EXPIRED");
            contractorRepository.save(c);
            LOG.info("Contractor marked expired: {}", c.getCnum());
        }
    }

}
