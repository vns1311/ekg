import edu.stanford.nlp.ie.util.RelationTriple;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.naturalli.NaturalLogicAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

import java.util.Collection;
import java.util.Properties;

public class BuildKG {
    public static String text = "The quick brown fox jumped over the lazy dog";

    public static void main(String[] args) {
        Properties prop = new Properties();
        prop.setProperty("annotators", "tokenize,ssplit,pos,lemma,depparse,natlog,openie");
        prop.setProperty("openie.format", "ollie");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(prop);
        Annotation doc = new Annotation(text);
        pipeline.annotate(doc);
        for (CoreMap sentence : doc.get(CoreAnnotations.SentencesAnnotation.class)) {
            Collection<RelationTriple> triples = sentence.get(NaturalLogicAnnotations.RelationTriplesAnnotation.class);
            for (RelationTriple triple : triples) {
                System.out.println(triple.confidence + "|" + triple.subjectGloss() + "|" + triple.relationGloss() + "|" + triple.objectGloss());
            }
        }
    }
}
