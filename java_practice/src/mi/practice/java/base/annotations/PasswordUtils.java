package mi.practice.java.base.annotations;

import java.util.List;

public class PasswordUtils {
	@UseCase(id = 47, description = "Passwords must contain at least one numerice")
	public boolean validatePassword(String password) {
		return (password.matches("\\w*\\d\\w*"));
	}

	@UseCase(id = 48)
	public String encryptPassword(String password) {
		// 反序
		return new StringBuilder(password).reverse().toString();
	}

	@UseCase(id = 49, description = "New password can't equal previously used ones")
	public boolean checkForNewPassword(List<String> prevPassowrds,
			String password) {
		return !prevPassowrds.contains(password);
	}
}
