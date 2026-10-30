package db;
import java.time.OffsetDateTime;

public class AccessCode {
    private int id ;
    private int meetingId;
    private String codeHash;
    private boolean isActive;
    private OffsetDateTime  expiresAt;



    public AccessCode(int id,int meetingId, String codeHash,boolean isActive, OffsetDateTime expiresAt){
        if(id<=0){
           throw new IllegalArgumentException(" Id must be positive number.");
        }
        if (meetingId<=0){
             throw new IllegalArgumentException(" MeetingId must be positive number.");
        }
        if(expiresAt==null){
             throw new IllegalArgumentException(" expires date is required.");
        }

        if (codeHash==null|| codeHash.isBlank()){
             throw new IllegalArgumentException(" codeHash can't be empty.");
        }
        this.id=id;
        this.meetingId=meetingId;
        this.codeHash=codeHash;
        this.isActive=isActive;
        this.expiresAt=expiresAt;
    
    }

    public int getId(){
        return id ;
    }
    
    public int meetingId(){
        return meetingId ;
    }
    
    public String codeHash(){
        return  codeHash ;
    }
     
    public boolean isActive(){
        return   isActive ;
    }
     
    public OffsetDateTime  expiresAt(){
        return    expiresAt ;
    }

    public boolean isExpired(){

        OffsetDateTime now = OffsetDateTime.now();
        if (expiresAt.isBefore(now)|| expiresAt.isEqual(now)){ 
            return true; 
        } return false;


    }
    

    





}
