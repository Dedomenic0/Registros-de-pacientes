package Dedomenic0.registroPacientes.service;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class ArquivoService {

    public void gravaExel (List<String> lista, String nomeArquivo) throws IOException {
        //criar uma planilha
        XSSFWorkbook wb = new XSSFWorkbook();
//pegar o diretório do usuário e criar um arquivo com o determinado nome
        String PathTillProject = System.getProperty("user.dir");
        File pasta = new File(PathTillProject + "/src/main/resources/files/");
        if (!pasta.exists()) {
            pasta.mkdir();
        }

        FileOutputStream fileOut = new FileOutputStream(PathTillProject + "/src/main/resources/files/" + nomeArquivo + ".xlsx");
//criar várias abas
        XSSFSheet abaPrimaria = wb.createSheet("Contagem");

        for (int i = 0; i < lista.size(); i++) {
            XSSFRow linhas = abaPrimaria.createRow(i);
            String[] dados = lista.get(i).split(",");

                for (int j = 0; j < dados.length; j++) {
                    XSSFCell coluna = linhas.createCell(j);
                    coluna.setCellValue(dados[j]);
                }
        }
//escrever tudo o que foi feito no arquivo
        wb.write(fileOut);

//fecha a escrita de dados nessa planilha
        wb.close();
    }
}
