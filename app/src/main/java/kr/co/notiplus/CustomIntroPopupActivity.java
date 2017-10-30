package kr.co.notiplus;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import kr.co.notiplus.lib.Notiplus;
import kr.co.notiplus.lib.util.NotiplusIntroPopupBuilder;

public class CustomIntroPopupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_intro_popup);

        findViewById(R.id.bt_show_intro_popup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotiplusIntroPopupBuilder introPopupBuilder = Notiplus.createIntroPopupBuilder(CustomIntroPopupActivity.this);
                introPopupBuilder
                        .setTitle("알림바 설정")
//                        .setImageResource(0)
                        .setSubTitle("알림바를 시작합니다!")
                        .setMessage("실시간 인기 컨텐츠와\n다양한 혜택을 만나보세요!")
                        .setPositiveButton("닫기")
                        .setPositiveButtonColor(Color.parseColor("#F94E51"))
                        .setPositiveButtonListener(new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.i("Notiplus", "clicked positive button");
                            }
                        });

                // show 함수 호출과 동시에 dialog 반환
                Dialog dialog = introPopupBuilder.show();
            }
        });
    }

}