package edu.cnm.deepdive.checkapps;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.List;

public class ImageAdapter extends ArrayAdapter<ApplicationInfo> {

  public ImageAdapter(@NonNull Context context, int resource) {
    super(context, resource);
  }

  public ImageAdapter(@NonNull Context context, int resource, int textViewResourceId) {
    super(context, resource, textViewResourceId);
  }

  public ImageAdapter(@NonNull Context context, int resource,
      @NonNull ApplicationInfo[] objects) {
    super(context, resource, objects);
  }

  public ImageAdapter(@NonNull Context context, int resource, int textViewResourceId,
      @NonNull ApplicationInfo[] objects) {
    super(context, resource, textViewResourceId, objects);
  }

  public ImageAdapter(@NonNull Context context, int resource,
      @NonNull List<ApplicationInfo> objects) {
    super(context, resource, objects);
  }

  public ImageAdapter(@NonNull Context context, int resource, int textViewResourceId,
      @NonNull List<ApplicationInfo> objects) {
    super(context, resource, textViewResourceId, objects);
  }

  @NonNull
  @Override
  public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
    ImageView view = (ImageView) ((convertView != null) ? convertView
        : LayoutInflater.from(getContext()).inflate(R.layout.icon_item, parent, false));
    try {
      ApplicationInfo info = getItem(position);
      Drawable icon = getContext().getPackageManager().getApplicationIcon(info.packageName);
      view.setImageDrawable(icon);
    } catch (NameNotFoundException e) {
      Log.e(getClass().getName(), e.getMessage(), e);
    }
    return view;
  }

}
