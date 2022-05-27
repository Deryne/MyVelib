package fr.epf.min1.deryne.myvelib.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import fr.epf.min1.deryne.myvelib.R;
import fr.epf.min1.deryne.myvelib.Station;
import fr.epf.min1.deryne.myvelib.StationAdapter;
import fr.epf.min1.deryne.myvelib.StationItem;

public class Homefragment extends Fragment {

    private ArrayList<StationItem> stationItems = new ArrayList<>();


  public View onCreateView(@NonNull LayoutInflater inflater,
                           ViewGroup container, Bundle savedInstanceState){
      View root = inflater.inflate(R.layout.fragment_home, container, false);

      RecyclerView recyclerView = root.findViewById(R.id.recyclerView);
      recyclerView.setHasFixedSize(true);
      recyclerView.setAdapter(new StationAdapter((stationItems, getActivity()));
      recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
      //faire varier les images de vélib/images de lieux aux alentours
      stationItems.add(new StationItem(R.drawable.velibimage, "Latte", "0", "0"));
      stationItems.add(new StationItem(R.drawable.velibimage, "velib1","1","0"));
      stationItems.add(new StationItem(R.drawable.velibimage, "velib2", "2", "0"));
      stationItems.add(new StationItem(R.drawable.velibimage, "velib3", "3", "0"));
      stationItems.add(new StationItem(R.drawable.velibimage, "velib4", "4", "0"));
      stationItems.add(new StationItem(R.drawable.velibimage, "velib5", "5", "0"));
      stationItems.add(new StationItem(R.drawable.velibimage, "velib6", "6", "0"));
      stationItems.add(new StationItem(R.drawable.velibimage, "velib7", "7", "0"));
      stationItems.add(new StationItem(R.drawable.velibimage, "velib8", "8", "0"));
      stationItems.add(new StationItem(R.drawable.velibimage, "velib9", "9", "0"));



      return root;
  }

}
