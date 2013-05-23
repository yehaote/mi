package mi.practice.java.base.annotations.database;

@DBTable(name = "MEMBER")
public class Member {
	@SQLString(30)
	String firstName;
	@SQLString(50)
	String lastName;
	@SQLInteger
	Integer age;
	@SQLString(value = 30, constraints = @Constraints(primayKey = true))
	String handle;
	static int memberCount;

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public Integer getAge() {
		return age;
	}

	public String getHandle() {
		return handle;
	}

	@Override
	public String toString() {
		return handle;
	}
}
