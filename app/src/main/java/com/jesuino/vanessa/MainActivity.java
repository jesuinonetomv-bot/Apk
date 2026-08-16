package com.jesuino.vanessa;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import androidx.annotation.NonNull;

public class MainActivity extends Activity {
    private WebView webView;
    private static final int PERM = 10;

    @Override public void onCreate(Bundle b) {
        super.onCreate(b);
        webView = new WebView(this);
        setContentView(webView);
        WebSettings s = webView.getSettings();
        s.setJavaScriptEnabled(true);
        s.setDomStorageEnabled(true);
        s.setMediaPlaybackRequiresUserGesture(false);
        s.setAllowFileAccess(true);
        s.setAllowContentAccess(true);
        webView.setWebChromeClient(new WebChromeClient(){
            @Override public void onPermissionRequest(PermissionRequest request){
                runOnUiThread(() -> request.grant(request.getResources()));
            }
        });
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
            checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO}, PERM);
        } else load();
    }
    private void load(){ webView.loadUrl("file:///android_asset/index.html"); }
    @Override public void onRequestPermissionsResult(int r,@NonNull String[] p,@NonNull int[] g){
        super.onRequestPermissionsResult(r,p,g); if(r==PERM) load();
    }
}
