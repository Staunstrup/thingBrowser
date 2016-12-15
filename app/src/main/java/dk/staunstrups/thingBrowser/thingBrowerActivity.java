package dk.staunstrups.thingBrowser;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import io.realm.ObjectServerError;
import io.realm.Realm;
import io.realm.SyncConfiguration;
import io.realm.SyncCredentials;
import io.realm.SyncUser;

public class thingBrowerActivity extends Activity implements SyncUser.Callback {

  // Various constants needed by Realm
  private static final String USERNAME = "test@test.dk";
  private static final String PASSWORD = "napoleon";
  public static final String AUTH_URL = "http://" + "Dell" + ":9080/auth";
  public static final String REALM_URL = "realm://" + "Dell" + ":9080/~/realmthings";

  // GUI variables
  private Button addThing, listThings;
  private TextView newWhat, newWhere;
  // area for displaying list of things
  private ListView mainListView;
  private ArrayAdapter listAdapter;

  //Database of things - singleton
  private static ThingsDB thingsDB;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_thing_browser);
    setUpRealmSync();
  }

  protected void onResume() {
    super.onResume();
    thingsDB = ThingsDB.get(this);
    setUpUI();
  }

  private void setUpUI() {
    newWhat = (TextView) findViewById(R.id.what_text);
    newWhere = (TextView) findViewById(R.id.where_text);

    // Button add thing
    addThing = (Button) findViewById(R.id.add_button);
    addThing.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
      HideSoftKeyboard();
      if ((newWhat.getText().length() > 0) && (newWhere.getText().length() > 0)) {
        Thing newThing = new Thing(
          newWhat.getText().toString().trim(),
          newWhere.getText().toString().trim());
        thingsDB.addThing(newThing);
        newWhat.setText("");
        newWhere.setText("");
      }
      }
    });

    // Button list all things
    listThings = (Button) findViewById(R.id.list_button);
    listThings.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        mainListView = (ListView) findViewById(R.id.mainListView);
        listAdapter = new ArrayAdapter<>(thingBrowerActivity.this, R.layout.list_item_thing, thingsDB.listThingsDB());
        mainListView.setAdapter(listAdapter);
      }
    });
  }

  private void setUpRealmSync() {
    if (SyncUser.currentUser() == null) {
      SyncCredentials myCredentials = SyncCredentials.usernamePassword(USERNAME, PASSWORD, false);
      SyncUser.loginAsync(myCredentials, AUTH_URL, this);
    } else {
      setupSync(SyncUser.currentUser());
    }
  }

  @Override
  public void onSuccess(SyncUser user) {  setupSync(user);  }

  @Override
  public void onError(ObjectServerError error) {
    Toast.makeText(this, "Failed to login - Using local database only", Toast.LENGTH_LONG).show();
  }

  private void setupSync(SyncUser user) {
    SyncConfiguration defaultConfig = new SyncConfiguration.Builder(user, REALM_URL).build();
    Realm.setDefaultConfiguration(defaultConfig);
    Toast.makeText(this, "Logged in", Toast.LENGTH_LONG).show();
  }

  private void HideSoftKeyboard() {
    InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
    inputManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
  }
}


