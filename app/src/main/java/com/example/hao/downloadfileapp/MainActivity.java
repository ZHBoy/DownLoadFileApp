package com.example.hao.downloadfileapp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.hao.downloadfileapp.download.DownloadProgressHandler;
import com.example.hao.downloadfileapp.download.ProgressHelper;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    Button download;
    TextView file_path;
    TextView file_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        download = findViewById(R.id.download);
        file_path = findViewById(R.id.file_path);
        file_name = findViewById(R.id.file_name);
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                download();
            }
        });
        PermissionRequest.verifyStoragePermissions(this);

        file_path.setText("http://static.tripbe.com/videofiles/20121214/9533522808.f4v.mp4");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void download(){
        //监听下载进度
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setProgressNumberFormat("%1d KB/%2d KB");
        dialog.setTitle("下载");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCancelable(false);
        dialog.show();

        Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://static.tripbe.com/");
        OkHttpClient.Builder builder = ProgressHelper.addProgress(null);
        DownloadApi retrofit = retrofitBuilder
                .client(builder.build())
                .build().create(DownloadApi.class);

        ProgressHelper.setProgressHandler(new DownloadProgressHandler() {
            @Override
            protected void onProgress(long bytesRead, long contentLength, boolean done) {
                dialog.setMax((int) (contentLength/1024));
                dialog.setProgress((int) (bytesRead/1024));

                if(done){
                    dialog.dismiss();
                }
            }
        });

        Call<ResponseBody> call = retrofit.downloadFile("http://static.tripbe.com/videofiles/20121214/9533522808.f4v.mp4");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                InputStream is = response.body().byteStream();

                File outputFile = new File(Environment.getExternalStoragePublicDirectory
                        (Environment.DIRECTORY_DOWNLOADS), "12345.mp4");
                try {
                    FileUtils.writeFile(is,outputFile);
                    file_name.setText(outputFile.getPath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("MainActivity",t.getMessage());
            }
        });

    }
}
