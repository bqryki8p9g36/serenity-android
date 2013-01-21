/**
 * The MIT License (MIT)
 * Copyright (c) 2012 David Carver
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 * OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF
 * OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.github.kingargyle.plexappclient;

import java.io.IOException;
import java.util.WeakHashMap;

import com.github.kingargyle.plexapp.PlexappFactory;
import com.github.kingargyle.plexapp.config.IConfiguration;
import com.github.kingargyle.plexappclient.core.ServerConfig;
import com.github.kingargyle.plexappclient.core.imagecache.PlexAppImageManager;
import com.novoda.imageloader.core.LoaderSettings;
import com.novoda.imageloader.core.cache.LruBitmapCache;

import android.app.Application;
import android.graphics.drawable.BitmapDrawable;
import android.os.StrictMode;

/**
 * Global manager for the Serenity application
 * 
 * @author dcarver
 *
 */
public class SerenityApplication extends Application {
	
	private static PlexAppImageManager imageManager;
	private static PlexappFactory plexFactory;
	private WeakHashMap<String, BitmapDrawable> backgroundImageCache;

	
	@Override
	public void onCreate() {
		super.onCreate();
		
		// Yes I know this is bad, really need to make network activity happen
		// in AsyncTask.
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
		
		
		LoaderSettings settings = new LoaderSettings.SettingsBuilder()
	      .withDisconnectOnEveryCall(true).withAsyncTasks(true).withUpsampling(true).build(this);
		
	    imageManager = new PlexAppImageManager(this, settings);
	    
	    try {
	    	IConfiguration config = ServerConfig.getInstance(this);
	    	plexFactory = PlexappFactory.getInstance(config);
	    	
			PlexImageCacheService service = new PlexImageCacheService(5);
			service.execute();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static PlexAppImageManager getImageManager() {
	    return imageManager;
	}
	
	public static PlexappFactory getPlexFactory() {
		return plexFactory;
	}
	

}