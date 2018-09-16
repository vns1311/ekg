import edu.stanford.nlp.util.Triple;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.util.URIref;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.apache.jena.vocabulary.XSD;

public class DatabaseUtility {
    String nameSpace;
    Model model;

    public DatabaseUtility(String namespace) {
        nameSpace = namespace;
        model = ModelFactory.createDefaultModel();
        model.setNsPrefix("", nameSpace);
        model.setNsPrefix("rdf", RDF.getURI());
        model.setNsPrefix("xsd", XSD.getURI());
        model.setNsPrefix("rdfs", RDFS.getURI());
    }

    public void generateResource(Triple triplet) {
        Resource statement = model.createResource();
        Resource subject = model.createResource().addProperty(RDFS.label, (String) triplet.first);
        Resource relation = model.createResource(nameSpace + URIref.encode((String) triplet.second));
        Resource object = model.createResource().addProperty(RDFS.label, (String) triplet.third);

        statement.addProperty(RDF.subject, subject);
        statement.addProperty(RDF.predicate, relation);
        statement.addProperty(RDF.object, object);

    }

    public void showDatabase() {
        // Show the model in a few different formats.
        //RDFDataMgr.write( System.out, model, Lang.TTL );
        //RDFDataMgr.write( System.out, model, Lang.NTRIPLES );
        RDFDataMgr.write(System.out, model, Lang.RDFXML);
        //RDFDataMgr.write( System.out, model, Lang.TURTLE );
    }
}
