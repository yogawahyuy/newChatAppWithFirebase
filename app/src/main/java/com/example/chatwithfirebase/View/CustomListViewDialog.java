package com.example.chatwithfirebase.View;

import android.app.Dialog;
import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CustomListViewDialog extends Dialog {

    public CustomListViewDialog(@NonNull Context context) {
        super(context);
    }

    public CustomListViewDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected CustomListViewDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
}
