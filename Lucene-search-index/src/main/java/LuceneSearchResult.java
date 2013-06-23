import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: gayyzxyx
 * Date: 13-6-23
 * Time: 下午2:59
 * To change this template use File | Settings | File Templates.
 */
public class LuceneSearchResult implements Serializable {
    public long time;
    public List<String> resultList;
    public int counts;

    public LuceneSearchResult() {

    }

    public long getTime() {

        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public List<String> getResultList() {
        return resultList;
    }

    public void setResultList(List<String> resultList) {
        this.resultList = resultList;
    }

    public int getCounts() {
        return counts;
    }

    public void setCounts(int counts) {
        this.counts = counts;
    }


}
