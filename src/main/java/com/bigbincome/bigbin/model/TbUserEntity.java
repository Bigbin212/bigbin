package com.bigbincome.bigbin.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;

@Data
@Entity
@Table(name = "tb_user")
public class TbUserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String extUserId;
    private String name;
    private Byte sex;
    private String code;
    private String phone;
    private Date birth;
    private String photo;
    private String identity;
    private String nativePlace;
    private String departmentId;
    private String region;
    private String radioNo;
    private Integer titleId;
    private Date workingTime;
    private String policeRank;
    private String graduatedFrom;
    private String educationalBackground;
    private String secondmentInd;
    private String ryxh;
    private boolean deleted;
    private String createdBy;
    private Timestamp createdTime;
    private String updatedBy;
    private Timestamp updatedTime;
    private Integer version;
    private String policeAttribute;
    private String policeCategory;
    private String cuPhone;
    private String cuVirtualPhone;
    private String cmPhone;
    private String cmVirtualPhone;
    private String officePhone;
    private String homePhone;
    private String leaveReason;
    private Boolean hasDefaultRole;
    private Boolean hasEnabled;
    private Boolean hasDepartment;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TbUserEntity that = (TbUserEntity) o;
        return id == that.id &&
                deleted == that.deleted &&
                Objects.equals(extUserId, that.extUserId) &&
                Objects.equals(name, that.name) &&
                Objects.equals(sex, that.sex) &&
                Objects.equals(code, that.code) &&
                Objects.equals(phone, that.phone) &&
                Objects.equals(birth, that.birth) &&
                Objects.equals(photo, that.photo) &&
                Objects.equals(identity, that.identity) &&
                Objects.equals(nativePlace, that.nativePlace) &&
                Objects.equals(departmentId, that.departmentId) &&
                Objects.equals(region, that.region) &&
                Objects.equals(radioNo, that.radioNo) &&
                Objects.equals(titleId, that.titleId) &&
                Objects.equals(workingTime, that.workingTime) &&
                Objects.equals(policeRank, that.policeRank) &&
                Objects.equals(graduatedFrom, that.graduatedFrom) &&
                Objects.equals(educationalBackground, that.educationalBackground) &&
                Objects.equals(secondmentInd, that.secondmentInd) &&
                Objects.equals(ryxh, that.ryxh) &&
                Objects.equals(createdBy, that.createdBy) &&
                Objects.equals(createdTime, that.createdTime) &&
                Objects.equals(updatedBy, that.updatedBy) &&
                Objects.equals(updatedTime, that.updatedTime) &&
                Objects.equals(version, that.version) &&
                Objects.equals(policeAttribute, that.policeAttribute) &&
                Objects.equals(policeCategory, that.policeCategory) &&
                Objects.equals(cuPhone, that.cuPhone) &&
                Objects.equals(cuVirtualPhone, that.cuVirtualPhone) &&
                Objects.equals(cmPhone, that.cmPhone) &&
                Objects.equals(cmVirtualPhone, that.cmVirtualPhone) &&
                Objects.equals(officePhone, that.officePhone) &&
                Objects.equals(homePhone, that.homePhone) &&
                Objects.equals(leaveReason, that.leaveReason) &&
                Objects.equals(hasDefaultRole, that.hasDefaultRole) &&
                Objects.equals(hasEnabled, that.hasEnabled) &&
                Objects.equals(hasDepartment, that.hasDepartment);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, extUserId, name, sex, code, phone, birth, photo, identity, nativePlace, departmentId, region, radioNo, titleId, workingTime, policeRank, graduatedFrom, educationalBackground, secondmentInd, ryxh, deleted, createdBy, createdTime, updatedBy, updatedTime, version, policeAttribute, policeCategory, cuPhone, cuVirtualPhone, cmPhone, cmVirtualPhone, officePhone, homePhone, leaveReason, hasDefaultRole, hasEnabled, hasDepartment);
    }
}
