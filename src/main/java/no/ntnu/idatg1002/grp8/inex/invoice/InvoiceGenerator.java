package no.ntnu.idatg1002.grp8.inex.invoice;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.stream.Stream;
import lombok.experimental.UtilityClass;
import no.ntnu.idatg1002.grp8.inex.ErrorLogger;

/**
 * The InvoiceGenerator class generates a PDF invoice with given information and items
 * and saves it to a specified folder path.
 */
@UtilityClass
public class InvoiceGenerator {
  /**
   * This function generates a PDF invoice with given information and items and saves it to a
   * specified folder path.
   *
   * @param invoiceInformation A Map containing information about the invoice,
   *                           such as the customer's name and address, invoice ID, issue date,
   *                           due date, customer reference, and company reference.
   * @param items              The "items" parameter is a List of Item objects,
   *                           which represent the individual line items on an invoice.
   *                           Each Item object contains a description, quantity, price,
   *                           and total sum. The "generateInvoice" method uses this list to
   *                           populate the invoice with the appropriate line items
   * @param folderPath         The path to the folder where the generated invoice PDF will be saved.
   */
  public static void generateInvoice(
      Map<String, String> invoiceInformation,
      List<Item> items,
      Path folderPath
  ) throws IOException {
    File file = new File("cfg/pdf/invoice_empty.pdf");

    Long invoiceNumber = invoiceNumberGenerator(folderPath);

    try (PdfReader reader = new PdfReader(file.getPath())) {
      File generatedFile =
          new File(
              folderPath.toString(), invoiceNumber + "_" + invoiceInformation.get("id") + "_"
              + LocalDate.now()
              + ".pdf");
      if (!Files.exists(Path.of(generatedFile.getPath()))) {
        try {
          Files.createDirectories(folderPath);
          Files.createFile(Path.of(generatedFile.getPath()));
        } catch (IOException e) {
          ErrorLogger.getLOGGER().log(Level.SEVERE, "Unable to create PDF file");
        }
      }
      try (PdfWriter writer = new PdfWriter(new FileOutputStream(generatedFile));
           PdfDocument pdfDoc = new PdfDocument(reader, writer);
           Document document = new Document(pdfDoc, PageSize.A4)) {

        PdfFont bf = PdfFontFactory.createFont("Helvetica-Bold");
        PdfFont regularFont = PdfFontFactory.createFont("Helvetica");
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");

        document.add(
            styledParagraph(invoiceInformation, String.valueOf(invoiceNumber), regularFont, 143, 61,
                100));
        document.add(styledParagraph(invoiceInformation, "name", bf, 44, 110, 200));
        document.add(styledParagraph(invoiceInformation, "address", regularFont, 44, 122, 200));
        document.add(styledParagraph(invoiceInformation, "location", regularFont, 44, 134, 200));
        document.add(styledParagraph(invoiceInformation, "location2", regularFont, 44, 146, 200));
        document.add(styledParagraph(invoiceInformation, "id", regularFont, 370, 171, 100));
        document.add(styledParagraph(invoiceInformation, "issue", regularFont, 370, 183, 200));
        document.add(styledParagraph(invoiceInformation, "issue", regularFont, 370, 195, 200));
        document.add(
            styledParagraph(invoiceInformation, "customerRef", regularFont, 370, 207, 200));
        document.add(styledParagraph(invoiceInformation, "companyRef", regularFont, 370, 219, 200));

        int j = 0;
        double invoiceSum = 0;
        for (Item item : items) {
          document.add(new Paragraph(item.getDescription())
              .setFont(regularFont)
              .setFontSize(10)
              .setFixedPosition(1, 36, PageSize.A4.getTop() - 282 - (14f * j), 200)
              .setTextAlignment(TextAlignment.LEFT));

          document.add(new Paragraph(decimalFormat.format(item.getQuantity()))
              .setFont(regularFont)
              .setFontSize(10)
              .setFixedPosition(1, 202, PageSize.A4.getTop() - 282 - (14f * j), 200)
              .setTextAlignment(TextAlignment.RIGHT));

          document.add(new Paragraph(decimalFormat.format(item.getPrice()))
              .setFont(regularFont)
              .setFontSize(10)
              .setFixedPosition(1, 279, PageSize.A4.getTop() - 282 - (14f * j), 200)
              .setTextAlignment(TextAlignment.RIGHT));

          document.add(new Paragraph(decimalFormat.format(item.getSum()))
              .setFont(regularFont)
              .setFontSize(10)
              .setFixedPosition(1, 357, PageSize.A4.getTop() - 282 - (14f * j), 200)
              .setTextAlignment(TextAlignment.RIGHT));

          invoiceSum += item.getSum();
          j++;
        }

        document.add(new Paragraph(decimalFormat.format(invoiceSum))
            .setFont(regularFont)
            .setFontSize(9)
            .setFixedPosition(1, 98, PageSize.A4.getTop() - 677, 200)
            .setTextAlignment(TextAlignment.RIGHT));

        document.add(new Paragraph(invoiceInformation.get("due"))
            .setFont(regularFont)
            .setFontSize(9)
            .setFixedPosition(1, 350, PageSize.A4.getTop() - 574, 200)
            .setTextAlignment(TextAlignment.RIGHT));

        document.add(new Paragraph(invoiceInformation.get("kid"))
            .setFont(regularFont)
            .setFontSize(9)
            .setFixedPosition(1, 350, PageSize.A4.getTop() - 586, 200)
            .setTextAlignment(TextAlignment.RIGHT));

        document.add(new Paragraph(String.valueOf(invoiceNumber))
            .setFont(regularFont)
            .setFontSize(9)
            .setFixedPosition(1, 350, PageSize.A4.getTop() - 598, 200)
            .setTextAlignment(TextAlignment.RIGHT));

        document.add(new Paragraph(decimalFormat.format(invoiceSum))
            .setFont(bf)
            .setFontSize(14)
            .setFixedPosition(1, 350, PageSize.A4.getTop() - 702, 200)
            .setTextAlignment(TextAlignment.RIGHT));

        OpenPdf.openInvoice(generatedFile);
      }
    }
  }

  /**
   * The function creates a styled paragraph with a given font, font size, position, and text
   * alignment, and replaces certain text with values from a provided hash map.
   *
   * @param hashMap A map that contains key-value pairs where the key is a string representing a
   *                placeholder in the text
   *                and the value is the actual text to replace the placeholder with.
   * @param text    The text that needs to be displayed in the paragraph.
   * @param font    The font to be used for the paragraph. It is of type PdfFont.
   * @param left    The left margin of the paragraph, measured in points (1/72 of an inch).
   * @param bottom  The "bottom" parameter in the "styledParagraph" method is an integer value that
   *                represents the distance from the bottom edge of the page
   *                to the bottom edge of the paragraph.
   *                It is used to set the vertical position of the paragraph on the page.
   * @param width   The width parameter represents the width of the paragraph in
   *                points (1/72 of an inch).
   *                It is used to set the fixed position of the paragraph on the PDF page.
   * @return        A `Paragraph` object is being returned.
   */
  private static Paragraph styledParagraph(Map<String, String> hashMap, String text, PdfFont font,
                                           int left, int bottom, int width) {
    String regex = "\\d+";
    if (!text.matches(regex)) {
      text = hashMap.get(text);
    }
    return new Paragraph(text)
        .setFont(font)
        .setFontSize(9)
        .setFixedPosition(1, left, PageSize.A4.getTop() - bottom, width)
        .setTextAlignment(TextAlignment.LEFT);
  }

  /**
   * This Java function generates a unique invoice number by counting the number of files in a given
   * directory and incrementing it by one.
   *
   * @param dir The "dir" parameter is a Path object that represents the directory where the invoice
   *            files are stored.
   *            The method uses this parameter to list all the files in the directory and
   *            count them to generate a new invoice number.
   * @return The method is returning a long value, which is the next invoice number to be used.
   */
  private static long invoiceNumberGenerator(Path dir) throws IOException {
    long invoiceNumber;
    try (Stream<Path> files = Files.list(dir)) {
      invoiceNumber = files.count();
      invoiceNumber++;
      return invoiceNumber;
    }
  }
}