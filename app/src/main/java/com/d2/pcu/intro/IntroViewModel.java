package com.d2.pcu.intro;

import android.view.View;

import androidx.lifecycle.ViewModel;

import com.d2.pcu.App;

public class IntroViewModel extends ViewModel {

    private IntroClickListener introClickListener;

    void setIntroClickListener(IntroClickListener introClickListener) {
        this.introClickListener = introClickListener;
    }

    public void onSkipClick(View view) {
        if (introClickListener != null) {
            onFinishClick();
            introClickListener.onSkipClick();
        }
    }

    void onFinishClick() {
        App.getInstance().getRepositoryInstance().setOnBoardingState(true);
    }

    public void onNextClick(View view) {
        if (introClickListener != null) {
            introClickListener.onNextClick();
        }
    }

    void setSelectedScreenId(int id) {
        App.getInstance().getRepositoryInstance().setSelectedStartScreenId(id);
    }

    void agreementApprove(boolean isApprove) {
        App.getInstance().getRepositoryInstance().saveAgreementApprove(isApprove);
    }

    boolean getAgreementState() {
        return App.getInstance().getRepositoryInstance().getAgreementState();
    }
}
