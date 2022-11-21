package com.example.hangyou.ui.group;

import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;

public class GroupViewModel extends ViewModel {
    public ObservableField<Integer> totalUser;

    public GroupViewModel(ObservableField<Integer> totalUser) {
        this.totalUser = totalUser;
    }

    public int getTotalUser() {
        return totalUser.get();
    }

    public void setTotalUser(ObservableField<Integer> totalUser) {
        this.totalUser = totalUser;
    }
}
