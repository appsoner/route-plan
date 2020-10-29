package com.qdcares.avms.inspection.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ServiceTest {


    @Setter
    @Getter
    static class Path implements Comparable<Path> {
        String name;
        String reverseName;
        PointDto point1;
        PointDto point2;

        public Path(PointDto point1, PointDto point2) {
            this.point1 = point1;
            this.point2 = point2;
            this.name = point1.getName() + point2.getName();
            this.reverseName = point2.getName() + point1.getName();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Path path = (Path) o;
            return Objects.equals(point1, path.point1) &&
                    Objects.equals(point2, path.point2);
        }

        @Override
        public int compareTo(Path o) {
            int scoreO = o.getPoint1().getIndex() + o.getPoint2().getIndex();
            int score = this.getPoint1().getIndex() + this.getPoint2().getIndex();
            return Integer.compare(score, scoreO);
        }

        PointDto getPoint(String level){
            return getPoints().stream().filter(i->i.getLevel().equals(level)).findFirst().orElse(null);
        }

        List<PointDto> getPoints() {
            return Lists.newArrayList(point1, point2);
        }
    }

    @Getter
    @Setter
    static class PointDto implements Comparable<PointDto> {
        String level;
        String code;
        Integer index;
        String name;

        public PointDto(String level, String code, Integer index) {
            this.level = level;
            this.code = code;
            this.index = index;
            this.name = level + "[" + index + "]";
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            PointDto pointDto = (PointDto) o;
            return Objects.equals(level, pointDto.level) &&
                    Objects.equals(code, pointDto.code) &&
                    Objects.equals(index, pointDto.index) &&
                    Objects.equals(name, pointDto.name);
        }

        @Override
        public int compareTo(PointDto pointDto) {
            return Integer.compare(this.getIndex(), pointDto.getIndex());
        }

        @Override
        public int hashCode() {
            return Objects.hash(level, index);
        }
    }

    public static void main(String[] args) {

        Map<String, String> levelToCode = Maps.newHashMapWithExpectedSize(5);
        levelToCode.put("C", "F2");
        levelToCode.put("B", "F3");
        levelToCode.put("A", "F4");
        levelToCode.put("a", "F");
        levelToCode.put("b", "E");
        levelToCode.put("c", "D");

        Map<String, String> codeToLevel = Maps.newHashMapWithExpectedSize(levelToCode.size());
        levelToCode.forEach((key, value) -> codeToLevel.put(value, key));

        Map<String, PointDto> aPoints = IntStream.range(1, 10).
                mapToObj(i -> new PointDto("a", levelToCode.get("a"), i)).
                collect(Collectors.toMap(PointDto::getName, pointDto -> pointDto));
        Map<String, PointDto> bPoints = IntStream.range(1, 10).
                mapToObj(i -> new PointDto("b", levelToCode.get("b"), i)).
                collect(Collectors.toMap(PointDto::getName, pointDto -> pointDto));

        Map<String, PointDto> cPoints = IntStream.range(1, 10).
                mapToObj(i -> new PointDto("c", levelToCode.get("c"), i)).
                collect(Collectors.toMap(PointDto::getName, pointDto -> pointDto));

        Map<String, PointDto> APoints = IntStream.range(1, 10).
                mapToObj(i -> new PointDto("A", levelToCode.get("A"), i)).
                collect(Collectors.toMap(PointDto::getName, pointDto -> pointDto));

        Map<String, PointDto> BPoints = IntStream.range(1, 10).
                mapToObj(i -> new PointDto("B", levelToCode.get("B"), i)).
                collect(Collectors.toMap(PointDto::getName, pointDto -> pointDto));
        Map<String, PointDto> CPoints = IntStream.range(1, 10).
                mapToObj(i -> new PointDto("C", levelToCode.get("C"), i)).
                collect(Collectors.toMap(PointDto::getName, pointDto -> pointDto));

        Map<String, Map<String, PointDto>> levelPoints = Maps.newHashMapWithExpectedSize(6);
        levelPoints.put("a", aPoints);
        levelPoints.put("b", bPoints);
        levelPoints.put("c", cPoints);
        levelPoints.put("A", APoints);
        levelPoints.put("B", BPoints);
        levelPoints.put("C", CPoints);

        Map<String, Path> aPaths = generatePath(aPoints);
        Map<String, Path> bPaths = generatePath(bPoints);
        Map<String, Path> cPaths = generatePath(cPoints);
        Map<String, Path> APaths = generatePath(APoints);
        Map<String, Path> BPaths = generatePath(BPoints);
        Map<String, Path> CPaths = generatePath(CPoints);

        Map<String, Map<String, Path>> levelToPaths = Maps.newHashMapWithExpectedSize(6);
        levelToPaths.put("a", aPaths);
        levelToPaths.put("b", bPaths);
        levelToPaths.put("c", cPaths);
        levelToPaths.put("A", APaths);
        levelToPaths.put("B", BPaths);
        levelToPaths.put("C", CPaths);

        List<Path> pathaC = Lists.newArrayList(new Path(CPoints.get("C[1]"), aPoints.get("a[9]")),
                new Path(CPoints.get("C[1]"), aPoints.get("a[7]")),
                new Path(CPoints.get("C[3]"), aPoints.get("a[9]")),
                new Path(CPoints.get("C[3]"), aPoints.get("a[7]")));

        List<Path> pathaB = Lists.newArrayList(new Path(BPoints.get("B[1]"), aPoints.get("a[6]")),
                new Path(BPoints.get("B[1]"), aPoints.get("a[4]")),
                new Path(BPoints.get("B[3]"), aPoints.get("a[6]")),
                new Path(BPoints.get("B[3]"), aPoints.get("a[4]")));
        List<Path> pathaA = Lists.newArrayList(new Path(APoints.get("A[1]"), aPoints.get("a[3]")),
                new Path(APoints.get("A[1]"), aPoints.get("a[1]")),
                new Path(APoints.get("A[3]"), aPoints.get("a[3]")),
                new Path(APoints.get("A[3]"), aPoints.get("a[1]")));

        List<Path> pathbC = Lists.newArrayList(new Path(CPoints.get("C[4]"), bPoints.get("b[9]")),
                new Path(CPoints.get("C[4]"), bPoints.get("b[7]")),
                new Path(CPoints.get("C[6]"), bPoints.get("b[9]")),
                new Path(CPoints.get("C[6]"), bPoints.get("b[7]")));
        List<Path> pathbB = Lists.newArrayList(new Path(BPoints.get("B[4]"), bPoints.get("b[6]")),
                new Path(BPoints.get("B[4]"), bPoints.get("b[4]")),
                new Path(BPoints.get("B[6]"), bPoints.get("b[6]")),
                new Path(BPoints.get("B[6]"), bPoints.get("b[4]")));
        List<Path> pathbA = Lists.newArrayList(new Path(APoints.get("A[4]"), bPoints.get("b[3]")),
                new Path(APoints.get("A[4]"), bPoints.get("b[1]")),
                new Path(APoints.get("A[6]"), bPoints.get("b[3]")),
                new Path(APoints.get("A[6]"), bPoints.get("b[1]")));

        List<Path> pathcC = Lists.newArrayList(new Path(CPoints.get("C[7]"), cPoints.get("c[9]")),
                new Path(CPoints.get("C[7]"), cPoints.get("c[7]")),
                new Path(CPoints.get("C[9]"), cPoints.get("c[9]")),
                new Path(CPoints.get("C[9]"), cPoints.get("c[7]")));
        List<Path> pathcB = Lists.newArrayList(new Path(BPoints.get("B[7]"), cPoints.get("c[6]")),
                new Path(BPoints.get("B[7]"), cPoints.get("c[4]")),
                new Path(BPoints.get("B[9]"), cPoints.get("c[6]")),
                new Path(BPoints.get("B[9]"), cPoints.get("c[4]")));
        List<Path> pathcA = Lists.newArrayList(new Path(APoints.get("A[7]"), cPoints.get("c[3]")),
                new Path(APoints.get("A[7]"), cPoints.get("c[1]")),
                new Path(APoints.get("A[9]"), cPoints.get("c[3]")),
                new Path(APoints.get("A[9]"), cPoints.get("c[1]")));

        Map<String, List<Path>> crossPathMap = Maps.newHashMapWithExpectedSize(9);
        crossPathMap.put("aC", pathaC);
        crossPathMap.put("Ca", pathaC);
        crossPathMap.put("aB", pathaB);
        crossPathMap.put("Ba", pathaB);
        crossPathMap.put("aA", pathaA);
        crossPathMap.put("Aa", pathaA);
        crossPathMap.put("bC", pathbC);
        crossPathMap.put("Cb", pathbC);
        crossPathMap.put("bB", pathbB);
        crossPathMap.put("Bb", pathbB);
        crossPathMap.put("bA", pathbA);
        crossPathMap.put("Ab", pathbA);
        crossPathMap.put("cC", pathcC);
        crossPathMap.put("Cc", pathcC);
        crossPathMap.put("cB", pathcB);
        crossPathMap.put("Bc", pathcB);
        crossPathMap.put("cA", pathcA);
        crossPathMap.put("Ac", pathcA);

        //F2->F->F4
        List<String> pathParams = Lists.newArrayList("F2", "F", "F4");
        List<String> pathLevels = pathParams.stream().map(codeToLevel::get).collect(Collectors.toList());

        //startPoint decide by runway
        Map<String, PointDto> startLevelPoints = levelPoints.get(pathLevels.stream().findFirst().get());
        PointDto startPoint = startLevelPoints
                .values().stream().filter(i -> i.getIndex().equals(startLevelPoints.values().size())).findFirst().get();
        boolean toNorth = true;
        //begin from 2
        Map<String, List<Path>> resultPathsMap = Maps.newHashMapWithExpectedSize(2);
        for (int index = 0; index < pathLevels.size(); index++) {
            String currentLevel = pathLevels.get(index);
            if (index == 0) {
                String behindLevel = pathLevels.get(index + 1);
                List<Path> crossCurrentBehindPaths = crossPathMap.get(currentLevel + behindLevel);
                List<PointDto> crossCurrentBehindPoints = Lists.newArrayList();
                crossCurrentBehindPaths.forEach(path -> {
                    crossCurrentBehindPoints.add(path.getPoint2());
                    crossCurrentBehindPoints.add(path.getPoint1());
                });

                List<PointDto> crossCurrentBehindDistinctPoints = crossCurrentBehindPoints.stream().distinct().collect(Collectors.toList());
                List<PointDto> crossBehindPointsWithSameStartLevel = crossCurrentBehindDistinctPoints.stream()
                        .filter(i -> i.getLevel().equals(startPoint.getLevel())).collect(Collectors.toList());
                PointDto crossMatchPoint = findWithMinimalDistancePoint(startPoint, crossBehindPointsWithSameStartLevel);
                List<Path> currentLevelPaths = generatePathsByPointWithSameLevel(startPoint, crossMatchPoint, levelToPaths, levelPoints);
                List<Path> currentUsedCrossPaths = crossCurrentBehindPaths.stream()
                        .filter(i -> i.getPoints().stream().map(PointDto::getName)
                                .collect(Collectors.toList()).contains(crossMatchPoint.getName()))
                        .collect(Collectors.toList());

                List<PointDto> behindLevelUsedPoints = currentUsedCrossPaths.stream().map(Path::getPoints).reduce((a, b) -> {
                    a.addAll(b);
                    return a;
                }).get().stream().filter(i->i.getLevel().equals(behindLevel)).collect(Collectors.toList());




                continue;
            }

            if (index == pathLevels.size() - 1) {
                continue;

            }

            String previousLevel = pathLevels.get(index - 1);
            String behindLevel = pathLevels.get(index + 1);

            List<Path> currentPreviousPaths = crossPathMap.get(currentLevel + previousLevel);
            List<PointDto> currentPreviousPoints = Lists.newArrayList();
            currentPreviousPaths.forEach(path -> {
                currentPreviousPoints.add(path.getPoint1());
                currentPreviousPoints.add(path.getPoint2());
            });
            List<PointDto> currentPreviousDistinct = currentPreviousPoints.stream().distinct().collect(Collectors.toList());

            List<Path> currentBehindPaths = crossPathMap.get(currentLevel + behindLevel);
            List<PointDto> currentBehindPoints = Lists.newArrayList();
            currentBehindPaths.forEach(path -> {
                currentBehindPoints.add(path.getPoint2());
                currentBehindPoints.add(path.getPoint1());
            });

            List<PointDto> currentBehindDistinct = currentBehindPoints.stream().distinct().collect(Collectors.toList());


        }


        System.out.println("hdd");
    }

    private static List<Path> generatePathsByPointWithSameLevel(PointDto beginPoint, PointDto endPoint,
                                                                Map<String, Map<String, Path>> levelToPaths,
                                                                Map<String, Map<String, PointDto>> levelToPoints) {
        if (!beginPoint.getLevel().equals(endPoint.getLevel())) {
            return Lists.newArrayListWithExpectedSize(0);
        }
        int beginIndex = beginPoint.getIndex();
        int endIndex = endPoint.getIndex();
        List<Path> paths = Lists.newArrayListWithCapacity(Math.abs(beginIndex - endIndex));
        String level = beginPoint.getLevel();
        Map<String, Path> pathMap = levelToPaths.get(level);
        Map<String, PointDto> pointMap = levelToPoints.get(level);
        if (beginIndex > endIndex) {
            for (int j = beginIndex; j > endIndex; j--) {
                PointDto pointDto1 = pointMap.get(level + "[" + j + "]");
                PointDto pointDto2 = pointMap.get(level + "[" + (j - 1) + "]");
                paths.add(pathMap.get(pointDto1.getName() + pointDto2.getName()));
            }
        } else {
            for (int j = beginIndex; j < endIndex; j++) {
                PointDto pointDto1 = pointMap.get(level + "[" + j + "]");
                PointDto pointDto2 = pointMap.get(level + "[" + (j + 1) + "]");
                paths.add(pathMap.get(pointDto1.getName() + pointDto2.getName()));
            }
        }
        return paths;
    }

    public static Map<String, Path> generatePath(Map<String, PointDto> pointDtoMap) {
        Map<String, Path> aPaths = pointDtoMap.values().stream().map(pointDto -> {
            if (pointDtoMap.size() < pointDto.getIndex() + 1) {
                return null;
            }
            return new Path(pointDto, pointDtoMap.get(pointDto.level + "[" + (pointDto.getIndex() + 1) + "]"));
        }).filter(Objects::nonNull).collect(Collectors.toMap(Path::getName, path -> path));

        Map<String, Path> aReversePath = aPaths.values().stream().collect(Collectors.toMap(Path::getReverseName, path -> path));
        aPaths.putAll(aReversePath);
        return aPaths;
    }

    public static PointDto findWithMinimalDistancePoint(PointDto targetPoint, List<PointDto> sourcePoints) {
        if (sourcePoints.isEmpty()) {
            return null;
        }
        sourcePoints = sourcePoints.stream().distinct().sorted().collect(Collectors.toList());
        PointDto pointDto1 = sourcePoints.stream().findFirst().get();
        PointDto pointDto2 = sourcePoints.get(sourcePoints.size() - 1);
        if (Math.abs(targetPoint.getIndex() - pointDto1.getIndex()) >
                Math.abs(targetPoint.getIndex() - pointDto2.getIndex())) {
            return pointDto2;
        }
        return pointDto1;
    }
}
