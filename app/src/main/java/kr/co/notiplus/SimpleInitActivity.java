package kr.co.notiplus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import kr.co.notiplus.lib.Notiplus;

public class SimpleInitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_init);

        findViewById(R.id.bt_more).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Notiplus.goMoreActivity(SimpleInitActivity.this);
            }
        });

        findViewById(R.id.bt_settings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Notiplus.goCampaignSettingActivity(SimpleInitActivity.this);
            }
        });

        findViewById(R.id.bt_custom_init_popup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SimpleInitActivity.this, CustomIntroPopupActivity.class));
            }
        });

        findViewById(R.id.bt_custom_switch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SimpleInitActivity.this, CustomSwitchActivity.class));
            }
        });


        // 캠페인 활성화를 위한 상태값 초기화 이후 자동으로 알림바를 활성화합니다.
        Notiplus.init(this);    // == Notiplus.init(this, true);

        // 기본 제공되는 노티플러스 안내 팝업 노출
        if (!Notiplus.isIntroPopupShown(this)) {
            Notiplus.showIntroPopup(this, true);
        }

    }

}