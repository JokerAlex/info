package org.ylgzs.info.util;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.bson.Document;
import org.springframework.util.StringUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


/**
 * @ClassName ExcelUtil
 * @Description excel 读取
 * @Author alex
 * @Date 2018/10/4
 **/
public class ExcelUtil {

    /**
     * 点
     */
    public static final String POINT = ".";

    /**
     * 2003- 版本的excel
     */
    public static final String EXCEL_2003L =".xls";
    /**
     * 2007+ 版本的excel
     */
    public static final String EXCEL_2007U =".xlsx";

    private Workbook getWorkbook(String fileName) throws Exception{
        Workbook wb = null;
        InputStream inStr = new FileInputStream(fileName);
        String fileType = fileName.substring(fileName.lastIndexOf("."));
        if(EXCEL_2003L.equals(fileType)){
            wb = new HSSFWorkbook(inStr);
        }else if(EXCEL_2007U.equals(fileType)){
            wb = new XSSFWorkbook(inStr);
        }else{
            throw new Exception("解析的文件格式有误！");
        }
        return wb;
    }


    private static List<List<String>> read(Sheet sheet) {
        //TODO 重复列过滤
        return StreamSupport.stream(sheet.spliterator(), true)
                .filter(Objects::nonNull)
                .map(row -> StreamSupport.stream(row.spliterator(), false)
                .map(cell -> {
                    String cellValue;
                    if (cell == null){
                        return "NULL";
                    } else {
                        switch (cell.getCellType()) {
                            case STRING:
                                cellValue = cell.getStringCellValue().trim();
                                cellValue = StringUtils.isEmpty(cellValue) ? "NULL" : cellValue;
                                break;
                            case BOOLEAN:
                                cellValue = String.valueOf(cell.getBooleanCellValue());
                                break;
                            case FORMULA:
                                cellValue = String.valueOf(cell.getCellFormula().trim());
                                break;
                            case NUMERIC:
                                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                                    cellValue = DateUtil.parseYYYYMMDDDate(cell.getDateCellValue().toString()).toString();
                                } else {
                                    cellValue = new DecimalFormat("#.##").format(cell.getNumericCellValue());
                                }
                                break;
                            case BLANK:
                                cellValue = "NULL";
                                break;
                            case ERROR:
                                cellValue = "ERROR";
                                break;
                            default:
                                cellValue = cell.toString().trim();
                                break;
                        }
                    }
                    return cellValue;
                })
                .collect(Collectors.toList()))
                .collect(Collectors.toList());
    }

    public static List<Map<String, String>> readByMap(InputStream inputStream) throws IOException {
        Workbook workbook = WorkbookFactory.create(inputStream);
        Sheet sheet = workbook.getSheetAt(0);
        List<List<String>> listList = read(sheet);
        List<String> colName = listList.get(0);
        listList.remove(0);

        List<Map<String, String>> mapList = new ArrayList<>(listList.size());
        Map<String, String> row;
        for (List<String> aListList : listList) {
            row = new HashMap<>(colName.size());
            for (int j = 0; j < colName.size(); j++) {
                row.put(colName.get(j), aListList.get(j));
            }
            mapList.add(row);
        }
        return mapList;
    }

    public static List<Document> readByDocument(InputStream inputStream) throws IOException {
        Workbook workbook = WorkbookFactory.create(inputStream);
        Sheet sheet = workbook.getSheetAt(0);
        List<List<String>> listList = read(sheet);
        List<String> colName = listList.get(0);
        listList.remove(0);

        List<Document> documentList = new ArrayList<>(listList.size());
        for (List<String> rowList : listList) {
            Document rowDocument = new Document();
            for (int j = 0; j < colName.size(); j++) {
                rowDocument.append(colName.get(j), rowList.get(j));
            }

            documentList.add(rowDocument);
        }
        return documentList;
    }


}
