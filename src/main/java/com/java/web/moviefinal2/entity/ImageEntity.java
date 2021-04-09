package com.java.web.moviefinal2.entity;

import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "images3")
@Data
public class ImageEntity extends BaseEntity {

    @Column(name = "keyimage",length = 1024)
    private String keyImage;

    @Column(name = "objecturl",length = 1024)
    private String ObjectUrl;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "image",cascade = CascadeType.ALL)
    private List<AppUser> user = new ArrayList<>();

}
