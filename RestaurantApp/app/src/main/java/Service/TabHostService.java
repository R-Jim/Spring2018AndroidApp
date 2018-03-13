package service;

import android.support.v4.app.FragmentTabHost;

/**
 * Created by Swomfire on 23-Feb-18.
 */

public interface TabHostService {
    void tabIconReset(FragmentTabHost tabHost);

    void tabInitializer(FragmentTabHost tabHost);

    void tabChooseIndicator(FragmentTabHost tabHost, String switchCase);
}
