package com.d2.pcu;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.d2.pcu.data.Repository;
import com.d2.pcu.data.model.profile.UserState;
import com.d2.pcu.databinding.ActivityMainBinding;
import com.d2.pcu.fragments.cabinet.OnCabinetButtonsClickListener;
import com.d2.pcu.fragments.cabinet.donate.OnDonatesClickListener;
import com.d2.pcu.fragments.cabinet.support.OnChatClickListener;
import com.d2.pcu.fragments.cabinet.user_profile.OnEditProfileDataClickListener;
import com.d2.pcu.fragments.cabinet.user_profile.ProfileMenuViewModel;
import com.d2.pcu.fragments.calendar.CalendarFragmentDirections;
import com.d2.pcu.fragments.calendar.OnCalendarEventItemClickListener;
import com.d2.pcu.fragments.map.MapFragmentDirections;
import com.d2.pcu.fragments.news.vertical.NewsFragmentDirections;
import com.d2.pcu.listeners.InfoDialogListener;
import com.d2.pcu.listeners.OnAdditionalFuncMapListener;
import com.d2.pcu.listeners.OnAdditionalFuncNewsListener;
import com.d2.pcu.listeners.OnAdditionalFuncPrayersListener;
import com.d2.pcu.listeners.OnBackButtonClickListener;
import com.d2.pcu.listeners.OnLoadingStateChangedListener;
import com.d2.pcu.listeners.OnNotificationClickListener;
import com.d2.pcu.login.LogoutOnClickListener;
import com.d2.pcu.ui.error.OnError;
import com.d2.pcu.ui.error.fragments.ErrorFragment;
import com.d2.pcu.ui.utils.UIUtils;
import com.d2.pcu.utils.Constants;
import com.d2.pcu.utils.WebViewLocaleHelper;
import com.google.android.gms.maps.model.LatLng;
import com.yariksoffice.lingver.Lingver;

import java.util.Locale;

import timber.log.Timber;


public class MainActivity extends AppCompatActivity implements OnError,
        OnBackButtonClickListener, OnLoadingStateChangedListener, OnCalendarEventItemClickListener,
        OnAdditionalFuncMapListener, OnAdditionalFuncNewsListener, OnAdditionalFuncPrayersListener,
        OnCabinetButtonsClickListener, OnDonatesClickListener, OnChatClickListener,
        OnEditProfileDataClickListener, OnNotificationClickListener, InfoDialogListener,
        LogoutOnClickListener {

    private ActivityMainBinding binding;
    private NavController navController;
    private Repository repository;
    private ErrorFragment errorFragment;
    private WebViewLocaleHelper webViewLocaleHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);

        webViewLocaleHelper = new WebViewLocaleHelper();

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.navigationView.setItemIconTintList(null);

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        navController.addOnDestinationChangedListener(
                (controller, destination, arguments) -> enableLoading(false)
        );

        if (!App.getInstance().getRepositoryInstance().getOnBoardingState()) {
            navController.navigate(R.id.introActivity);
            finish();
        }

        setStartScreen();
        NavigationUI.setupWithNavController(binding.navigationView, navController);

        repository = App.getInstance().getRepositoryInstance();

        getLifecycle().addObserver(repository);
        repository.setOnErrorListener(this);

        repository.getTransport().getStateSingleEvent().observe(this, userState -> {

            switch (userState) {
                default:
                case NON_AUTH:
                    break;
                case SIGNED_UP:
                    onProfileClick();

                    UIUtils.assembleModeratingDialog(
                            MainActivity.this,
                            getString(R.string.pass_was_send, repository.getCredentials(Constants.USER_EMAIL)),
                            getString(R.string.understand_text)
                    ).show();

                    break;
                case AUTHENTICATED:
                    onProfileClick();
                    break;
                case MODERATING:
                    UIUtils.assembleModeratingDialog(
                            MainActivity.this,
                            getString(R.string.pass_was_send_moderating, repository.getCredentials(Constants.USER_EMAIL)),
                            getString(R.string.understand_text)
                    ).show();
                    break;
            }
        });
    }

    private void setStartScreen() {
        int startId = App.getInstance().getRepositoryInstance().getSelectedStartScreenId();
        if (startId != R.id.resource_unset) {
            NavGraph navDestinations = navController.getGraph();
            TypedArray typedArray = getResources().obtainTypedArray(R.array.fragments_ids);
            int startScreen = typedArray.getResourceId(startId, R.id.mapFragment);
            typedArray.recycle();
            navDestinations.setStartDestination(startScreen);
            navController.setGraph(navDestinations);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private boolean handleIntent(Intent intent) {
        Timber.d("handle intent");
        if (intent.hasExtra(Constants.PUSH_NOTIFICATION_ID)) {
            Bundle bundle = new Bundle();
            bundle.putInt(Constants.PUSH_NOTIFICATION_ID, intent.getIntExtra(Constants.PUSH_NOTIFICATION_ID, 0));
            navController.navigate(R.id.notificationReadFragment, bundle);
            return true;
        } else if (intent.hasExtra(Constants.PUSH_NOTIFICATION)) {
            navController.navigate(R.id.notificationFragment);
            return true;
        }
        return false;
    }

    @Override
    public void onBackButtonPressed() {
        navController.popBackStack();
    }

    @Override
    public void onTempleInfoClick(String serializedTemple, int type) {
        if (type == Constants.TEMPLE_TYPE_CATHEDRAL) {
            MapFragmentDirections.ActionMapFragmentToTempleFragment action = MapFragmentDirections.actionMapFragmentToTempleFragment(serializedTemple);
            navController.navigate(action);
        } else {
            MapFragmentDirections.ActionMapFragmentToTempleContactsFragment action = MapFragmentDirections.actionMapFragmentToTempleContactsFragment(serializedTemple);
            navController.navigate(action);
        }
    }

    @Override
    public void enableLoading(boolean enable) {
        binding.loadingOverlayView.setVisibility(enable ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onError(int errorType) {
        enableLoading(false);
        if (errorFragment != null && errorFragment.isVisible()) return;
        errorFragment = ErrorFragment.newInstance(errorType);

        errorFragment.show(getSupportFragmentManager(), Constants.ERROR_FRAGMENT_TAG);
    }

    @Override
    public void onEventItemClick(String serializedEvent) {
        CalendarFragmentDirections.ActionCalendarFragmentToFragmentCalendarEvent action = CalendarFragmentDirections.actionCalendarFragmentToFragmentCalendarEvent(serializedEvent);
        navController.navigate(action);
    }

    @Override
    public void getDirectionsOnMap(LatLng destination) {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("www.google.com")
                .appendPath("maps")
                .appendPath("dir")
                .appendPath("")
                .appendQueryParameter("api", "1")
                .appendQueryParameter("destination", destination.latitude + "," + destination.longitude);
        Uri gmmIntentUri = builder.build();

        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");

        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        }
    }

    @Override
    public void openHorizontalFragment() {
        navController.navigate(NewsFragmentDirections.actionNewsFragmentToNewsHorizontalPagerFragment());
    }

    @Override
    public void onPrayItemClick() {
        navController.navigate(R.id.prayHorizontalFragment);
    }

    @Override
    public void onProfileClick() {
        if (App.getInstance().getRepositoryInstance().getAuthState() == UserState.AUTHENTICATED
                || App.getInstance().getRepositoryInstance().getAuthState() == UserState.SIGNED_UP) {
            navController.navigate(R.id.profileMenuFragment);
        } else {
            navController.navigate(R.id.loginActivity);
        }
    }

    @Override
    public void onSupportClick() {
        navController.navigate(R.id.supportFragment);
    }

    @Override
    public void onDonateClick() {
        navController.navigate(R.id.donateFragment);
    }

    @Override
    public void onDonateServiceClick(int serviceId) {
        Intent intent = new Intent(Intent.ACTION_VIEW);

        switch (serviceId) {
            case R.id.donates_monobank_btn: {
//                String url = "https://send.monobank.ua/9Y5rJTCoDD";
//                intent.setData(Uri.parse(url));
//
//                startActivity(intent);
                break;
            }
            case R.id.donates_liqpay_btn: {
                webViewLocaleHelper.implementWorkaround(this);
                navController.navigate(R.id.liqWebFragment);
                break;
            }
//            case R.id.donates_privat24_btn: {
//                navController.navigate(R.id.liqWebFragment);
//                break;
//            }
//            case R.id.donates_portmone_btn: {
//                Toast.makeText(getApplicationContext(), "Portmone click", Toast.LENGTH_SHORT).show();
//                break;
//            }
//            case R.id.donate_paypal_btn: {
//                Toast.makeText(getApplicationContext(), "PayPall click", Toast.LENGTH_SHORT).show();
//                break;
//            }
        }
    }

    @Override
    public void onChatClick(int chatId) {
        Intent intent = new Intent(Intent.ACTION_VIEW);

        switch (chatId) {
            case R.id.telegram_btn: {
                intent.setData(Uri.parse("https://t.me/vera_pravoslavna"));
                startActivity(intent);
                break;
            }
            case R.id.viber_btn: {
                Toast.makeText(getApplicationContext(), "Viber click", Toast.LENGTH_SHORT).show();

                break;
            }
            case R.id.btnFacebookWeb: {
                Intent intentWeb = new Intent(Intent.ACTION_VIEW, Uri.parse(BuildConfig.FACEBOOK_URL));
                intentWeb.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (intentWeb.resolveActivity(getPackageManager()) != null) {
                    startActivity(intentWeb);
                }
                break;
            }
        }
    }

    @Override
    public void onEditEmailOrPasswordClick(ProfileMenuViewModel.ChangeDataType changeDataType) {
        Bundle arg = new Bundle();
        arg.putSerializable("changeDataType", changeDataType);

        navController.navigate(R.id.changeProfileEnteringDataFragment, arg);
    }

    @Override
    public void onEditPasswordClick(ProfileMenuViewModel.ChangeDataType changeDataType) {
        // TODO: 02.04.2020
    }

    @Override
    public void onNotificationClick() {
        navController.navigate(R.id.notificationFragment);
    }

//    @Override
//    public void onNotificationItemClick() {
//        navController.navigate(R.id.notificationReadFragment);
//    }

    @Override
    public void showInfoDialog(String msg, String btnTitle) {
        UIUtils.assembleModeratingDialog(this, msg, btnTitle).show();
    }

    @Override
    public void showInfoDialog(int msgId, int btnTitleId) {
        UIUtils.assembleModeratingDialog(this, getString(msgId), getString(btnTitleId)).show();
    }

    @Override
    public void onLogout() {
        if (repository != null) {
            repository.logout();
            App.getInstance().getNotificationHelper().syncNotification();
            navController.popBackStack(R.id.profileFragment, false);
        }
    }
}
