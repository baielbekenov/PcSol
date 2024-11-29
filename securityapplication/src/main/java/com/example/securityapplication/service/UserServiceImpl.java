

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void createUser(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(MD5Util.hashPassword(password));
        user.setIsBlocked(false);
        user.setIsDeleted(false);
        user.setBlockedDate(null);
        user.setDeletedDate(null);
        user.setPasswordExpired(null);
        user.setRoles(Set.of("USER"));
        userRepository.save(user); // Сохраняем в базе данных
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username); // Поиск по имени пользователя
    }

    @Override
    public void blockUser(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        userOptional.ifPresent(user -> {
            user.setIsBlocked(true);
            user.setBlockedDate(System.currentTimeMillis());
            userRepository.save(user); // Сохраняем блокировку
        });
    }

    @Override
    public void deleteUser(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        userOptional.ifPresent(user -> {
            user.setIsDeleted(true);
            user.setDeletedDate(System.currentTimeMillis());
            userRepository.save(user); // Помечаем как удаленного
        });
    }

    @Override
    public void restoreUser(String username) {
        Optional<User> userOptional = userRepository.findByUsername(username);
        userOptional.ifPresent(user -> {
            user.setIsDeleted(false);
            user.setDeletedDate(null);
            userRepository.save(user); // Восстанавливаем
        });
    }
}