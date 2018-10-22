package com.enstrapp.fieldtekpro.notifications;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Attachment_Download_SER
{

    @SerializedName("d")
    @Expose
    private D d;
    public D getD()
    {
        return d;
    }
    public void setD(D d)
    {
        this.d = d;
    }


    public class D
    {
        @SerializedName("Content")
        @Expose
        private String Content;

        public String getContent() {
            return Content;
        }

        public void setContent(String content) {
            Content = content;
        }
    }



}