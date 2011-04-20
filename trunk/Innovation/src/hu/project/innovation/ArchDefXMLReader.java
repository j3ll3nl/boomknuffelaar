package hu.project.innovation;
import hu.project.innovation.configuration.model.ArchitectureDefinition;
import hu.project.innovation.configuration.model.Layer;

import java.io.CharArrayWriter;
import java.io.FileReader;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;


public class ArchDefXMLReader extends DefaultHandler {
	
	private CharArrayWriter contents = new CharArrayWriter();
	private ArchitectureDefinition ar = new ArchitectureDefinition();
	private Layer currentLayer;
	
	public void startElement( String namespaceURI, String localName, String qName, Attributes attr ) throws SAXException {
		
		contents.reset();
		
		if ( localName.equals( "layer" ) ) {
			currentLayer = new Layer();
			ar.addLayer(currentLayer);	
		}
		
	}
	
	public void endElement(String namespaceURI, String localName, String qName) {
		
		if ( localName.equals( "id" ) ) {
			currentLayer.setId(Integer.parseInt(contents.toString()));
		}
		
		if ( localName.equals( "name" ) ) {
			currentLayer.setName(contents.toString());
		}
		
		if ( localName.equals( "description" ) ) {
			currentLayer.setDescription(contents.toString());
		}
		
	}
	
	public void characters(char[] ch, int start, int length) throws SAXException {
		
		contents.write( ch, start, length );
		
	}
	
	public ArchitectureDefinition getArchitectureDefinition() {
		return ar;
	}
	
	public static void main(String[] args) {
	
		try {
			
			XMLReader xr = XMLReaderFactory.createXMLReader();
			
			ArchDefXMLReader reader = new ArchDefXMLReader();
			xr.setContentHandler(reader);
			
			xr.parse( new InputSource(new FileReader( "architecture_definition.xml" )) );
			
			ArchitectureDefinition ar = reader.getArchitectureDefinition();
			System.out.println(ar.toXML());
		
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
	
	}

}
