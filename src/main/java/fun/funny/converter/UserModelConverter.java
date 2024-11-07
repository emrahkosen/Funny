package fun.funny.converter;

import fun.funny.dto.UserDTO;
import fun.funny.entity.Book;
import fun.funny.entity.User;
import fun.funny.entity.Role;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;
@Component
public class UserModelConverter implements ModelConverter<User, UserDTO> {
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserModelConverter(BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDTO entity2Dto(User user) {
        if (user == null) return null;

        UserDTO dto = new UserDTO();
        dto.setUserId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setEmail(user.getEmail());
        dto.setBirthDate(user.getBirthDate());
        dto.setNationality(user.getNationality());


        if(user.getRoles()!=null){
            dto.setRoles(user.getRoles().stream()
                    .map(Role::getName)
                    .collect(Collectors.toSet()));
        }

        // Map readBooks to a list of book IDs
        if(user.getReadBooks() != null){
            dto.setReadBookIds(user.getReadBooks().stream()
                    .map(Book::getId)
                    .collect(Collectors.toList()));
        }

        if(user.getFollowingBooks() != null){
            dto.setFollowingBookIds(user.getFollowingBooks().stream()
                    .map(Book::getId)
                    .collect(Collectors.toList()));
        }
        return dto;
    }

    @Override
    public User createDto2Entity(UserDTO dto) {
        if (dto == null) return null;

        User user = new User();
        user.setUsername(dto.getUsername());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setEmail(dto.getEmail());
        user.setBirthDate(dto.getBirthDate());
        user.setNationality(dto.getNationality());
        user.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));
        return user;
    }

    @Override
    public void updateDto2Entity(UserDTO dto, User entity) {
        if (dto == null || entity == null) return;

        entity.setUsername(dto.getUsername());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());
        entity.setBirthDate(dto.getBirthDate());
        entity.setNationality(dto.getNationality());
        entity.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));

        // Note: If roles or books need to be updated, this requires additional logic
        // as it may involve fetching or creating related entities.
    }
}
