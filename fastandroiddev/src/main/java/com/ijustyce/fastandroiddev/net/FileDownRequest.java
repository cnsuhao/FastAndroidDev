package com.ijustyce.fastandroiddev.net;

import android.net.Uri;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Created by yangchun on 16/5/30.
 */
public class FileDownRequest extends Request<byte[]> {

    private ProcessListener downListener;
    private String savePath;
    public FileDownRequest(String url, ProcessListener downListener, String savePath) {
        super(Method.GET, url, null);
        this.savePath = savePath;
        this.downListener = downListener;
        setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, 20,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    }

    @Override
    protected void deliverResponse(byte[] response) {

        try {
            if (response != null) {
                try {
                    InputStream input = new ByteArrayInputStream(response);
                    File file = new File(savePath);
                    BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(file));
                    byte data[] = new byte[1024];
                    int count;
                    while ((count = input.read(data)) != -1) {
                        output.write(data, 0, count);
                    }
                    output.flush();
                    output.close();
                    input.close();
                    downListener.onDownload(savePath, Uri.fromFile(file).toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Response<byte[]> parseNetworkResponse(NetworkResponse response) {

        //   responseHeaders = response.headers;
        return Response.success(response.data, HttpHeaderParser.parseCacheHeaders(response));
    }
}
