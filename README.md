# **Báo cáo Chương trình Tìm kiếm**
### **Tác giả**: Trịnh Văn Long

## **1. Giới thiệu**
Chương trình này thực hiện tìm kiếm một mảng con trong một mảng lớn ngẫu nhiên bằng ba phương pháp khác nhau: **Tìm kiếm Băm (Hash Search)**, **Tìm kiếm Nhị phân (Binary Search)** và **Tìm kiếm Tuần tự (Sequential Search)**. Mục tiêu là so sánh tốc độ tìm kiếm của từng phương pháp để xác định phương pháp hiệu quả nhất trong các tình huống khác nhau.<br>
- **online compiler**  : https://www.programiz.com/java-programming/online-compiler/

## **2. Mô tả Chương trình**

### **2.1. Tạo Mảng Số Ngẫu nhiên**
Chương trình sử dụng các tham số sau để tạo mảng và thực hiện tìm kiếm:
- **randomNumberArraySize**: Kích thước của mảng số ngẫu nhiên.
- **inputArraySize**: Kích thước của mảng con cần tìm kiếm.
- **loop**: Số lần thực hiện tìm kiếm để tính toán thời gian trung bình.

**Để tạo mảng con:**
- Dùng hàm `input()` để nhập các số vào mảng con.
- Hoặc dùng hàm `genInputNumberList()` để tạo mảng con ngẫu nhiên từ mảng lớn.

### **2.2. Các Phương pháp Tìm kiếm**
1. **Hash Search**: Sử dụng cấu trúc dữ liệu HashSet để lưu trữ va kiểm tra sự tồn tại của các phần tử trong mảng con. Đây là phương pháp tìm kiếm nhanh và hiệu quả nhất trong hầu hết các trường hợp.
2. **Binary Search**: Tìm kiếm nhị phân thực hiện trên mảng đã sắp xếp. Tuy nhiên, việc sắp xếp mảng trước khi tìm kiếm làm tăng thời gian thực thi.
3. **Sequential Search**: Duyệt tuần tự qua mảng lớn để tìm kiếm từng phần tử của mảng con. Phương pháp này đơn giản nhưng kém hiệu quả khi kích thước mảng tăng lên.

## **3. Kết quả Thực nghiệm**
**Hash Search**: Cho ra thời gian tìm kiếm nhanh nhất ổn định và nhanh nhất. <br>
**Sequential Search**:  Cho ra thời gian tìm kiếm nhanh nhất khi mảng con nhỏ và chậm dần khi mảng con có độ dài tăng dần. <br>
**Binary Search**: Cho ra thời gian tìm kiếm chậm nhất, **do mảng cần sort trước khi tìm kiếm**. <br>

### 3.1. **Mảng con cần tìm kiếm có kích thước lớn:**
```
int randomNumberArraySize = 100000;
int inputArraySize = 400;
int loop = 10;
Hash Search    | Binary Search  | Sequential Search
----------------------------------------------------------------------------
HashSet | All  | SortList | All |
10      | 10   | 39       | 39  | 50                 |
9       | 9    | 23       | 23  | 36                 |
3       | 3    | 18       | 18  | 15                 |
3       | 3    | 16       | 17  | 18                 |
2       | 3    | 16       | 16  | 14                 |
3       | 3    | 17       | 17  | 15                 |
3       | 3    | 17       | 17  | 15                 |
3       | 3    | 15       | 16  | 16                 |
3       | 3    | 17       | 17  | 16                 |
4       | 4    | 20       | 20  | 17                 |
Thời gian trung bình:
4               | 20              | 21
```
### 3.2. **Mảng con cần tìm kiếm có kích thước nhỏ:**
**Sequential Search**:  Cho ra thời gian tìm kiếm nhanh nhất. <br>
```
int randomNumberArraySize = 100000;
int inputArraySize = 4;
int loop = 10;
Hash Search    | Binary Search  | Sequential Search 
----------------------------------------------------------------------------
HashSet | All  | SortList | All |                   
10      | 10   | 44       | 44  | 13                 | 1004890321, 84666096, 346344239, 2095244955
7       | 7    | 22       | 22  | 1                  | 2083130887, 952599133, 1607062573, 1103162952
4       | 4    | 17       | 17  | 1                  | 1459037563, 453397790, 1556107493, 1316499904
3       | 3    | 17       | 17  | 1                  | 992184502, 1089004858, 1134109967, 826338137
3       | 3    | 16       | 16  | 1                  | 1047355103, 1691279107, 549648269, 1831266892
2       | 2    | 18       | 18  | 1                  | 1100559941, 1038418762, 487172290, 1291938995
2       | 2    | 33       | 33  | 1                  | 803141403, 1329432516, 1125537619, 1849960429
4       | 4    | 17       | 17  | 1                  | 756532626, 1665332066, 1163972814, 921650248
3       | 3    | 17       | 17  | 0                  | 1746758501, 424905048, 857552411, 21347313
17      | 17   | 18       | 18  | 1                  | 414671832, 1205350081, 99062414, 1544749528
Thời gian trung bình:
5               | 21              | 2
```
### 4. **Thuật Toán**
### 4.1. **Binary Search**
```
    public static SearchResult binarySearch(
            List<Integer> inputNumberList,
            List<Integer> uniqueRandomNumberList) {
        // init model
        SearchResult searchResult = new SearchResult();
        long timeStart = System.currentTimeMillis();

        // sort inputNumberList
        long timeSortStart = System.currentTimeMillis();
        Collections.sort(uniqueRandomNumberList);
        long timeSortEnd = System.currentTimeMillis();

        // find with binarySearchNumber algorithm
        for (int number : inputNumberList) {
            if (binarySearchNumber(uniqueRandomNumberList, number)) {
                searchResult.addResult(number);
            }
        }

        // write result
        long timeEnd = System.currentTimeMillis();
        searchResult.setTimeSortList(timeSortEnd - timeSortStart);
        searchResult.setTimeAll(timeEnd - timeStart);
        return searchResult;
    }
    private static boolean binarySearchNumber(List<Integer> sortedList, int target) {
        int left = 0;
        int right = sortedList.size() - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            int midValue = sortedList.get(mid);

            if (midValue == target) {
                return true;
            }
            if (midValue < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return false;
    }
```
### 4.2. **Sequential Search**
```
    public static SearchResult sequentialSearch(
            List<Integer> inputNumbers,
            List<Integer> uniqueNumbers) {

        // init model
        SearchResult searchResult = new SearchResult();
        long startTime = System.currentTimeMillis();

        // convert inputNumbers to set
        Set<Integer> uniqueNumberSet = new HashSet<>(uniqueNumbers);
        List<Integer> remainingInputNumbers = new ArrayList<>(inputNumbers);

        // find vs remove child number from set
        Iterator<Integer> iterator = remainingInputNumbers.iterator();
        while (iterator.hasNext()) {
            int inputNumber = iterator.next();
            if (uniqueNumberSet.contains(inputNumber)) {
                searchResult.addResult(inputNumber);
                uniqueNumberSet.remove(inputNumber);
            }
        }
        // write result
        long endTime = System.currentTimeMillis();
        searchResult.setTimeAll(endTime - startTime);
        return searchResult;
    }
```
### 4.3. **Hash Search**
```
    public static SearchResult hashSearch(
            List<Integer> inputNumberList,
            List<Integer> uniqueRandomNumberList) {
        // init model
        SearchResult searchResult = new SearchResult();
        long timeStart = System.currentTimeMillis();

        // convert list to set
        long timeConvertSetStart = System.currentTimeMillis();
        Set<Integer> numberSet = new HashSet<>(uniqueRandomNumberList);
        long timeConvertSetEnd = System.currentTimeMillis();

        // find number
        for (int number : inputNumberList) {
            if (numberSet.contains(number)) {
                searchResult.addResult(number);
            }
        }

        // write result
        long timeEnd = System.currentTimeMillis();
        searchResult.setTimeHashSet(timeConvertSetEnd - timeConvertSetStart);
        searchResult.setTimeAll(timeEnd - timeStart);
        return searchResult;
    }
```

