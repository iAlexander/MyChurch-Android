package com.d2.pcu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.d2.pcu.databinding.ActivityMainBinding;
import com.d2.pcu.fragments.map.MapFragmentDirections;
import com.d2.pcu.listeners.OnBackButtonClickListener;
import com.d2.pcu.listeners.OnLoadingEnableListener;
import com.d2.pcu.listeners.OnMoreTempleInfoClickListener;
import com.d2.pcu.utils.Constants;

public class MainActivity extends AppCompatActivity implements OnBackButtonClickListener, OnMoreTempleInfoClickListener, OnLoadingEnableListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private ActivityMainBinding binding;
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(binding.navigationView, navController);

        getLifecycle().addObserver(App.getInstance().getRepositoryInstance());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        NavigationUI.onNavDestinationSelected(item, navController);
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
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
}
