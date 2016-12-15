package dk.staunstrups.thingBrowser;
import android.content.Context;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmResults;

public class ThingsDB {
  private static Realm realm;

  private static ThingsDB sThingsDB; //singleton

  public static ThingsDB get(Context context) {
    if (realm==null) realm= Realm.getDefaultInstance();
    if (sThingsDB == null) sThingsDB= new ThingsDB(context);
    return sThingsDB;
  }

  public RealmList<Thing> listThingsDB() {

    //TO-DO Necessary to do the following in two steps ??
    RealmResults<Thing> allThings= realm.where(Thing.class).findAll();
    RealmList<Thing> q= new RealmList<Thing>();
    for(Thing c: allThings) { q.add(c); }
    return q;
  }


  public void addThing(Thing thing) {
    final Thing fthing= thing;
    realm.executeTransaction(new Realm.Transaction() {
      @Override
      public void execute(Realm realm) {
        realm.copyToRealm(fthing);
      }});
  }

  // Fill database for testing purposes
  private ThingsDB(Context context) {
    /*Hack to add contents to an empty database
      addThing(new Thing("Mouse", "Desk"));
      addThing(new Thing("iPhone", "Desk"));
      addThing(new Thing("Sunglasses", "Desk"));
      addThing(new Thing("Keyboard", "Desk"));
      addThing(new Thing("Display", "Desk"));
      addThing(new Thing("Computer", "Desk"));
      addThing(new Thing("Cable", "Desk")); */
  }
}