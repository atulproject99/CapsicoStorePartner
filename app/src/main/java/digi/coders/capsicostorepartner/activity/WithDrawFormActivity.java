package digi.coders.capsicostorepartner.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import digi.coders.capsicostorepartner.R;
import digi.coders.capsicostorepartner.adapter.PaymentMethodAdapter;
import digi.coders.capsicostorepartner.databinding.ActivityWithDrawFormBinding;
import digi.coders.capsicostorepartner.model.PaymentModeImage;

public class WithDrawFormActivity extends AppCompatActivity {

    ActivityWithDrawFormBinding binding;
    private List<PaymentModeImage> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityWithDrawFormBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        //recycler view

        loadPaymentMethodOption();

    }

    private void loadPaymentMethodOption() {
        list=new ArrayList<>();
        list.add(new PaymentModeImage(R.drawable.phone_pay));
        list.add(new PaymentModeImage(R.drawable.google_pay));
        list.add(new PaymentModeImage(R.drawable.amazon));
        list.add(new PaymentModeImage(R.drawable.paytm));
        list.add(new PaymentModeImage(R.drawable.bank_icon));
        PaymentMethodAdapter adapter=new PaymentMethodAdapter(list);
        binding.paymentMethodOption.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        binding.paymentMethodOption.setAdapter(adapter);
        adapter.findMyPosition(new PaymentMethodAdapter.GetPosition() {
            @Override
            public void click(View v, int position) {
                if(position==0)
                {

                    binding.detailsForm.setVisibility(View.VISIBLE);
                    binding.bankDetails.setVisibility(View.GONE);
                }
                else if(position==1)
                {
                    binding.detailsForm.setVisibility(View.VISIBLE);
                    binding.bankDetails.setVisibility(View.GONE);

                }
                else if(position==2)
                {

                    binding.detailsForm.setVisibility(View.VISIBLE);
                    binding.bankDetails.setVisibility(View.GONE);
                }
                else if(position==3)
                {
                    binding.detailsForm.setVisibility(View.VISIBLE);
                    binding.bankDetails.setVisibility(View.GONE);
                }
                else
                {
                    binding.detailsForm.setVisibility(View.GONE);
                    binding.bankDetails.setVisibility(View.VISIBLE);
                }

            }
        });

    }

}