import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

public class HungarianAlgorithm {

  int[][] matrix; // initial cost matrix

  // markers in the matrix
  int[] rowHasSquare;
  int[] colHasSquare;
  int[] coveredRows;
  int[] coveredCols;
  int[] staredZeroesInRow;
  
  /** 
     * HungarianAlgorithm.  
     * constructor for class 
     * take input as a matrix
     */
  public HungarianAlgorithm(int[][] matrix) {
    // checking square matrix  
    if (matrix.length != matrix[0].length) {
      try {
        throw new IllegalAccessException("Given matrix is not a square Matrix");
      } catch (IllegalAccessException ex) {
        System.err.println(ex);
        System.exit(1);
      }
    }

    this.matrix = matrix;
    rowHasSquare = new int[matrix.length]; // rowHasSquare & colHasSquare indicate the position
    colHasSquare = new int[matrix[0].length]; // of the marked zeroes

    coveredRows = new int[matrix.length]; // indicates whether a row is covered
    coveredCols = new int[matrix[0].length]; // indicates whether a column is covered
    staredZeroesInRow = new int[matrix.length]; // storage for the 0*
    Arrays.fill(staredZeroesInRow, -1); // initialising with -1
    Arrays.fill(rowHasSquare, -1); // initialising with -1
    Arrays.fill(colHasSquare, -1); // initialising with -1
  }

  /**
   *  Return optimal solution.
   */
  public int[][] findOptimalAssignment() {
    reduceMatrix(); // reduce matrix
    independentZeros(); // mark independent zeroes in row and column
    coverColumns(); // cover columns which contain a marked zero

    while (!allColumnsAreCovered()) {
      int[] mainZero = choose_zero_To_assign();
      while (mainZero == null) { // while no zero found in choose_zero_To_assign
        findMinSubAdd();
        mainZero = choose_zero_To_assign();
      }
      if (rowHasSquare[mainZero[0]] == -1) {
        // there is no square mark in the mainZero line
        alternatingZeros(mainZero);
        coverColumns(); // cover columns which contain a marked zero
      } else {
        // there is square mark in the mainZero line
        // step 5
        coveredRows[mainZero[0]] = 1; // cover row of mainZero
        coveredCols[rowHasSquare[mainZero[0]]] = 0; // uncover column of mainZero
        findMinSubAdd();
      }
    }

    int[][] optimalAssignment = new int[matrix.length][];
    for (int i = 0; i < colHasSquare.length; i++) {
      optimalAssignment[i] = new int[] { i, colHasSquare[i] };
    }
    return optimalAssignment;//  returning location of assignment in matrix
  }

  /**
     * Check if all columns are covered. If that's the case then the optimal
     * solution is found. return true or false
     */
  private boolean allColumnsAreCovered() {
    for (int i : coveredCols) {
      if (i == 0) {
        return false;
      }
    }
    return true;
  }

  /*
     * Reduce the matrix so that in each row and column at least one zero
     * exists: 1. subtract each row minima from each element of the row 2. subtract
     * each column minima from each element of the column
     */
  private void reduceMatrix() {
    // rows
    for (int[] aaMatrix : matrix) {
      //find the min value of the current row
      int currentRowMin = matrix[0][0];
      for (int anAaMatrix : aaMatrix) {
        if (anAaMatrix < currentRowMin) {
          currentRowMin = anAaMatrix;
        }
      }
      // subtract min value from each element of the current row
      for (int k = 0; k < aaMatrix.length; k++) {
        aaMatrix[k] -= currentRowMin;
      }
    }

    // cols
    for (int i = 0; i < matrix[0].length; i++) {
      // find the min value of the current column
      int currentColMin = matrix[0][0];
      for (int[] aaMatrix : matrix) {
        if (aaMatrix[i] < currentColMin) {
          currentColMin = aaMatrix[i];
        }
      }
      // subtract min value from each element of the current column
      for (int[] aaMatrix : matrix) {
        aaMatrix[i] -= currentColMin;
      }
    }
  }

  /**
     * mark each 0 with a "square", if there are no other marked zeroes in
     * the same row or column.
     */
  private void independentZeros() {
    int[] rowHasSquare = new int[matrix.length];
    int[] colHasSquare = new int[matrix[0].length];

    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix.length; j++) {
        // mark if current value == 0 & there are no other marked zeroes in the same row
        // or column
        if (matrix[i][j] == 0 && rowHasSquare[i] == 0 && colHasSquare[j] == 0) {
          rowHasSquare[i] = 1;
          colHasSquare[j] = 1;
          rowHasSquare[i] = j; // save the row-position of the zero
          colHasSquare[j] = i; // save the column-position of the zero
        }
      }
    }
  }

  /*
     * Cover all columns which are marked with a "square"
     */
  private void coverColumns() {
    for (int i = 0; i < colHasSquare.length; i++) {
      coveredCols[i] = colHasSquare[i] == -1 ? 0 : 1;
    }
  }

  /*
     * Find zero value Z_0 and mark it as "0*".
     *
     * return position of Z_0 in the matrix
     */
  private int[] choose_zero_To_assign() {
    for (int i = 0; i < matrix.length; i++) {
      if (coveredRows[i] == 0) {
        for (int j = 0; j < matrix[i].length; j++) {
          if (matrix[i][j] == 0 && coveredCols[j] == 0) {
            staredZeroesInRow[i] = j; // mark as 0*
            return new int[] { i, j };
          }
        }
      }
    }
    return null;
  }

  /*
     * Create a chain K of alternating "squares" and "0*" mainZero => Z_0 of
     * Step 4
     */
  private void alternatingZeros(int[] mainZero) {
    int i = mainZero[0];
    int j = mainZero[1];

    Set<int[]> kk = new LinkedHashSet<>();
    // add Z_0 to kk
    kk.add(mainZero);
    boolean found = false;
    do {
      // add Z_1 to kk if
      // there is a zero Z_1 which is marked with a "square " in the column of Z_0
      if (colHasSquare[j] != -1) {
        kk.add(new int[] { colHasSquare[j], j });
        found = true;
      } else {
        found = false;
      }

      // if no zero element Z_1 marked with "square" exists in the column of Z_0, then
      // cancel the loop
      if (!found) {
        break;
      }

      // replace Z_0 with the 0* in the row of Z_1
      i = colHasSquare[j];
      j = staredZeroesInRow[i];
      // add the new Z_0 to K
      if (j != -1) {
        kk.add(new int[] { i, j });
        found = true;
      } else {
        found = false;
      }

    } while (found); // As long as no new "square" marks are found

    for (int[] zero : kk) {
      // remove all "square" marks in K
      if (colHasSquare[zero[1]] == zero[0]) {
        colHasSquare[zero[1]] = -1;
        rowHasSquare[zero[0]] = -1;
      }
      // replace the 0* marks in K with "square" marks
      if (staredZeroesInRow[zero[0]] == zero[1]) {
        rowHasSquare[zero[0]] = zero[1];
        colHasSquare[zero[1]] = zero[0];
      }
    }

    // remove all marks
    Arrays.fill(staredZeroesInRow, -1);
    Arrays.fill(coveredRows, 0);
    Arrays.fill(coveredCols, 0);
  }

  /*
     * 1. Find the smallest uncovered value in the matrix. 2. Subtract it
     * from all uncovered values 3. Add it to all twice-covered values
     */
  private void findMinSubAdd() {
    // Find the smallest uncovered value in the matrix
    int minUncoveredValue = Integer.MAX_VALUE;
    for (int i = 0; i < matrix.length; i++) {
      if (coveredRows[i] == 1) {
        continue;
      } // finding minima
      for (int j = 0; j < matrix[0].length; j++) {
        if (coveredCols[j] == 0 && matrix[i][j] < minUncoveredValue) {
          minUncoveredValue = matrix[i][j];
        }
      }
    }

    if (minUncoveredValue > 0) {
      for (int i = 0; i < matrix.length; i++) {
        for (int j = 0; j < matrix[0].length; j++) {
          if (coveredRows[i] == 1 && coveredCols[j] == 1) {
            // Add min to all twice-covered values
            matrix[i][j] += minUncoveredValue;
          } else if (coveredRows[i] == 0 && coveredCols[j] == 0) {
            // Subtract min from all uncovered values
            matrix[i][j] -= minUncoveredValue;
          }
        }
      }
    }
  }
}