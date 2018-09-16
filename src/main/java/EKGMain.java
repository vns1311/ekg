import edu.stanford.nlp.ie.util.RelationTriple;
import edu.stanford.nlp.io.IOUtils;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.naturalli.NaturalLogicAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import edu.stanford.nlp.util.Triple;

import java.io.IOException;
import java.util.Collection;
import java.util.Properties;

public class EKGMain {
    public static void main(String[] args) throws IOException {
        DatabaseUtility kBase = new DatabaseUtility("http://ekg.com/testNS/");
        String fileName = "/home/shyam/consciousness.txt";
        String content = IOUtils.slurpFile(fileName);
        Properties prop = new Properties();
        prop.setProperty("annotators", "tokenize,ssplit,pos,lemma,depparse,natlog,ner,coref,openie");
        prop.setProperty("openie.format", "default");
        prop.setProperty("openie.resolve_coref", "true");

        StanfordCoreNLP pipeline = new StanfordCoreNLP(prop);
        Annotation annotate = new Annotation(content);
        pipeline.annotate(annotate);
        for (CoreMap sentence : annotate.get(CoreAnnotations.SentencesAnnotation.class)) {
            Collection<RelationTriple> triples = sentence.get(NaturalLogicAnnotations.RelationTriplesAnnotation.class);
            for (RelationTriple triple : triples) {
                Triple triplet = new Triple(triple.subjectLemmaGloss(), triple.relationLemmaGloss(), triple.objectLemmaGloss());
                kBase.generateResource(triplet);
            }
        }
        kBase.showDatabase();
    }
}
