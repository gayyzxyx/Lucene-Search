import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import javax.naming.directory.SearchResult;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: gayyzxyx
 * Date: 13-6-22
 * Time: 下午4:07
 * To change this template use File | Settings | File Templates.
 */
public class LuceneSearch {
    private String strIndexDir;
    private String strDataDir;
    private String strFileType;
    private long startTime = new Date().getTime();
    public LuceneSearch (String indexDir,String dataDir,String fileType) throws IOException {
        this.strIndexDir = indexDir;
        this.strDataDir = dataDir;
        this.strFileType = fileType;
    }
    public LuceneSearch(String dataDir,String fileType) throws IOException {
        this.strDataDir = dataDir;
        this.strIndexDir = "C:\\LuceneIndex";
        this.strFileType = fileType;
    }

    public LuceneSearchResult getFoundFiles(String queryString) throws IOException, ParseException {
        List<String> result = new ArrayList<String>();
        LuceneSearchResult luceneSearchResult = new LuceneSearchResult();
        Analyzer analyzer =  new StandardAnalyzer(Version.LUCENE_43);
        DirectoryReader directoryReader = DirectoryReader.open(FSDirectory.open(new File(this.strIndexDir)));
        IndexSearcher indexSearcher = new IndexSearcher(directoryReader);
        QueryParser parser = new QueryParser(Version.LUCENE_43,"content",analyzer);
        Query query = parser.parse(queryString);
        ScoreDoc[] hits = indexSearcher.search(query,null,1000).scoreDocs;
        System.out.println("Now Finding:");
        for(int i = 0;i < hits.length;i++){
            Document hitDoc = indexSearcher.doc(hits[i].doc);
            System.out.println(hitDoc.get("path"));
            result.add(hitDoc.get("path"));
        }
        directoryReader.close();
        System.out.println("Time Used:"+(new Date().getTime()-startTime)+" mileseconds");
        luceneSearchResult.setCounts(hits.length);
        luceneSearchResult.setResultList(result);
        luceneSearchResult.setTime((new Date().getTime()-startTime));
        return luceneSearchResult;
    }
}
