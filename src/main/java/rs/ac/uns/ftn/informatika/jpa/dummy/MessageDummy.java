package rs.ac.uns.ftn.informatika.jpa.dummy;

import rs.ac.uns.ftn.informatika.jpa.model.Message;
import rs.ac.uns.ftn.informatika.jpa.model.MessageType;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class MessageDummy {

    public static AtomicLong counter;
    public ConcurrentHashMap<Long, Message> messages;

    public MessageDummy() {
        this.counter = new AtomicLong();
        this.messages = new ConcurrentHashMap<Long, Message>();
        UserDummy userDummy = new UserDummy();
        this.messages.put(Long.valueOf(1), new Message(Long.valueOf(1), userDummy.users.get(Long.valueOf(1)), userDummy.users.get(Long.valueOf(2)),"poruka1", new Date(2022, 12, 5, 22, 50), MessageType.RIDE,1));
        this.messages.put(Long.valueOf(2), new Message(Long.valueOf(2), userDummy.users.get(Long.valueOf(1)), userDummy.users.get(Long.valueOf(2)),"poruka2", new Date(2022, 12, 5, 22, 50), MessageType.PANIC,1));
        this.messages.put(Long.valueOf(3), new Message(Long.valueOf(3), userDummy.users.get(Long.valueOf(2)),userDummy.users.get(Long.valueOf(1)),"poruka3", new Date(2022, 12, 5, 22, 50), MessageType.RIDE,1));

    }
}
