package ai.remi.comm.util.http;

import okhttp3.Call;
import okhttp3.Response;
import java.io.IOException;

public interface OkHttp3Callback{
    void success(Call call, Response response) throws IOException;

    void failed(Call call, IOException e);
}

