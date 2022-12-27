package com.example.hangyou.ui.group;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.hangyou.R;

public class GroupCommentDialog extends Dialog {
    /**
     * 上下文对象 *
     */
    Activity context;


    public GroupCommentDialog(Activity context) {
        super(context);
        this.context= context;
    }

    public GroupCommentDialog(Activity context, int theme) {
        super(context, theme);
        this.context= context;
    }

    private void commit() {
        EditText et_comment = findViewById(R.id.group_comment_input);
        String comment = et_comment.getText().toString();

    }

    private void clockDialog() {
        onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.group_comment_edit);
        Window dialogWindow = this.getWindow();
        WindowManager m = context.getWindowManager();
        dialogWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Display d = m.getDefaultDisplay();
        WindowManager.LayoutParams p = dialogWindow.getAttributes();
        p.height = (int) (d.getHeight() * 0.55);
        p.width = (int) (d.getWidth() * 0.8);
        dialogWindow.setAttributes(p);

        Button bt_commit = findViewById(R.id.group_comment_commit);
        bt_commit.setOnClickListener(v -> commit());
        Button bt_return = findViewById(R.id.group_comment_return);
        bt_return.setOnClickListener(v -> clockDialog());

        this.setCancelable(true);
    }
}
