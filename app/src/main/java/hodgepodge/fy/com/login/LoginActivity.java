package hodgepodge.fy.com.login;

import android.Manifest;
import android.content.Intent;
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

import hodgepodge.fy.com.api.ApiService;
import hodgepodge.fy.com.entity.HomeBean;
import hodgepodge.fy.com.entity.LoginBean;
import hodgepodge.fy.com.entity.NewsBean;
import com.fy.baselibrary.permission.PermissionActivity;
import com.fy.baselibrary.retrofit.NetCallBack;
import com.fy.baselibrary.retrofit.RequestUtils;
import com.fy.baselibrary.retrofit.RxHelper;
import com.fy.baselibrary.retrofit.RxNetCache;
import com.fy.baselibrary.retrofit.dialog.IProgressDialog;
import com.fy.baselibrary.statusbar.MdStatusBarCompat;
import com.fy.baselibrary.utils.ConstantUtils;
import com.fy.baselibrary.utils.JumpUtils;
import com.fy.baselibrary.utils.KeyBoardUtils;
import com.fy.baselibrary.utils.L;
import com.fy.baselibrary.utils.SpfUtils;
import com.fy.baselibrary.utils.T;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import hodgepodge.fy.com.R;
import hodgepodge.fy.com.main.MainActivity;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

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

        Intent intent = new Intent(this, PermissionActivity.class);
        intent.putExtra(PermissionActivity.KEY_PERMISSIONS_ARRAY,
                new String[]{Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO});

        startActivityForResult(intent, PermissionActivity.CALL_BACK_PERMISSION_REQUEST_CODE);
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
//                login();
//                getNews();
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

    private void login(){
        IProgressDialog progressDialog = new IProgressDialog().init(mContext)
                .setDialogMsg(R.string.user_login);

        String mUserName = editUser.getText().toString().trim();
        String mPassWord = editPass.getText().toString().trim();

        Map<String, Object> param = new HashMap<>();
        param.put("username", mUserName);
        param.put("password", mPassWord);

        RequestUtils.create(ApiService.class)
                .loginToApp(param)
                .compose(RxHelper.handleResult())
                .doOnSubscribe(disposable -> RequestUtils.addDispos(disposable))
                .flatMap(new Function<LoginBean, ObservableSource<HomeBean>>() {
                    @Override
                    public ObservableSource<HomeBean> apply(LoginBean loginBean) throws Exception {

                        Bundle bundle = new Bundle();
                        bundle.putString("大王", "大王叫我来巡山");
                        JumpUtils.jump(mContext, MainActivity.class, bundle);

                        //获取最新的token
                        ConstantUtils.token = loginBean.getToken();
                        mCache.put("token", ConstantUtils.token, 14400);//token 超时时间

                        if (null != loginBean.getStudent()) {
                            //缓存学生信息 和 单独换粗学生id , 单独学生头像
                            mCache.put(ConstantUtils.student, loginBean.getStudent());
                            ConstantUtils.studentID = loginBean.getStudent().getStudentid();
                            ConstantUtils.head_portrait = loginBean.getStudent().getTouxiangurl();
                            SpfUtils.saveIntToSpf("studentId", ConstantUtils.studentID);
                            SpfUtils.saveStrToSpf("touxiangurl", ConstantUtils.head_portrait);
                        }

                        Map<String, Object> homeParam = new HashMap<>();
                        homeParam.put("token", ConstantUtils.token);
                        homeParam.put("studentid", ConstantUtils.studentID);
                        return RequestUtils
                                .create(ApiService.class)
                                .getHome(homeParam)
                                .compose(RxHelper.handleResult())
                                .doOnSubscribe(disposable -> RequestUtils.addDispos(disposable));
                    }
                })
                .subscribe(new NetCallBack<HomeBean>(progressDialog) {
                    @Override
                    protected void onSuccess(HomeBean t) {
                        editUser.setText("aaaa");
                    }

                    @Override
                    protected void updataLayout(int flag) {
                        L.e("net updataLayout", flag + "-----");
                    }
                });
    }

    private void loginTwo(){
        IProgressDialog progressDialog = new IProgressDialog().init(mContext)
                .setDialogMsg(R.string.user_login);

        String mUserName = editUser.getText().toString().trim();
        String mPassWord = editPass.getText().toString().trim();

        Map<String, Object> param = new HashMap<>();
        param.put("username", mUserName);
        param.put("password", mPassWord);

        new RxNetCache.Builder().setApi("loginToApp").create()
                .request(RequestUtils
                        .create(ApiService.class)
                        .loginToApp(param)
                        .compose(RxHelper.handleResult()))
                .flatMap(new Function<LoginBean, ObservableSource<HomeBean>>() {
                    @Override
                    public ObservableSource<HomeBean> apply(LoginBean loginBean) throws Exception {
                        //获取最新的token
                        ConstantUtils.token = loginBean.getToken();
                        mCache.put("token", ConstantUtils.token, 14400);//token 超时时间

                        if (null != loginBean.getStudent()) {
                            //缓存学生信息 和 单独换粗学生id , 单独学生头像
                            mCache.put(ConstantUtils.student, loginBean.getStudent());
                            ConstantUtils.studentID = loginBean.getStudent().getStudentid();
                            ConstantUtils.head_portrait = loginBean.getStudent().getTouxiangurl();
                            SpfUtils.saveIntToSpf("studentId", ConstantUtils.studentID);
                            SpfUtils.saveStrToSpf("touxiangurl", ConstantUtils.head_portrait);
                        }

                        Map<String, Object> homeParam = new HashMap<>();
                        homeParam.put("token", ConstantUtils.token);
//                        homeParam.put("studentid", ConstantUtils.studentID);
                        homeParam.put("studentid", "1");
                        return RequestUtils.create(ApiService.class)
                                .getHome(homeParam)
                                .compose(RxHelper.handleResult());
                    }
                })
                .subscribe(new NetCallBack<HomeBean>(progressDialog) {
                    @Override
                    protected void onSuccess(HomeBean t) {
                        Bundle bundle = new Bundle();
                        bundle.putString("大王", "大王叫我来巡山");
                        JumpUtils.jump(mContext, MainActivity.class, bundle);
                    }

                    @Override
                    protected void updataLayout(int flag) {

                    }
                });
    }

    private void getNews(){
        IProgressDialog progressDialog = new IProgressDialog().init(mContext)
                .setDialogMsg(R.string.user_login);

        RequestUtils.create(ApiService.class)
                .getNews()
                .compose(RxHelper.handleResult())
                .doOnSubscribe(RequestUtils::addDispos)
                .subscribe(new NetCallBack<List<NewsBean>>(progressDialog) {
                    @Override
                    protected void onSuccess(List<NewsBean> t) {
                        L.e(t.toString());
                    }

                    @Override
                    protected void updataLayout(int flag) {
                        L.e("net updataLayout", flag + "-----");
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PermissionActivity.CALL_BACK_PERMISSION_REQUEST_CODE) {
            switch (resultCode) {
                case PermissionActivity.CALL_BACK_RESULT_CODE_SUCCESS:
                    T.showLong("权限申请成功！");
                    break;
                case PermissionActivity.CALL_BACK_RESULE_CODE_FAILURE:
                    T.showLong("权限申请失败！");
                    break;
            }
        }
    }
}
