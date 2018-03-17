package service;

import android.support.v4.app.FragmentTabHost;

/**
 * Created by Swomfire on 23-Feb-18.
 */

public interface TabHostService {
    public final String REQUEST_TAB_ID = "REQUEST_LIST_TAB";
    public final String TABLE_TAB_ID = "TABLE_LIST_TAB";
    public final String DISH_TAB_ID = "DISH_LIST_TAB";

    void tabIconReset(FragmentTabHost tabHost);

    void tabInitializer(FragmentTabHost tabHost);

    void tabChooseIndicator(FragmentTabHost tabHost, String switchCase);
}
