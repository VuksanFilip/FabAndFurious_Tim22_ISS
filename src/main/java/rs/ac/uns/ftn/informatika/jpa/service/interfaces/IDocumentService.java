package rs.ac.uns.ftn.informatika.jpa.service.interfaces;

import rs.ac.uns.ftn.informatika.jpa.model.Document;

import java.util.List;
import java.util.Optional;

public interface IDocumentService {

    List<Document> getAll();

    Optional<Document> getDocument(String id);

    void add(Document document);

    void deleteById(String id);
}
