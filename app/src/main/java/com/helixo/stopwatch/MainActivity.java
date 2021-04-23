package com.helixo.stopwatch;

import android.animation.LayoutTransition;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.helixo.stopwatch.adapter.LapsAdapter;
import com.helixo.stopwatch.databinding.ActivityMainBinding;

import java.util.Timer;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private MainViewModel viewModel;

    private Button startButton;
    private Button lapButton;
    private TextView textView;
    private Timer timer;
    private LayoutTransition transition;
    private LinearLayout linearLayout;
    private int currentTime = 0;
    private int lapTime = 0;
    private int lapCounter = 0;
    private boolean lapViewExists;
    private boolean isButtonStartPressed = false;

    LapsAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setupObservers();
        setupUi();
    }

    private void setupObservers() {
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        viewModel.currentTime.observe(this, time -> {
            binding.tvTime.setText(time);
        });
        viewModel.rightButton.observe(this, buttonState -> {
            switch (buttonState) {
                case STOP: {
                    binding.btnRight.setText(R.string.btn_stop);
                    binding.btnRight.setTextColor(getResources().getColor(R.color.stop_text));
                    Drawable iv = ResourcesCompat.getDrawable(getResources(), R.drawable.bg_stop, null);
                    binding.ivRight.setImageDrawable(iv);
                    break;
                }
                case START: {
                    binding.btnRight.setText(getString(R.string.btn_start));
                    binding.btnRight.setTextColor(getResources().getColor(R.color.start_text));
                    Drawable iv = ResourcesCompat.getDrawable(getResources(), R.drawable.bg_start, null);
                    binding.ivRight.setImageDrawable(iv);
                    break;
                }
            }
        });
        viewModel.leftButton.observe(this, buttonState -> {
            switch (buttonState) {
                case LAP:
                    binding.btnLap.setText(R.string.btn_lap);
                    break;
                case RESET:
                    binding.btnLap.setText(R.string.btn_reset);
                    break;
            }
        });
        viewModel.isLeftButtonEnabled.observe(this, isEnabled -> {
            binding.btnLap.setEnabled(isEnabled);
        });
        viewModel.laps.observe(this, items -> {
            adapter.setItems(items);
        });
    }

    private void setupUi() {
        adapter = new LapsAdapter();
        binding.rvLaps.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        binding.rvLaps.setLayoutManager(linearLayoutManager);
        binding.rvLaps.addItemDecoration(new DividerItemDecoration(this, RecyclerView.VERTICAL));

        binding.btnLap.setOnClickListener(v -> {
            viewModel.onLeftButtonClicked();
        });
        binding.btnRight.setOnClickListener(v -> {
            viewModel.onRightButtonClicked();
        });
    }

}