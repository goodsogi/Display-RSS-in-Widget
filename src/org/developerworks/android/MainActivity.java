package org.developerworks.android;




import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RemoteViews;


public class MainActivity extends AppWidgetProvider {

	private List<Message> messages;
	List<String> titles;

	@Override
	public void onEnabled(Context context) {
		super.onEnabled(context);
		Log.i("rsstest", "in the onEnabled, MainActivity");
	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		Log.i("rsstest", "in the onUpdate, MainActivity");
		loadFeed(ParserType.ANDROID_SAX);
		

		for (int i = 0; i < appWidgetIds.length; i++) {

			RemoteViews widget = new RemoteViews(context.getPackageName(),
					R.layout.widget);
			widget.setTextViewText(R.id.text1, titles.get(0));
			widget.setTextViewText(R.id.text2, titles.get(1));
			widget.setTextViewText(R.id.text2, titles.get(2));
			widget.setTextViewText(R.id.text2, titles.get(3));

			appWidgetManager.updateAppWidget(appWidgetIds[i], widget);
		}

		super.onUpdate(context, appWidgetManager, appWidgetIds);

	}

	private void loadFeed(ParserType type) {
		try {
			
			Log.i("rsstest", "in the loadFeed");
			
			Log.i("AndroidNews", "ParserType=" + type.name());
			FeedParser parser = FeedParserFactory.getParser(type);
			long start = System.currentTimeMillis();
			 messages = parser.parse();
			long duration = System.currentTimeMillis() - start;

			titles = new ArrayList<String>(messages.size());
			for (Message msg : messages) {
				Log.i("rsstest", "for (Message msg : messages)");
				
				titles.add(msg.getTitle());
				
				
			}

		} catch (Throwable t) {
			Log.e("AndroidNews", t.getMessage(), t);
		}
	}

}
