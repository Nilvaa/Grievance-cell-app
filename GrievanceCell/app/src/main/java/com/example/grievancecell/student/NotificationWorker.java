package com.example.grievancecell.student;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.grievancecell.R;
import com.example.grievancecell.student.MyGrievanceFragment;

public class NotificationWorker extends Worker {

    public NotificationWorker(
            @NonNull Context context,
            @NonNull WorkerParameters params) {
        super(context, params);
    }

    @NonNull
    @Override
    public Result doWork() {
        // Retrieve data from input data
        String grievanceId = getInputData().getString("grievance_id");
        String grievanceDescription = getInputData().getString("grievance_description");

        if (grievanceId != null && grievanceDescription != null) {
            showNotification(getApplicationContext(), grievanceId, grievanceDescription);
        }
        return Result.success();
    }

    private void showNotification(Context context, String grievanceId, String grievanceDescription) {
        // Use NotificationCompat to build the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, MyGrievanceFragment.CHANNEL_ID)
                .setSmallIcon(R.drawable.grie)
                .setContentTitle("Grievance Notification")
                .setContentText("Your grievance " + grievanceDescription + " haven't replied yet. Kindly forward to a higher faculty")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        // Show the notification
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        if (ActivityCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        notificationManager.notify(Integer.parseInt(grievanceId), builder.build());
    }
}
