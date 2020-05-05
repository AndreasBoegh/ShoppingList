package com.example.shoppinglist;

import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.shoppinglist.dummy.DummyContent;

import org.w3c.dom.Text;

public class AddNewItem extends Fragment implements View.OnClickListener {


    public static AddNewItem newInstance() {
        return new AddNewItem();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.add_new_item_fragment, container, false);

        Button b = view.findViewById(R.id.addItemToListButton);
        b.setOnClickListener(this);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onClick(View v) {

        /**TextView itemNameTV = v.findViewById(R.id.addNewItemName);
        TextView itemAmountTV = v.findViewById(R.id.addNewItemAmount);
        if (itemNameTV != null && itemAmountTV != null) {
            String content = itemNameTV.toString();
            String amount = itemAmountTV.toString();
            DummyContent.addItem(new DummyContent.DummyItem(content, amount));
        }
         **/

            ((MainActivity)getActivity()).addToData();
            ((MainActivity)getActivity()).changeFragment(new ItemFragment());


    }
}


