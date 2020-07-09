package com.alexsashkin.circle_manager;

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
    public List<CircleModel> getCircles() {
        return null;
    }

    @Override
    public CircleModel getCircle(UUID circleId) {
        return null;
    }

    @Override
    public void removeCircle(UUID circleId) {

    }

    @Override
    public int getCircleCount(int countMemberInCircle, TypeCompareEnum typeCompare) {
        return 0;
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