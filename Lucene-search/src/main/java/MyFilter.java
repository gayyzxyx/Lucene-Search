import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: gayyzxyx
 * Date: 13-6-23
 * Time: 上午12:37
 * To change this template use File | Settings | File Templates.
 */
public class MyFilter extends FileFilter {
    private String ext;

    public MyFilter(String extString)
    {
        this.ext = extString;
    }
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }

        String extension = getExtension(f);
        if (extension.toLowerCase().equals(this.ext.toLowerCase()))
        {
            return true;
        }
        return false;
    }


    public String getDescription() {
        return this.ext.toUpperCase();
    }

    private String getExtension(File f) {
        String name = f.getName();
        int index = name.lastIndexOf('.');

        if (index == -1)
        {
            return "";
        }
        else
        {
            return name.substring(index + 1).toLowerCase();
        }
    }
}
