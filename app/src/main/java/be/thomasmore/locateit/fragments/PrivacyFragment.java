package be.thomasmore.locateit.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import be.thomasmore.locateit.R;

public class PrivacyFragment extends Fragment {

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_privacy, container, false);

        // Change toolbar Title
        getActivity().setTitle(getString(R.string.nav_privacy));

        return view;
    }

}
