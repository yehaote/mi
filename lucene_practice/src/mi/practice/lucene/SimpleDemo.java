package mi.practice.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.*;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

import java.io.File;
import java.io.IOException;

/**
 * Lucene简单的Demo
 * 注意: 如果你是下lucene的源代码在工程中执行的话会报错, 需要把jar包引入到工程中来
 * User: 愁乐天
 * Date: 13-5-17
 * Time: 上午1:07
 */
public class SimpleDemo {
    public static void main(String[] args) throws IOException {
        Version version = Version.LUCENE_43;
        // 创建一个Document
        Document document = new Document();
        Field field = new StringField("name", "value", Field.Store.YES);
        Field fieldStore = new StringField("store_field", "store_value", Field.Store.YES);
        document.add(fieldStore);
        document.add(field);
        // 创建一个目录, 用于存放索引
        Directory directory = FSDirectory.open(new File("/home/waf/tmp/index"));
        //Directory directory = new RAMDirectory();
        // 定义索引写入器的一些参数
        Analyzer analyzer = new StandardAnalyzer(version);
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(version, analyzer);
        // 初始化索引写入器, 并把文档写入到索引中去
        IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);
        indexWriter.addDocument(document);
        indexWriter.commit();
        indexWriter.close();
        // 对索引进行查询
        IndexReader reader = DirectoryReader.open(directory);
        IndexSearcher indexSearcher = new IndexSearcher(reader);
        TopDocs result = indexSearcher.search(new TermQuery(new Term("name", "value")), 10);
        System.out.println(result.totalHits);
        reader.close();
    }
}
