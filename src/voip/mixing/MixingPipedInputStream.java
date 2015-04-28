package voip.mixing;

import java.io.IOException;
import java.io.PipedInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;


/**
 * 
 * Mixing stream for ulaw audio byte
 * 
 * @author Luca
 *
 */

public class MixingPipedInputStream extends PipedInputStream{	

	private Collection<PipedInputStream> streams;

	public MixingPipedInputStream(Collection<PipedInputStream> streams) throws EmptyCollectionException {
		super();
		this.streams = streams;
		if(streams.isEmpty()){
			throw new EmptyCollectionException();
		}
	}
	
	@Override
	public synchronized int available() throws IOException {
		Iterator<PipedInputStream> iterator = streams.iterator();
		int available = iterator.next().available() ;
		
		while (iterator.hasNext()) {
			available = Math.max(available, iterator.next().available());			
		}
		
		return available;		
	}
	
	@Override
	public int read(byte[] b) throws IOException {
		// TODO Auto-generated method stub
		
		int byteToRead = b.length;
		if(b.length== 0)
			return 0;
		byte[] mixed = b;
		short streamSize = (short) streams.size();
		short[] coded = new short[byteToRead];
		for(PipedInputStream stream :streams){
			byte[] data = new byte[byteToRead];
			stream.read(data);			
			for (int i = 0; i < data.length; i++) {
				short linear = (short) (TConversionTool.ulaw2linear(data[i]) / streamSize);
				coded[i] += linear; 
			}			
		}
				
		for (int i = 0; i < mixed.length; i++) {
			mixed[i] = TConversionTool.linear2ulaw((int)coded[i]);
		}
		return byteToRead ;
	}
}
