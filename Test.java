import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Test {

    public static void main(String[] args) {
        int randomNumberArraySize = 100000;
        int inputArraySize = 4;
        int loop = 10;
        printHeader();

        long totalHashSearchTime = 0;
        long totalBinarySearchTime = 0;
        long totalSequentialSearchTime = 0;
//        List<Integer> inputNumberList = input();
//        printHeader();
        for (int i = 0; i < loop; i++) {
            List<Integer> randomNumberList      = generateUniqueRandomNumbers(randomNumberArraySize, Integer.MAX_VALUE);
            List<Integer> inputNumberList       = genInputNumberList(randomNumberList, inputArraySize, randomNumberArraySize);

            SearchResult hashSearchResult       = hashSearch(inputNumberList, randomNumberList);
            SearchResult binarySearchResult     = binarySearch(inputNumberList, randomNumberList);
            SearchResult sequentialSearchResult = sequentialSearch(inputNumberList, randomNumberList);

            totalHashSearchTime         += hashSearchResult.getTimeAll();
            totalBinarySearchTime       += binarySearchResult.getTimeAll();
            totalSequentialSearchTime   += sequentialSearchResult.getTimeAll();

            printSearchResults(hashSearchResult, binarySearchResult, sequentialSearchResult);
        }

        System.out.println("Thoi gian trung binh:");
        System.out.printf("%-15d | %-15d | %-18d%n",
                totalHashSearchTime / loop,
                totalBinarySearchTime / loop,
                totalSequentialSearchTime / loop);
    }

    public static List<Integer> genInputNumberList(
            List<Integer> randomNumberList,
            int inputArraySize,
            int randomNumberArraySize) {
        List<Integer> inputNumberList = new ArrayList<>();
        List<Integer> inputIndices = generateUniqueRandomNumbers(inputArraySize, randomNumberArraySize);
        for (int index = 0; index < inputArraySize; index++) {
            inputNumberList.add(randomNumberList.get(inputIndices.get(index)));
        }
        return inputNumberList;
    }

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

    public static List<Integer> input() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("NHAP KICH THUOC MANG: ");
        int n = scanner.nextInt();
        List<Integer> listNumber = new ArrayList<>();
        System.out.println("NHAP CAC PHAN TU CUA MANG:");
        for (int i = 0; i < n; i++) {
            System.out.print("Phan tu thu " + (i + 1) + ": ");
            listNumber.add(scanner.nextInt());
        }
        return listNumber;
    }

    private static void printHeader() {
        System.out.printf("%-14s | %-14s | %-18s%n", "Hash Search", "Binary Search", "Sequential Search");
        System.out.printf("----------------------------------------------------------------------------%n");
        System.out.printf("%-7s | %-4s | %-8s | %-3s | %-18s%n", "HashSet", "All", "SortList", "All", "");
    }

    private static void printSearchResults(
            SearchResult hashSearchResult,
            SearchResult binarySearchResult,
            SearchResult sequentialSearchResult) {
        if (!(hashSearchResult.getResult().size() == binarySearchResult.getResult().size() &&
                hashSearchResult.getResult().size() == sequentialSearchResult.getResult().size())) {
            System.out.println("1 TRONG CAC THUAT TOAN TIM KIEM BAI");
            return;
        }

        String resultString = hashSearchResult.getResult().stream()
                .map(String::valueOf)
                .collect(Collectors.joining(", "));

        System.out.printf("%-7d | %-4d | %-8d | %-3d | %-18s | %-18s\n",
                hashSearchResult.getTimeHashSet(),
                hashSearchResult.getTimeAll(),
                binarySearchResult.getTimeSortList(),
                binarySearchResult.getTimeAll(),
                sequentialSearchResult.getTimeAll(),
                resultString);
    }

    public static ArrayList<Integer> generateUniqueRandomNumbers(int length, int maxValue) {
        Set<Integer> uniqueNumbers = new HashSet<>(length);
        Random random = new Random();

        while (uniqueNumbers.size() < length) {
            int randomNumber = random.nextInt(maxValue);
            uniqueNumbers.add(randomNumber);
        }
        return new ArrayList<>(uniqueNumbers);
    }

    public static void writeFile(Set<Integer> numbers, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (int number : numbers) {
                writer.write(number + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Integer> readFile(String filePath) {
        List<Integer> numberList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    int number = Integer.parseInt(line.trim());
                    numberList.add(number);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return numberList;
    }
}

class SearchResult {
    long timeAll = 0l;
    long timeSortList = 0l;
    long timeHashSet = 0l;
    List<Integer> result = new ArrayList<>();

    public long getTimeSortList() {
        return this.timeSortList;
    }

    public void setTimeSortList(long timeSortList) {
        this.timeSortList = timeSortList;
    }

    public long getTimeHashSet() {
        return this.timeHashSet;
    }

    public void setTimeHashSet(long timeHashSet) {
        this.timeHashSet = timeHashSet;
    }

    public long getTimeAll() {
        return this.timeAll;
    }

    public void setTimeAll(long timeAll) {
        this.timeAll = timeAll;
    }

    public List<Integer> getResult() {
        return this.result;
    }

    public void addResult(Integer item) {
        this.result.add(item);
    }

}
