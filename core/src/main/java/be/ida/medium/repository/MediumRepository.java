package be.ida.medium.repository;

import be.ida.medium.bean.MediumPost;
import be.ida.medium.bean.MediumPublication;

public interface MediumRepository {
    void storeMediumPublication(MediumPublication mediumPublication);
}
