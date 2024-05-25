package org.repro3d.utils;

import org.repro3d.model.Job;
import org.repro3d.model.Printer;
import org.repro3d.model.Status;
import org.repro3d.repository.JobRepository;
import org.repro3d.service.PrinterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JobScheduler {

    private final JobRepository jobRepository;
    private final PrinterService printerService;

    @Autowired
    public JobScheduler(JobRepository jobRepository, PrinterService printerService) {
        this.jobRepository = jobRepository;
        this.printerService = printerService;
    }

    @Scheduled(fixedRate = 60000) // Check every minute
    public void checkWaitingJobs() {
        System.out.println("Checking for waiting jobs...");
        List<Job> waitingJobs = jobRepository.findByStatusOrderByJobIdAsc(new Status(1L, "Waiting"));
        System.out.println("Found " + waitingJobs.size() + " waiting jobs.");
        for (Job job : waitingJobs) {
            System.out.println("Processing job ID: " + job.getJobId());
            List<Printer> printers = printerService.getPrinters();
            for (Printer printer : printers) {
                if (printerService.isPrinterAvailable(printer)) {
                    System.out.println("Printer ID " + printer.getPrinter_id() + " is available.");
                    boolean jobStarted = printerService.startPrintJob(printer, job);
                    if (jobStarted) {
                        System.out.println("Started job ID: " + job.getJobId() + " on printer ID: " + printer.getPrinter_id());
                        job.setStatus(new Status(2L, "In Progress"));
                        job.setPrinter(printer);
                        jobRepository.save(job);
                        return;
                    } else {
                        System.out.println("Failed to start job ID: " + job.getJobId() + " on printer ID: " + printer.getPrinter_id());
                    }
                } else {
                    System.out.println("Printer ID " + printer.getPrinter_id() + " is not available.");
                }
            }
        }
    }

    @Scheduled(fixedRate = 120000) // Check every 120 seconds
    public void checkInProgressJobs() {
        System.out.println("Checking for in-progress jobs...");
        List<Job> inProgressJobs = jobRepository.findByStatusOrderByJobIdAsc(new Status(2L, "In Progress"));
        System.out.println("Found " + inProgressJobs.size() + " in-progress jobs.");
        for (Job job : inProgressJobs) {
            Printer printer = job.getPrinter();
            if (printer != null) {
                System.out.println("Checking job completion for job ID: " + job.getJobId() + " on printer ID: " + printer.getPrinter_id());
                if (printerService.isJobComplete(printer, job)) {
                    System.out.println("Job ID: " + job.getJobId() + " is complete.");
                    printerService.completeJob(job);
                    checkWaitingJobs();
                } else {
                    System.out.println("Job ID: " + job.getJobId() + " is still in progress.");
                }
            } else {
                System.out.println("No printer assigned to job ID: " + job.getJobId());
            }
        }
    }
}

