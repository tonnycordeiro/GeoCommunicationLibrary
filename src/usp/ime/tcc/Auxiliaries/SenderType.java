package usp.ime.tcc.Auxiliaries;

public enum SenderType {
    BROADCAST_TO_ONE(1), BROADCAST_TO_ALL(2), UNICAST(3);
   
    int id;
    
    SenderType(int id) {
       this.id = id;
    }
    
    public int getId(){
       return id;
    }
    
    public static SenderType getSenderType(int id){
        switch(id){
            case 1:
                return BROADCAST_TO_ONE;
            case 2:
                return BROADCAST_TO_ALL;
            case 3:
                return UNICAST;
            default:
                return null;
        }
    }
   
}
