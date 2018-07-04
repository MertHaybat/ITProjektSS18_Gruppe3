package de.hdm.itprojektss18Gruppe3.shared.report;

import java.util.Vector;

/**
 * Ein <code>ReportWriter</code>, der Reports mittels HTML formatiert. Das im
 * Zielformat vorliegende Ergebnis wird in der Variable <code>reportText</code>
 * abgelegt und kann nach Aufruf der entsprechenden Prozessierungsmethode mit
 * <code>getReportText()</code> ausgelesen werden.
 * 
 * @author Thies
 */
public class HTMLReportWriter extends ReportWriter {

	/**
	 * Diese Variable wird mit dem Ergebnis einer Umwandlung (vgl.
	 * <code>process...</code>-Methoden) belegt. Format: HTML-Text
	 */
	private String reportText = "";

	/**
	 * Zurücksetzen der Variable <code>reportText</code>.
	 */
	public void resetReportText() {
		this.reportText = "";
	}

	/**
	 * Umwandeln eines <code>Paragraph</code>-Objekts in HTML.
	 * 
	 * @param p
	 *            der Paragraph
	 * @return HTML-Text
	 */
	public String paragraph2HTML(Paragraph p) {
		if (p instanceof CompositeParagraph) {
			return this.paragraph2HTML((CompositeParagraph) p);
		} else {
			return this.paragraph2HTML((SimpleParagraph) p);
		}
	}

	/**
	 * Umwandeln eines <code>CompositeParagraph</code>-Objekts in HTML.
	 * 
	 * @param p
	 *            der CompositeParagraph
	 * @return HTML-Text
	 */
	public String paragraph2HTML(CompositeParagraph p) {
		StringBuffer result = new StringBuffer();

		for (int i = 0; i < p.getNumParagraphs(); i++) {
			result.append("<p>" + p.getParagraphAt(i) + "</p>");
		}

		return result.toString();
	}

	/**
	 * Umwandeln eines <code>SimpleParagraph</code>-Objekts in HTML.
	 * 
	 * @param p
	 *            der SimpleParagraph
	 * @return HTML-Text
	 */
	public String paragraph2HTML(SimpleParagraph p) {
		return "<p>" + p.toString() + "</p>";
	}

	/**
	 * HTML-Header-Text produzieren.
	 * 
	 * @return HTML-Text
	 */
	public String getHeader() {
		StringBuffer result = new StringBuffer();

		result.append("<html><head><title></title></head><body>");
		return result.toString();
	}

	/**
	 * HTML-Trailer-Text produzieren.
	 * 
	 * @return HTML-Text
	 */
	public String getTrailer() {
		return "</body></html>";
	}

	public String getReportText(){
		  return this.getHeader() + this.reportText + this.getTrailer();
	  }
	  
	
	@Override
	public void process(AlleKontakteReport p) {
		this.resetReportText();
		StringBuffer result = new StringBuffer();
		// result.append("<h3>" + p.getTitle() + "</h3>");
		result.append("<table style=\"width:40%\"><tr>");
		result.append("<td><b>" + p.getTitle() + "</b></td></tr><tr>");

		result.append("<td><b>" + paragraph2HTML(p.getHeaderData()) + "</b></td>");

		result.append("<td width=200>" + paragraph2HTML(p.getImprint()) + "</td>");
		result.append("</tr><tr><td></td><td>" + p.getCreated().toString() + "</td></tr></table>");

		Vector<Row> rows = p.getRows();
		result.append("<br><br>");
		result.append("<table style=\"width:100%\">");
		for (int i = 0; i < rows.size(); i++) {
			Row row = rows.elementAt(i);
			result.append("<tr>");
			for (int k = 0; k < row.getColumns().size(); k++) {
				if (i == 0) {

					result.append("<td style=\"background:silver;font-weight:bold\">" + row.getColumnAt(k) + "</td>");
				} else {
					if (i > 1) {
						result.append("<td style=\"border-top:1px solid silver\">" + row.getColumnAt(k) + "</td>");
					} else {
						result.append("<td valign=\"top\">" + row.getColumnAt(k) + "</td>");
					}
				}
			}
			result.append("</tr>");
		}

		result.append("</table>");
		this.reportText = result.toString();

	}

	@Override
	public void process(AlleKontakteByTeilhaberschaftReport p) {
		this.resetReportText();
		  StringBuffer result = new StringBuffer();
		  result.append("<table style=\"width:100%;border:1px solid silver\"><tr>");
			result.append("<td><b>" + p.getTitle() + "</b></td></tr><tr>");

			result.append("<td><b>" + paragraph2HTML(p.getHeaderData()) + "</b></td>");

			result.append("<td width=200>" + paragraph2HTML(p.getImprint()) + "</td>");
			result.append("</tr><tr><td></td><td>" + p.getCreated().toString() + "</td></tr></table>");
			
		  	 Vector<Row> rows = p.getRows();
		     result.append("<table style=\"width:100%\">");
		     for (int i = 0; i < rows.size(); i++) {
		         Row row = rows.elementAt(i);
		         result.append("<tr>");
		         for (int k = 0; k < row.getColumns().size(); k++) {
		           if (i == 0) {
		        	
		             result.append("<td style=\"background:silver;font-weight:bold\">" + row.getColumnAt(k)
		                 + "</td>");	             
		           }
		           else {
		             if (i > 1) {
		               result.append("<td style=\"border-top:1px solid silver\">"
		                   + row.getColumnAt(k) + "</td>");
		             }
		             else {
		               result.append("<td valign=\"top\">" + row.getColumnAt(k) + "</td>");
		             }
		           }
		         }
		         result.append("</tr>");
		       }

		       result.append("</table>");    
		       this.reportText = result.toString();
	}

	@Override
	public void process(KontakteMitBestimmtenEigenschaftenUndAuspraegungenReport p) {
		this.resetReportText();
		StringBuffer result = new StringBuffer();

		result.append("<table style=\"width:100%;border:1px solid silver\"><tr>");
		result.append("<td><b>" + p.getTitle() + "</b></td></tr><tr>");

		result.append("<td><b>" + paragraph2HTML(p.getHeaderData()) + "</b></td>");

		result.append("<td width=200>" + paragraph2HTML(p.getImprint()) + "</td>");
		result.append("</tr><tr><td></td><td>" + p.getCreated().toString() + "</td></tr></table>");

		Vector<Row> rows = p.getRows();
		result.append("<table style=\"width:100%\">");
		for (int i = 0; i < rows.size(); i++) {
			Row row = rows.elementAt(i);
			result.append("<tr>");
			for (int k = 0; k < row.getColumns().size(); k++) {
				if (i == 0) {

					result.append("<td style=\"background:silver;font-weight:bold\">" + row.getColumnAt(k) + "</td>");
				} else {
					if (i > 1) {
						result.append("<td style=\"border-top:1px solid silver\">" + row.getColumnAt(k) + "</td>");
					} else {
						result.append("<td valign=\"top\">" + row.getColumnAt(k) + "</td>");
					}
				}
			}
			result.append("</tr>");
		}

		result.append("</table>");
		this.reportText = result.toString();
	}
}