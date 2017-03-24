package com.xxl.ebook.com.xxl.ebook.utils;

import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;

/**
 * Created by xuxiaolong on 2017/3/24.
 */

public class AnimUtils {
    public static TranslateAnimation MoveToTopForShow() {
        TranslateAnimation translateAnimation =
                new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                        Animation.RELATIVE_TO_SELF, 0.0f,
                        Animation.RELATIVE_TO_SELF, 1.0f,
                        Animation.RELATIVE_TO_SELF, 0.0f);
        translateAnimation.setDuration(500);
        return translateAnimation;

    }
    public static TranslateAnimation MoveToTopForHide() {
        TranslateAnimation translateAnimation =
                new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                        Animation.RELATIVE_TO_SELF, 0.0f,
                        Animation.RELATIVE_TO_SELF, 0.0f,
                        Animation.RELATIVE_TO_SELF, -1.0f);
        translateAnimation.setDuration(500);
        return translateAnimation;

    }
    public static TranslateAnimation MoveToBottomForShow() {
        TranslateAnimation translateAnimation =
                new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                        Animation.RELATIVE_TO_SELF, 0.0f,
                        Animation.RELATIVE_TO_SELF, -1.0f,
                        Animation.RELATIVE_TO_SELF, 0.0f);
        translateAnimation.setDuration(500);
        return translateAnimation;

    }
    public static TranslateAnimation MoveToBottomForHide() {
        TranslateAnimation translateAnimation =
                new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                        Animation.RELATIVE_TO_SELF, 0.0f,
                        Animation.RELATIVE_TO_SELF, 0.0f,
                        Animation.RELATIVE_TO_SELF, 1.0f);
        translateAnimation.setDuration(500);
        return translateAnimation;

    }
}
