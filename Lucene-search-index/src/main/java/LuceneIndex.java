/**
 * Created with IntelliJ IDEA.
 * User: gayyzxyx
 * Date: 13-6-21
 * Time: 上午12:15
 * To change this template use File | Settings | File Templates.
 */

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

public class LuceneIndex {
    private String strIndexDir;
    private String strDataDir;
    private String strFileType;
    public LuceneIndex(String indexDir,String dataDir,String fileType){
        this.strIndexDir = indexDir;
        this.strDataDir = dataDir;
        this.strFileType = fileType;
    }
    public LuceneIndex(String dataDir,String fileType){
        this.strIndexDir = "C:\\LuceneIndex";
        this.strDataDir = dataDir;
        this.strFileType = fileType;
    }
    public boolean goIndex() throws IOException{
        if(strDataDir == null||strDataDir.equals("")){
            return false;
        }
        long startTime = new Date().getTime();
        File indexDir  = new File(this.strIndexDir);
        File dataDir = new File(this.strDataDir);
        File[] textFiles = dataDir.listFiles();
        File[] indexs = indexDir.listFiles();
        for(int i=0;i<indexs.length;i++){
            if(indexs[i].isFile()){
                indexs[i].delete();
            }
        }
        Directory directory = FSDirectory.open(indexDir);
        Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_43);
        IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_43,analyzer);
        IndexWriter indexWriter = new IndexWriter(directory,config);
        for(int i=0;i<textFiles.length;i++){
            if(textFiles[i].isFile()&&textFiles[i].getName().toLowerCase().endsWith(this.strFileType)){
                indexWriter.addDocument(addIndex(textFiles[i]));
            }
            else if(textFiles[i].isDirectory()){
                 getFileByDocument(indexWriter,textFiles[i]);
            }
        }
        indexWriter.close();
        System.out.println("Time Used:"+(new Date().getTime()-startTime)+" mileseconds");
        return true;
    }
    public Document addIndex(File file) throws IOException{
        System.out.println("File "+file.getCanonicalPath()+" is being indexing.");
        Document document = new Document();
        document.add(new Field("path",file.getPath(), Field.Store.YES,Field.Index.NO));
        document.add(new Field("content",new InputStreamReader(new FileInputStream(file.getCanonicalPath()),"GBK")));
        return document;
    }
    public void getFileByDocument(IndexWriter indexWriter,File file) throws IOException{
        if(file!=null&&file.exists()&&file.isDirectory()){
            File []files = file.listFiles();
            if(files!=null&&files.length>0){
                for(int i=0;i<files.length;i++){
                    if(files[i].isDirectory()){
                        getFileByDocument(indexWriter,files[i]);
                    }
                    else if(files[i].isFile()&&files[i].getName().toLowerCase().endsWith(this.strFileType)){
                         indexWriter.addDocument(addIndex(files[i]));
                    }
                }
            }
        }
    }
}
