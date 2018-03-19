package utils;

import android.graphics.drawable.GradientDrawable;
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
        }
        view.setBackground(gradientDrawable);
    }

    public enum GradientMode {
        LEFT_RIGHT("L_R"), RIGHT_LEFT("R_L"), TOP_BOTTOM("T_B"), BOTTOM_TOP("B_T");

        private String mode;

        GradientMode(String mode) {
            this.mode = mode;
        }

        public String getMode() {
            return mode;
        }
    }
}
