package com.twitter.elastic.kafka.twiter.to.kafka.service.runner;

import twitter4j.TwitterException;

public interface StreamRunner {
    void start() throws TwitterException;
}
