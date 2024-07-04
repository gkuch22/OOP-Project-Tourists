package javaFiles;

import java.util.Date;

public class MessageImpl implements Message{

    private String type;
    private int from_id;
    private int to_id;
    private String context;
    private Date date;


    MessageImpl(String type, int from_id, int to_id, String context, Date date){
        this.type = type;
        this.from_id = from_id;
        this.to_id = to_id;
        this.context = context;
        this.date = date;
    }


    @Override
    public String getType() {
        return type;
    }

    @Override
    public int getFromId() {
        return from_id;
    }

    @Override
    public int getToId() {
        return to_id;
    }

    @Override
    public String getContext() {
        return context;
    }

    @Override
    public Date getDate() {
        return date;
    }

}
