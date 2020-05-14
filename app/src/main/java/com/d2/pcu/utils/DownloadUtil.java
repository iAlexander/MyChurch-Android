/*
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.d2.pcu.utils;

import android.content.Context;
import android.os.Environment;

import com.d2.pcu.R;
import com.google.android.exoplayer2.database.ExoDatabaseProvider;
import com.google.android.exoplayer2.offline.DownloadManager;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.upstream.cache.Cache;
import com.google.android.exoplayer2.upstream.cache.NoOpCacheEvictor;
import com.google.android.exoplayer2.upstream.cache.SimpleCache;
import com.google.android.exoplayer2.util.Util;

import java.io.File;

public class DownloadUtil {

    private static DownloadManager downloadManager;
    private static Cache downloadCache;

    public DownloadUtil() {
    }

    public static void init(Context appContext) {
        File downloadDirectory = new File(appContext.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "prays");
        DefaultHttpDataSourceFactory httpDataSourceFactory = new DefaultHttpDataSourceFactory(
                Util.getUserAgent(appContext, appContext.getString(R.string.app_name)),
                25000, 25000, true
        );

        ExoDatabaseProvider databaseProvider = new ExoDatabaseProvider(appContext);
        httpDataSourceFactory.getDefaultRequestProperties().clear();
//        Map<String, String> map = new HashMap<>();
//        map.put("Accept-Language","en-US,en;q=0.9");
//        map.put("Accept-Encoding","gzip, deflate");
//        map.put("Accept", "audio/mpeg");
//        httpDataSourceFactory.getDefaultRequestProperties().clearAndSet(map);
//        httpDataSourceFactory.getDefaultRequestProperties().set("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9");

        if (!downloadDirectory.exists()) {
            downloadDirectory.mkdir();
        }
        downloadCache = new SimpleCache(
                downloadDirectory,
                new NoOpCacheEvictor(),
                databaseProvider);

        downloadManager = new DownloadManager(
                appContext,
                databaseProvider,
                downloadCache,
                httpDataSourceFactory);

        downloadManager.setMinRetryCount(2);
//        downloadManager.setRequirements(new Requirements(Requirements.NETWORK));

        downloadManager.setMaxParallelDownloads(2);

    }

    public static synchronized DownloadManager getDownloadManager() {
        return downloadManager;
    }

    public static synchronized Cache getDownloadCache() {
        return downloadCache;
    }
}
