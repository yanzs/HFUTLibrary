package yanzs.hfutlibrary.activity.mine;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import yanzs.hfutlibrary.adapter.MineInfoAdapter;
import yanzs.hfutlibrary.base.BaseActivity;
import yanzs.hfutlibrary.constant.ShareKey;
import yanzs.hfutlibrary.constant.Values;
import yanzs.hfutlibrary.listener.OnMineScrollStateChangedListener;
import yanzs.hfutlibrary.R;
import yanzs.hfutlibrary.util.FileUtil;
import yanzs.hfutlibrary.util.JsoupUtil;
import yanzs.hfutlibrary.util.ShareUtil;

public class Mine_Info extends BaseActivity {
    @BindView(R.id.mine_info_recycle)
    RecyclerView mine_info_recycle;
    @BindView(R.id.mine_info_rela_toolbar)
    RelativeLayout mine_info_rela_toolbar;
    @BindView(R.id.mine_info_img_back)
    ImageView mine_info_img_back;
    @BindView(R.id.mine_info_img_inform)
    ImageView mine_info_img_inform;
    @BindView(R.id.mine_info_text_title)
    TextView mine_info_text_title;
    private final int RESULT_LOAD_BACKGROUND = 1000;
    private final int RESULT_LOAD_HEAD = 1100;
    private final int RESULT_LOAD_HEAD_CUT = 1200;
    private final int RESULT_LOAD_BACKGROUND_CUT = 1400;
    private MineInfoAdapter mineInfoAdapter;
    private List<String> data;
    private Uri uritempFile;
    private OnMineScrollStateChangedListener mineScrollStateChangedListener;

    @Override
    protected void initActivity() {
        data = JsoupUtil.getMineInfo(this);
        mine_info_recycle.setLayoutManager(new LinearLayoutManager(this));
        mineInfoAdapter = new MineInfoAdapter(this, data, this);
        mine_info_recycle.setAdapter(mineInfoAdapter);
        mine_info_img_inform.setVisibility(View.INVISIBLE);
        mine_info_text_title.setVisibility(View.INVISIBLE);
        mineScrollStateChangedListener = new OnMineScrollStateChangedListener(mine_info_img_inform, mine_info_text_title, mine_info_rela_toolbar);
        mine_info_recycle.addOnScrollListener(mineScrollStateChangedListener);
    }

    @Override
    protected int getResId() {
        return R.layout.module_activity_mine_info;
    }


    @OnClick(R.id.mine_info_img_back)
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.mine_info_img_back:
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case RESULT_LOAD_BACKGROUND:
                if (resultCode == RESULT_OK && data != null) {
                    Uri imageUri = data.getData();
                    Intent backIntent = new Intent("com.android.camera.action.CROP");
                    backIntent.setDataAndType(imageUri, "image/*");
                    backIntent.putExtra("return-data", false);
                    backIntent.putExtra("crop", "true");
                    backIntent.putExtra("aspectX", 10);
                    backIntent.putExtra("aspectY", 7);
                    backIntent.putExtra("outputX", dpTopx(this, 330));
                    backIntent.putExtra("outputY", dpTopx(this, 231));
                    uritempFile = Uri.parse("file://" + "/" + Environment.getExternalStorageDirectory().getPath() + "/" + "background.jpg");
                    backIntent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
                    backIntent.putExtra(MediaStore.EXTRA_OUTPUT, uritempFile);
                    startActivityForResult(backIntent, RESULT_LOAD_BACKGROUND_CUT);

                }
                break;
            case RESULT_LOAD_HEAD:
                if (resultCode == RESULT_OK && data != null) {
                    Uri headUri = data.getData();
                    Intent headIntent = new Intent("com.android.camera.action.CROP");
                    headIntent.setDataAndType(headUri, "image/*");
                    headIntent.putExtra("return-data", true);
                    headIntent.putExtra("crop", "true");
                    headIntent.putExtra("aspectX", 1);
                    headIntent.putExtra("aspectY", 1);
                    headIntent.putExtra("outputX", dpTopx(this, 100));
                    headIntent.putExtra("outputY", dpTopx(this, 100));
                    startActivityForResult(headIntent, RESULT_LOAD_HEAD_CUT);
                }
                break;
            case RESULT_LOAD_BACKGROUND_CUT:
                Bitmap map = null;
                try {
                    map = BitmapFactory.decodeStream(getContentResolver().openInputStream(uritempFile));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                try {
                        if (map != null) {
                            FileUtil.saveBitmapToFile(map, getFilesDir().getPath(), Values.UESR_DEFAULT_BACKGROUND);
                            ShareUtil.storeLocalData(this, ShareKey.SHARED_KEY, ShareKey.KEY_THEME_BACKGROUND_DEFAULT, false);
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                mineInfoAdapter.getRela_background().setBackground(new BitmapDrawable(getResources(), map));
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                break;
            case RESULT_LOAD_HEAD_CUT:
                if (data != null) {
                    Bitmap bitmap = data.getParcelableExtra("data");
                    try {
                        if (bitmap != null) {
                            FileUtil.saveBitmapToFile(bitmap, getFilesDir().getPath(), Values.UESR_DEFAULT_HEAD);
                            ShareUtil.storeLocalData(this, ShareKey.SHARED_KEY, ShareKey.KEY_THEME_HEAD_DEFAULT, false);
                            mineInfoAdapter.getCirimg_header().setImageDrawable(new BitmapDrawable(getResources(), bitmap));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    public static int dpTopx(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

}
