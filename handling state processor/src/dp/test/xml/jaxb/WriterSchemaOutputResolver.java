package dp.test.xml.jaxb;

import java.io.IOException;
import java.io.Writer;

import javax.xml.bind.SchemaOutputResolver;
import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;

/**
 * This is a basic example of resolver whose job is basically to provide
 * The JAXB schema generator with the result object where the output can be stored.
 * 
 * @author DPavlov
 */
public class WriterSchemaOutputResolver extends SchemaOutputResolver
{

	private final Writer out;
	
	
	public WriterSchemaOutputResolver(Writer out) {
		super();
		this.out = out;
	}


	@Override
	public Result createOutput(String namespaceUri, String suggestedFileName) throws IOException {
		final StreamResult result = new StreamResult(this.out);
		result.setSystemId("no-id"); // Result MUST contain system id, or JAXB throws an error message
		return result;
	}

}
