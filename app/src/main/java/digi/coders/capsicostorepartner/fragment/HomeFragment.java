package digi.coders.capsicostorepartner.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import digi.coders.capsicostorepartner.R;
import digi.coders.capsicostorepartner.adapter.HomeTabAdapter;
import digi.coders.capsicostorepartner.databinding.FragmentHomeBinding;


public class HomeFragment extends Fragment {
    FragmentHomeBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentHomeBinding.inflate(inflater,container,false);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.tablayout.addTab(binding.tablayout.newTab().setText("New Orders"));
        binding.tablayout.addTab(binding.tablayout.newTab().setText("Preparing"));
        binding.tablayout.addTab(binding.tablayout.newTab().setText("Completed"));
        binding.tablayout.setTabGravity(TabLayout.GRAVITY_FILL);

        HomeTabAdapter homeTabAdapter = new HomeTabAdapter(getActivity(),getChildFragmentManager(), binding.tablayout.getTabCount());
        binding.viewpager.setAdapter(homeTabAdapter);

        binding.viewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(binding.tablayout));

        binding.tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                binding.viewpager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });



        //set current date
        Calendar calendar = Calendar.getInstance();
//date format is:  "Date-Month-Year Hour:Minutes am/pm"

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy"); //Date and time
        String currentDateandTime = sdf.format(new Date());
        //String currentDate = sdf.format(calendar.getTime());

//Day of Name in full form like,"Saturday", or if you need the first three characters you have to put "EEE" in the date format and your result will be "Sat".
        SimpleDateFormat sdf_ = new SimpleDateFormat("EEEE");

        Date date = new Date();
        String dayName = sdf_.format(date);
        binding.currentDate.setText("" + dayName + " " +sdf.format(new Date()));


    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        binding=null;
    }
}