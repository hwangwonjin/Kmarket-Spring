package kr.co.kmarket.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="km_member_point")
public class MyPointEntity {

    @Id
    @Column(name="pointNo")
    private int pointNo;
    private String uid;

    @Column(name="ordNo")
    private String ordNo;
    private int point;

    @Column(name="pointDate")
    private String pointDate;
    private String pointdes;

    @Column(name="expireDate")
    private String expireDate;
}
