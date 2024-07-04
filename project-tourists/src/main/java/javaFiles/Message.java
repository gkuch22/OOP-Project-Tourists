package javaFiles;

import java.util.Date;

public interface Message {

    String getType();
    int getFromId();
    int getToId();
    String getContext();
    Date getDate();


}
