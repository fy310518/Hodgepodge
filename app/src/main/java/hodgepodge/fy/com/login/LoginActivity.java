package hodgepodge.fy.com.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.fy.baselibrary.base.BaseActivity;
import com.fy.baselibrary.statusbar.MdStatusBarCompat;
import com.fy.baselibrary.utils.JumpUtils;
import com.fy.baselibrary.utils.KeyBoardUtils;
import com.fy.baselibrary.utils.SpfUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hodgepodge.fy.com.R;
import hodgepodge.fy.com.main.MainActivity;

/**
 * 登录
 * Created by fangs on 2017/12/12.
 */
public class LoginActivity extends BaseActivity {

    @BindView(R.id.cBoxPass)
    CheckBox cBoxPass;
    @BindView(R.id.tvLoginTitle)
    TextView tvLoginTitle;

    @BindView(R.id.editUser)
    EditText editUser;
    @BindView(R.id.editPass)
    EditText editPass;
    @BindView(R.id.login_title)
    TextView mLoginTitle;
    @BindView(R.id.iv_account_delete)
    AppCompatImageView mIvAccountDelete;
    @BindView(R.id.iv_password_delete)
    AppCompatImageView mIvPasswordDelete;

    @Override
    protected void setStatusBarType() {
        MdStatusBarCompat.setImageTransparent(this);
    }

    @Override
    protected int setContentView() {
        return 0;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_login);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void init(Bundle savedInstanceState) {

        Spannable sp = new SpannableString(getString(R.string.loginTitel));
        sp.setSpan(new AbsoluteSizeSpan(20, true), 0, 15, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        sp.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.txtSuperColor)),
                0, 15, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

        sp.setSpan(new AbsoluteSizeSpan(14, true), 15, sp.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        sp.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.txtSecondColor)),
                15, sp.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        tvLoginTitle.setText(sp);

        Spannable sp1 = new SpannableString(getString(R.string.loginTitel2));
        sp1.setSpan(new AbsoluteSizeSpan(20, true), 0, 10, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        sp1.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.txtSuperColor)),
                0, 10, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);

        sp1.setSpan(new AbsoluteSizeSpan(14, true), 10, sp1.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        sp1.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.txtSecondColor)),
                10, sp1.length(), Spannable.SPAN_INCLUSIVE_EXCLUSIVE);
        mLoginTitle.setText(sp1);

        //设置缓存的登录 账号 密码
        editUser.setText(SpfUtils.getSpfSaveStr("username"));
        if (!TextUtils.isEmpty(SpfUtils.getSpfSaveStr("password"))) {
            editPass.setText(SpfUtils.getSpfSaveStr("password"));
            editUser.setSelection(editUser.getText().length());
            cBoxPass.setChecked(true);
        }
    }

    @OnClick({R.id.tvLogin, R.id.iv_account_delete, R.id.iv_password_delete})
    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.tvLogin:
                Bundle bundle = new Bundle();
                bundle.putString("大王", "大王叫我来巡山");
                JumpUtils.jump(mContext, MainActivity.class, bundle);
                break;
            case R.id.iv_account_delete:
                editUser.setText("");
                KeyBoardUtils.openKeyBoard(LoginActivity.this);
                break;
            case R.id.iv_password_delete:
                editPass.setText("");
                KeyBoardUtils.openKeyBoard(LoginActivity.this);
                break;
        }
    }
}
