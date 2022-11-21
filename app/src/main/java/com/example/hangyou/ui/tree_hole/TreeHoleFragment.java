package com.example.hangyou.ui.tree_hole;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.hangyou.databinding.FragmentTreeHoleBinding;

public class TreeHoleFragment extends Fragment {

    private FragmentTreeHoleBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        TreeHoleViewModel treeHoleViewModel =
                new ViewModelProvider(this).get(TreeHoleViewModel.class);

        binding = FragmentTreeHoleBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}