package es.bifacia.ytmp3.service.excel.impl;

import es.bifacia.ytmp3.service.excel.ExcelService;
import org.springframework.stereotype.Service;

@Service
public class ExcelServiceImpl implements ExcelService {
    private static final String COLLECTIONS_SHEET_NAME = "Colecciones";
    public static final String GAMES_OWNERS_FILE_PATH = "/Users/alejandro.calle/varios/gamesOwners.xlsx";

    public ExcelServiceImpl() {
        super();
    }



}
