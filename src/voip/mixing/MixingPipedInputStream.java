package voip.mixing;

import java.io.IOException;
import java.io.PipedInputStream;
import java.util.Collection;
import java.util.Iterator;

import voip.audio_forwarder.Forwarder;

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
		short streamSize = (short) streams.size();
		short[] coded = new short[byteToRead];

		boolean found = false;

		for (PipedInputStream stream : streams) {
			if(stream.available() > Forwarder.DATA_LENTH){
				found = true;

				byte[] data = new byte[byteToRead];
				stream.read(data);
				for (int i = 0; i < data.length; i++) {
					short linear = (short) (TConversionTool.ulaw2linear(data[i]) / streamSize);
					coded[i] += linear;
				}
			}
		}

		if(found)
			return 1;

		for (int i = 0; i < mixed.length; i++) {
			mixed[i] = TConversionTool.linear2ulaw((int) coded[i]);
		}

		return 0;
	}
}
