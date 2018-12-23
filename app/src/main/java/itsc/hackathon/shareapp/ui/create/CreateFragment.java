package itsc.hackathon.shareapp.ui.create;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import itsc.hackathon.shareapp.R;
import itsc.hackathon.shareapp.data.network.model.notification.Notification;
import itsc.hackathon.shareapp.data.network.model.post.Post;
import itsc.hackathon.shareapp.data.network.model.topic.Topic;
import itsc.hackathon.shareapp.di.component.ActivityComponent;
import itsc.hackathon.shareapp.ui.base.BaseFragment;
import itsc.hackathon.shareapp.ui.detail.DetailPostFragment;
import itsc.hackathon.shareapp.ui.notification.NotificationAdapter;
import itsc.hackathon.shareapp.ui.notification.NotificationMvpPresenter;
import itsc.hackathon.shareapp.ui.notification.NotificationMvpView;
import itsc.hackathon.shareapp.ui.post.PostFragment;

import static itsc.hackathon.shareapp.utils.AppConstants.DETAIL_POST_KEY;

public class CreateFragment extends BaseFragment implements CreateMvpView{

    @Inject
    CreateMvpPresenter<CreateMvpView> mPresenter;

    @Inject
    NotificationAdapter mAdapter;

    @Inject
    LinearLayoutManager mLayoutManager;

    @BindView(R.id.ed_compose_description)
    EditText etDescription;

    @BindView(R.id.ed_compose_title)
    EditText etTitle;

    @BindView(R.id.uploadedMaterial)
    ListView mListView;

    @BindView(R.id.to_whom_spinner)
    Spinner mToWhomSpinner;

    String[] items;
    String[] itemsQuestionType;
    String selectedSpinner = "";
    String selectedQuestionTypeSpinner = "";
    ArrayList<Uri> fileUris;
    ArrayList<String> fileUriList = new ArrayList<>();
    ArrayAdapter adapter;
    int lowerApi = 0;

//    public static String parentFragment;

    private static final int MY_PERMISSION_REQUEST = 100;

    private int PICK_FILE_REQUEST = 1;
    private int REQUEST_CHOOSER = 2;
    public static final String TAG = "CreateFragment";

    public static CreateFragment newInstance() {
        Bundle args = new Bundle();
        CreateFragment fragment = new CreateFragment();
        fragment.setArguments(args);
        return fragment;
    }

//    public static CreateFragment newInstance(Post post, String fragmentPassed) {
//        Bundle args = new Bundle();
//        args.putSerializable(DETAIL_POST_KEY, post);
//        CreateFragment fragment = new CreateFragment();
//        fragment.setArguments(args);
//        parentFragment = fragmentPassed;
//        return fragment;
//    }

    private View mLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create, container, false);

        ActivityComponent component = getActivityComponent();
        if (component != null) {
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            mPresenter.onAttach(this);
        }

        setUp(view);


        return view;
    }

    @Override
    protected void setUp(View view) {

        adapter = new ArrayAdapter<>(getBaseActivity(), R.layout.file_listview, R.id.file_title, fileUriList);

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @OnClick(R.id.btn_upload_file_layout)
    void onUploadFilesClicked() {
        if (ContextCompat.checkSelfPermission(getBaseActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestCameraPermission();
        } else {
            openFileChooser();
        }
    }

    private void requestCameraPermission() {
        // Permission has not been granted and must be requested.
        if (ActivityCompat.shouldShowRequestPermissionRationale(getBaseActivity(),
                Manifest.permission.READ_EXTERNAL_STORAGE)) {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // Display a SnackBar with cda button to request the missing permission.
            Snackbar.make(mLayout, "External file access required.",
                    Snackbar.LENGTH_INDEFINITE).setAction("OK", view -> {
                // Request the permission
                ActivityCompat.requestPermissions(getBaseActivity(),
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSION_REQUEST);
            }).show();

        } else {
            Snackbar.make(mLayout, "External file does not exist", Snackbar.LENGTH_SHORT).show();
            // Request the permission. The result will be received in onRequestPermissionResult().
            ActivityCompat.requestPermissions(getBaseActivity(),
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSION_REQUEST);
        }
    }

    @OnClick(R.id.nav_back_btn)
    void onNavBackClick() {
        getBaseActivity().onFragmentDetached(TAG, PostFragment.TAG);
    }

    public void openFileChooser() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(Intent.createChooser(intent, "Select file"),
                        PICK_FILE_REQUEST);
            } catch (Exception e) {
                //todo handle the exception in here
            }
//        } else {
//            lowerApi = 1;
//            try {
//                // Create the ACTION_GET_CONTENT Intent
//                Intent getContentIntent = FileUtils.createGetContentIntent();
//
//                Intent intent = Intent.createChooser(getContentIntent, getString(R.string.select_file));
//                startActivityForResult(intent, REQUEST_CHOOSER);
//            } catch (Exception e) {
//                //todo handle the exception in here
//            }
        }
    }

    @Override
    public void showUserQuestionables(List<Topic> questionableUsers) {
//        Log.e("---->Questionable", String.valueOf(questionableUsers.size()));
        String[] visibleItems = new String[questionableUsers.size()];
        items = new String[questionableUsers.size()];
        for (int i = 0; i < questionableUsers.size(); i++) {
            visibleItems[i] = questionableUsers.get(i).getName();
            items[i] = questionableUsers.get(i).getId();
        }

        hideLoading();

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getBaseActivity(), android.R.layout.simple_spinner_dropdown_item, visibleItems);//ArrayAdapter.createFromResource(this, , android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        mToWhomSpinner.setAdapter(adapter);
    }

    @Override
    public void openQuestionListActivity() {
        getBaseActivity().onFragmentDetached(TAG, PostFragment.TAG);
    }

    @OnClick(R.id.btn_compose_submit)
    void onSubmitClicked() {
        if (!selectedSpinner.isEmpty() && !selectedQuestionTypeSpinner.isEmpty()
                && !etTitle.getText().toString().trim().isEmpty()
                && !etDescription.getText().toString().trim().isEmpty()) {

            try {
                mPresenter.composeQuestion(etTitle.getText().toString().trim(), selectedSpinner,
                        etDescription.getText().toString().trim(), fileUris, lowerApi);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        } else if (etTitle.getText().toString().trim().isEmpty()) {
            etTitle.requestFocus();
            etTitle.setError("Title is required.");
        } else if (etDescription.getText().toString().trim().isEmpty()) {
            etDescription.requestFocus();
            etDescription.setError("Description is required.");
        }
    }


}
