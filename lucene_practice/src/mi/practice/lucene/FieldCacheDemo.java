package mi.practice.lucene;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.index.AtomicReader;
import org.apache.lucene.index.BinaryDocValues;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.FilterAtomicReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.SlowCompositeReaderWrapper;
import org.apache.lucene.search.FieldCache;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;

public class FieldCacheDemo {
	public static void main(String[] args) throws IOException {
		Directory directory = FSDirectory.open(new File("/home/waf/tmp/index"));
		IndexReader reader = DirectoryReader.open(directory);
		AtomicReader atomicReader = SlowCompositeReaderWrapper.wrap(reader);
		BinaryDocValues values = FieldCache.DEFAULT.getTerms(atomicReader,
				"name");
		BytesRef term = new BytesRef();
		values.get(0, term);
		System.out.println(term.utf8ToString());
	}
}
