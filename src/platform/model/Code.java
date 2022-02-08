package platform.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Code {

    @Id
    @Column(nullable = false)
    private UUID id;
    @Column(columnDefinition = "TEXT")
    private String code;
    @Column
    private LocalDateTime date;
    @Column
    private Long time;
    @Column
    private Long views;

    private boolean toBeDeleted;
    private boolean restricted;
    private boolean restrictedByTime;
    private boolean restrictedByViews;

    public Code(String code, Long time, Long views) {
        this.id = UUID.randomUUID();
        this.code = code;
        this.date = LocalDateTime.now();
        if (time == null || time == 0L) {
            this.time = 0L;
            this.restrictedByTime = false;
        } else {
            this.time = time;
            this.restrictedByTime = true;
        }
        if (views == null || views == 0L) {
            this.views = 0L;
            this.restrictedByViews = false;
        } else {
            this.views = views;
            this.restrictedByViews = true;
        }
        this.restricted = (restrictedByTime || restrictedByViews);
        this.toBeDeleted = false;
    }

//    public boolean isRestricted() {
//        return (this.time > 0L && this.views > 0L);
//    }

    public Code updateAndReturn() {
        long secondsDiff = Math.abs( ChronoUnit.SECONDS.between(this.date, LocalDateTime.now()) );
        if (this.restricted) {
            this.time = Math.max(0L, this.time - secondsDiff);
            this.views = this.views - 1;
            if (this.time == 0L && this.restrictedByTime) {
                toBeDeleted = true;
            }
            if (this.views < 0L && restrictedByViews) {
                toBeDeleted = true;
            }
            if (this.views < 0L) {
                this.views = 0L;
            }
        }
        return this;
    }


}
