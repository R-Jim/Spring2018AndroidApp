package Service;

import android.support.v4.app.FragmentTabHost;
import android.widget.ImageView;
import android.widget.TabHost;

/**
 * Created by Swomfire on 23-Feb-18.
 */

public interface TabHostService {
    void tabIconReset(FragmentTabHost tabHost);

    void tabInitializer(FragmentTabHost tabHost);

    void tabChooseIndicator(FragmentTabHost tabHost, String switchCase);
}
