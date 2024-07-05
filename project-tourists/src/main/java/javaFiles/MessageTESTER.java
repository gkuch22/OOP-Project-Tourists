package javaFiles;

import junit.framework.TestCase;

import java.util.Date;

public class MessageTESTER extends TestCase {

    public void testMessage(){
        String type = "textmessage";
        int fromId = 1;
        int toId = 2;
        String context = "temp";
        Date date = new Date();
        Message message = new MessageImpl(type, fromId, toId, context, date);

        assertEquals(type, message.getType());
        assertEquals(fromId, message.getFromId());
        assertEquals(toId, message.getToId());
        assertEquals(context, message.getContext());
        assertEquals(date, message.getDate());
    }

}
