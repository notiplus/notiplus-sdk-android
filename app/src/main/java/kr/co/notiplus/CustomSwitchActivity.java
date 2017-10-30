package kr.co.notiplus;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.widget.CompoundButton;

import kr.co.notiplus.lib.Notiplus;
import kr.co.notiplus.lib.listener.OnCampaignEnableChangedListener;
import kr.co.notiplus.lib.listener.OnCampaignEnableSyncListener;

public class CustomSwitchActivity extends AppCompatActivity {

    SwitchCompat switchCompat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_switch);
        switchCompat = (SwitchCompat) findViewById(R.id.switch_enable);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // 캠페인 활성화 상태를 서버와 동기화하여 적용
        Notiplus.syncCampaignEnable(this, new OnCampaignEnableSyncListener() {

            @Override
            public void onEnableSyncError() {
                super.onEnableSyncError();
                // network error
            }

            @Override
            public void onEnableChanged(boolean isEnable) {
                super.onEnableChanged(isEnable);
                switchCompat.setChecked(isEnable);
                switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, final boolean isChecked) {
//                        checkChangedDefault(isChecked);
                        checkChangedSync(isChecked);
//                        checkChangedCustom(isChecked);
                    }
                });
            }
        });
    }

    /**
     * 캠페인 활성화 상태를 제어할 수 있습니다.
     * 시스템 설정으로 인해 알림이 차단된 경우에는 노티플러스에서 제공하는 해제 유도 팝업이 노출됩니다.
     * 사용자가 알림 차단설정을 해제하지 않는 경우가 발생 할 수 있습니다.
     * 유효한 설정값 노출을 위해서는 isCampaignEnable() 메소드로 UI 생명주기에 맞춰 값을 반영해주십시오.
     *
     * @param isChecked
     */
    void checkChangedDefault(boolean isChecked) {
        Notiplus.setCampaignEnable(CustomSwitchActivity.this, isChecked);
    }

    /**
     * [권장]
     * <p>
     * 캠페인 활성화 상태를 제어할 수 있습니다.
     * 시스템 설정으로 인해 알림이 차단된 경우에는 노티플러스에서 제공하는 해제 유도 팝업이 노출됩니다.
     * 사용자가 알림 차단설정을 변경하는 경우에 boolean 값을 onEnableChanged() 함수를 통해 전달합니다.
     *
     * @param isChecked
     */
    void checkChangedSync(boolean isChecked) {
        Notiplus.setCampaignEnable(CustomSwitchActivity.this, isChecked, new OnCampaignEnableChangedListener() {

            @Override
            public void onEnableChanged(boolean isEnable) {
                super.onEnableChanged(isEnable);
                switchCompat.setChecked(isEnable);
            }
        });
    }

    /**
     * 캠페인 활성화 상태를 제어할 수 있습니다.
     * 상태값 저장 실패시 토스트와 시스템 설정으로 인해 알림이 차단된 경우 노출되는 해제 유도 팝업 구현부를 커스터마이징 할 수 있습니다.
     * 사용자가 알림 차단설정을 변경하는 경우에 boolean 값을 onEnableChanged() 함수를 통해 전달합니다.
     *
     * @param isChecked
     */
    void checkChangedCustom(boolean isChecked) {
        Notiplus.setCampaignEnable(CustomSwitchActivity.this, isChecked, new OnCampaignEnableChangedListener() {

            @Override
            public void needAllowNotification() {

                // 노티플러스 자체 팝업 구현부 주석
                // super.needAllowNotification();

                if (!((Activity) context).isFinishing()) {
                    new AlertDialog.Builder(context)
                            .setMessage("알림 기능이 차단되어있습니다.")
                            .setPositiveButton("설정", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    // 노티플러스에서 제공하는 설정화면 이동 함수
                                    Notiplus.goNotificationSettings(context);
                                }
                            }).setNegativeButton("취소", null)
                            .create().show();
                }
            }

            @Override
            public void onEnableChangedError() {

                // 캠페인 활성화 상태값 변경 실패시 토스트 구현부
                super.onEnableChangedError();
            }

            @Override
            public void onEnableChanged(boolean isEnable) {
                super.onEnableChanged(isEnable);
                switchCompat.setChecked(isEnable);
            }
        });
    }

}