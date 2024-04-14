package repro3d.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Represents a print job entity in the system.
 * <p>
 * This entity stores information about a job, including its associated printer,
 * status, and the start and end dates of the job. It is linked to specific items
 * by their item ID, which references an external entity in the OrderDB.
 */
@Entity
@Table(name = "job",schema = "PrinterDB")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Job {

    /**
     * The unique identifier for the job.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long job_id;

    /**
     * The item ID this job is associated with.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", referencedColumnName = "item_id")
    private Item item;

    /**
     * The printer assigned to this job. It is a many-to-one relationship since
     * multiple jobs can be assigned to a single printer.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "printer_id", referencedColumnName = "printer_id")
    private Printer printer;

    /**
     * The current status of the job. It is a many-to-one relationship since
     * multiple jobs can share the same status (e.g., 'Pending', 'Completed').
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id",  referencedColumnName = "status_id")
    private Status status;

    /**
     * The start date and time of the job.
     */
    @Column
    private Date start_date;

    /**
     * The end date and time of the job.
     */
    @Column
    private Date end_date;
}
