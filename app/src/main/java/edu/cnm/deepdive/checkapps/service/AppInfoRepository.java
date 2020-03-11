package edu.cnm.deepdive.checkapps.service;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.util.Log;
import edu.cnm.deepdive.checkapps.model.AppInfo;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AppInfoRepository {

  private final Context context;

  public AppInfoRepository(Context context) {
    this.context = context;
  }

  public Single<List<AppInfo>> getPackages() {
    return Single.fromCallable(this::buildAppPackageList)
        .subscribeOn(Schedulers.io());
  }

  private List<AppInfo> buildAppPackageList() {
    PackageManager manager = context.getPackageManager();
    Intent intent = new Intent(Intent.ACTION_MAIN);
    intent.addCategory(Intent.CATEGORY_LAUNCHER);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
    List<ResolveInfo> intents = manager.queryIntentActivities(intent, 0);
    List<AppInfo> apps = new ArrayList<>();
    for (ResolveInfo resInfo : intents) {
      try {
        AppInfo appInfo = new AppInfo();
        appInfo.setPackage(resInfo.activityInfo.packageName);
        appInfo.setLabel(manager.getApplicationLabel(
            manager.getApplicationInfo(resInfo.activityInfo.packageName, 0)).toString());
        apps.add(appInfo);
      } catch (NameNotFoundException e) {
        Log.e(getClass().getName(), "Can't add app to list", e);
      }
    }
    Collections.sort(apps);
    return apps;
  }

}
