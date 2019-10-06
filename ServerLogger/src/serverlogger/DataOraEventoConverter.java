package serverlogger;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

public class DataOraEventoConverter implements Converter // 01
{
	@Override
	public void marshal(Object obj, HierarchicalStreamWriter writer, MarshallingContext mc)
	{
		DataOraEvento doe = (DataOraEvento)obj;
		writer.addAttribute("formato", doe.formato);
		writer.setValue(doe.valore);
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext uc)
	{
		return new DataOraEvento(reader.getAttribute("formato"), reader.getValue());
	}

	@Override
	public boolean canConvert(Class type)
	{
		return DataOraEvento.class.isAssignableFrom(type);
	}
}

/* COMMENTI:
 * (01) Il Converter è utilizzato da XStream per fare il corretto marshalling
 * XML della classe DataOraEvento:
 * <nome formato="formato">valore</nome>
 */