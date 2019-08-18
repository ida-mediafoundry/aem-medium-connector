package be.ida.jetpack.medium.repository;

import be.ida.jetpack.medium.model.MediumPost;
import be.ida.jetpack.medium.model.MediumPublication;

public interface MediumPostRepository {
    MediumPost getMediumPostByPath(String mediumPostPath);
}
