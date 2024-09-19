import java.util.*;

public class StringMatcher {

    // Method to check if a string `s` contains a permutation of any concatenated subset of words
    public static boolean containsPermutationsOfSubsets(String s, List<String> words) {
        int numWords = words.size();
        for (int r = 1; r <= numWords; r++) {
            List<List<String>> subsets = getSubsets(words, r);
            for (List<String> subset : subsets) {
                String concatenated = String.join("", subset);
                Map<Character, Integer> targetCount = getFrequencyCount(concatenated);
                int targetLength = concatenated.length();

                if (s.length() < targetLength) {
                    continue;
                }

                Map<Character, Integer> windowCount = getFrequencyCount(s.substring(0, targetLength));
                
                if (isPermutation(windowCount, targetCount)) {
                    return true;
                }

                for (int i = targetLength; i < s.length(); i++) {
                    char startChar = s.charAt(i - targetLength);
                    char endChar = s.charAt(i);

                    windowCount.put(endChar, windowCount.getOrDefault(endChar, 0) + 1);
                    windowCount.put(startChar, windowCount.get(startChar) - 1);

                    if (windowCount.get(startChar) == 0) {
                        windowCount.remove(startChar);
                    }

                    if (isPermutation(windowCount, targetCount)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private static Map<Character, Integer> getFrequencyCount(String str) {
        Map<Character, Integer> countMap = new HashMap<>();
        for (char c : str.toCharArray()) {
            countMap.put(c, countMap.getOrDefault(c, 0) + 1);
        }
        return countMap;
    }

    private static boolean isPermutation(Map<Character, Integer> countMap1, Map<Character, Integer> countMap2) {
        return countMap1.equals(countMap2);
    }

    private static List<List<String>> getSubsets(List<String> list, int size) {
        List<List<String>> subsets = new ArrayList<>();
        generateSubsets(list, size, 0, new ArrayList<>(), subsets);
        return subsets;
    }

    private static void generateSubsets(List<String> list, int size, int index, List<String> current, List<List<String>> subsets) {
        if (current.size() == size) {
            subsets.add(new ArrayList<>(current));
            return;
        }
        if (index >= list.size()) {
            return;
        }

        current.add(list.get(index));
        generateSubsets(list, size, index + 1, current, subsets);

        current.remove(current.size() - 1);
        generateSubsets(list, size, index + 1, current, subsets);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Read the input string
        System.out.print("Enter the string s: ");
        String s = scanner.nextLine();

        int numWords = 0;
        while (true) {
            try {
                System.out.print("Enter the number of words: ");
                numWords = Integer.parseInt(scanner.nextLine());
                if (numWords <= 0) {
                    throw new NumberFormatException("Number must be positive.");
                }
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a positive integer.");
            }
        }

        List<String> words = new ArrayList<>();
        for (int i = 0; i < numWords; i++) {
            System.out.print("Enter word " + (i + 1) + ": ");
            words.add(scanner.nextLine());
        }

        // Close the scanner
        scanner.close();

        // Call the method and print the result
        boolean result = containsPermutationsOfSubsets(s, words);
        System.out.println("Contains permutation of any concatenated subset of words: " + result);
    }
}
