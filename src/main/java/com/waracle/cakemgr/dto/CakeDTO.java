package com.waracle.cakemgr.dto;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
public class CakeDTO {
	
	@JsonIgnore
	@Schema(description = "Uniquq identifier of the cake")
	private Long id;
	
	@NotEmpty
	@Schema(description = "Title of the cake")
    private String title;

	@NotEmpty
	@Schema(description = "Description of the cake")
    private String description;

	@NotEmpty
	@Pattern(regexp = "[(http(s)?):\\/\\/(www\\.)?a-zA-Z0-9@:%._\\-\\+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*)")
	@Schema(description = "Image URL of the cake", defaultValue = "image url")
    private String imageUrl;
}
