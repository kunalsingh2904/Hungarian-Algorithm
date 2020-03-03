import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class JUnitTest {

  @Test
public void test() { // Test case 1
    int[][] matrix = { { 10, 20, 30, 40 }, { 50, 60, 70, 80 }, { 40, 30, 20, 10
      }, { 80, 70, 60, 50 } };
    TestProgram kk1 = new TestProgram(matrix);
    int result = kk1.get_value();
    assertEquals(140,result);
  }

  @Test
public void test2() {   // Test case 2
    int[][] matrix1 = { { 2500, 4000, 3500 }, { 4000, 6000, 3500 }, { 2000, 4000,
        2500 } };
    TestProgram kk2 = new TestProgram(matrix1);
    int result2 = kk2.get_value();
    assertEquals(9500,result2);
  }
  
  @Test
public void test3() {   // Test case 3
    int[][] matrix = { { 1500, 4000, 4500 }, { 2000, 6000, 3500 }, { 2000, 4000,
        2500 } };
    TestProgram kk2 = new TestProgram(matrix);
    int result2 = kk2.get_value();
    assertEquals(8500,result2);
  }
}
