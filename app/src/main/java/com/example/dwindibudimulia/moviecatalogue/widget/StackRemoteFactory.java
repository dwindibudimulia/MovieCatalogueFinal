package com.example.dwindibudimulia.moviecatalogue.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.dwindibudimulia.moviecatalogue.R;
import com.example.dwindibudimulia.moviecatalogue.db.WidgetHelper;
import com.example.dwindibudimulia.moviecatalogue.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class StackRemoteFactory implements RemoteViewsService.RemoteViewsFactory {
    private final List<Bitmap> widgetItems = new ArrayList<>();
    private final Context mContext;
    private WidgetHelper helper;

    StackRemoteFactory(Context context) {
        mContext = context;
        helper = WidgetHelper.getInstance(mContext);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        helper.open();
        ArrayList<Movie> movies = helper.getAllMovie();
        System.out.println("size = " + movies.size());
        try {
            for (Movie movie : movies) {
                widgetItems.add(BitmapFactory.decodeStream(new java.net.URL(movie.getBackDrop()).openStream()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return widgetItems.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);
        remoteViews.setImageViewBitmap(R.id.img_widget, widgetItems.get(i));


        Bundle bundle = new Bundle();
        bundle.putInt(FavoriteWidget.EXTRA_ITEM, i);
        Intent intent = new Intent();
        intent.putExtras(bundle);
        remoteViews.setOnClickFillInIntent(R.id.img_widget, intent);
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
