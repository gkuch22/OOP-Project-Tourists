package javaFiles;

import java.util.Comparator;
import java.util.Date;

public class HistoryImpl implements History{
    String name;
    String description;
    String type;
    Date date;

    public HistoryImpl(String name, String description, String type, Date date){
        this.name = name;
        this.description = description;
        this.type = type;
        this.date = date;
    }

    @Override
    public Date get_history_date() {
        return this.date;
    }

    @Override
    public void set_history_date(Date date) {
        this.date = date;
    }

    @Override
    public String get_history_name() {
        return this.name;
    }

    @Override
    public void set_history_name(String name) {
        this.name = name;
    }

    @Override
    public String get_history_description() {
        return this.description;
    }

    @Override
    public void set_history_description(String description) {
        this.description = description;
    }

    @Override
    public String get_history_type() {
        return this.type;
    }

    @Override
    public void set_history_type() {
        this.type = type;
    }

    public class Historycomparator implements Comparator<History>{
        @Override
        public int compare(History o1, History o2) {
            return o1.get_history_date().compareTo(o2.get_history_date());
        }
    }
}
