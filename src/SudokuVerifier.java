
public class SudokuVerifier {

	//sudoku checker modified from the original code by Ibrahim Ali
	//https://codereview.stackexchange.com/a/180840
	
	public static final int LENGTH = 81;
	public static final int ROW_LENGTH = 9;

	public int verify(String candidateSolution) {
		int[][] sudoku = new int[ROW_LENGTH][ROW_LENGTH];
		char[] arrayChars;
		int sudokuRowSize = 0;
		int sudokuColumnSize = 0;
		
		if (!lengthChecker(candidateSolution)) {
			return -1;
		} else {
			arrayChars = candidateSolution.toCharArray();
			for (int loop = 0; loop < arrayChars.length; loop++) {
				try {
					int number = Integer.parseInt("" + arrayChars[loop]);
					if (number > 0 && number <= 9) {
						if (loop > 0 && loop % 9 == 0) {
							sudokuColumnSize = 0;
							sudokuRowSize++;
						}
						sudoku[sudokuRowSize][sudokuColumnSize] = number;
						sudokuColumnSize++;
					} else {
						return -1;
					}
				} catch (NumberFormatException ne) {
					ne.printStackTrace();
					return 1;
				}
			}

			return sudokuRuleChecker(sudoku);
		}
	}

	//checks whether if any of the rules are broken (columns, rows, sub-grids).
	private int sudokuRuleChecker(int[][] sudoku) {
		int result = 0;

		for (int i = 0; i < ROW_LENGTH; i += 3) {
			for (int j = 0; j < ROW_LENGTH; j += 3) {
				if (duplicateChecker(gridToArrayConverter(i, j, sudoku))) {
					return -2;
				}
			}
		}

		for (int loop = 0; loop < ROW_LENGTH; loop++) {
			if (duplicateChecker(sudoku[loop])) {
				return -3;
			}
		}

		for (int loop = 0; loop < ROW_LENGTH; loop++) {
			int[] arrayToCheck = new int[9];
			for (int loop2 = 0; loop2 < ROW_LENGTH; loop2++) {
				arrayToCheck[loop2] = sudoku[loop2][loop];
			}
			if (duplicateChecker(arrayToCheck)) {
				return -4;
			}
		}

		return result;
	}

	
	//checks whether a single dimensional array has any duplicates
	public boolean duplicateChecker(int[] arrayToCheck) {
		boolean result = false;
		for (int loop = 0; loop < ROW_LENGTH; loop++) {
			int numberToCheck = arrayToCheck[loop];
			for (int innerLoop = 0; innerLoop < ROW_LENGTH; innerLoop++) {
				if (innerLoop != loop && numberToCheck == arrayToCheck[innerLoop]) {
					result = true;
					break;
				}
			}
		}

		return result;
	}
	
	//Converts two dimensional array to a single dimensional array
	public int[] gridToArrayConverter(int i, int j, int[][] array) {
		int[] result = new int[ROW_LENGTH];
		int resultIndex = 0;
		for (int k = i; k < (i + 3); k++) {
			for (int l = j; l < (j + 3); l++) {
				result[resultIndex] = array[k][l];
				resultIndex++;
			}
		}
		return result;

	}

	// checks whether String is LENGTH characters long
	public boolean lengthChecker(String stringToCheck) {
		boolean result = false;
		if (stringToCheck.length() == LENGTH) {
			result = true;
		}
				return result;
	}

public static void main(String[] args) {
		SudokuVerifier sudokuObject = new SudokuVerifier();
		int result = sudokuObject
				.verify("417369825632158947958724316825437169791586432346912758289643571573291684164875293");
		System.out.println("Result: " + result);
	} 
}