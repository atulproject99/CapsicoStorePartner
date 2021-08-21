package digi.coders.capsicostorepartner.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import digi.coders.capsicostorepartner.R;
import digi.coders.capsicostorepartner.databinding.FragmentOfflineBinding;

public class OfflineFragment extends Fragment {

    FragmentOfflineBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentOfflineBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //startDuty
        binding.startDuty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getParentFragmentManager().beginTransaction().replace(R.id.container,new HomeFragment()).commit();
            }
        });

    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        binding=null;
    }
}