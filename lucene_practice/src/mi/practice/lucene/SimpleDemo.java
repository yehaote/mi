package mi.practice.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.BinaryDocValuesField;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.Version;

import java.io.File;
import java.io.IOException;

/**
 * Lucene简单的Demo 注意: 如果你是下lucene的源代码在工程中执行的话会报错, 需要把jar包引入到工程中来 User: 愁乐天 Date:
 * 13-5-17 Time: 上午1:07
 */
public class SimpleDemo {
	public static void main(String[] args) throws IOException {
		Version version = Version.LUCENE_43;
		// 创建一个Document
		Document document = new Document();
		Field field = new TextField("fieldName", "Hello man can you see this in index!", Field.Store.YES);
		field.setBoost(2.0f);
		Field fieldStore = new StringField("fieldName2", "fieldValueOnlyStore",
				Field.Store.YES);
		//
		// FieldType fieldAllType = new FieldType();
		// fieldAllType.setIndexed(true);
		// fieldAllType.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS);
		// fieldAllType.setOmitNorms(false);
		// fieldAllType.setStored(true);
		// fieldAllType.setStoreTermVectorOffsets(true);
		// fieldAllType.setStoreTermVectorPayloads(true);
		// fieldAllType.setStoreTermVectorPositions(true);
		// fieldAllType.setStoreTermVectors(true);
		// fieldAllType.setTokenized(true);
		// Field fieldAll = new Field("name", "all things need to store",
		// fieldAllType);

		document.add(field);
		// document.add(new BinaryDocValuesField("name", new
		// BytesRef("hello")));
		document.add(fieldStore);
		// document.add(fieldAll);
		
		Document doc2 = new Document();
		doc2.add(field);
		
		// 创建一个目录, 用于存放索引
		Directory directory = FSDirectory.open(new File("/home/waf/tmp/index"));
		// Directory directory = new RAMDirectory();
		// 定义索引写入器的一些参数
		Analyzer analyzer = new StandardAnalyzer(version);
		IndexWriterConfig indexWriterConfig = new IndexWriterConfig(version,
				analyzer);

		// indexWriterConfig.setCodec(new Lucene40Codec());
		// 初始化索引写入器, 并把文档写入到索引中去
		IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);
		indexWriter.addDocument(document);
		indexWriter.addDocument(doc2);
		indexWriter.commit();
		indexWriter.close();
		// 对索引进行查询
		IndexReader reader = DirectoryReader.open(directory);
		IndexSearcher indexSearcher = new IndexSearcher(reader);
		TopDocs result = indexSearcher.search(new TermQuery(new Term("name",
				"value")), 10);
		System.out.println(result.totalHits);
		reader.close();
	}
}
