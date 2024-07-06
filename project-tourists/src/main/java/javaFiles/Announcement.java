package javaFiles;

import java.util.Date;

public interface Announcement {
    public int get_post_id();
    public void set_post_id(int id);
    public String get_post_text();
    public void  set_post_text(String text);
    public int get_user_id();
    public void set_user_id(int id);
    public Date get_date();
    public void set_date(Date date);
}
