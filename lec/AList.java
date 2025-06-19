/** Array based list.
 *  @author Josh Hug
 */

public class AList {
    // 错误1：data 和 size 被声明为局部变量。
    // 问题：在构造函数中定义的变量，在构造函数执行结束后就会被销毁，
    //       其他方法无法访问它们，导致编译错误（"cannot find symbol"）。
    // 修正：将 data 和 size 声明为类的实例变量 (成员变量)，使其在对象生命周期内都可用。
    private int[] data; // 用于存储列表元素的底层数组
    private int size;   // 列表当前包含的元素数量

    /** Creates an empty list. */
    public AList() {
        // 错误2：数组声明语法错误 (int[100] data = {0};)。
        // 问题：Java 中数组大小是在创建时指定，不是在类型声明中。{0} 这种初始化方式也仅限声明时。
        // 修正：使用 new int[capacity] 来创建数组，并给一个初始容量。
        // 错误3：初始容量固定为100，没有考虑动态扩容。
        // 修正：为数组设置一个较小的初始容量，并后续引入扩容机制。
        data = new int[8]; // 设置一个初始容量，例如 8。
        size = 0;          // 初始时列表为空，元素数量为0。
    }

    // 修正4：添加私有辅助方法 `resize` 用于数组扩容。
    // 问题：原代码未考虑数组满的情况，`addLast` 会导致 `ArrayIndexOutOfBoundsException`。
    // 修正：当数组容量不足时，创建新数组并将旧元素复制过去。
    private void resize(int capacity) {
        int[] newData = new int[capacity];
        // 将旧数组中的有效元素复制到新数组
        System.arraycopy(data, 0, newData, 0, size);
        this.data = newData; // 更新引用，指向新数组
    }

    /** Inserts X into the back of the list. */
    public void addLast(int x) {
        // 修正5：在添加元素前检查是否需要扩容。
        // 问题：如果 `size` 等于 `data.length`，直接 `data[size] = x` 会导致越界。
        // 修正：当数组已满时，调用 `resize` 方法，通常将其容量翻倍。
        if (size == data.length) {
            resize(data.length * 2); // 扩容为当前容量的两倍
        }
        data[size] = x;
        size += 1;
    }

    /** Returns the item from the back of the list. */
    public int getLast(){
        // 错误6：列表为空时返回 0，这可能导致歧义，且未处理越界异常。
        // 问题：如果列表中确实可以存储 0，则无法区分是真实元素还是空列表的指示。
        //       在空列表上 `data[size - 1]` 会导致 `ArrayIndexOutOfBoundsException`。
        // 修正：在访问前检查列表是否为空，为空则抛出 `IndexOutOfBoundsException`。
        if (size == 0) {
            throw new IndexOutOfBoundsException("Cannot get last item from an empty list.");
        }
        return data[size - 1]; // 最后一个元素的索引是 size - 1
    }

    /** Gets the ith item in the list (0 is the front). */
    public int get(int i) {
        // 错误7：未对传入索引 i 进行边界检查。
        // 问题：如果 i 超出有效范围 [0, size-1]，`data[i]` 会导致 `ArrayIndexOutOfBoundsException`。
        // 修正：检查 i 是否在有效范围内，否则抛出 `IndexOutOfBoundsException`。
        if (i < 0 || i >= size) {
            throw new IndexOutOfBoundsException("Index " + i + " is out of bounds for list of size " + size);
        }
        return data[i];
    }

    /** Returns the number of items in the list. */
    public int size() {
        // 此方法逻辑正确，前提是 size 是正确的实例变量。
        return size;
    }

    /** Deletes item from back of the list and
     * returns deleted item. */
    public int removeLast() {
        // 错误8：未对列表是否为空进行检查。
        // 问题：在空列表上调用 `data[size - 1]` 会导致 `ArrayIndexOutOfBoundsException`。
        // 修正：在操作前检查列表是否为空，为空则抛出 `IndexOutOfBoundsException`。
        if (size == 0) {
            throw new IndexOutOfBoundsException("Cannot remove last item from an empty list.");
        }
        // 错误9：`output` 变量未声明。
        // 修正：声明 `itemToReturn` 变量并赋值。
        int itemToReturn = data[size - 1];
        size -= 1; // 逻辑上移除元素，即减少 size。
        // 注意：对于原始类型（如 int），通常无需将数组位置清零。
        // 如果是对象类型，可以考虑 `data[size] = null;` 帮助垃圾回收。

        // 修正10 (可选优化)：考虑在元素减少到一定程度时进行缩容，以节省内存。
        // 例如：当 size 小于容量的 1/4 且容量大于初始容量时，将容量减半。
        // if (size > 0 && size < data.length / 4 && data.length > 8) {
        //     resize(data.length / 2);
        // }
        return itemToReturn;
    }
}
