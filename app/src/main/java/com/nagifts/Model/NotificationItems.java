package com.nagifts.Model;

public class NotificationItems {

    public String location;
    public String fullname;
    public String DelBoy_Fname;
    public String DeliveryDate;
    public String listid;
    public String relation;
    public String designation;
    public String status;

    //public String time;

    public NotificationItems() {
    }

    public NotificationItems(String location,String fullname, String DelBoy_Fname  ,String DeliveryDate ,String listId ,String relation, String designation, String status) {

        this.location = location;
        this.DelBoy_Fname = DelBoy_Fname;
        this.fullname = fullname;
        this.DeliveryDate = DeliveryDate;
        this.listid = listId;
        this.relation = relation;
        this.designation = designation;

        this.status = status;

    }



    public String getlocation() {
        return location;
    }

    public void setlocation(String location) {
        this.location = location;
    }

    public String getfullnames() {
        return fullname;
    }

    public void setfullname(String fullname) {
        this.fullname = fullname;
    }

    public String getDelBoy_Fname() {
        return DelBoy_Fname;
    }

    public void setDelBoy_Fname(String DelBoy_Fname) {
        this.DelBoy_Fname = DelBoy_Fname;
    }

    public String getDeliveryDate() {
        return DeliveryDate;
    }
    public void setDeliveryDate(String DeliveryDate) {
        this.DeliveryDate = DeliveryDate;
    }

    public String getListid() {
        return listid;
    }
    public void setListid(String listid) {
        this.listid = listid;
    }

    public String getrelaton() {
        return relation;
    }
    public void setrelation(String relation) {
        this.relation = relation;
    }



    public String getdesignation() {
        return designation;
    }
    public void setdesignation(String designation) {
        this.designation = designation;
    }

    public String getstatus() {
        return status;
    }
    public void setstatus(String status) {
        this.status = status;
    }

   // public String gettime() {
//        return time;
//    }
//    public void settime(String time) {
//        this.time = time;
//    }


}
