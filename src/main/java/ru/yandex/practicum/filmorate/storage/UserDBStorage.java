package ru.yandex.practicum.filmorate.storage;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.FromTo;
import ru.yandex.practicum.filmorate.model.Model;
import ru.yandex.practicum.filmorate.model.User;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository("userDBStorage")
@AllArgsConstructor
public class UserDBStorage implements StorageUser {
    private JdbcTemplate jdbcTemplate;

    @Override
    public boolean isExist(int id) {
        return get(id) != null;
    }

    @Override
    public void update(Model model) {
        User user = (User) model;
        String sql = "UPDATE users SET name = ?, birth_date = ?, email = ?, login = ? WHERE user_id = ?";
        jdbcTemplate.update(sql, user.getName(), user.getBirthday(), user.getEmail(), user.getLogin(), user.getId());
    }

    @Override
    public Model save(Model model) {
        User user = (User) model;
        String sql = ("INSERT INTO users (name, birth_date, email, login) VALUES (?,?,?,?)");
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(con -> {
            PreparedStatement stmt = con.prepareStatement(sql, new String[]{"user_id"});
            stmt.setString(1, user.getName());
            stmt.setDate(2, Date.valueOf(user.getBirthday()));
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getLogin());
            return stmt;
        }, keyHolder);
        user.setId(keyHolder.getKey().intValue());
        return user;
    }

    @Override
    public User get(int id) {
        String sql = "SELECT * FROM users WHERE user_id = ?";
        User user = jdbcTemplate.queryForObject(sql, this::rowMapperUser, id);
        if (Objects.isNull(user)) {
            return null;
        }
        String sqlGetLikes = "SELECT user2_id FROM friend  WHERE user_id = ?";
        List<Integer> likes = new LinkedList<>(jdbcTemplate.query(sqlGetLikes, this::mapRowToInteger, id));

        user.setFriends(likes);
        return user;

    }

    private Integer mapRowToInteger(ResultSet rs, int rowNum) throws SQLException {
        return rs.getInt("user2_id");
    }

    private User rowMapperUser(ResultSet rs, int idRow) throws SQLException {
        return new User(rs.getInt("user_id"), rs.getString("name"),
                rs.getString("email"), rs.getString("login"),
                rs.getDate("birth_date").toLocalDate());
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM users WHERE user_id = ?";
        jdbcTemplate.update(sql, id);

    }

    @Override
    public Map<Integer, User> getModelMap() {
        String sql = "SELECT * FROM users";
        return jdbcTemplate.query(sql, this::rowMapperUser).stream().collect(Collectors.toMap(User::getId,
                Function.identity()));
    }

    @Override
    public void removeIdFromIdSet(FromTo user) {
        String sql = "DELETE FROM friend WHERE user_id = ? AND user2_id = ?";
        jdbcTemplate.update(sql, user.getFrom(), user.getTo());
    }

    @Override
    public <T extends Model> T addToSet(FromTo userFriend) {
        String sql = "INSERT INTO  friend (user_id, user2_id, IS_APPROVED) VALUES (?,?,?);";
        jdbcTemplate.update(sql, userFriend.getFrom(), userFriend.getTo(), true);
        return null;
    }

}
