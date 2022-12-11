package rs.ac.uns.ftn.informatika.jpa.dummy;

import rs.ac.uns.ftn.informatika.jpa.model.Document;
import rs.ac.uns.ftn.informatika.jpa.model.Driver;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class DocumentDummy {
    public static AtomicLong counter;
    public ConcurrentHashMap<Long, Document> documents;

    public DocumentDummy() {
        this.counter = new AtomicLong();
        this.documents = new ConcurrentHashMap<Long, Document>();
    }
}
