package db;
import java.time.LocalDateTime;

public class Meeting {

    private int meetingId;
    private String title;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int floorNumber;
 
    public Meeting(int meetingId, String title, LocalDateTime startTime, LocalDateTime endTime, int floorNumber) {
        if (title == null || title.isEmpty()) {
            throw new IllegalArgumentException("Title cannot be null or empty.");
        }
        if (startTime == null || endTime == null) {
            throw new IllegalArgumentException("Start time and end time cannot be null.");
        }
        if (!endTime.isAfter(startTime)) {
            throw new IllegalArgumentException("End time must be after start time.");
        }
        if(meetingId <= 0) {
            throw new IllegalArgumentException("Meeting ID cannot be non-positive.");
        }
        if(floorNumber < 0) {   
            throw new IllegalArgumentException("Floor number cannot be negative.");
        }

        this.meetingId = meetingId;
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.floorNumber = floorNumber;
    }


    public int getMeetingId() {
        return meetingId;
    }
    public String getTitle() {
        return title;
    }
    public LocalDateTime getStartTime() {
        return startTime;
    }
    public LocalDateTime getEndTime() {
        return endTime;
    }
    public int getFloorNumber() {
        return floorNumber;
    }

   


   
}

