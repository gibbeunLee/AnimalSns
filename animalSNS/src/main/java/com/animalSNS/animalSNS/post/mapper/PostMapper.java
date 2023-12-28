package com.animalSNS.animalSNS.post.mapper;
import com.animalSNS.animalSNS.post.dto.PostDto;
import com.animalSNS.animalSNS.post.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PostMapper {
    Post postPostDtoToPost(PostDto.Post postPostDto);
    Post postPatchDtoToPost(PostDto.Patch patchPostDto);
    List<PostDto.Response> postToPostResponseDto(List<Post> posts);
}
