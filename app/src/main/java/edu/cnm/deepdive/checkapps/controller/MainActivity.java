package edu.cnm.deepdive.checkapps.controller;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import edu.cnm.deepdive.checkapps.R;
import edu.cnm.deepdive.checkapps.view.AppRecyclerAdapter;
import edu.cnm.deepdive.checkapps.viewmodel.MainViewModel;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    RecyclerView iconList = findViewById(R.id.icon_list);
    MainViewModel viewModel = new ViewModelProvider(this).get(MainViewModel.class);
    viewModel.getApps().observe(this, (apps) -> {
      AppRecyclerAdapter adapter = new AppRecyclerAdapter(this, apps);
      iconList.setAdapter(adapter);
    });
    viewModel.refreshApps();
  }

}
