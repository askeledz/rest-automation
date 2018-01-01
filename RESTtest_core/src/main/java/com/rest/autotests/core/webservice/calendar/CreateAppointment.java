package com.rest.autotests.core.webservice.calendar;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.rest.autotests.core.objects.Adress;
import com.rest.autotests.core.objects.BasicObject;

import static org.testng.Assert.assertEquals;

/**
 * Created by Andrej Skeledzija 2017
 */
public class CreateAppointment extends BasicObject {


    private int teamId;    //"teamId":"{{teamId}}",
    private String date;   ///        "date":"1455986092121",
    private Adress address; //        "address":{}
    private String start; //      "start":"1455986092121",
    private Long end; //       "end":null,
    private boolean  isFullTime; //      "isFullTime":false,
    private String  repeatMode; //      "repeatMode":"NEVER",
    private boolean  isRecurring;    //   "isRecurring":false,
    private Long reminder;      // "reminder":null,
    private Long meetAtDate;    // "meetAtDate":null,
    private Long meetingPoint;    //  "meetingPoint":null,
    private String eventType;  //      "eventType":"TRAINING",
    private int teamUserId;   //    "teamUserId":"{{teamUserId}}",
    private String summary;     //  "summary":"opis"

    private boolean success;
    private int appointmentId;
    private long recurringEnd;
    private String message;

    public CreateAppointment(int teamId, String date, Adress address, String start, Long end, boolean isFullTime, String repeatMode, boolean isRecurring, Long reminder, Long meetAtDate, Long meetingPoint, String eventType, int teamUserId, String summary) {
        this.teamId = teamId;
        this.date = date;
        this.address = address;
        this.start = start;
        this.end = end;
        this.isFullTime = isFullTime;
        this.repeatMode = repeatMode;
        this.isRecurring = isRecurring;
        this.reminder = reminder;
        this.meetAtDate = meetAtDate;
        this.meetingPoint = meetingPoint;
        this.eventType = eventType;
        this.teamUserId = teamUserId;
        this.summary = summary;
        this.endpoint = "/calendar/createAppointment";
    }


    public CreateAppointment() {
        this.endpoint = "/calendar/createAppoint";

    }




    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Adress getAddress() {
        return address;
    }

    public void setAddress(Adress address) {
        this.address = address;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public Long getEnd() {
        return end;
    }

    public void setEnd(Long end) {
        this.end = end;
    }

    public boolean isFullTime() {
        return isFullTime;
    }

    public void setFullTime(boolean fullTime) {
        isFullTime = fullTime;
    }

    public String getRepeatMode() {
        return repeatMode;
    }

    public void setRepeatMode(String repeatMode) {
        this.repeatMode = repeatMode;
    }

    public boolean isRecurring() {
        return isRecurring;
    }

    public void setRecurring(boolean recurring) {
        isRecurring = recurring;
    }

    public Long getReminder() {
        return reminder;
    }

    public void setReminder(Long reminder) {
        this.reminder = reminder;
    }

    public Long getMeetAtDate() {
        return meetAtDate;
    }

    public void setMeetAtDate(Long meetAtDate) {
        this.meetAtDate = meetAtDate;
    }

    public Long getMeetingPoint() {
        return meetingPoint;
    }

    public void setMeetingPoint(Long meetingPoint) {
        this.meetingPoint = meetingPoint;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public int getTeamUserId() {
        return teamUserId;
    }

    public void setTeamUserId(int teamUserId) {
        this.teamUserId = teamUserId;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public long getRecurringEnd() {
        return recurringEnd;
    }

    public void setRecurringEnd(long recurringEnd) {
        this.recurringEnd = recurringEnd;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void compareTo(CreateAppointment ca) {
        assertEquals(this.isSuccess(), true, "Success");
        assertEquals(this.getAppointmentId(), ca.getAppointmentId(), "AppointmentIds are differ");
        assertEquals(this.getMessage(), ca.getMessage(), "Messages are differ");

    }

    public void compareTo(Boolean success, int appointmentId,  String message){
        assertEquals(this.isSuccess(), true, "Success");
        assertEquals(this.getAppointmentId(), appointmentId, "AppointmentIds are differ");
        assertEquals(this.getMessage(), message, "Messages are differ");
    }

    @Override
    public String toString() {
        return "CreateAppointment{" +
                "teamId=" + teamId +
                ", date='" + date +  '\'' +        //String
                ", address=" + address.toString() +
                ", start='" + start + '\'' +        //String
                ", end=" + end +
                ", isFullTime=" + isFullTime +
                ", repeatMode='" + repeatMode + '\'' +
                ", isRecurring=" + isRecurring +
                ", reminder=" + reminder +
                ", meetAtDate=" + meetAtDate +
                ", meetingPoint=" + meetingPoint +
                ", eventType='" + eventType + '\'' +
                ", teamUserId='" + teamUserId + '\'' +
                ", summary='" + summary + '\'' +
                '}';
    }


    @Override
    public String toJson(String... omittedFields) {
        Gson gson = new Gson();
        JsonObject jsonObject = gson.toJsonTree(this).getAsJsonObject();
        for (String str : omittedFields) {
            jsonObject.remove(str);
        }
        jsonObject.remove("endpoint");
        jsonObject.remove("id");
        return jsonObject.toString();
    }
}
