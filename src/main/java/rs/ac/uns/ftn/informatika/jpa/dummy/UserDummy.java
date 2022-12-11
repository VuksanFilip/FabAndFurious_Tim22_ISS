package rs.ac.uns.ftn.informatika.jpa.dummy;

import rs.ac.uns.ftn.informatika.jpa.model.Message;
import rs.ac.uns.ftn.informatika.jpa.model.User;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class UserDummy {

    public static AtomicLong counter;
    public ConcurrentHashMap<Long, User> users;

    public UserDummy() {
        this.counter = new AtomicLong();
        this.users = new ConcurrentHashMap<Long, User>();
        this.users.put(Long.valueOf(1), new User(Long.valueOf(1), "Pera", "Peric", "picture1", "0655555555", "pera.peric@email.com", "address1", "pera123"));
        this.users.put(Long.valueOf(2), new User(Long.valueOf(2), "Stefan", "Stefanovic", "picture2", "0654444444", "stefan.stefanovic@email.com", "address2", "stefan123"));
    }

    public ArrayList<Message> getMessages(Long id){
        ArrayList<Message> messages = new ArrayList<Message>();
        MessageDummy messageDummy = new MessageDummy();
        for(Long messageId : messageDummy.messages.keySet()){
            if(messageDummy.messages.get(messageId).getReciever().getId() == id || messageDummy.messages.get(messageId).getSender().getId() == id){
                messages.add(messageDummy.messages.get(messageId));
            }
        }
        return messages;
    }
}
