package com.mlorenzo.springrestclientexamples.services;

import java.util.List;

import com.mlorenzo.springrestclientexamples.models.User;

public interface ApiService {
	List<User> getUsers(Integer limit);
}
