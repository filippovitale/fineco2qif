/*
 * Copyright 2007 Filippo Vitale (http://www.filippovitale.it)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package it.filippovitale.fineco2qif.logic;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class ExcelSheetAnalysisLogic {

    private static final Logger log = Logger.getLogger(ExcelSheetAnalysisLogic.class);
	public static final String NULL_RAPPRESENTATION = "[null]";

    public static HSSFSheet getSheetFromFile(String filename, String sheetname) {
    	HSSFSheet sheet = null;
    	try {
			POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(filename));
			HSSFWorkbook wb = new HSSFWorkbook(fs);
			sheet = wb.getSheet(sheetname);
		} catch (Exception e) {
			log.error(e);
		}
		
        dumpSheet(sheet);

        return sheet;
	}
    
	public static List<String[]> getCells(HSSFSheet sheet,
			Short[] columnAbsolutePositions, boolean looseConstraint) {
		List<String[]> cells = new ArrayList<String[]>();
        for (int rowAbsolutePosition = sheet.getFirstRowNum() + 1 ; rowAbsolutePosition <= sheet.getLastRowNum() ; rowAbsolutePosition++) {
	        HSSFRow row = sheet.getRow(rowAbsolutePosition);

			if(row==null) {
				if(looseConstraint) {
					log.debug("empty row in the absolute position \"" + rowAbsolutePosition + "\"");
				} else {
					log.warn("row in the absolute position \"" + rowAbsolutePosition + "\" has problem");
				}
				continue;
			}

	        String cellStringValues[] = new String[5];
	        for (int columnRelativePosition = 0 ; columnRelativePosition < cellStringValues.length ; columnRelativePosition++) {
		        HSSFCell cell = row.getCell(columnAbsolutePositions[columnRelativePosition]);
		        String cellStringValue = cell!=null ? cell.getStringCellValue() : null;
				if(cellStringValue!=null && cellStringValue.trim().equals("")) {
					cellStringValue = null;
				}
		        cellStringValues[columnRelativePosition] = cellStringValue;
			}

			if(cellStringValues[0]==null && cellStringValues[1]==null && cellStringValues[2]==null && cellStringValues[3]==null && cellStringValues[4]==null) {
				if(looseConstraint) {
					log.debug("empty row in the absolute position \"" + rowAbsolutePosition + "\"");
				} else {
					log.warn("row in the absolute position \"" + rowAbsolutePosition + "\" has problem, it's {null, null, null, null, null}");
				}
			} else {
				cells.add(cellStringValues);
			}
        }
        
        dumpCells(cells);
        validateCells(cells, looseConstraint);

		return cells;
	}

	public static void validateCells(List<String[]> cells, boolean looseConstraint) {
		for (Iterator<String[]> cellsIter = cells.iterator(); cellsIter.hasNext();) {
			String[] cellStringValues = cellsIter.next();
			if(!validateCell(cellStringValues, looseConstraint)) {
				log.warn("row=" + dumpCellToString(cellStringValues) + " (relative position) has problem");
			}
		}
	}

	private static boolean validateCell(String[] cellStringValues, boolean looseConstraint) {
		if(cellStringValues==null || cellStringValues.length!=5) {
			return false;
		}

		if(looseConstraint) {
			return true;
		}

		if(cellStringValues[0]==null) {
			return false;
		}

		if(cellStringValues[0]!=null && cellStringValues[1]==null && cellStringValues[2]==null && cellStringValues[3]==null && cellStringValues[4]==null) {
			return true;
		} else if(cellStringValues[0]!=null && cellStringValues[1]!=null && cellStringValues[2]==null && cellStringValues[3]==null && cellStringValues[4]==null) {
			return true;
		} else if(cellStringValues[0]!=null && cellStringValues[1]!=null && cellStringValues[2]!=null && cellStringValues[3]==null && cellStringValues[4]==null) {
			return true;
		} else if(cellStringValues[0]!=null && cellStringValues[1]!=null && cellStringValues[2]!=null && cellStringValues[3]!=null && cellStringValues[4]==null) {
			return true;
		} else if(cellStringValues[0]!=null && cellStringValues[1]!=null && cellStringValues[2]!=null && cellStringValues[3]!=null && cellStringValues[4]!=null) {
			return true;
		}
		
		return false;
	}

	private static boolean validateOnlyOneCellNotNull(String[] cellStringValues) {
		if(cellStringValues==null || cellStringValues.length!=5) {
			return false;
		}

		if(cellStringValues[0]!=null && cellStringValues[1]==null && cellStringValues[2]==null && cellStringValues[3]==null && cellStringValues[4]==null) {
			return true;
		} else if(cellStringValues[0]==null && cellStringValues[1]!=null && cellStringValues[2]==null && cellStringValues[3]==null && cellStringValues[4]==null) {
			return true;
		} else if(cellStringValues[0]==null && cellStringValues[1]==null && cellStringValues[2]!=null && cellStringValues[3]==null && cellStringValues[4]==null) {
			return true;
		} else if(cellStringValues[0]==null && cellStringValues[1]==null && cellStringValues[2]==null && cellStringValues[3]!=null && cellStringValues[4]==null) {
			return true;
		} else if(cellStringValues[0]==null && cellStringValues[1]==null && cellStringValues[2]==null && cellStringValues[3]==null && cellStringValues[4]!=null) {
			return true;
		}
		
		return false;
	}

	public static List<String[]> normalizeCells(List<String[]> cells) {
		List<String[]> normalizedCells = new ArrayList<String[]>();
		
		String[] previousNotNormalizedCell = {null, null, null, null, null};
		for(String[] notNormalizedCell : cells) {
			log.debug("previousNotNormalizedCell=" + dumpCellToString(previousNotNormalizedCell));
			log.debug("notNormalizedCell=" + dumpCellToString(notNormalizedCell));
			
			List<String[]> innerNormalizedCells = new ArrayList<String[]>();
			innerNormalizedCells = normalizeOneCell(notNormalizedCell, previousNotNormalizedCell);
			
			List<String[]> innerInnerNormalizedCells = new ArrayList<String[]>();
			for(String[] innerNormalizedCell : innerNormalizedCells) {
				innerInnerNormalizedCells.add(normalizeOnlyOneCell(innerNormalizedCell, previousNotNormalizedCell));
				previousNotNormalizedCell = updatePreviousNotNormalizedCell(previousNotNormalizedCell, innerNormalizedCell);
			}

			for(String[] innerNormalizedCell : innerNormalizedCells) {
				log.debug("innerNormalizedCells=" + dumpCellToString(innerNormalizedCell));
			}
			
			normalizedCells.addAll(innerInnerNormalizedCells);

			log.debug("-------- normalized cell ratio 1:" + innerNormalizedCells.size());
		}

		return normalizedCells;
	}

	// -------------------------------------------------------------------------
	
	private static String[] updatePreviousNotNormalizedCell(String[] previousNotNormalizedCell, String[] notNormalizedCell) {
		String[] updatedPreviousNotNormalizedCell = { null, null, null, null, null};
		for (int i = 0; i < notNormalizedCell.length; i++) {
			String normalizedCell = notNormalizedCell[i];
			if(normalizedCell!=null) {
				updatedPreviousNotNormalizedCell[i]=normalizedCell;
			} else {
				updatedPreviousNotNormalizedCell[i]=previousNotNormalizedCell[i];
			}
		}
		
		return updatedPreviousNotNormalizedCell;
	}

	public static List<String[]> normalizeOneCell(String[] notNormalizedCell, String[] previousNotNormalizedCell) {
		List<String[]> normalizedCells = new ArrayList<String[]>();

		for (int i = 0; i < notNormalizedCell.length; i++) {
			String cellValue = notNormalizedCell[i];
			if(cellValue!=null) {
				String[] normalizedCell = {null, null, null, null, null};
				normalizedCell[i]=cellValue;
				normalizedCells.add(normalizedCell);
			}
		}
		
		return normalizedCells;
	}

	private static String[] normalizeOnlyOneCell(String[] notNormalizedCell, String[] previousNotNormalizedCell) {
		validateOnlyOneCellNotNull(notNormalizedCell);

		String[] ret = {null, null, null, null, null};
		if(notNormalizedCell[0]==null && notNormalizedCell[1]==null && notNormalizedCell[2]==null && notNormalizedCell[3]==null && notNormalizedCell[4]!=null) {
			if(previousNotNormalizedCell[0]!=null && previousNotNormalizedCell[1]!=null && previousNotNormalizedCell[2]!=null && previousNotNormalizedCell[3]!=null) {
				int a = 4;
				int b = 5;
				for (int k = 0; k < a; k++) {
					ret[k]=previousNotNormalizedCell[k];
				}
				for (int k = a; k < b; k++) {
					ret[k]=notNormalizedCell[k];
				}
			} else {
				log.warn("previousNotNormalizedCell[0]!=null && previousNotNormalizedCell[1]!=null && previousNotNormalizedCell[2]!=null && previousNotNormalizedCell[3]!=null was expected");
			}
		} else if(notNormalizedCell[0]==null && notNormalizedCell[1]==null && notNormalizedCell[2]==null && notNormalizedCell[3]!=null && notNormalizedCell[4]==null) {
			if(previousNotNormalizedCell[0]!=null && previousNotNormalizedCell[1]!=null && previousNotNormalizedCell[2]!=null) {
				int a = 3;
				int b = 4;
				for (int k = 0; k < a; k++) {
					ret[k]=previousNotNormalizedCell[k];
				}
				for (int k = a; k < b; k++) {
					ret[k]=notNormalizedCell[k];
				}
			} else {
				log.warn("previousNotNormalizedCell[0]!=null && previousNotNormalizedCell[1]!=null && previousNotNormalizedCell[2]!=null was expected");
			}
		} else if(notNormalizedCell[0]==null && notNormalizedCell[1]==null && notNormalizedCell[2]!=null && notNormalizedCell[3]==null && notNormalizedCell[4]==null) {
			if(previousNotNormalizedCell[0]!=null && previousNotNormalizedCell[1]!=null) {
				int a = 2;
				int b = 3;
				for (int k = 0; k < a; k++) {
					ret[k]=previousNotNormalizedCell[k];
				}
				for (int k = a; k < b; k++) {
					ret[k]=notNormalizedCell[k];
				}
			} else {
				log.warn("previousNotNormalizedCell[0]!=null && previousNotNormalizedCell[1]!=null was expected");
			}
		} else if(notNormalizedCell[0]==null && notNormalizedCell[1]!=null && notNormalizedCell[2]==null && notNormalizedCell[3]==null && notNormalizedCell[4]==null) {
			if(previousNotNormalizedCell[0]!=null) {
				int a = 1;
				int b = 2;
				for (int k = 0; k < a; k++) {
					ret[k]=previousNotNormalizedCell[k];
				}
				for (int k = a; k < b; k++) {
					ret[k]=notNormalizedCell[k];
				}
			} else {
				log.warn("previousNotNormalizedCell[0]!=null && previousNotNormalizedCell[1]!=null && previousNotNormalizedCell[2]!=null && previousNotNormalizedCell[3]!=null was expected");
			}
		} else if(notNormalizedCell[0]!=null && notNormalizedCell[1]==null && notNormalizedCell[2]==null && notNormalizedCell[3]==null && notNormalizedCell[4]==null) {
			int a = 0;
			int b = 1;
			for (int k = 0; k < a; k++) {
				ret[k]=previousNotNormalizedCell[k];
			}
			for (int k = a; k < b; k++) {
				ret[k]=notNormalizedCell[k];
			}
		}

		return ret;
	}

	// -------------------------------------------------------------------------

	private static void dumpSheet(HSSFSheet sheet) {
		if(sheet==null) {
            log.debug("The sheet to dump is null!");
            return;
		}
		
		for (int i = sheet.getFirstRowNum(); i <= sheet.getLastRowNum() ; i++) {
        	log.debug("row#" + i + "=");
        	HSSFRow row = sheet.getRow(i);
        	if(row==null) {
            	log.debug("\t| " + NULL_RAPPRESENTATION);
        		continue;
        	}
            for (int j = row.getFirstCellNum(); j <= row.getLastCellNum() ; j++) {
            	HSSFCell cell = row.getCell((short) j);
            	String cellValue = getCellValue(cell);
            	log.debug("\t| " + cellValue );
            }
            log.debug("\n");
		}
	}

    public static String getCellStringValue(HSSFSheet sheet, int row, short column) {
		HSSFRow cellRow = sheet.getRow(row);
		return getCellStringValue(cellRow, column);
    }
    
    public static String getCellStringValue(HSSFRow cellRow, short column) {
		HSSFCell cell = cellRow!=null ? cellRow.getCell(column) : null;
		return getCellValue(cell);
    }

    public static String getCellValue(HSSFCell cell) {
    	switch (cell!=null ? cell.getCellType() : Integer.MIN_VALUE) {
		case HSSFCell.CELL_TYPE_STRING:
			return cell.getStringCellValue();
		case HSSFCell.CELL_TYPE_NUMERIC:
			return String.valueOf(cell.getNumericCellValue());
		default:
			return NULL_RAPPRESENTATION;
		}
	}

	private static void dumpCells(List<String[]> cells) {
		for (Iterator<String[]> cellsIter = cells.iterator(); cellsIter.hasNext();) {
	    	String[] cellStringValues = cellsIter.next();
	    	log.debug(" - " + dumpCellToString(cellStringValues));
		}
	}

	public static String dumpCellToString(String[] cellStringValues) {
		return cellStringValues[0] + "," + cellStringValues[1] + "," + cellStringValues[2] + "," + cellStringValues[3] + "," + cellStringValues[4];
	}

}
