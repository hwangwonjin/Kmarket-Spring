package kr.co.kmarket.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="km_product_cate2")
public class CateEntity {
    @Id
    private int cate1;
    private int cate2;
    private String c2Name;
}
