package com.alexsashkin.circle_manager;

import java.util.List;
import java.util.UUID;

public interface ICircleManager {

    void createCircle(UUID circleId, String name);

    List<CircleModel> getCircles();

    public CircleModel getCircle(UUID circleId);

    void removeCircle(UUID circleId);

    int getCircleCount(int countMemberInCircle, TypeCompareEnum typeCompare);

    void addMember(UUID circleId, MemberModel member);

    void removeMember(UUID circleId, UUID memberId);

    int getDuplicateCirclesCount();

    List<CircleModel> removeDuplicateCircle();
}
