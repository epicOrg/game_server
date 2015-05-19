package voip.mixing;

import java.io.IOException;
import java.io.PipedInputStream;
import java.util.Collection;
import java.util.Iterator;

/**
 * Mixing stream for ulaw audio byte.
 * 
 * @author Luca
 * @date 2015/04/28
 */

public class MixingPipedInputStream extends PipedInputStream {

	private Collection<PipedInputStream> streams;

	public MixingPipedInputStream(Collection<PipedInputStream> streams) {
		super();
		this.streams = streams;
	}

	@Override
	public synchronized int available() throws IOException {
		Iterator<PipedInputStream> iterator = streams.iterator();
		int available = iterator.next().available();

		while (iterator.hasNext()) {
			available = Math.max(available, iterator.next().available());
		}

		return available;
	}

	@Override
	public int read(byte[] b) throws IOException {
		int byteToRead = b.length;
		byte[] mixed = b;
		int[] coded = new int[byteToRead];

		int found = 0;

		for (PipedInputStream stream : streams) {
			if(stream.available() > byteToRead){
				found++;

				byte[] data = new byte[byteToRead];
				stream.read(data);
				for (int i = 0; i < data.length; i++) {
					short linear = (short) (TConversionTool.ulaw2linear(data[i]));
					coded[i] += linear;
				}
			}
		}

		if(found == 0)
			return 0;

		for (int i = 0; i < mixed.length; i++) {
			mixed[i] = TConversionTool.linear2ulaw(coded[i] / found);
		}

		return 1;
	}
}
