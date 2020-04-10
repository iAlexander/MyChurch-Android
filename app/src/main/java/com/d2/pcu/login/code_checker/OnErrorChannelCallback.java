package com.d2.pcu.login.code_checker;


import com.d2.pcu.utils.livedata_utils.SingleLiveEvent;

public interface OnErrorChannelCallback {

    SingleLiveEvent<Boolean> getErrorChannel();

}
