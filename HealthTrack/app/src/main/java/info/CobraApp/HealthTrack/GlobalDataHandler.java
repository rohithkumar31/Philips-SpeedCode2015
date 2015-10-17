package info.CobraApp.HealthTrack;

/**
 * Created by Sachin on 17-10-2015.
 */
public class GlobalDataHandler {
    static String Username;

    public static void setUsername(String s){
        Username=s;
    }
    public static String getUsername(){
        return Username;
    }

}
