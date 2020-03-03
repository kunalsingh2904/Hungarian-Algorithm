import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

public class TestProgram {
  int[][] matrix;

  public TestProgram(int[][] matrix) {
    this.matrix = matrix;
  }

  /**
   * Main function.
   */
  public static void main(String[] args) {  //main function

    int rows = 0; // counting number of lines in file
    int columns = 0;
    // finding row and col length
    try (Scanner input = new Scanner(new BufferedReader(new FileReader(args[0])))) {  
      while (input.hasNextLine()) {
        ++rows;
        String[] line = input.nextLine().trim().split(" ");
        columns = line.length;
      }
      input.close();
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(1);
    }
    int[][] matrix = new int[rows][columns];
    // System.out.println(rows + " " + columns);
    // read in the data
    try (Scanner input = new Scanner(new File(args[0]))) { // storing in matrix
      for (int i = 0; i < rows; ++i) {
        for (int j = 0; j < columns; ++j) {
          if (input.hasNextInt()) {
            matrix[i][j] = input.nextInt();
          }
        }
      }
      input.close();
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(1);
    }
    // duplicate of matrix
    int[][] duplicateMatrix = new int[matrix.length][matrix[0].length];
    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix[0].length; j++) {
        duplicateMatrix[i][j] = matrix[i][j];
      }
    }

    // find optimal assignment
    HungarianAlgorithm ha = new HungarianAlgorithm(matrix);
    int[][] assignment = ha.findOptimalAssignment();
    int totalCost = 0;
    if (assignment.length > 0) { // print assignment
      System.out.println("Worker(row) <> Work(col) <> Cost\n");
      for (int[] anAssignment : assignment) {
        System.out.println(" " + anAssignment[1] + "            " + anAssignment[0] + "         "
                  + duplicateMatrix[anAssignment[1]][anAssignment[0]]);
        totalCost += duplicateMatrix[anAssignment[1]][anAssignment[0]];
      }
    } else {
      System.out.println("no assignment found!");
    }
    System.out.println("\nTotal cost: " + totalCost);

  }
  
  /**
     * return minimum cost.
     * function is made callable from other file
     */
  public int get_value() { // return minimum cost
    // duplicate of matrix
    int[][] duplicateMatrix = new int[matrix.length][matrix[0].length];
    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix[0].length; j++) {
        duplicateMatrix[i][j] = matrix[i][j];
      }
    }

    // find optimal assignment
    HungarianAlgorithm ha = new HungarianAlgorithm(matrix);
    int[][] assignment = ha.findOptimalAssignment();
    int totalCost = 0;
    if (assignment.length > 0) { // print assignment
      System.out.println("Worker(row) <> Work(col) <> Cost\n");
      for (int[] anAssignment : assignment) {
        System.out.println(" " + anAssignment[1] + "            " + anAssignment[0] + "          "
                  + duplicateMatrix[anAssignment[1]][anAssignment[0]]);
        totalCost += duplicateMatrix[anAssignment[1]][anAssignment[0]];
      }
    } else {
      System.out.println("no assignment found!");
    }
    System.out.println("\nTotal cost: " + totalCost);
    System.out.println();
    return totalCost;
  }
}
