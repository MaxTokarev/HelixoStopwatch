package com.helixo.stopwatch;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.helixo.stopwatch.adapter.LapItem;
import com.helixo.stopwatch.models.ButtonState;
import com.helixo.stopwatch.util.TimeFormatUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.helixo.stopwatch.models.ButtonState.LAP;
import static com.helixo.stopwatch.models.ButtonState.RESET;
import static com.helixo.stopwatch.models.ButtonState.START;
import static com.helixo.stopwatch.models.ButtonState.STOP;

public class MainViewModel extends ViewModel {

    MutableLiveData<String> currentTime = new MutableLiveData<>();
    MutableLiveData<ButtonState> rightButton = new MutableLiveData<>(START);
    MutableLiveData<ButtonState> leftButton = new MutableLiveData<>(LAP);
    MutableLiveData<Boolean> isLeftButtonEnabled = new MutableLiveData<>(false);
    MutableLiveData<List<LapItem>> laps = new MutableLiveData<>();

    private int lapTime = 0;
    private int lapNumber = 0;
    private int currentTimeMill = 0;

    Timer timer;

    public void onRightButtonClicked() {
        if (rightButton.getValue() == START) {
            startTime();
            rightButton.setValue(STOP);
            leftButton.setValue(LAP);
        } else if (rightButton.getValue() == STOP) {
            stopTime();
            leftButton.setValue(RESET);
            rightButton.setValue(START);
        }
    }

    public void onLeftButtonClicked() {
        if (leftButton.getValue() == LAP) {
            lapTime();
        } else if (leftButton.getValue() == RESET) {
            rightButton.setValue(START);
            leftButton.setValue(LAP);
            isLeftButtonEnabled.setValue(false);
            laps.postValue(new ArrayList<>());
            resetTime();
        }
    }

    private void startTime() {
        isLeftButtonEnabled.postValue(true);
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                currentTimeMill += 1;
                lapTime += 1;
                if (laps.getValue() != null && !laps.getValue().isEmpty()) {
                    List<LapItem> items = laps.getValue();
                    LapItem newItem = items.get(laps.getValue().size() - 1);
                    newItem.setTime(TimeFormatUtil.toDisplayString(lapTime));
                    items.remove(laps.getValue().size() - 1);
                    items.add(newItem);
                    laps.postValue(items);
                }
                currentTime.postValue(TimeFormatUtil.toDisplayString(currentTimeMill));
            }
        }, 0, 10);
    }

    private void lapTime() {
        lapNumber++;
        List<LapItem> items;
        if (laps.getValue() == null) {
            items = new ArrayList<>();
        } else {
            items = laps.getValue();
        }
        LapItem newItem = new LapItem();
        newItem.setLapNumber(lapNumber);
        newItem.setTime(TimeFormatUtil.toDisplayString(lapTime));
        items.add(newItem);

        laps.postValue(items);
    }

    private void resetTime() {
        timer.cancel();
        currentTimeMill = 0;
        lapNumber = 0;
        lapTime = 0;
        currentTime.setValue(TimeFormatUtil.toDisplayString(currentTimeMill));
    }

    private void stopTime() {
        timer.cancel();
    }
}


