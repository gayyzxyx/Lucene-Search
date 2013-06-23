import org.apache.lucene.queryparser.classic.ParseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
/**
 * Created with IntelliJ IDEA.
 * User: gayyzxyx
 * Date: 13-6-22
 * Time: 下午11:21
 * To change this template use File | Settings | File Templates.
 */
public class LuceneSearchSwing extends JFrame implements ActionListener{
    private JPanel inPanel;
    private JPanel inPanel1;
    private JPanel inPanel2;
    private String[] fileType = new String[]{"java","log","txt"};
    private JLabel searchKey = new JLabel("关键字：");
    private JLabel directory = new JLabel("目录：");
    private JLabel timeUse = new JLabel("");
    private JLabel resultCount = new JLabel("");
    private JLabel type = new JLabel("文件类型");
    private JButton open = new JButton("打开");
    private JButton goIndex = new JButton("索引");
    private JPanel outPanel;
    private JFileChooser jFileChooser = new JFileChooser("C:");
    private JTextField key = new JTextField(20);
    private JTextField textField = new JTextField(20);
    private String filePath;
    private JButton search = new JButton("查找");
    private JTextArea resultArea = new JTextArea("",20,60);
    private JComboBox jComboBoxFileType = new JComboBox(fileType);
    private JList jList = new JList();
    private String stringFileType;
    public LuceneSearchSwing() throws IOException {
        super();
        this.setSize(810, 400);
        inPanel = new JPanel();
        inPanel1 = new JPanel();
        inPanel2 = new JPanel();
        inPanel.setLayout(new FlowLayout());
        inPanel1.setLayout(new BorderLayout());
        inPanel2.setLayout(new BorderLayout());
        //textField.setBounds(10, 10, 100, 20);
        //key.setBounds(94, 49, 160, 20);
        open.addActionListener(this);
        inPanel.add(directory);
        inPanel.add(textField);
        inPanel.add(open);

        jComboBoxFileType.setEditable(false);
        jComboBoxFileType.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                stringFileType = ((JComboBox)e.getSource()).getSelectedItem().toString();
            }
        });
        inPanel.add(type);
        inPanel.add(jComboBoxFileType);

        inPanel.add(searchKey);
        inPanel.add(key);


        search.addActionListener(this);
        inPanel.add(search);
        goIndex.addActionListener(this);
        inPanel.add(goIndex);


        inPanel.add(timeUse);
        inPanel.add(resultCount);

        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        inPanel.add(new JScrollPane(resultArea));
        //inPanel.add(resultArea);
        this.add(inPanel);
        //this.add(inPanel1,BorderLayout.CENTER);
        //this.add(inPanel2,BorderLayout.SOUTH);
        this.setTitle("搜索");

    }
    public void actionPerformed(ActionEvent e){
        if(e.getActionCommand().equals("打开")){
            jFileChooser.setAcceptAllFileFilterUsed(false);
            jFileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            for(String str : fileType){
                jFileChooser.setFileFilter(new MyFilter(str));
            }
            int returnValue = jFileChooser.showOpenDialog(null);
            if(returnValue == JFileChooser.APPROVE_OPTION){
                try {
                    filePath = jFileChooser.getSelectedFile().getCanonicalPath();
                    textField.setText(filePath);
                } catch (IOException e1) {
                    e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        }
        else if(e.getActionCommand().equals("查找")){
            try {
                resultArea.setText("");
                LuceneSearch luceneSearch = new LuceneSearch(textField.getText(),stringFileType);
                LuceneSearchResult luceneSearchResult = luceneSearch.getFoundFiles(key.getText());
                for(String str : luceneSearchResult.getResultList()){
                    resultArea.append(str);
                    resultArea.append("\r\n");

                }
                timeUse.setText("用时："+luceneSearchResult.getTime()+" 毫秒");
                resultCount.setText("匹配："+luceneSearchResult.getCounts()+" 个文件");
            } catch (IOException e1) {
                e1.printStackTrace();
            } catch (ParseException e1) {
                e1.printStackTrace();
            }
        }
        else if(e.getActionCommand().equals("索引")){
            LuceneIndex luceneIndex = new LuceneIndex(textField.getText(),stringFileType);
            timeUse.setText("索引状态：");
            resultCount.setText("索引中");
            try {
                if(luceneIndex.goIndex()){
                    resultCount.setText("索引完毕");
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
    public static void main(String[] args) throws IOException{
        LuceneSearchSwing luceneSearch = new LuceneSearchSwing();
        luceneSearch.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        luceneSearch.setVisible(true);
    }
}
