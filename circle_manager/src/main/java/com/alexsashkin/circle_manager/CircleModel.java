package com.alexsashkin.circle_manager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

public class CircleModel implements Comparable<CircleModel>, Serializable {

    private UUID id;
    private String name;
    private Map<UUID, MemberModel> membersList;

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CircleModel(UUID id, String name) {
        this.id = id;
        this.name = name;
        membersList = new HashMap<>();
    }


    public MemberModel addMember(MemberModel member) {
        return membersList.put(member.getId(), member);
    }

    public int removeMember(UUID memberId) {
        membersList.remove(memberId);
        return membersList.size();
    }

    public MemberModel getMember(UUID memberId) {
        return membersList.get(memberId);
    }

    public List<MemberModel> getMembersList() {
        return new ArrayList(membersList.values());
    }

    public List<MemberModel> getMembersList(double latitude, double longitude, double distance,
                                            TypeCompareEnum typeCompare) {
        List<MemberModel> list = new ArrayList<>();

        for (Map.Entry<UUID, MemberModel> entry : membersList.entrySet()) {
            int compareResult = entry.getValue().checkDistance(latitude, longitude, distance);
            switch (typeCompare) {
                case LESS:
                    if (compareResult == -1) {
                        list.add(entry.getValue());
                    }
                    break;
                case EQUAL:
                    if (compareResult == 0) {
                        list.add(entry.getValue());
                    }
                    break;
                case GREATER:
                    if (compareResult == 1) {
                        list.add(entry.getValue());
                    }
                    break;
                case LESS_EQUAL:
                    if (compareResult <= 0) {
                        list.add(entry.getValue());
                    }
                    break;
                case GREATER_EQUAL:
                    if (compareResult >= 0) {
                        list.add(entry.getValue());
                    }
                    break;
                case ALL:
                default:
                    list.add(entry.getValue());
            }
        }

        return list;
    }

    public boolean checkCount(int countMember, TypeCompareEnum typeCompare) {
        switch (typeCompare) {
            case LESS:
                return countMember > membersList.size();
            case EQUAL:
                return countMember == membersList.size();
            case GREATER:
                return countMember < membersList.size();
            case LESS_EQUAL:
                return countMember >= membersList.size();
            case GREATER_EQUAL:
                return countMember <= membersList.size();
            case ALL:
            default:
                return true;
        }
    }


    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        return o != null && id.compareTo(((CircleModel) o).id) == 0;
    }

    @Override
    public int compareTo(CircleModel o) {
        return id.compareTo(o.id);
    }

    @Override
    public String toString() {
        return String.format(Locale.getDefault(),
                "UUID: %s, name: %s, members: %s",
                id.toString(),
                name,
                membersList != null ? membersList.toString() : "[]"
        );
    }
}
