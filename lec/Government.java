/** A rather contrived exercise to test your understanding of when
 nested classes may be made static. This is NOT an example of good
 class design, even after you fix the bug.

 The challenge with this file is to delete the keyword static the
 minimum number of times so that the code compiles.

 Guess before TRYING to compile, otherwise the compiler will spoil
 the problem.*/
    public class Government {

        // 原错误：public void main(String []){
        // 编译错误原因1：`String []` 后面缺少参数名（标识符）。
        // 编译错误原因2：方法声明为 `void`（无返回值），但方法体中有 `return 0;`。
        //
        // 修正方案：
        // 1. 为 `String []` 参数添加一个标识符，例如 `args`。
        // 2. 将方法的返回类型从 `void` 修改为 `int`，以匹配 `return 0;`。
        //    （注意：这个 `main` 方法不是 Java 程序的标准入口点，因为它缺少 `static` 关键字。
        //    但根据题目要求是“删除 static 的最小次数”，此处不涉及删除，只是修正语法。）
        public int main(String[] args){ // 修正了参数声明和返回类型
            return 0;
        }
        private int treasury = 5;

        private void spend() {
            treasury -= 1;
        }

        private void tax() {
            treasury += 1;
        }

        public void report() {
            System.out.println(treasury);
        }

        public static Government greaterTreasury(Government a, Government b) {
            if (a.treasury > b.treasury) {
                return a;
            }
            return b;
        }

        public class Peasant {
            public void doStuff() {
                System.out.println("hello");
            }
        }

        public class King {
            public void doStuff() {
                spend();
            }
        }

        public class Mayor {
            public void doStuff() {
                tax();
            }
        }

        public class Accountant {
            public void doStuff() {
                report();
            }
        }

        public class Thief {
            public void doStuff() {
                treasury = 0;
            }
        }

        public static class Explorer {
            public void doStuff(Government a, Government b) {
                Government favorite = Government.greaterTreasury(a, b);
                System.out.println("The best government has treasury " + favorite.treasury);
            }
        }
    }