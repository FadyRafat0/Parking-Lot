package com.example.parking;

import com.example.parking.json.JSONUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;

public class Feedback
{
    private double rate;
    private String feedbackMessage;
    private final int ownerID;
    private final int ReservationID;

    public Feedback(int ownerID, int ReservationID,double rate, String feedbackMessage)
    {
        this.ownerID=ownerID;
        this.ReservationID=ReservationID;
        this.rate=rate;
        this.feedbackMessage=feedbackMessage;
    }

    public double getRate() {
        return rate;
    }
    public String getFeedbackMessage() {
        return feedbackMessage;
    }
    public int getOwenerID() {
        return ownerID;
    }
    public int getReservationID() {
        return ReservationID;
    }

    // Save the feedback to feedbacks.json
    public static void saveFeedbackToFile() {
        // Load existing feedbacks
        ArrayList<Feedback> feedbacks = SystemManager.getAllFeedBacks();
        // Save the updated list of feedbacks
        JSONUtils.saveToFile(feedbacks, "feedbacks.json");
    }
    // Load all feedbacks from feedbacks.json
    public static ArrayList<Feedback> loadFeedbacks() {
        return JSONUtils.loadFromFile("feedbacks.json", new com.google.gson.reflect.TypeToken<ArrayList<Feedback>>() {}.getType());
    }
}
