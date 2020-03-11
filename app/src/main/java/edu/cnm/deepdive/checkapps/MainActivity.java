package edu.cnm.deepdive.checkapps;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.util.Log;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ListView iconList = findViewById(R.id.icon_list);
    List<ApplicationInfo> apps = getApplications();
    ImageAdapter adapter = new ImageAdapter(this, R.layout.icon_item, apps);
    iconList.setAdapter(adapter);
  }

  private List<ApplicationInfo> getApplications() {
    PackageManager manager = getPackageManager();
    Intent intent = new Intent(Intent.ACTION_MAIN);
    intent.addCategory(Intent.CATEGORY_LAUNCHER);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
    List<ResolveInfo> intents = getPackageManager().queryIntentActivities(intent, 0);
    List<ApplicationInfo> apps = new ArrayList<>();
    for (ResolveInfo info : intents) {
      try {
        apps.add(manager.getApplicationInfo(info.activityInfo.packageName, 0));
      } catch (NameNotFoundException e) {
        Log.e(getClass().getName(), "Can't add app to list", e);
      }
    }
    // HACK This is a terrible way to sort! You should instead create instances of your model class
    //  (which should implement Comparable), and then sort list of those instances.
    Collections.sort(apps, (app1, app2) ->
        manager.getApplicationLabel(app1).toString().compareTo(
            manager.getApplicationLabel(app2).toString()));
    return apps;
  }

}
