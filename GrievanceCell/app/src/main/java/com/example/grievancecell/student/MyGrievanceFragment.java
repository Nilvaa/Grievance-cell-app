package com.example.grievancecell.student;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.grievancecell.R;
import com.example.grievancecell.adaptor.Grievance_adaptor;
import com.example.grievancecell.common.SpeakNowModel;
import com.example.grievancecell.common.Utility;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyGrievanceFragment extends Fragment {

    public static final String CHANNEL_ID = "GrievanceNotificationChannel"; // Set your actual channel ID here
    private ListView listView;
    private List<SpeakNowModel> grievanceList;
    private Grievance_adaptor adapter;

    private static final int NOTIFICATION_ID = 1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_my_grievance, container, false);
        listView = root.findViewById(R.id.mygrie);
        grievanceList = new ArrayList<>();
        adapter = new Grievance_adaptor(requireActivity(), grievanceList);
        listView.setAdapter(adapter);
        fetchMyGrievances();
        return root;
    }

    private void fetchMyGrievances() {
        com.android.volley.RequestQueue queue = Volley.newRequestQueue(getContext());

        StringRequest request = new StringRequest(Request.Method.POST, Utility.url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (!response.trim().equals("failed")) {
                            Gson gson = new Gson();
                            SpeakNowModel[] grievances = gson.fromJson(response.trim(), SpeakNowModel[].class);
                            grievanceList.addAll(Arrays.asList(grievances));
                            adapter.notifyDataSetChanged();
                            scheduleNotifications();
                        } else {
                            Toast.makeText(getContext(), "No data found", Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                SharedPreferences sharedPreferences = requireContext().getSharedPreferences("profileprefer", Context.MODE_PRIVATE);
                String uid = sharedPreferences.getString("UID", "");
                Map<String, String> map = new HashMap<>();
                map.put("requestType", "MyGrievances");
                map.put("u_id", uid);
                return map;
            }
        };

        queue.add(request);
    }

    private void scheduleNotifications() {
        for (SpeakNowModel grievance : grievanceList) {
            if (grievance.getG_status().equals("sent")) {
                long elapsedTime = calculateElapsedTime(grievance.getG_date());
                if (elapsedTime > 5 * 24 * 60 * 60 * 1000) { // 5 days in milliseconds
                    // Fetch grievance description along with grievance ID
                    String grievanceDescription = grievance.getG_des();
                    scheduleNotification(grievance.getG_id(), grievanceDescription);
                }
            }

        }
    }



    private long calculateElapsedTime(String gDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date creationDate = sdf.parse(gDate);
            Date currentDate = new Date();
            return currentDate.getTime() - creationDate.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1; // Return -1 if there's an error in parsing or calculation
    }


    // Inside MyGrievanceFragment class

    private void scheduleNotification(String grievanceId, String grievanceDescription) {
        // Create a Data object with the grievance ID and description
        Data inputData = new Data.Builder()
                .putString("grievance_id", grievanceId)
                .putString("grievance_description", grievanceDescription)
                .build();

        // Create a OneTimeWorkRequest with the NotificationWorker class and input data
        OneTimeWorkRequest notificationWork = new OneTimeWorkRequest.Builder(NotificationWorker.class)
                .setInputData(inputData)
                .build();

        // Enqueue the work request
        WorkManager.getInstance(requireContext()).enqueue(notificationWork);
        Log.d("Notification", "Notification work scheduled for grievance ID: " + grievanceId);
    }


}
