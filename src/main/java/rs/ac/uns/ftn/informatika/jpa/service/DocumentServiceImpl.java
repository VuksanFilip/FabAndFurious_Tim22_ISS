package rs.ac.uns.ftn.informatika.jpa.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.informatika.jpa.model.Document;
import rs.ac.uns.ftn.informatika.jpa.repository.DocumentRepository;
import rs.ac.uns.ftn.informatika.jpa.service.interfaces.IDocumentService;

import java.util.List;
import java.util.Optional;

@Service
public class DocumentServiceImpl implements IDocumentService {

    private DocumentRepository documentRepository;

    @Autowired
    public DocumentServiceImpl(DocumentRepository documentRepository) {
        this.documentRepository = documentRepository;
    }

    public List<Document> getAll() {
        return (List<Document>) this.documentRepository.findAll();
    }

    @Override
    public void deleteById(String id) {
        this.documentRepository.deleteById(Long.parseLong(id));
    }

    @Override
    public Optional<Document> getDocument(String id) {
        return  this.documentRepository.findById(Long.parseLong(id));
    }

    public void add(Document document) {
        this.documentRepository.save(document);
    }
}
