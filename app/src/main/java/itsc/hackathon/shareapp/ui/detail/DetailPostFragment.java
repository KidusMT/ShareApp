package itsc.hackathon.shareapp.ui.detail;

import android.hardware.Sensor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import itsc.hackathon.shareapp.R;
import itsc.hackathon.shareapp.data.network.model.post.Post;
import itsc.hackathon.shareapp.di.component.ActivityComponent;
import itsc.hackathon.shareapp.ui.base.BaseFragment;

import static itsc.hackathon.shareapp.utils.AppConstants.DETAIL_POST_KEY;

public class DetailPostFragment extends BaseFragment {

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
////        setContentView(R.layout.fragment_detail);
//    }

    public static String parentFragment;

    public static DetailPostFragment newInstance(Post post, String fragmentPassed) {
        Bundle args = new Bundle();
        args.putSerializable(DETAIL_POST_KEY, post);
        DetailPostFragment fragment = new DetailPostFragment();
        fragment.setArguments(args);
        parentFragment = fragmentPassed;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detail, container, false);

        ActivityComponent component = getActivityComponent();
        if (component != null) {
//            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
//            mPresenter.onAttach(this);
//            mAdapter.setCallback(this);
//            mAdapter.setEditCallback(this);
//            mAdapter.setDeleteCallback(this);
        }

//        if (getArguments() != null)
//            mSensor = (Sensor) getArguments().getSerializable(DETAIL_POST_KEY);

        setUp(view);

        return view;
    }

    @Override
    protected void setUp(View view) {

    }
}
