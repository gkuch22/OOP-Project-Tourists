package javaFiles;

import java.util.Date;

public interface History {
    public Date get_history_date();
    public void set_history_date(Date date);
    public String get_history_name();
    public void set_history_name(String name);
    public String get_history_description();
    public void set_history_description(String description);
    public String get_history_type();
    public void set_history_type();
}
