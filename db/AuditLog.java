
package db;

import java.time.OffsetDateTime;
import java.util.List;

public class AuditLog {

    private int id;
    private String eventType;
    private String description;
    private OffsetDateTime timestamp;

    public AuditLog(int id, String eventType,
                    String description, OffsetDateTime timestamp) {
        
        
                if (id <= 0) {
                    throw new IllegalArgumentException("Id must be positive");
                }

                if (eventType==null || eventType.isBlank()) {
                    throw new IllegalArgumentException("Event type is required");
                }
                if (description==null|| description.isBlank( )){
                    throw new IllegalArgumentException("Description is required");
                }
                if (timestamp==null ){
                   throw new IllegalArgumentException("Timestamp is required"); 
                }

                this.id=id;
                this.eventType=eventType;
                this.description=description;
                this.timestamp=timestamp;
    }

    public  OffsetDateTime getTimestamp(){
        return timestamp;
    }
    public int getId(){
        return id;
    }

    public String getEventType(){
        return eventType;
    }

    public String getDescription(){
        return description;
    }

    public void logEvent(){
        System.out.println(eventType);
        System.out.println  (description);
        System.out.println(timestamp);
    }
    
    public static String exportLogs (List <AuditLog> logs) {
        StringBuilder result= new StringBuilder();
        
        for (AuditLog log: logs){
           result.append(log.getId());
            result.append(" | ");
            result.append(log.getEventType());
            result.append(" | ");
            result.append(log.getDescription());
            result.append(" | ");
            result.append(log.getTimestamp());
            result.append(System.lineSeparator());

        } 
        return result.toString();
   
    }

}
