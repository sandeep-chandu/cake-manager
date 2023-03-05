package com.waracle.cakemgr.domain;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
//@Table(name = "Cake", uniqueConstraints = {@UniqueConstraint(columnNames = "id")})
public class Cake implements Serializable{

    private static final long serialVersionUID = -1798070786993154676L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    @Schema(description = "Unique identifier of the cake, it is auto generated by the system and not a mandatory field during create operation.")
    private Long id;

    @NotEmpty
    @Column(name = "title", unique = false, length = 100)
    @Schema(description = "Title of the cake")
    private String title;

    @NotEmpty
    @Column(name = "description", unique = false, length = 100)
    @Schema(description = "description of the cake")
    private String description;

    @NotEmpty
    @Pattern(regexp = "[(http(s)?):\\/\\/(www\\.)?a-zA-Z0-9@:%._\\-\\+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*)")
    @Column(name = "image_url", unique = false, length = 300)
    @Schema(description = "URL of the cake image", example = "http://www.abc.com/img.jpg")
    private String imageUrl;
}