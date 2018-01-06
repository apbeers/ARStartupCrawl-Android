package com.arcu.arstartupcrawlnative;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by shawn on 1/5/2018.
 */

public class NotificationListViewAdapter extends ArrayAdapter<PushNotification> {
    public NotificationListViewAdapter(@NonNull Context context, List<PushNotification> trips) {
        super(context, R.layout.custom_notification_row, trips);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater notificationInflater = LayoutInflater.from(getContext());
        View customView = notificationInflater.inflate(R.layout.custom_notification_row, parent, false);

        PushNotification singleNotification = getItem(position);

        TextView titleText = (TextView) customView.findViewById(R.id.titleTextView);
        TextView bodyText = (TextView) customView.findViewById(R.id.bodyTextView);
        TextView dateText = (TextView) customView.findViewById(R.id.dateTextView);

        titleText.setText(singleNotification.getTitle());
        bodyText.setText(singleNotification.getBody());
        dateText.setText(singleNotification.getDatetime());

        return customView;
    }
}
