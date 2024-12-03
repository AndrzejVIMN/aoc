package tech.paramount.andrzejvimn.aoc.y2022;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Day7 {

    private static final String INPUT_FILE = "2022/day7-input.txt";

    public static void solve() {
        var input = Reader.readLines(INPUT_FILE);
        readFiles(input);
        Directory.allDirectories.forEach(dir -> System.out.println(dir.name + " " + getSize(dir)));
        var result = Directory.allDirectories.stream().filter(dir -> getSize(dir) <= 100_000).mapToInt(Day7::getSize).sum();
        System.out.println(result);
    }
    
    public static void solve2() {
        var input = Reader.readLines(INPUT_FILE);
        var topDir = readFiles(input);
        int total = 70_000_000;
        var required = 30_000_000;
        var initialFree = total - getSize(topDir);
        var toDelete = required - initialFree;
        var smallestMatchingDir = Directory.allDirectories.stream()
                .filter(dir -> getSize(dir) > toDelete)
                .min(Comparator.comparing(Day7::getSize))
                .orElseThrow();
        System.out.println(smallestMatchingDir + " size: " + getSize(smallestMatchingDir));
    }

    private static class File {
        @Override
        public String toString() {
            return "File{" +
                    "name='" + name + '\'' +
                    ", size=" + size +
                    '}';
        }

        private final String name;
        private final int size;

        private final Directory parentDir;

        private File(String name, int size, Directory parentDir) {
            this.name = name;
            this.size = size;
            this.parentDir = parentDir;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            File file = (File) o;
            return size == file.size && name.equals(file.name) && Objects.equals(parentDir, file.parentDir);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, size, parentDir);
        }
    }

    private static class Directory {
        @Override
        public String toString() {
            return "Directory{" +
                    "name='" + name + '\'' +
                    ", files=" + files +
                    ", subdirectories=" + subdirectories +
                    '}';
        }

        private static final Set<Directory> allDirectories = new HashSet<>();

        private final String name;
        private final Set<File> files = new HashSet<>();
        private final Set<Directory> subdirectories = new HashSet<>();

        private final Directory parentDir;

        private Directory(String name, Directory parentDir) {
            this.name = name;
            this.parentDir = parentDir;
            allDirectories.add(this);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Directory directory = (Directory) o;
            return name.equals(directory.name) && Objects.equals(parentDir, directory.parentDir);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, parentDir);
        }
    }

    private static int getSize(Directory directory) {
        int size = 0;
        for (var file : directory.files) {
            size += file.size;
        }
        for (var subdir : directory.subdirectories) {
            size += getSize(subdir);
        }
        return size;
    }

    private static Directory readFiles(List<String> input) {
        Directory topDir = new Directory("/", null);
        Directory currentDir = topDir;

        for (var line : input) {
            if (line.equals("$ cd ..")) {
                currentDir = currentDir.parentDir;
            } else if (line.matches("\\$ cd [^/]+")) {
                String name = line.substring(5);
                currentDir = currentDir.subdirectories.stream().filter(sub -> sub.name.equals(name)).findFirst().orElseThrow();
            } else if (line.equals("$ ls")) {
                continue;
            } else if (line.matches("dir .+")) {
                String name = line.substring(4);
                currentDir.subdirectories.add(new Directory(name, currentDir));
            } else if (line.matches("\\d+ .+")) {
                String size = line.split(" ")[0];
                String name = line.split(" ")[1];
                currentDir.files.add(new File(name, Integer.parseInt(size), currentDir));
            }
        }

        return topDir;
    }
}
