package dk.staunstrups.thingBrowser;
import android.app.Application;
import android.util.Log;
import io.realm.Realm;
import io.realm.log.AndroidLogger;
import io.realm.log.RealmLog;

public class thingBrowserApplication extends Application {
  @Override
  public void onCreate() {
   super.onCreate();
   Realm.init(this);
  }
 }