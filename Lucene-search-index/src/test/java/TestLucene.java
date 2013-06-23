import org.apache.lucene.queryparser.classic.ParseException;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: gayyzxyx
 * Date: 13-6-22
 * Time: 下午10:08
 * To change this template use File | Settings | File Templates.
 */
public class TestLucene {
    public static void main(String[] args) throws IOException, ParseException {
        LuceneSearch luceneSearch = new LuceneSearch("F:\\work\\intellij_project\\Android",".java");
        luceneSearch.getFoundFiles("Activity");
    }
}
