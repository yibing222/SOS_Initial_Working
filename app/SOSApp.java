import android.app.Application;

public class SOSApp extends Application {
    private static SOSApp instance;


    public static SOSApp getInstance(){
        return instance;
    }

    @Override
    public void onCreate(){
        onCreate();
        instance = this;
    }
}
