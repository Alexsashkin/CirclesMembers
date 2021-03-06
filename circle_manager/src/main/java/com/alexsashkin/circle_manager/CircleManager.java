package com.alexsashkin.circle_manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class CircleManager implements ICircleManager {

    private Map<UUID, CircleModel> circleList;

    public CircleManager() {
        circleList = new HashMap<>();
    }

    @Override
    public synchronized void createCircle(UUID circleId, String name) {
        circleList.put(circleId, new CircleModel(circleId, name));
    }

    @Override
    public synchronized List<CircleModel> getCircles() {
        return new ArrayList<>(circleList.values());
    }

    @Override
    public synchronized CircleModel getCircle(UUID circleId) {
        return circleList.get(circleId);
    }

    @Override
    public synchronized void removeCircle(UUID circleId) {
        circleList.remove(circleId);
    }

    @Override
    public synchronized int getCircleCount(int countMemberInCircle, TypeCompareEnum typeCompare) {
        int countCircles = 0;

        for (Map.Entry<UUID, CircleModel> entry : circleList.entrySet()) {
            if (entry.getValue().checkCount(countMemberInCircle, typeCompare)) {
                countCircles++;
            }
        }

        return countCircles;
    }

    @Override
    public synchronized void addMember(UUID circleId, MemberModel member) {
        if (!circleList.containsKey(circleId)) {
            createCircle(circleId, circleId.toString());
        }
        circleList.get(circleId).addMember(member);
    }

    @Override
    public synchronized void removeMember(UUID circleId, UUID memberId) {
        int countMembers = circleList.get(circleId).removeMember(memberId);
        if (countMembers == 0) {
            circleList.remove(circleId);
        }
    }

    // TODO: 09.07.2020 Important!!!
    // Due to the fact that we use Map for circleList methods,
    // there will never be duplicates and
    // getDuplicateCirclesCount, removeDuplicateCircle methods are not needed

    @Override
    public synchronized int getDuplicateCirclesCount() {
        Set<Integer> setCircles = new HashSet<>(circleList.hashCode());
        return circleList.size() - setCircles.size();
    }

    @Override
    public synchronized List<CircleModel> removeDuplicateCircle() {
        return getCircles();
    }
}