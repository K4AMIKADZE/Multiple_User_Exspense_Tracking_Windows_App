package GenerallyUsedCode;

import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.colors.DeviceRgb;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.VerticalAlignment;

import DBControllers.ReadingInformatioFromDB;
import ItemClases.GetCurrentUser;
import ItemClases.Settings;
import ItemClases.UniqueCurrency;
import ItemClases.UserInfoConvertPDF;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.io.font.constants.StandardFonts;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.awt.Desktop;
import com.itextpdf.layout.borders.Border;


public class GenerateUserPDF {

        

    static public void CreatePDF(ArrayList<UserInfoConvertPDF> UserInfo, ArrayList<GetCurrentUser> CurrentUser) throws IOException{

        ArrayList<String> dates = new ArrayList<>();
        ArrayList<UniqueCurrency> unique = new ArrayList<>();
        dates.clear();
        unique.clear();
        
        uniquefinder(UserInfo, unique);
        // put dates into dates array
        for(UserInfoConvertPDF s : UserInfo){
                dates.add(s.getDate());
        }

        String downloads = System.getProperty("user.home") + "/Downloads/"+ CurrentUser.get(0).getName() + "_" + Logic.GetFullDatePDF()  +".pdf";

        PdfWriter writer = new PdfWriter(downloads);
        PdfDocument pdf = new PdfDocument(writer);
        Document doc = new Document(pdf);
        // line
        SolidBorder headerBorder = new SolidBorder(ColorConstants.BLACK, 1);
        // color
        DeviceRgb lightBlue = new DeviceRgb(41, 135, 236);
       
        // fonts
        PdfFont regular = PdfFontFactory.createFont(StandardFonts.HELVETICA);
        PdfFont bold = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);

        

        // Title
        doc.add(new Paragraph("Information about " + CurrentUser.get(0).getName())
                .setFont(bold)
                .setFontSize(30)
                .setTextAlignment(TextAlignment.CENTER)
                .setFontColor(lightBlue));

                String maxDate = Collections.max(dates);
                String minDate = Collections.min(dates);
        // user info
        doc.add(new Paragraph(
                "Doc num: #" +  generateDocNum() + "\n"+ "Date: " + Logic.GetCurrentDate() +"\n" + "User: "+  CurrentUser.get(0).getName() + "\n" + "Database: " + CurrentUser.get(0).getDatabase() + "\n" + "InfoPoints: " + UserInfo.size() + "\n" + "Currencys: " + unique.size() + "\n" + "From To: " +  minDate + " - " +maxDate)
                .setFont(bold)
                .setMarginBottom(20).setBorderBottom(headerBorder));

       

        // Table
        float[] widths = {30f, 70f, 80f,80f,80f, 80f, 80f};
        Table table = new Table(widths);
        table.setHorizontalAlignment(HorizontalAlignment.CENTER).setMarginBottom(10);
   
        table.addHeaderCell(new Cell().add(new Paragraph("ID").setFont(bold)).setBackgroundColor(lightBlue).setFontColor(ColorConstants.WHITE).setBorderLeft(Border.NO_BORDER).setBorderRight(Border.NO_BORDER).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
        table.addHeaderCell(new Cell().add(new Paragraph("Date").setFont(bold)).setBackgroundColor(lightBlue).setFontColor(ColorConstants.WHITE).setBorderLeft(Border.NO_BORDER).setBorderRight(Border.NO_BORDER).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
        table.addHeaderCell(new Cell().add(new Paragraph("Type").setFont(bold)).setBackgroundColor(lightBlue).setFontColor(ColorConstants.WHITE).setBorderLeft(Border.NO_BORDER).setBorderRight(Border.NO_BORDER).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
        table.addHeaderCell(new Cell().add(new Paragraph("Location").setFont(bold)).setBackgroundColor(lightBlue).setFontColor(ColorConstants.WHITE).setBorderLeft(Border.NO_BORDER).setBorderRight(Border.NO_BORDER).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
        table.addHeaderCell(new Cell().add(new Paragraph("Transaction").setFont(bold)).setBackgroundColor(lightBlue).setFontColor(ColorConstants.WHITE).setBorderLeft(Border.NO_BORDER).setBorderRight(Border.NO_BORDER).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
        table.addHeaderCell(new Cell().add(new Paragraph("Currency").setFont(bold)).setBackgroundColor(lightBlue).setFontColor(ColorConstants.WHITE).setBorderLeft(Border.NO_BORDER).setBorderRight(Border.NO_BORDER).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));
        table.addHeaderCell(new Cell().add(new Paragraph("Amount").setFont(bold)).setBackgroundColor(lightBlue).setFontColor(ColorConstants.WHITE).setBorderLeft(Border.NO_BORDER).setBorderRight(Border.NO_BORDER).setTextAlignment(TextAlignment.CENTER).setBorder(Border.NO_BORDER));

      

        for(UserInfoConvertPDF s : UserInfo){

        table.addCell(new Cell()
        .add(new Paragraph(String.valueOf(s.getId())).setFont(bold))
        .setBorder(Border.NO_BORDER)
        .setBackgroundColor(lightBlue)
        .setFontColor(ColorConstants.WHITE)
        .setTextAlignment(TextAlignment.CENTER));

table.addCell(new Cell()
        .add(new Paragraph(s.getDate()).setFont(regular))
        .setBorderLeft(Border.NO_BORDER)
        .setBorderRight(Border.NO_BORDER)
        .setTextAlignment(TextAlignment.CENTER));

table.addCell(new Cell()
        .add(new Paragraph(s.getType()).setFont(regular))
        .setBorderLeft(Border.NO_BORDER)
        .setBorderRight(Border.NO_BORDER)
        .setTextAlignment(TextAlignment.CENTER));

table.addCell(new Cell()
        .add(new Paragraph(s.getLocation()).setFont(regular))
        .setBorderLeft(Border.NO_BORDER)
        .setBorderRight(Border.NO_BORDER)
        .setTextAlignment(TextAlignment.CENTER));

table.addCell(new Cell()
        .add(new Paragraph(s.getTransaction()).setFont(regular))
        .setBorderLeft(Border.NO_BORDER)
        .setBorderRight(Border.NO_BORDER)
        .setTextAlignment(TextAlignment.CENTER));

table.addCell(new Cell()
        .add(new Paragraph(s.getCurrency()).setFont(regular))
        .setBorderLeft(Border.NO_BORDER)
        .setBorderRight(Border.NO_BORDER)
        .setTextAlignment(TextAlignment.CENTER));

table.addCell(new Cell()
        .add(new Paragraph(s.getSpent()).setFont(regular))
        .setBorderLeft(Border.NO_BORDER)
        .setBorderRight(Border.NO_BORDER)
        .setTextAlignment(TextAlignment.CENTER));
        
        }
        
        
        doc.add(table);


    

        for(UniqueCurrency s : unique){
        doc.add(new Paragraph( s.getCurrencyName() + ": " + s.getAmount())
                .setFont(bold)
                .setFontSize(14)
                .setMarginLeft(390)
                
        );
        }

         doc.add(new Paragraph()
                .setBorderBottom(headerBorder)
                
        );

        doc.add(new Paragraph( "Total: " + EurTotal(unique) + " Eur")
                .setFont(bold)
                .setFontSize(16)
                .setMarginLeft(360)
                .setFontColor(lightBlue)
                
        );




        doc.add(new Paragraph("\nInformation was provided by program from ["+ CurrentUser.get(0).getName()+"] exspenses ©").setFont(bold).setTextAlignment(TextAlignment.CENTER));

        // close the pdf creation
        doc.close();

        // open pdf for user to see
        File file = new File(downloads);
        Desktop.getDesktop().open(file);   
  

    }

    static public void uniquefinder(ArrayList<UserInfoConvertPDF> UserInfo, ArrayList<UniqueCurrency> unique){

        for(UserInfoConvertPDF s : UserInfo){

                String currency = s.getCurrency();
                double amount = Double.parseDouble(s.getSpent());
                Boolean exists = false;

                for(UniqueCurrency ss : unique){
                    if(currency.equalsIgnoreCase(ss.getCurrencyName())){
                        exists = true;
                        ss.addnums(amount);
                                
                }
                }

                if(!exists){
                   unique.add(new UniqueCurrency(currency, amount));
                }
        }

        for(UniqueCurrency s : unique){
                System.out.println(s);
        }

}


     static public double EurTotal(ArrayList<UniqueCurrency> unique){

        double amount = 0;

        double UsdToEur = 0;
        double RubToEur = 0;
        double GbpToEur = 0;

         ArrayList<Settings> settings = ReadingInformatioFromDB.getSettings();
        for(Settings s : settings){
            if(s.getSettingname().equalsIgnoreCase("Usd to Eur Convertion")){
              UsdToEur = Double.parseDouble(s.getSettingValue());
            }
             if(s.getSettingname().equalsIgnoreCase("Rub to Eur Conversion")){
              RubToEur = Double.parseDouble(s.getSettingValue());
            }
             if(s.getSettingname().equalsIgnoreCase("Gbp to Eur Conversion")){
              GbpToEur = Double.parseDouble(s.getSettingValue());
            }
        }

        for(int i = 0; i < unique.size(); i++){

         if(unique.get(i).getCurrencyName().equalsIgnoreCase("Eur")){
            amount += unique.get(i).getAmount() * 1;
          }
          else if(unique.get(i).getCurrencyName().equalsIgnoreCase("Usd")){
            
            amount += unique.get(i).getAmount() * UsdToEur;
          }
          else if(unique.get(i).getCurrencyName().equalsIgnoreCase("Rub")){
            
            amount += unique.get(i).getAmount() * RubToEur;
          }
           else if(unique.get(i).getCurrencyName().equalsIgnoreCase("Gbp")){
            
            amount += unique.get(i).getAmount() * GbpToEur;
          }
          else{
            amount += unique.get(i).getAmount() * 1;
            
          }


     }

     return Double.parseDouble(String.format("%.2f", amount));
     

}

     static public String generateDocNum(){

        StringBuilder docnum = new StringBuilder();

        LocalDate date = LocalDate.now();

        String year = String.valueOf(date).substring(0,4);
        String month = String.valueOf(date).substring(5,7);
        String day = String.valueOf(date).substring(8,10);

        Random random = new Random();
        int com = Integer.parseInt(month) + Integer.parseInt(day);
        int randnum = random.nextInt(com)+10000;

        docnum.append(year);
        docnum.append(randnum);


        return String.valueOf(docnum);
}


}
