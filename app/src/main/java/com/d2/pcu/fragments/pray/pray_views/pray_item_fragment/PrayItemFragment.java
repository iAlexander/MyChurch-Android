package com.d2.pcu.fragments.pray.pray_views.pray_item_fragment;
//
//import android.content.Context;
//import android.net.Uri;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.databinding.DataBindingUtil;
//import androidx.lifecycle.ViewModelProviders;
//
//import com.d2.pcu.R;
//import com.d2.pcu.data.model.pray.Pray;
//import com.d2.pcu.databinding.FragmentItemPrayBinding;
import com.d2.pcu.fragments.BaseFragment;
//import com.d2.pcu.listeners.OnBackButtonClickListener;
//import com.google.android.exoplayer2.ExoPlayer;
//import com.google.android.exoplayer2.ExoPlayerFactory;
//import com.google.android.exoplayer2.Player;
//import com.google.android.exoplayer2.SimpleExoPlayer;
//import com.google.android.exoplayer2.source.MediaSource;
//import com.google.android.exoplayer2.source.ProgressiveMediaSource;
//import com.google.android.exoplayer2.ui.PlayerControlView;
//import com.google.android.exoplayer2.upstream.DataSource;
//import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
//import com.google.android.exoplayer2.util.Util;
//import com.google.gson.Gson;
//
public class PrayItemFragment extends BaseFragment {
//
//    private static final String TAG = PrayItemFragment.class.getSimpleName();
//
//    private FragmentItemPrayBinding binding;
//    private PrayItemViewModel viewModel;
//
//    private OnBackButtonClickListener onBackButtonClickListener;
//    private Player.EventListener eventListener;
//
//    private boolean playWhenReady = false;
//    private int currentWindow = 0;
//    private long playbackPosition = 0;
//
//    private SimpleExoPlayer player;
//
//    private Pray pray;
//
//    @Override
//    public void onAttach(@NonNull Context context) {
//        super.onAttach(context);
//        onBackButtonClickListener = (OnBackButtonClickListener) context;
//    }
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        if (getArguments() != null) {
//            pray = new Gson().fromJson(PrayItemFragmentArgs.fromBundle(getArguments()).getSerializedPray(), Pray.class);
//        }
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_item_pray, container, false);
//
//        return binding.getRoot();
//    }
//
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//    }
//
//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        viewModel = ViewModelProviders.of(getActivity()).get(PrayItemViewModel.class);
//        viewModel.setOnBackButtonClickListener(onBackButtonClickListener);
//        viewModel.setPray(pray);
//
//        binding.setModel(viewModel);
//        binding.setPray(pray);
//
//        initializePlayer();
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        if (Util.SDK_INT >= 24) {
//            initializePlayer();
//        }
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        if ((Util.SDK_INT < 24 || player == null)) {
//            initializePlayer();
//        }
//    }
//
//    private void initializePlayer() {
//        if (player == null) {
//            player = new SimpleExoPlayer.Builder(getContext()).build();
//            binding.playerV.setPlayer(player);
//
//            eventListener = new Player.EventListener() {
//                @Override
//                public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
//                    if (playbackState == Player.STATE_IDLE || playbackState == Player.STATE_ENDED || !playWhenReady) {
//                        binding.playerV.setKeepScreenOn(false);
//                    } else {
//                        binding.playerV.setKeepScreenOn(true);
//                    }
//                }
//            };
//
//            Uri uri = Uri.parse(pray.getUrlMP3());
//            MediaSource mediaSource = buildMediaSource(uri);
//
//            player.setPlayWhenReady(playWhenReady);
//            player.seekTo(currentWindow, playbackPosition);
//            player.addListener(eventListener);
//            player.prepare(mediaSource, false, false);
//        }
//    }
//
//    private MediaSource buildMediaSource(Uri uri) {
//        DataSource.Factory dataSourceFactory =
//                new DefaultDataSourceFactory(getContext(), getString(R.string.app_name));
//
//        return new ProgressiveMediaSource.Factory(dataSourceFactory).createMediaSource(uri);
//    }
//
//    private void releasePlayer() {
//        if (player != null) {
//            playWhenReady = player.getPlayWhenReady();
//            playbackPosition = player.getContentPosition();
//            currentWindow = player.getCurrentWindowIndex();
//            player.removeListener(eventListener);
//            player.release();
//            player = null;
//        }
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        if (Util.SDK_INT < 24) {
//            releasePlayer();
//        }
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        if (Util.SDK_INT >= 24) {
//            releasePlayer();
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        onBackButtonClickListener = null;
//    }
}
