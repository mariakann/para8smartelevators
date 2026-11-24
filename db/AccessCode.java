package db;
import java.time.OffsetDateTime;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;


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


    public static String   generateHash(String firstCode){

        if(firstCode==null || firstCode.isBlank()){
            throw new IllegalArgumentException("Access code cannot be empty");
        }

        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte []  codeBytes =firstCode.getBytes(StandardCharsets.UTF_8);
            byte[] hashBytes= digest.digest(codeBytes);
            StringBuilder hexHash = new StringBuilder();
        for  (byte  b : hashBytes){
            hexHash.append(String.format("%02x", b));
        }return hexHash.toString();

        } catch (NoSuchAlgorithmException e ){
             throw new IllegalStateException("SHA-256 algorithm unavailable", e);
       }
    }


    public boolean validateCode(String firstCode){
        if (isActive() ==false){
            return false;
        }
        if (isExpired()== true){
            return false ;
        }
        if  (firstCode==null|| firstCode.isBlank()){
            return  false;
        }
         return codeHash.equals(generateHash(firstCode));


    }







}
