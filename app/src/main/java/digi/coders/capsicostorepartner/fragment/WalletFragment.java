package digi.coders.capsicostorepartner.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;
import com.razorpay.Checkout;

import org.json.JSONObject;

import digi.coders.capsicostorepartner.R;
import digi.coders.capsicostorepartner.activity.KYCActivity;
import digi.coders.capsicostorepartner.adapter.TransactionAdapter;
import digi.coders.capsicostorepartner.databinding.FragmentWalletBinding;

public class WalletFragment extends Fragment {
    FragmentWalletBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding=FragmentWalletBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull  View view, @Nullable  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //withdraw money
        binding.withdrawMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                withDrawMoney();
            }
        });
        //transaction list
        loadTrsactionList();
    }
    private void withDrawMoney() {
        startActivity(new Intent(getActivity(), KYCActivity.class));
    }

    private void loadTrsactionList() {
        binding.transactionList.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
        binding.transactionList.setAdapter(new TransactionAdapter());
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        binding=null;
    }


}
