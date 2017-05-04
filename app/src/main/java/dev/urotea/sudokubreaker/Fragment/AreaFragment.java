package dev.urotea.sudokubreaker.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.ItemSelect;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.ViewsById;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import dev.urotea.sudokubreaker.MainActivity_;
import dev.urotea.sudokubreaker.Model.AreaModel;
import dev.urotea.sudokubreaker.R;

@EFragment(R.layout.fragment_area)
public class AreaFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    private AreaModel mParam1;

    @ViewById(R.id.submit_button)
    Button submitButton;

    @ViewsById({R.id.sp_left_up, R.id.sp_middle_up, R.id.sp_right_up,
                R.id.sp_left_middle, R.id.sp_middle_middle, R.id.sp_right_middle,
                R.id.sp_left_bottom, R.id.sp_middle_bottom, R.id.sp_right_bottom})
    List<Spinner> spinnerList;

    private OnFragmentInteractionListener mListener;

    public AreaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1
     * @return A new instance of fragment AreaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AreaFragment newInstance(AreaModel param1) {
        AreaFragment fragment = new AreaFragment_();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = (AreaModel) getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @AfterViews
    void init() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this.getActivity(),
                R.layout.spinner_item, getResources().getStringArray(R.array.input_list));
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        for(Spinner spinner : spinnerList) {
            spinner.setAdapter(adapter);
        }

        List<Integer> positionList = new ArrayList<>();
        for(String s : mParam1.getList()) {
            int tmp = s.isEmpty() ? 0 : Integer.parseInt(s);
            positionList.add(tmp);
        }
        for(int i=0;i<spinnerList.size();i+=1) {
            spinnerList.get(i).setSelection(positionList.get(i));
        }

        for(int i=0;i<spinnerList.size();i+=1) {
            spinnerList.get(i).setTag(i);
        }
    }

    @Click(R.id.submit_button)
    public void onButtonPressed() {
        if (mListener != null) {
            mListener.onClickSubmitButton(mParam1);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @ItemSelect({R.id.sp_left_up, R.id.sp_middle_up, R.id.sp_right_up,
                 R.id.sp_left_middle, R.id.sp_middle_middle, R.id.sp_right_middle,
                 R.id.sp_left_bottom, R.id.sp_middle_bottom, R.id.sp_right_bottom})
    public void onSelectedSpinnerItem(boolean selected, String s) {
        List<String> tmp = new ArrayList<>();
        for(Spinner spinner : spinnerList) {
            tmp.add(spinner.getSelectedItem().toString());
        }
        mParam1.setList(tmp);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onClickSubmitButton(AreaModel areaModel);
    }
}
