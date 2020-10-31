package com.qdcares.avms.inspection.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
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
    public static class Segment implements Comparable<Segment> {
        String name;
        String reverseName;
        PointDto point1;
        PointDto point2;

        public Segment(PointDto point1, PointDto point2) {
            this.point1 = point1;
            this.point2 = point2;
            this.name = point1.getName() + point2.getName();
            this.reverseName = point2.getName() + point1.getName();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Segment segment = (Segment) o;
            return Objects.equals(point1, segment.point1) &&
                    Objects.equals(point2, segment.point2);
        }

        @Override
        public int compareTo(Segment o) {
            int scoreO = o.getPoint1().getIndex() + o.getPoint2().getIndex();
            int score = this.getPoint1().getIndex() + this.getPoint2().getIndex();
            return Integer.compare(score, scoreO);
        }

        PointDto getPoint(String level) {
            return getPoints().stream().filter(i -> i.getLevel().equals(level)).findFirst().orElse(null);
        }

        List<PointDto> getPoints() {
            return Lists.newArrayList(point1, point2);
        }
    }

    @Getter
    @Setter
    public static class PointDto implements Comparable<PointDto> {
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

        Map<String, Segment> aPaths = generatePath(aPoints);
        Map<String, Segment> bPaths = generatePath(bPoints);
        Map<String, Segment> cPaths = generatePath(cPoints);
        Map<String, Segment> APaths = generatePath(APoints);
        Map<String, Segment> BPaths = generatePath(BPoints);
        Map<String, Segment> CPaths = generatePath(CPoints);

        Map<String, Map<String, Segment>> levelToPaths = Maps.newHashMapWithExpectedSize(6);
        levelToPaths.put("a", aPaths);
        levelToPaths.put("b", bPaths);
        levelToPaths.put("c", cPaths);
        levelToPaths.put("A", APaths);
        levelToPaths.put("B", BPaths);
        levelToPaths.put("C", CPaths);

        List<Segment> pathaC = Lists.newArrayList(new Segment(CPoints.get("C[1]"), aPoints.get("a[9]")),
                new Segment(CPoints.get("C[1]"), aPoints.get("a[7]")),
                new Segment(CPoints.get("C[3]"), aPoints.get("a[9]")),
                new Segment(CPoints.get("C[3]"), aPoints.get("a[7]")));

        List<Segment> pathaB = Lists.newArrayList(new Segment(BPoints.get("B[1]"), aPoints.get("a[6]")),
                new Segment(BPoints.get("B[1]"), aPoints.get("a[4]")),
                new Segment(BPoints.get("B[3]"), aPoints.get("a[6]")),
                new Segment(BPoints.get("B[3]"), aPoints.get("a[4]")));
        List<Segment> pathaA = Lists.newArrayList(new Segment(APoints.get("A[1]"), aPoints.get("a[3]")),
                new Segment(APoints.get("A[1]"), aPoints.get("a[1]")),
                new Segment(APoints.get("A[3]"), aPoints.get("a[3]")),
                new Segment(APoints.get("A[3]"), aPoints.get("a[1]")));

        List<Segment> pathbC = Lists.newArrayList(new Segment(CPoints.get("C[4]"), bPoints.get("b[9]")),
                new Segment(CPoints.get("C[4]"), bPoints.get("b[7]")),
                new Segment(CPoints.get("C[6]"), bPoints.get("b[9]")),
                new Segment(CPoints.get("C[6]"), bPoints.get("b[7]")));
        List<Segment> pathbB = Lists.newArrayList(new Segment(BPoints.get("B[4]"), bPoints.get("b[6]")),
                new Segment(BPoints.get("B[4]"), bPoints.get("b[4]")),
                new Segment(BPoints.get("B[6]"), bPoints.get("b[6]")),
                new Segment(BPoints.get("B[6]"), bPoints.get("b[4]")));
        List<Segment> pathbA = Lists.newArrayList(new Segment(APoints.get("A[4]"), bPoints.get("b[3]")),
                new Segment(APoints.get("A[4]"), bPoints.get("b[1]")),
                new Segment(APoints.get("A[6]"), bPoints.get("b[3]")),
                new Segment(APoints.get("A[6]"), bPoints.get("b[1]")));

        List<Segment> pathcC = Lists.newArrayList(new Segment(CPoints.get("C[7]"), cPoints.get("c[9]")),
                new Segment(CPoints.get("C[7]"), cPoints.get("c[7]")),
                new Segment(CPoints.get("C[9]"), cPoints.get("c[9]")),
                new Segment(CPoints.get("C[9]"), cPoints.get("c[7]")));
        List<Segment> pathcB = Lists.newArrayList(new Segment(BPoints.get("B[7]"), cPoints.get("c[6]")),
                new Segment(BPoints.get("B[7]"), cPoints.get("c[4]")),
                new Segment(BPoints.get("B[9]"), cPoints.get("c[6]")),
                new Segment(BPoints.get("B[9]"), cPoints.get("c[4]")));
        List<Segment> pathcA = Lists.newArrayList(new Segment(APoints.get("A[7]"), cPoints.get("c[3]")),
                new Segment(APoints.get("A[7]"), cPoints.get("c[1]")),
                new Segment(APoints.get("A[9]"), cPoints.get("c[3]")),
                new Segment(APoints.get("A[9]"), cPoints.get("c[1]")));

        Map<String, List<Segment>> crossPathMap = Maps.newHashMapWithExpectedSize(9);
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
//        List<String> pathParams = Lists.newArrayList("F2", "F", "F4");
        List<String> pathParams = Lists.newArrayList("F2", "E", "F4");
        List<String> pathLevels = pathParams.stream().map(codeToLevel::get).collect(Collectors.toList());

        //startPoint decide by runway
        String startLevel = pathLevels.stream().findFirst().get();
        Map<String, PointDto> startLevelPoints = levelPoints.get(startLevel);
        PointDto startPoint = startLevelPoints
                .values().stream().filter(i -> i.getIndex().equals(startLevelPoints.values().size())).findFirst().get();

        Map<PointDto, List<Segment>> resultPathsMap = Maps.newHashMapWithExpectedSize(2);
        List<Segment> startSegments =
                generatePathsByPointWithSameLevel(startPoint,
                        startLevelPoints.get(startLevel + "[" + (startLevelPoints.size() - startPoint.getIndex() + 1) + "]"),
                        levelToPaths, levelPoints).stream().distinct().collect(Collectors.toList());
        resultPathsMap.put(startPoint, startSegments);
        for (int index = 0; index < pathLevels.size() - 1; index++) {
            String currentLevel = pathLevels.get(index);
            String behindLevel = pathLevels.get(index + 1);

            List<Segment> currentBehindCrossedArcSegments = crossPathMap.get(currentLevel + behindLevel);
            List<PointDto> currentBehindCrossedPoints = currentBehindCrossedArcSegments.stream()
                    .map(Segment::getPoints).flatMap(List::stream)
                    .distinct().collect(Collectors.toList());

            List<PointDto> crossOnCurrentLevelPoints = currentBehindCrossedPoints.stream()
                    .filter(i -> i.getLevel().equals(currentLevel)).collect(Collectors.toList());

            PointDto crossOnCurrentLevelMatchStartPoint =
                    findPointWithMinimalDistanceToTargetPoints(Lists.newArrayList(resultPathsMap.keySet()),
                            crossOnCurrentLevelPoints);
            PointDto crossOnCurrentLevelMatchEndPoint =
                    findPointWithMinimalDistanceToTargetPoints(crossOnCurrentLevelPoints,
                            Lists.newArrayList(crossOnCurrentLevelMatchStartPoint));

            List<Segment> currentLevelUsedSegments =
                    generatePathsByPointWithSameLevel(crossOnCurrentLevelMatchStartPoint,
                            crossOnCurrentLevelMatchEndPoint, levelToPaths, levelPoints);
            List<Segment> currentBehindCrossedUsedArcSegments = currentBehindCrossedArcSegments.stream()
                    .filter(i -> i.getPoints().stream().map(PointDto::getName)
                            .collect(Collectors.toList()).contains(crossOnCurrentLevelMatchEndPoint.getName()))
                    .collect(Collectors.toList());

            if (currentBehindCrossedUsedArcSegments.isEmpty()) {
                throw new RuntimeException("no cross find, level path is " + currentLevel + "->" + behindLevel + " , code path is " +
                        levelToCode.get(currentLevel) + "->" + levelToCode.get(behindLevel));
            }

            List<PointDto> behindLevelUsedPoints = currentBehindCrossedUsedArcSegments.stream().map(Segment::getPoints)
                    .flatMap(List::stream).filter(i -> i.getLevel().equals(behindLevel)).collect(Collectors.toList());

            Map<PointDto, List<Segment>> behindLevelUsedSegmentsMap =
                    generateBehindUsedPaths(behindLevelUsedPoints, levelPoints, levelToPaths);

            List<Segment> previousLevelsSegments =
                    resultPathsMap.entrySet().stream()
                            .filter(entry -> entry.getKey().getName().equals(crossOnCurrentLevelMatchStartPoint.getName()))
                            .findFirst().get().getValue();

            Segment segmentFollowUsedArcSegment = previousLevelsSegments.stream().filter(i ->
                    i.getPoints().stream().anyMatch(j -> j.getName().equals(crossOnCurrentLevelMatchStartPoint.getName())) &&
                            i.getPoint1().getLevel().equals(i.getPoint2().getLevel())).findFirst().get();
            int segmentFollowUsedArcSegmentIndex = previousLevelsSegments.indexOf(segmentFollowUsedArcSegment);
            List<Segment> previousLevelsUsedSegments = previousLevelsSegments.subList(0, segmentFollowUsedArcSegmentIndex);
            List<Segment> previousAndCurrentLevelsSegments = Lists.newArrayListWithCapacity(
                    previousLevelsSegments.size() + currentLevelUsedSegments.size());
            previousAndCurrentLevelsSegments.addAll(previousLevelsUsedSegments);
            previousAndCurrentLevelsSegments.addAll(currentLevelUsedSegments);

            resultPathsMap.clear();
            behindLevelUsedSegmentsMap.forEach((key, value) -> {
                List<Segment> result = Lists.newArrayList(previousAndCurrentLevelsSegments.toArray(new Segment[0]));
                Segment crossArcSegment = currentBehindCrossedUsedArcSegments.stream().filter(i ->
                        i.getPoints().stream().map(PointDto::getName).anyMatch(j -> j.equals(key.getName()))).findFirst().get();
                result.add(crossArcSegment);
                result.addAll(value);
                resultPathsMap.put(key, result);

            });
        }

        resultPathsMap.forEach((key, value) -> {
            System.out.println(key.getName() + ":" + value.stream().map(Segment::getName).collect(Collectors.joining("->")));
        });
    }

    private static Map<PointDto, List<Segment>> generateBehindUsedPaths(List<PointDto> behindLevelUsedPoints,
                                                                        Map<String, Map<String, PointDto>> levelPoints,
                                                                        Map<String, Map<String, Segment>> levelToPaths) {

        if (behindLevelUsedPoints == null || behindLevelUsedPoints.isEmpty()) {
            return null;
        }
        PointDto pointDto = behindLevelUsedPoints.stream().findFirst().get();
        String level = pointDto.getLevel();
        Map<String, PointDto> pointDtoMap = levelPoints.get(level);
        Map<PointDto, List<Segment>> resultPathMap = Maps.newHashMapWithExpectedSize(2);

        if (behindLevelUsedPoints.size() == 1) {
            if (pointDto.index - 1 > 0) {
                PointDto end = pointDtoMap.get(level + "[" + 1 + "]");
                List<Segment> segments = generatePathsByPointWithSameLevel(pointDto, end, levelToPaths, levelPoints);
                resultPathMap.put(pointDto, segments);
            } else {
                PointDto end = pointDtoMap.get(level + "[" + pointDtoMap.size() + "]");
                List<Segment> segments = generatePathsByPointWithSameLevel(pointDto, end, levelToPaths, levelPoints);
                resultPathMap.put(pointDto, segments);
            }
        }
        if (behindLevelUsedPoints.size() == 2) {
            PointDto pointDto1 = behindLevelUsedPoints.stream().findFirst().get();
            PointDto pointDto2 = behindLevelUsedPoints.get(behindLevelUsedPoints.size() - 1);
            PointDto minPoint = pointDto1.getIndex() <= pointDto2.getIndex() ? pointDto1 : pointDto2;
            PointDto maxPoint = pointDto1.getIndex() > pointDto2.getIndex() ? pointDto1 : pointDto2;
            PointDto firstPoint = pointDtoMap.get(level + "[" + 1 + "]");
            PointDto endPoint = pointDtoMap.get(level + "[" + pointDtoMap.size() + "]");
            List<Segment> minSegments = generatePathsByPointWithSameLevel(minPoint, firstPoint, levelToPaths, levelPoints);
            List<Segment> maxSegments = generatePathsByPointWithSameLevel(maxPoint, endPoint, levelToPaths, levelPoints);
            if (!minSegments.isEmpty()) {
                resultPathMap.put(minPoint, minSegments);
            }
            if (!maxSegments.isEmpty()) {
                resultPathMap.put(maxPoint, maxSegments);
            }
        }
        return resultPathMap;
    }

    private static List<Segment> generatePathsByPointWithSameLevel(PointDto beginPoint, PointDto endPoint,
                                                                   Map<String, Map<String, Segment>> levelToPaths,
                                                                   Map<String, Map<String, PointDto>> levelToPoints) {
        if (!beginPoint.getLevel().equals(endPoint.getLevel())) {
            return Lists.newArrayListWithExpectedSize(0);
        }
        int beginIndex = beginPoint.getIndex();
        int endIndex = endPoint.getIndex();
        List<Segment> segments = Lists.newArrayListWithCapacity(Math.abs(beginIndex - endIndex));
        String level = beginPoint.getLevel();
        Map<String, Segment> pathMap = levelToPaths.get(level);
        Map<String, PointDto> pointMap = levelToPoints.get(level);

        if (beginIndex > endIndex) {
            for (int j = beginIndex; j > endIndex; j--) {
                PointDto pointDto1 = pointMap.get(level + "[" + j + "]");
                PointDto pointDto2 = pointMap.get(level + "[" + (j - 1) + "]");
                segments.add(pathMap.get(pointDto1.getName() + pointDto2.getName()));
            }
        } else {
            for (int j = beginIndex; j < endIndex; j++) {
                PointDto pointDto1 = pointMap.get(level + "[" + j + "]");
                PointDto pointDto2 = pointMap.get(level + "[" + (j + 1) + "]");
                segments.add(pathMap.get(pointDto1.getName() + pointDto2.getName()));
            }
        }

        return segments;
    }

    public static Map<String, Segment> generatePath(Map<String, PointDto> pointDtoMap) {
        Map<String, Segment> aPaths = pointDtoMap.values().stream().map(pointDto -> {
            if (pointDtoMap.size() < pointDto.getIndex() + 1) {
                return null;
            }
            return new Segment(pointDto, pointDtoMap.get(pointDto.level + "[" + (pointDto.getIndex() + 1) + "]"));
        }).filter(Objects::nonNull).collect(Collectors.toMap(Segment::getName, segment -> segment));

        Map<String, Segment> aReversePath = aPaths.values().stream().collect(Collectors.toMap(Segment::getReverseName, segment -> segment));
        aPaths.putAll(aReversePath);
        return aPaths;
    }

    public static PointDto findPointWithMinimalDistanceToTargetPoints(List<PointDto> sourcePoints, List<PointDto> targetPoints) {
        if (sourcePoints.isEmpty() || targetPoints.isEmpty()) {
            throw new RuntimeException("source points & target points can not be empty");
        }
        int flag = (int) Lists.<List<PointDto>>newArrayList(sourcePoints, targetPoints).stream()
                .flatMap(List::stream).map(PointDto::getLevel).distinct().count();
        if (flag > 1) {
            throw new RuntimeException("points are not the same level, can not compute");
        }
        sourcePoints = sourcePoints.stream().sorted().distinct().collect(Collectors.toList());
        targetPoints = targetPoints.stream().sorted().distinct().collect(Collectors.toList());
        if (sourcePoints.stream().findFirst().get().getIndex() <
                targetPoints.get(targetPoints.size() - 1).getIndex() &&
                sourcePoints.get(sourcePoints.size() - 1).getIndex() >
                        targetPoints.stream().findFirst().get().getIndex()) {
            throw new RuntimeException("source point and targetPoints has intersection set, can not compute");
        }

        Integer meanPointPosition = targetPoints.stream().map(PointDto::getIndex).reduce(Integer::sum).get() / targetPoints.size();
        Map<Integer, PointDto> distanceToMap =
                sourcePoints.stream().collect(Collectors.toMap(point -> Math.abs(point.getIndex() - meanPointPosition), point -> point));
        Integer minDistance = distanceToMap.keySet().stream().sorted().findFirst().get();
        return distanceToMap.entrySet().stream().filter(entry -> entry.getKey().equals(minDistance)).findFirst().get().getValue();
    }
}
