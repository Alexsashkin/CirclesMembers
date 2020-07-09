package com.alexsashkin.circlesmembers;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alexsashkin.circle_manager.CircleManager;
import com.alexsashkin.circle_manager.CircleModel;
import com.alexsashkin.circle_manager.MemberModel;
import com.alexsashkin.circle_manager.TypeCompareEnum;
import com.alexsashkin.circlesmembers.databinding.ActivityMainBinding;

import java.util.Objects;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements CircleAdapterListener, MemberAdapterListener {

    private ActivityMainBinding layout;

    private CircleManager circleManager = new CircleManager();

    private CircleAdapter circleAdapter;
    private MemberAdapter memberAdapter;

    private CircleModel selectCircle;
    private MemberModel selectMember;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        layout = DataBindingUtil.setContentView(this, R.layout.activity_main);

        circleAdapter = new CircleAdapter(null, this);
        layout.circlesList.setAdapter(circleAdapter);
        layout.circlesList.setLayoutManager(new LinearLayoutManager(this));

        memberAdapter = new MemberAdapter(null, this);
        layout.membersList.setAdapter(memberAdapter);
        layout.membersList.setLayoutManager(new LinearLayoutManager(this));


        layout.btnAddCircle.setOnClickListener(v -> {
            String circleName = Objects.requireNonNull(layout.etCircleName.getText()).toString();

            if (TextUtils.isEmpty(circleName)) {
                return;
            }

            KeyboardUtils.hideKeyboard(v);

            circleManager.createCircle(UUID.randomUUID(), circleName);

            circleAdapter.setCirclesList(circleManager.getCircles());

            clearFields(layout.etCircleName);
        });

        layout.btnAddMember.setOnClickListener(v -> {
            String memberName = Objects.requireNonNull(layout.etMemberName.getText()).toString();

            if (TextUtils.isEmpty(memberName)) {
                return;
            }

            KeyboardUtils.hideKeyboard(v);

            MemberModel memberModel = new MemberModel(
                    UUID.randomUUID(),
                    memberName,
                    getDoubleValue(Objects.requireNonNull(layout.etMemberLatitude.getText()).toString()),
                    getDoubleValue(Objects.requireNonNull(layout.etMemberLongitude.getText()).toString())
            );

            UUID circleId = selectCircle == null ? UUID.randomUUID() : selectCircle.getId();
            circleManager.addMember(circleId, memberModel);

            circleAdapter.setCirclesList(circleManager.getCircles());

            memberAdapter.setMembersList(circleManager.getCircle(circleId).getMembersList());

            clearFields(layout.etMemberName, layout.etMemberLatitude, layout.etMemberLongitude);
        });

        layout.btnRemoveCircle.setOnClickListener(v -> {
            if (selectCircle != null) {
                circleManager.removeCircle(selectCircle.getId());
                circleAdapter.setCirclesList(circleManager.getCircles());
                memberAdapter.setMembersList(null);

                layout.btnRemoveCircle.setEnabled(false);
                layout.btnRemoveMember.setEnabled(false);
                layout.btnMemberDistanceFilter.setEnabled(false);
                clearFields(layout.selectCircleId, layout.selectCircleName, layout.selectMemberId, layout.selectMemberName);
            }

            KeyboardUtils.hideKeyboard(v);
        });

        layout.btnRemoveMember.setOnClickListener(v -> {
            if (selectCircle != null && selectMember != null) {
                circleManager.removeMember(selectCircle.getId(), selectMember.getId());
                circleAdapter.setCirclesList(circleManager.getCircles());
                CircleModel circle = circleManager.getCircle(selectCircle.getId());
                memberAdapter.setMembersList(circle == null ? null : circle.getMembersList());

                if (circle == null) {
                    layout.btnRemoveCircle.setEnabled(false);
                    layout.btnMemberDistanceFilter.setEnabled(false);
                    clearFields(layout.selectCircleId, layout.selectCircleName);
                }
                layout.btnRemoveMember.setEnabled(false);
                clearFields(layout.selectMemberId, layout.selectMemberName);
            }

            KeyboardUtils.hideKeyboard(v);
        });

        layout.btnMemberDistanceFilter.setOnClickListener(v -> {
            String latitude = Objects.requireNonNull(layout.etLatitude.getText()).toString();
            String longitude = Objects.requireNonNull(layout.etLongitude.getText()).toString();
            String distance = Objects.requireNonNull(layout.etDistance.getText()).toString();

            if (TextUtils.isEmpty(latitude) || TextUtils.isEmpty(longitude) || TextUtils.isEmpty(distance)) {
                return;
            }
            KeyboardUtils.hideKeyboard(v);

            memberAdapter.setMembersList(
                    circleManager.getCircle(selectCircle.getId())
                            .getMembersList(getDoubleValue(latitude), getDoubleValue(longitude),
                                    getDoubleValue(distance), TypeCompareEnum.LESS));

            clearFields(layout.etLatitude, layout.etLongitude, layout.etDistance);
        });
    }

    private Double getDoubleValue(String value) {
        return TextUtils.isEmpty(value) ? 0.0 : Double.valueOf(value);
    }

    private void clearFields(AppCompatEditText... views) {
        for (AppCompatEditText view : views) {
            view.setText("");
        }
    }

    private void clearFields(TextView... views) {
        for (TextView view : views) {
            view.setText("");
        }
    }

    @Override
    public void onSelectCircle(CircleModel circleModel) {
        KeyboardUtils.hideKeyboard(this);

        if (circleModel.equals(selectCircle)) {
            selectCircle = null;
            selectMember = null;
            layout.btnRemoveCircle.setEnabled(false);
            layout.btnRemoveMember.setEnabled(false);
            layout.btnMemberDistanceFilter.setEnabled(false);
            clearFields(layout.selectCircleId, layout.selectCircleName, layout.selectMemberId, layout.selectMemberName);
            memberAdapter.setMembersList(null);
            return;
        }

        layout.btnRemoveCircle.setEnabled(true);
        layout.btnMemberDistanceFilter.setEnabled(true);
        selectCircle = circleModel;
        layout.selectCircleId.setText(String.format(getString(R.string.format_id), selectCircle.getId()));
        layout.selectCircleName.setText(selectCircle.getName());
        memberAdapter.setMembersList(circleModel.getMembersList());
    }

    @Override
    public void onSelectMember(MemberModel memberModel) {
        KeyboardUtils.hideKeyboard(this);

        if (memberModel.equals(selectMember)) {
            selectMember = null;
            layout.btnRemoveMember.setEnabled(false);
            clearFields(layout.selectMemberId, layout.selectMemberName);
            return;
        }
        layout.btnRemoveMember.setEnabled(true);
        selectMember = memberModel;
        layout.selectMemberId.setText(String.format(getString(R.string.format_id), selectMember.getId()));
        layout.selectMemberName.setText(selectMember.getName());
    }
}