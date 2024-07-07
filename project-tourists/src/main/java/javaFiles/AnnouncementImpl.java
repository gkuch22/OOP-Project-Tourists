package javaFiles;

import java.util.Date;

public class AnnouncementImpl implements Announcement{

    int post_id;
    String post_name;
    String post_text;
    int user_id;
    Date date;

    public AnnouncementImpl(int post_id,String post_name,String post_text, int user_id, Date date){
        this.post_id = post_id;
        this.post_name = post_name;
        this.post_text = post_text;
        this.user_id = user_id;
        this.date = date;
    }

    @Override
    public int get_post_id() {
        return this.post_id;
    }

    @Override
    public void set_post_id(int id) {
        this.post_id = id;
    }

    public String get_post_name() {
        return this.post_name;
    }

    @Override
    public void set_post_name(String name) {
        this.post_name = name;
    }

    @Override
    public String get_post_text() {
        return this.post_text;
    }

    @Override
    public void set_post_text(String text) {
        this.post_text = text;
    }

    @Override
    public int get_user_id() {
        return this.user_id;
    }

    @Override
    public void set_user_id(int id) {
        this.user_id = id;
    }

    @Override
    public Date get_date() {
        return this.date;
    }

    @Override
    public void set_date(Date date) {
        this.date = date;
    }
}
