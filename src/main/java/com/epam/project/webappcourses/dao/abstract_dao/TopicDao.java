package com.epam.project.webappcourses.dao.abstract_dao;

import com.epam.project.webappcourses.entities.Topic;

import java.util.List;

public interface TopicDao {

    void createTopic(Topic theme);

    List<Topic> findAllTopics();

    void deleteTopic(int topicId);
}
