package com.chen.shixj.entity;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * User: jpm
 * Date: 13-8-21
 * Time: 上午8:53
 * 碰到many为末端的加载就是延迟加载，若one为末端则为立即加载，除了one-to-one。
 */
@Entity
@Table(name = "tbl_product")
public class Product extends IdEntity {

    private Nav nav;
//    private Long navId;
    private String productName;
    private String tmallLink;
    private float productPrice;
    private String details;
    private String productCreateDate;
    private Set<ProductImage> productImages;

    @ManyToOne
    @JoinColumn(name = "nav_id")
    public Nav getNav() {
        return nav;
    }

    public void setNav(Nav nav) {
        this.nav = nav;
    }

//    public Long getNavId() {
//        return navId;
//    }
//
//    public void setNavId(Long navId) {
//        this.navId = navId;
//    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getTmallLink() {
        return tmallLink;
    }

    public void setTmallLink(String tmallLink) {
        this.tmallLink = tmallLink;
    }

    public float getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(float productPrice) {
        this.productPrice = productPrice;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getProductCreateDate() {
        return productCreateDate;
    }

    public void setProductCreateDate(String productCreateDate) {
        this.productCreateDate = productCreateDate;
    }

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    public Set<ProductImage> getProductImages() {
        return productImages;
    }

    public void setProductImages(Set<ProductImage> productImages) {
        this.productImages = productImages;
    }

}
