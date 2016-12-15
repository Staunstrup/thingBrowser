package dk.staunstrups.thingBrowser;
import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Thing extends RealmObject {
  private String mWhat;
  private String mWhere;


  public Thing() {
    mWhat="no what";
    mWhere="no where";
  }

  public Thing(String what, String where) {
    mWhat = what;
    mWhere = where;
  }

  public String getmWhat() {
    return mWhat;
  }
  public void setmWhat(String mWhat) {
    this.mWhat=mWhat;
  }
  public String getmWhere() {
    return mWhere;
  }
  public void setmWhere(String mWhere) {
    this.mWhere=mWhere;
  }

  @Override
  public String toString() { return oneLine("Item: ","is here: "); }
  public String oneLine(String pre, String post) {
    return pre+mWhat + " "+post + mWhere;
  }

}
