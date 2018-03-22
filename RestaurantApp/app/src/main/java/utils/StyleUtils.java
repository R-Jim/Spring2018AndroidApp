package utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.view.View;

/**
 * Created by Swomfire on 16-Mar-18.
 */

public class StyleUtils {
    public static void setGradientBackground(View view, int id, int[] colors, String mode) {
        setGradientBackground(view.findViewById(id), colors, mode);
    }

    public static void setGradientBackground(View view, int[] colors, String mode) {
        GradientDrawable gradientDrawable = null;
        switch (mode) {
            case "L_R":
                gradientDrawable = new GradientDrawable(
                        GradientDrawable.Orientation.LEFT_RIGHT, colors);
                break;
            case "R_L":
                gradientDrawable = new GradientDrawable(
                        GradientDrawable.Orientation.RIGHT_LEFT, colors);
                break;
            case "T_B":
                gradientDrawable = new GradientDrawable(
                        GradientDrawable.Orientation.TOP_BOTTOM, colors);
                break;
            case "B_T":
                gradientDrawable = new GradientDrawable(
                        GradientDrawable.Orientation.BOTTOM_TOP, colors);
                break;
            case "BL_TR":
                gradientDrawable = new GradientDrawable(
                        GradientDrawable.Orientation.BL_TR, colors);
                break;
        }
        view.setBackground(gradientDrawable);
    }

    public enum GradientMode {
        LEFT_RIGHT("L_R"), RIGHT_LEFT("R_L"), TOP_BOTTOM("T_B"), BOTTOM_TOP("B_T"), BOTTOMLEFT_TOPRIGHT("BL_TR");

        private String mode;

        GradientMode(String mode) {
            this.mode = mode;
        }

        public String getMode() {
            return mode;
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public static void showProgress(final boolean show, View context, View progressBar) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = 200;

            context.setVisibility(show ? View.GONE : View.VISIBLE);
            context.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    context.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
            progressBar.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
            context.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
}
