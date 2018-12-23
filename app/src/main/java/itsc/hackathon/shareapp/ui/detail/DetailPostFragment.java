package itsc.hackathon.shareapp.ui.detail;

import android.app.Dialog;
import android.content.Intent;
import android.hardware.Sensor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.thunder413.datetimeutils.DateTimeStyle;
import com.github.thunder413.datetimeutils.DateTimeUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import itsc.hackathon.shareapp.R;
import itsc.hackathon.shareapp.data.network.model.comment.Comment;
import itsc.hackathon.shareapp.data.network.model.post.Post;
import itsc.hackathon.shareapp.di.component.ActivityComponent;
import itsc.hackathon.shareapp.ui.base.BaseFragment;
import okhttp3.ResponseBody;

import static itsc.hackathon.shareapp.utils.AppConstants.DETAIL_POST_KEY;

public class DetailPostFragment extends BaseFragment implements DetailPostMvpView, DetailAdapter.Callback {

    @Inject
    DetailPostMvpPresenter<DetailPostMvpView> mPresenter;

    @Inject
    DetailAdapter mAdapter;

    @Inject
    LinearLayoutManager mLayoutManager;

    @BindView(R.id.detail_comment_recycler)
    RecyclerView mRecyclerView;

    @BindView(R.id.detail_swipe_to_refresh)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    @BindView(R.id.tv_no_comments)
    TextView tvNoMeasurement;

    @BindView(R.id.card_post_user_profile)
    ImageView mPostUserProfile;

    @BindView(R.id.card_post_title)
    TextView mPostTitle;

    @BindView(R.id.card_post_date)
    TextView mPostDate;

//    @BindView(R.id.card_post_attachment)
//    ImageView mPostAttachment;

    @BindView(R.id.card_post_description)
    TextView mPostDescription;

    @BindView(R.id.topic_container)
    LinearLayout topicContainer;

    @BindView(R.id.card_post_points)
    TextView mPostPoints;

    @BindView(R.id.card_post_comments_count)
    TextView mPostCommentCount;

    @BindView(R.id.card_post_vote_count)
    TextView mPostVoteCount;

    @BindView(R.id.detail_comment_count)
    TextView mCommentCount;

    @BindView(R.id.tv_document_title)
    TextView title;

    @BindView(R.id.download_document)
    ImageView downloadIcon;

    @BindView(R.id.card_post_btn_vote_up)
    ImageView btnVoteUp;

    @BindView(R.id.card_post_btn_vote_down)
    ImageView btnVoteDown;


    Post post;

    String fileName;


    public static final String TAG = "DetailPostFragment";

    public static String parentFragment;

    DownloadZipFileTask downloadZipFileTask;

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
            component.inject(this);
            setUnBinder(ButterKnife.bind(this, view));
            mPresenter.onAttach(this);
            mAdapter.setCallback(this);
//            mAdapter.setEditCallback(this);
//            mAdapter.setDeleteCallback(this);
        }

        if (getArguments() != null)
            post = (Post) getArguments().getSerializable(DETAIL_POST_KEY);

        setUp(view);

        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            // todo check how other classes done it
            if (post != null)
                mPresenter.loadComments(post.getId());
            mSwipeRefreshLayout.setRefreshing(false);
            hideLoading();
        });

        return view;
    }

    @Override
    protected void setUp(View view) {
        loadPage(post);
        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
    }

    @OnClick(R.id.nav_back_btn)
    void onNavBackClick() {
        getBaseActivity().onFragmentDetached(TAG, parentFragment);
    }

    @Override
    public void onDestroyView() {
        mPresenter.onDetach();
        super.onDestroyView();
    }

    @Override
    public void onItemClicked(Comment comment) {

    }

    @Override
    public void showComments(List<Comment> comments) {
        if (comments != null) {
            if (comments.size() > 0) {
                if (tvNoMeasurement != null && tvNoMeasurement.getVisibility() == View.VISIBLE)
                    tvNoMeasurement.setVisibility(View.GONE);
                if (mRecyclerView != null && mRecyclerView.getVisibility() == View.GONE)
                    mRecyclerView.setVisibility(View.VISIBLE);
                mAdapter.addItems(comments);
                mCommentCount.setText(String.valueOf(comments.size()) + " comments");
            } else {
                if (mRecyclerView != null && mRecyclerView.getVisibility() == View.VISIBLE)
                    mRecyclerView.setVisibility(View.GONE);
                if (mRecyclerView != null && mRecyclerView.getVisibility() == View.GONE) {
                    tvNoMeasurement.setVisibility(View.VISIBLE);
                    tvNoMeasurement.setText("No comments list found.");
                }
                mCommentCount.setVisibility(View.GONE);
            }
        }
        hideLoading();
    }

    @Override
    public void openDetailSensorActivity(Sensor sensor) {

    }

    @Override
    public void writeResponseBodyToDisk(ResponseBody body, Dialog dialog) {
        downloadZipFileTask = new DownloadZipFileTask();
        downloadZipFileTask.execute(body);
        onResume();
//        downloadListAdapter.notifyDataSetChanged();
        dialog.dismiss();
    }

    @Override
    public void loadPage(Post post) {
        if (post != null) {
            java.io.File destinationFile = null;

            // we can display id if the title is not being displayed...but it most probably works.
            toolbarTitle.setText((TextUtils.isEmpty(post.getTitle())) ? String.valueOf(post.getId()) : String.valueOf(post.getTitle()));

            // fetching the comments using the postId
            if (!TextUtils.isEmpty(post.getId()))
                mPresenter.loadComments(post.getId());

            showLoading();
            // todo tvNoMeasurements for the measurements and the whole sensor is different should be implemented
            tvNoMeasurement.setVisibility(View.GONE);
            // todo get back here and fix the comments
//            showComments(post.getId(), post.getMeasurements());

            if (!TextUtils.isEmpty(post.getCreatedAt()))
                mPostDate.setText(String.valueOf(DateTimeUtils.formatWithStyle(post.getCreatedAt(),
                        DateTimeStyle.MEDIUM)));

            if (!TextUtils.isEmpty(post.getTitle()))
                mPostTitle.setText(String.valueOf(post.getTitle()));



            if (!TextUtils.isEmpty(post.getFile())) {
                // todo check this out later
                fileName = post.getFile().substring(post.getFile().lastIndexOf("/")+1,post.getFile().length()-1);
                destinationFile = new java.io.File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), post.getFile());

                title.setText(String.valueOf(fileName));

                if (destinationFile.isFile()) {
                    downloadIcon.setImageResource(R.drawable.ic_folder_open);
                } else {
                    downloadIcon.setImageResource(R.drawable.ic_file_download);
                }

                File finalDestinationFile = destinationFile;

                downloadIcon.setOnClickListener(view -> {
                    if (finalDestinationFile.isFile())
                        openFile(finalDestinationFile.getPath());
                });
            }
            if (!TextUtils.isEmpty(post.getDescription()))
                mPostDescription.setText(String.valueOf(post.getDescription()));

        } else {// todo there has to be a way of expression the tvNoMeasurement in here in the else clause

        }
    }

    private class DownloadZipFileTask extends AsyncTask<ResponseBody, Pair<Integer, Long>, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(ResponseBody... urls) {
            //Copy you logic to calculate progress and call
//            if (!originalName.isEmpty())
//                saveToDisk(urls[0], originalName);
//            else if (!fileName.isEmpty())
            if (!TextUtils.isEmpty(fileName))
                saveToDisk(urls[0], fileName);
            return null;
        }

        protected void onProgressUpdate(Pair<Integer, Long>... progress) {

            if (progress[0].first == 100)
                Toast.makeText(getBaseActivity(), "File downloaded successfully", Toast.LENGTH_SHORT).show();

            if (progress[0].second > 0) {
                int currentProgress = (int) ((double) progress[0].first / (double) progress[0].second * 100);
//                progressBar.setProgress(currentProgress);

//                txtProgressPercent.setText("Progress " + currentProgress + "%");

            }

            if (progress[0].first == -1) {
                Toast.makeText(getBaseActivity(), "Download failed", Toast.LENGTH_SHORT).show();
            }

        }

        public void doProgress(Pair<Integer, Long> progressDetails) {
            publishProgress(progressDetails);
        }

        @Override
        protected void onPostExecute(String result) {

        }
    }

    private void saveToDisk(ResponseBody body, String filename) {
        try {

            File destinationFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), filename);

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(destinationFile);
                byte data[] = new byte[4096];
                int count;
                int progress = 0;
                long fileSize = body.contentLength();
                Log.d(TAG, "File Size=" + fileSize);
                while ((count = inputStream.read(data)) != -1) {
                    outputStream.write(data, 0, count);
                    progress += count;
                    Pair<Integer, Long> pairs = new Pair<>(progress, fileSize);
                    downloadZipFileTask.doProgress(pairs);
                    Log.d(TAG, "Progress: " + progress + "/" + fileSize + " >>>> " + (float) progress / fileSize);
                }

                outputStream.flush();

                Log.d(TAG, destinationFile.getParent());
                Pair<Integer, Long> pairs = new Pair<>(100, 100L);
                downloadZipFileTask.doProgress(pairs);
                return;
            } catch (IOException e) {
                e.printStackTrace();
                Pair<Integer, Long> pairs = new Pair<>(-1, Long.valueOf(-1));
                downloadZipFileTask.doProgress(pairs);
                Toast.makeText(getBaseActivity(), "Failed to save the file!", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "Failed to save the file!");
                return;
            } finally {
                if (inputStream != null) inputStream.close();
                if (outputStream != null) outputStream.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getBaseActivity(), "Failed to save the file!", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "Failed to save the file!");
            return;
        }
    }

    public void openFile(String filePath) {
        java.io.File file = new File(filePath);
        MimeTypeMap map = MimeTypeMap.getSingleton();
        String ext = MimeTypeMap.getFileExtensionFromUrl(file.getName());
        String type = map.getMimeTypeFromExtension(ext);

        if (type == null)
            type = "*/*";

        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri data = Uri.fromFile(file);

        intent.setDataAndType(data, type);

        startActivity(intent);
    }
}
