package qianfeng.popupapplication;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<String> userList;

    private boolean isclick = true;
    private EditText et_username;
    private PopupWindow pw;
    private List<String> list;
    private List<String> list1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_username = ((EditText) findViewById(R.id.et_username));

    }


    private void initListView(View pwView)
    {

        ListView lv = (ListView) pwView.findViewById(R.id.lv);

        list1 = new ArrayList<>();


        for (int i = 0; i < 50; i++) {
            list1.add("lisi:" + i);
        }


        MyBaseAdapter adapter = new MyBaseAdapter(this, list1);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                et_username.setText(list1.get(position));

                // 如果选中了ListView里面的某一项，记得关闭pwView哦，就是这个dismiss方法,pwView没有被销毁哦，只是暂时关闭了
                pw.dismiss();
            }
        });


    }

    public void showPw(View view) { // 点击展示PopupWindow控件


        if (isclick) {
            isclick = !isclick;
            Log.d("google-my:", "showPw: ------------------------------------------->" );
            View pwView = LayoutInflater.from(this).inflate(R.layout.pw_layout, null);

            initListView(pwView);


            // 是抢焦点的问题，pwView如果你没有设置这个true，它将抢不到焦点，焦点将是MainActivity抢到的
            //                           这个宽度和高度，是要显示的视图的宽度和高度，是不能为0的
            pw = new PopupWindow(pwView, et_username.getWidth(), 500,true);  // 设置了pwView的抢焦点能力，才可以对pwView里面的ListView进行操作，否则，将会点击不到ListView
            // 先让pwView抢到焦点，然后才能监听ListView的点击事件，否则，焦点将给MainActivity抢到

            // 让pw抢到焦点
            pw.setFocusable(true);
            // 可以让用户点击pwView视图以外的地方，关闭pwView
            pw.setOutsideTouchable(true);
            pw.setBackgroundDrawable(new BitmapDrawable());// 一行代码搞掂


            // 我要点击了一个item就可以在EditText里面显示出来


            //showAsDropDown表示pw在某一个控件的下方显示
            pw.showAsDropDown(et_username);  // 这行才是关键啊卧槽！！！记住这行最关键的代码啊！！！能不能显示pwView就看它的了

//         整理一下各种控件的显示方法
        } else {

            // isclick = false;
            isclick = !isclick;



        }


    }

    public void share(View view) {// 一键分享

        View inflate = LayoutInflater.from(this).inflate(R.layout.share_item, null);

                                                    // 指定宽度，指定高度
        PopupWindow popupWindow = new PopupWindow(inflate, LinearLayout.LayoutParams.MATCH_PARENT, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,200,getResources().getDisplayMetrics())); // 构造popupWindow的时候，就给它设置了抢焦点的能力。


        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());

        // 指定动画的样式，需要在style.xml里面新增<style name = "pwStyle">
        popupWindow.setAnimationStyle(R.style.pwStyle);

        
       popupWindow.showAtLocation(view, Gravity.BOTTOM,0,0); // 从顶部出来一个动画
    }
}
