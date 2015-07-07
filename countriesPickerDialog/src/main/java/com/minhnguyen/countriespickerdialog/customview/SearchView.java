package com.minhnguyen.countriespickerdialog.customview;

import android.app.Activity;
import android.content.Context;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.minhnguyen.countriespickerdialog.R;

/**
 * Created by Minh Nguyen on 6/9/2015.
 */
public class SearchView extends LinearLayout {

    private RelativeLayout rltEditText;
    private EditText edtSearchBox;
    private ImageView imgReset;
    private Context context;
    private OnSearchViewListener onSearchViewListener;
    private OnClickListener onClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            edtSearchBox.setText("");
            if (onSearchViewListener != null) {
                onSearchViewListener.OnCancelSearch();
            }
        }
    };

    private OnEditorActionListener mOnEditorActionListener = new OnEditorActionListener() {

        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_SEARCH && onSearchViewListener != null) {
                onSearchViewListener.OnSearchEndEditting(edtSearchBox.getText().toString());
                hideKeyboardWhenNotFocus((Activity) context);
            }
            return true;
        }
    };

    public SearchView(Context context) {
        super(context);
        init(context);
        this.context = context;
    }

    public SearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
        this.context = context;
    }

    public SearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs);
        init(context);
        this.context = context;
    }

    private void init(final Context context) {
        View searchView = inflate(context, R.layout.mn_search_view, null);
        rltEditText = (RelativeLayout) searchView.findViewById(R.id.mn_searchView_rltEditText);
        edtSearchBox = (EditText) searchView.findViewById(R.id.mn_searchView_edtSearchBox);
        edtSearchBox.setOnEditorActionListener(mOnEditorActionListener);

        imgReset = (ImageView) searchView.findViewById(R.id.mn_searchView_imgReset);
        imgReset.setOnClickListener(onClickListener);

        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        addView(searchView, layoutParams);
    }

    public EditText getEditTextSearch() {
        return edtSearchBox;
    }

    public ImageView getButtonClear() {
        return imgReset;
    }

    public void addTextChangedListener(TextWatcher textWatcher) {
        edtSearchBox.addTextChangedListener(textWatcher);
    }

    public void clearText() {
        edtSearchBox.setText("");
    }

    public void setTextSearch(String keyword) {
        edtSearchBox.setText(keyword);
    }

    public void setHint(String hint) {
        edtSearchBox.setHint(hint);
    }

    public void setOnSearchViewListener(OnSearchViewListener onSearchViewListener) {
        this.onSearchViewListener = onSearchViewListener;
    }

    public ImageView getImgReset() {
        return imgReset;
    }

    public void setImgReset(ImageView imgReset) {
        this.imgReset = imgReset;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public static void hideKeyboardWhenNotFocus(Activity activity) {
        InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        // check if no view has focus:
        View view = activity.getCurrentFocus();
        if (view != null) {
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public interface OnSearchViewListener {
        public void OnCancelSearch();

        public void OnSearchEndEditting(String text);

        public void OnOpenSearchSetting();

        public void OnCloseSearchSetting();
    }
}