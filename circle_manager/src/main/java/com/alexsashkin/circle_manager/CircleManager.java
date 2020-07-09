package com.alexsashkin.circle_manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    public void addMember(UUID circleId, MemberModel member) {

    }

    @Override
    public void removeMember(UUID circleId, UUID memberId) {

    }

    @Override
    public int getDuplicateCirclesCount() {
        return 0;
    }

    @Override
    public List<CircleModel> removeDuplicateCircle() {
        return null;
    }
}