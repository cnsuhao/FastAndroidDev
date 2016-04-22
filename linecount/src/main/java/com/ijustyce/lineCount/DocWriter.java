package com.ijustyce.lineCount;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by yangchun on 16/4/20.  写入doc
 */
public class DocWriter {

    public static boolean write(File file, String content){

        if (file == null || (!file.getName().endsWith(".docs")
                && !file.getName().endsWith(".doc"))||content == null) return false;

        if (file.exists()) file.delete();

        //Blank Document
        XWPFDocument document= new XWPFDocument();
        //Write the Document in file system
        try {
            FileOutputStream out = new FileOutputStream(file);

            //create Paragraph
            XWPFParagraph paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.LEFT);
            XWPFRun run = paragraph.createRun();
            setText(content, run);
            document.write(out);
            out.close();
            System.out.println("createparagraph.docx written successfully");
        }catch (Exception e){
            System.out.println("createparagraph.docx written failed");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private static void setText(String data, XWPFRun run){

        if (data.contains("\n")) {
            String[] lines = data.split("\n");
            int length = lines.length;
            System.out.println("length is " + length);
            run.setText(lines[0]); // set first line into XWPFRun
            for(int i=1;i < length ;i++){
                // add break and insert new text
                run.addBreak();
                run.setText(lines[i]);
                System.out.println("set text , line : " + i);
            }
        } else {
            run.setText(data);
        }
        System.out.println("begin write to file...");
    }
}
