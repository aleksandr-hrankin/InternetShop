package com.internet.shop.service.impl;

import com.internet.shop.dao.interfaces.UserDao;
import com.internet.shop.lib.Inject;
import com.internet.shop.lib.Service;
import com.internet.shop.model.User;
import com.internet.shop.service.interfaces.UserService;
import com.internet.shop.util.HashUtil;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Inject
    private UserDao userDao;

    @Override
    public User create(User user) {
        user.setSalt(HashUtil.getSalt());
        String hashPassword = HashUtil.hashPassword(user.getPassword(), user.getSalt());
        user.setPassword(hashPassword);
        userDao.create(user);
        return user;
    }

    @Override
    public User get(Long id) {
        return userDao.get(id).get();
    }

    @Override
    public List<User> getAll() {
        return userDao.getAll();
    }

    @Override
    public User update(User user) {
        return userDao.update(user);
    }

    @Override
    public boolean delete(Long id) {
        return userDao.delete(id);
    }

    @Override
    public Optional<User> findByLogin(String login) {
        return userDao.findByLogin(login);
    }
}
