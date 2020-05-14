package com.d2.pcu.intro;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.d2.pcu.MainActivity;
import com.d2.pcu.R;
import com.d2.pcu.databinding.ActivityIntroBinding;

public class IntroActivity extends AppCompatActivity {

    private ActivityIntroBinding binding;
    private IntroViewModel viewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_intro);

        viewModel = new ViewModelProvider(this).get(IntroViewModel.class);

        IntroAdapter adapter = new IntroAdapter(
                this,
                id -> viewModel.setSelectedScreenId(id)
        );

        viewModel.setIntroClickListener(new IntroClickListener() {
            @Override
            public void onSkipClick() {
                Intent intent = new Intent(IntroActivity.this, MainActivity.class);
                startActivity(intent);
                IntroActivity.this.finish();
            }

            @Override
            public void onNextClick() {
                if (binding.introViewpager.getCurrentItem() == adapter.getItemCount() - 1) {
                    viewModel.onFinishClick();
                    Intent intent = new Intent(IntroActivity.this, MainActivity.class);
                    startActivity(intent);
                    IntroActivity.this.finish();
                }
                binding.introViewpager.setCurrentItem(binding.introViewpager.getCurrentItem() + 1);
            }
        });

        binding.introViewpager.setAdapter(adapter);

        binding.tabDots.setViewPager2(binding.introViewpager);

        binding.setModel(viewModel);

        if (!viewModel.getAgreementState()) {
            AlertDialog dialog = assembleAgreementDialog();
            dialog.setCanceledOnTouchOutside(false);
            dialog.show();
        }
    }

    private AlertDialog assembleAgreementDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_terms, null);

        final AppCompatTextView policyTextView = dialogView.findViewById(R.id.policy_tv);
        policyTextView.setMovementMethod(LinkMovementMethod.getInstance());

        final AppCompatButton applyBtn = dialogView.findViewById(R.id.apply_into_btn);
        final AppCompatCheckBox checkBox = dialogView.findViewById(R.id.agreement_cx);

        builder.setView(dialogView);

        final AlertDialog dialog = builder.create();

        checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> applyBtn.setEnabled(isChecked));

        applyBtn.setOnClickListener(view -> {
            if (checkBox.isChecked()) {
                viewModel.agreementApprove(checkBox.isChecked());
                dialog.dismiss();
            } else {
                Toast.makeText(getApplicationContext(), getString(R.string.not_approve_policy), Toast.LENGTH_LONG).show();
            }
        });

        return dialog;
    }
}
