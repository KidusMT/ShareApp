package itsc.hackathon.shareapp.ui.create;


import android.net.Uri;

import java.net.URISyntaxException;
import java.util.List;

import itsc.hackathon.shareapp.di.PerActivity;
import itsc.hackathon.shareapp.ui.base.MvpPresenter;

/**
 * Created by KidusMT.
 */

@PerActivity
public interface CreateMvpPresenter<V extends CreateMvpView> extends MvpPresenter<V> {

    void composeQuestion(String title, String toWhom, String description, List<Uri> files, int lowerApi) throws URISyntaxException;
    void loadUserGroups();
//    void loadQuestionTypes();
}
